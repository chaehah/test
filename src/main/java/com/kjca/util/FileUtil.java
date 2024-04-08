package com.kjca.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class FileUtil {

	public static String uploadFile(HttpServletRequest req, String sDirectory) throws IOException, ServletException {
		Part part = req.getPart("file");	// file타입으로 전송된 폼값을 받아 Part 객체에 저장(폼태그 파일타입 name설정한걸로 작성)
		String partHeader = part.getHeader("content-disposition");
		// System.out.println(partHeader);
		String[] strr = partHeader.split("filename=");
		// System.out.println(strr[1]);
		String fileName = strr[1].trim().replace("\"", "");
		if(!fileName.isEmpty()) {
			part.write(sDirectory+File.separator+fileName);	// sDirectory:실제 경로	part.write를 통해서 실제 경로에 파일 이름을 저장하겠다는 의미
		}
		System.out.println("파일이름:"+fileName);
		return fileName;
	}
	
	//파일명 변경
	public static String renameFile(String sDirectory, String fileName) {
		String newFileName = null;
		try {
			String ext = fileName.substring(fileName.lastIndexOf("."));	// 확장자 가져오기
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			newFileName = now + ext;
			File oldFile = new File(sDirectory + File.separator + fileName);	// File.separator : 해당 OS의 구분자
			File newFile = new File(sDirectory + File.separator + newFileName);
			oldFile.renameTo(newFile);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return newFileName;
	}
}
