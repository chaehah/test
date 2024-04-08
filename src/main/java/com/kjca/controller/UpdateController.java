package com.kjca.controller;

import java.io.IOException;

import com.kjca.UserDAO;
import com.kjca.UserDTO;
import com.kjca.util.FileUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/userUpdate.do")
@MultipartConfig(maxFileSize= 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class UpdateController extends HttpServlet{
	private static final long serialVersionUID = -7116667807010110798L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String saveDirectory = "C:\\Java\\JSPStudy\\MVC2\\src\\main\\webapp\\uploads";
		if(req.getParameter("userFile") != null) {
			
		}
		String fileName = FileUtil.uploadFile(req, saveDirectory);
//		String savedFileName = FileUtil.renameFile(saveDirectory, fileName);
		int result = insertMyFile(req,fileName);
		
		resp.sendRedirect("./content.do?userNum="+req.getParameter("userNum"));
	}
	
	private int insertMyFile(HttpServletRequest req, String fileName) {
		UserDAO dao = new UserDAO();
		UserDTO dto = new UserDTO();

		int userNum = Integer.parseInt(req.getParameter("userNum"));
		String userId = req.getParameter("userId");
		String userPw = req.getParameter("userPw");
		String userName = req.getParameter("userName");
		int userAge = Integer.parseInt(req.getParameter("userAge"));
		String userEmail = req.getParameter("userEmail");
		String userPhone = req.getParameter("userPhone");
				
		dto.setUserNum(userNum);
		dto.setUserId(userId);
		dto.setUserPw(userPw);
		dto.setUserName(userName);
		dto.setUserAge(userAge);
		dto.setUserEmail(userEmail);
		dto.setUserPhone(userPhone);
		dto.setUserFile(fileName);
		int result = dao.updateUser(dto);
		return result;
	}
	

}
