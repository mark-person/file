package com.ppx.cloud.file.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.ppx.cloud.common.adapt.mongo.MongoUriItem;
import com.ppx.cloud.common.config.CommonConfig;

/**
 * 第一次配置生效使用grant的mongodb
 * @author mark
 * @date 2018年6月21日
 */
@Configuration
public class FirstConfig extends CommonConfig {
	
	@Value("${spring.data.mongodb.grant.uri}")
	private String mongodbGrantUri;
	
	@Value("${server.port}")
    private String serverPort;
	
	private final static String COL_CONFIG = "config";
	
	@Override
	public void setServerPort() {
	    FirstConfig.SERVER_PORT = serverPort;
	}
	
	@Bean(name="grantMongoTemplate")
	public MongoTemplate grantMongoTemplate() {
        MongoUriItem item = MongoUriItem.getMongoUriItem(mongodbGrantUri);
        MongoTemplate mongoTemplate = getNoAuthMongoTemplate(item);
        try {
            mongoTemplate.collectionExists("test"); 
        } catch (Throwable e) {
            String msg = e.getMessage();
            if (msg.indexOf("not authorized") >= 0) {
                // 没有使用密码连接则使用认证方式 
                mongoTemplate = getAuthMongoTemplate(item);
            }
            else {
                e.printStackTrace();
            }
        }
		return mongoTemplate;
	}
	
	private static MongoTemplate getNoAuthMongoTemplate(MongoUriItem item) {
        String mongoClientURI = "mongodb://" + item.getHost() + ":" + item.getPort() + "/" + item.getDb();
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(new MongoClientURI(mongoClientURI));
        // 去掉_class字段
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(simpleMongoDbFactory),
                new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(simpleMongoDbFactory, converter);
        return mongoTemplate;
    }
    
    private static MongoTemplate getAuthMongoTemplate(MongoUriItem item) {
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        addrs.add(new ServerAddress(item.getHost(), Integer.parseInt(item.getPort())));
        MongoCredential credential = MongoCredential.createScramSha1Credential(item.getUserName(), "admin", item.getPassword().toCharArray());
        MongoClientOptions options = MongoClientOptions.builder().writeConcern(WriteConcern.JOURNALED).build();
        MongoClient mongoClient = new MongoClient(addrs, credential, options);
        
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient, item.getDb());
        // 去掉_class字段
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(simpleMongoDbFactory),
                new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(simpleMongoDbFactory, converter);
        return mongoTemplate;
    }
	
    private void runFirstConf() {
        MongoTemplate mongoTemplate = grantMongoTemplate();

        // 加载first.properties配置文件
        Properties properties = new Properties();
        try (InputStream in = this.getClass().getResourceAsStream("/first.properties");) {
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> firstEnum = properties.stringPropertyNames();
        Map<String, String> firstMap = new LinkedHashMap<String, String>();
        for (String key : firstEnum) {
            firstMap.put(key, properties.getProperty(key));
        }

        // 不存在就插入，存在就跳过
        List<Map<String, Object>> insertList = new ArrayList<Map<String, Object>>();
        @SuppressWarnings("rawtypes")
        List<Map> listConf = mongoTemplate.findAll(Map.class, COL_CONFIG);
        
        Set<String> confKeySet = new HashSet<String>();
        listConf.forEach(map -> {
            confKeySet.add((String)map.get("_id"));
            System.setProperty((String)map.get("_id"), (String)map.get("value"));
        }); 
        
        firstMap.forEach((key, value) -> {
            if (!confKeySet.contains(key)) {
                Map<String, Object> insertMap = new LinkedHashMap<String, Object>();
                insertMap.put("_id", key);
                insertMap.put("value", value);
                insertMap.put("created", new Date());
                insertList.add(insertMap);
                System.setProperty(key, value);
            }
        });

        mongoTemplate.insert(insertList, COL_CONFIG);
    }
	
    @Bean
    public DataSource dataSource() {
        runFirstConf();
        return super.dataSource();
    }


}