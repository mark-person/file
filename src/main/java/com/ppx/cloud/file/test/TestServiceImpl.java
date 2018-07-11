package com.ppx.cloud.file.test;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppx.cloud.common.jdbc.MyCriteria;
import com.ppx.cloud.common.jdbc.MyDaoSupport;
import com.ppx.cloud.common.page.Page;


@Service
public class TestServiceImpl extends MyDaoSupport {

    @Transactional
    public List<Test> list(Page page, Test bean) {
      
        page.addDefaultOrderName("test_id").addPermitOrderName("test_price").addUnique("test_id");
        
        // 分页排序查询，是不是允许左边加"%"?
        MyCriteria c = createCriteria("where").addAnd("test_name like ?", "%", bean.getTestName(), "%");
        
        // 分开两条sql，mysql在count还会执行子查询, 总数返回0将不执行下一句
        StringBuilder cSql = new StringBuilder("select count(*) from test").append(c);
        StringBuilder qSql = new StringBuilder("select test_id, test_name, test_price, test_date, created from test").append(c);
        
        List<Test> list = queryPage(Test.class, page, cSql, qSql, c.getParaList());
        return list;
    }
    
    public int insert(Test bean) {
        // 后面带不允许重名的字段（该字段需要建索引）
        return insertEntity(bean, "test_name");
    }
    
    public Test get(Integer id) {
        Test bean = getJdbcTemplate().queryForObject("select * from test where test_id = ?",
                BeanPropertyRowMapper.newInstance(Test.class), id);     
        return bean;
    }
    
    public int update(Test bean) {
        // 后面带不允许重名的字段（该字段需要建索引）
        return updateEntity(bean, "test_name");
    }
    
    public int delete(Integer id) {
        // 大部分需求是状态删除，用update语句
        return getJdbcTemplate().update("delete from test where test_id = ?", id);
    }
}
