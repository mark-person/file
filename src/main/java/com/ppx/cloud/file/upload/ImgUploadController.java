package com.ppx.cloud.file.upload;

import org.springframework.stereotype.Controller;

@Controller
public class ImgUploadController {

//	private static Set<String> prodTypeSet = new HashSet<String>();
//	
//	static {
//		prodTypeSet.add("prod");
//		prodTypeSet.add("sku");
//	}
//	/**
//	 * product
//	 * 路经=merchantId/prod/yyyyMMdd/UUID.ext
//	 * 路经=merchantId/yyyyMMdd/UUID.ext
//	 * @param file
//	 * @param type 
//	 * @return
//	 */
//	@PostMapping @ResponseBody
//	public Map<String, Object> prodSave(@RequestParam("file") MultipartFile[] file, @RequestParam("type") String[] type) {
//		List<String> returnList = new ArrayList<String>();
//		
//		if (file == null || file.length == 0 || file.length != type.length) {
//			return ControllerReturn.ok(returnList);
//		}
//		
//		for (String t : type) {
//			if (!prodTypeSet.contains(t)) {
//				return ControllerReturn.ok(returnList);
//			}
//		}
//		
//		for (int i = 0; i < file.length; i++) {				
//			BufferedOutputStream buffStream = null;
//			try {
//				String fileName = file[i].getOriginalFilename();
//				if (StringUtils.isEmpty(fileName)) {
//					continue;
//				}
//				byte[] bytes = file[i].getBytes();
//				String path = getImgPath(fileName, type[i]);
//				String savePath = System.getProperty("file.imgFilePath") + path;
//				buffStream = new BufferedOutputStream(new FileOutputStream(new File(savePath)));
//				buffStream.write(bytes);
//				
//				// 图片压缩 不需要 
//				if ("prod".equals(type[i])) {
//					Thumbnails.of(savePath).size(240, 240).toFile(savePath + "_240.jpg");
//				}
//				
//				returnList.add(path);
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				if (buffStream != null) {
//					try {
//						buffStream.close();
//					} catch (Exception e) {}
//				}
//			}
//		}
//		return ControllerReturn.ok(returnList);
//	}
//	
//	private String getImgPath(String fileName, String type) {
//		int merchantId = GrantContext.getLoginAccount().getMerchantId();
//		String path = merchantId + "/" + type + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
//		File pathFile = new File(System.getProperty("file.imgFilePath") + path);
//		if (!pathFile.exists()) {
//			pathFile.mkdirs();
//		}
//		
//		String ext = fileName.substring(fileName.lastIndexOf("."));
//		String imgFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
//		return path + "/" + imgFileName;
//	}
//	
//	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//	private static Set<String> showTypeSet = new HashSet<String>();
//	
//	static {
//		showTypeSet.add("store");
//		showTypeSet.add("swiper");
//		showTypeSet.add("cat");
//		showTypeSet.add("brand");
//		showTypeSet.add("theme");
//		showTypeSet.add("promo");
//	}
//	
//	/**
//	 * 路经=merchantId/show/name.ext
//	 * show/
//	 * store/ swiper/ brand cat promo theme 
//	 * 
//	 * 
//	 * @param file
//	 * @return
//	 */
//	@PostMapping @ResponseBody
//	public Map<String, Object> showSave(@RequestParam("file") MultipartFile[] file, @RequestParam("type") String[] type) {
//		List<String> returnList = new ArrayList<String>();
//		
//		if (file == null || file.length == 0 || file.length != type.length) {
//			return ControllerReturn.ok(returnList);
//		}
//		
//		for (String t : type) {
//			if (!showTypeSet.contains(t)) {
//				return ControllerReturn.ok(returnList);
//			}
//		}
//		
//		
//		
//		for (int i = 0; i < file.length; i++) {				
//			BufferedOutputStream buffStream = null;
//			try {
//				String fileName = file[i].getOriginalFilename();
//				if (StringUtils.isEmpty(fileName)) {
//					continue;
//				}
//				byte[] bytes = file[i].getBytes();
//				String path = getShowPath(fileName, type[i]);
//				buffStream = new BufferedOutputStream(new FileOutputStream(new File(System.getProperty("file.imgFilePath") + path)));
//				buffStream.write(bytes);
//				returnList.add(path);
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				if (buffStream != null) {
//					try {
//						buffStream.close();
//					} catch (Exception e) {}
//				}
//			}
//		}
//		return ControllerReturn.ok(returnList);
//	}
//	
//	private String getShowPath(String fileName, String type) {
//		int merchantId = GrantContext.getLoginAccount().getMerchantId();
//		String ext = fileName.substring(fileName.lastIndexOf("."));
//		
//		if (type.equals("swiper") || type.equals("store")) {
//			String path = merchantId + "/show/" + type;
//			File pathFile = new File(System.getProperty("file.imgFilePath") + path);
//			if (!pathFile.exists()) {
//				pathFile.mkdirs();
//			}
//			String imgFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
//			return path + "/" + imgFileName;
//		}
//		else {
//			String path = merchantId + "/show";
//			File pathFile = new File(System.getProperty("file.imgFilePath") + path);
//			if (!pathFile.exists()) {
//				pathFile.mkdirs();
//			}
//			return path + "/" + type + ext;
//		}
//	}
}