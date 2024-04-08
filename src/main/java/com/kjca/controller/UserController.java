package com.kjca.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.kjca.UserDAO;
import com.kjca.UserDTO;
import com.kjca.smtp.NaverSMTP;
import com.kjca.util.FileUtil;
import com.kjca.util.PasswordUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("*.do")
@MultipartConfig(maxFileSize= 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class UserController extends HttpServlet {
	private static final long serialVersionUID = -61464143167341144L;
	HttpSession session = null;
	
	// Get방식
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI();
		if(path.contains("content")) {			// 회원정보 페이지 가기
			req.setAttribute("user", userIntroduce(req));
			req.getRequestDispatcher("/content.jsp").forward(req, resp);
		} else if(path.contains("delete")) {		//회원정보 삭제
			userDelete(req);
			resp.sendRedirect("./list.do");
		}else if(path.contains("logout")) {
			session.invalidate();
			System.out.println("로그아웃");
			resp.sendRedirect("./list.do");
		}
	}
	
	// Post방식
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI();
		if(path.contains("userJoin")) {	//회원가입
			String saveDirectory = "C:\\Java\\JSPStudy\\MVC2\\src\\main\\webapp\\uploads";
			String fileName = FileUtil.uploadFile(req, saveDirectory);
			int result = userJoin(req,fileName);
			if(result>0) {
				// sendJoinEmail(req); 회원가입 성공시 메일 전송
				resp.sendRedirect("./list.do");
			}else {
				req.getRequestDispatcher("/register.jsp").forward(req, resp);
			}
		} else if(path.contains("userLogin")) {	//로그인
			int result = userLogin(req);
			if(result>0) {
				resp.sendRedirect("./list.do");
			}else {
				resp.sendRedirect("./LoginForm.jsp");
			}
		}
	}
	
	// 회원가입 메소드
	private int userJoin(HttpServletRequest req, String fileName) {
		String userId = req.getParameter("userId");
		String userPw = PasswordUtil.encoding(req.getParameter("userPw"));
		String userName = req.getParameter("userName");
		int userAge = Integer.parseInt(req.getParameter("userAge"));
		String userEmail = req.getParameter("userEmail");
		String userPhone = req.getParameter("userPhone");
		
		UserDTO dto = new UserDTO();
		dto.setUserId(userId);
		dto.setUserPw(userPw);
		dto.setUserName(userName);
		dto.setUserAge(userAge);
		dto.setUserEmail(userEmail);
		dto.setUserPhone(userPhone);
		dto.setUserFile(fileName);
		
		UserDAO dao = new UserDAO();
		int result = dao.userInsert(dto);
		return result;
	}
	
	// 회원로그인 메소드
	private int userLogin(HttpServletRequest req) {
		String userId = req.getParameter("userId");
		String userPw = PasswordUtil.encoding(req.getParameter("userPw"));
		
		UserDAO dao = new UserDAO();
		UserDTO dto = dao.loginUser(userId,userPw);
		
		if(dto!=null) {
			System.out.println("로그인 성공"+dto.getUserId());
			session = req.getSession(); 
			session.setAttribute("userId", dto.getUserId());
			return 1;
		}else {
			System.out.println("로그인 실패");
			return 0;
		}
	}
	// 회원정보조회 메소드
	private UserDTO userIntroduce(HttpServletRequest req) {
		int userNum = Integer.parseInt(req.getParameter("userNum"));

		UserDAO dao = new UserDAO();
		UserDTO dto = dao.selectUser(userNum);
		return dto;
	}
	
	// 회원삭제
	private int userDelete(HttpServletRequest req) {
		int userNum = Integer.parseInt(req.getParameter("userNum"));

		UserDAO dao = new UserDAO();
		int result = dao.deleteUser(userNum);
		return result;
	}
	
	// 회원가입 메일 보내기 메소드
	private void sendJoinEmail(HttpServletRequest req) {
		Map<String,String> emailInfo = new HashMap<String,String>();
		emailInfo.put("from", "내이메일주소");	// 보내는 사람
		emailInfo.put("to", req.getParameter("userEmail"));		// 받는 사람
		emailInfo.put("subject", "회원가입 축하드립니다.");	// 제목

		String content = req.getParameter("userName")+"님 회원가입해주셔서 정말 감사합니다. \r\n설정하신 ID:"+req.getParameter("userId")+" 로 로그인 가능합니다.";
		emailInfo.put("content", content);
		emailInfo.put("format", "text/plain;charset=UTF-8");

		try{
			NaverSMTP smtpServer = new NaverSMTP();	// 메일 전송 클래스 생성
			smtpServer.emailSending(emailInfo);		// 전송
			System.out.println("이메일 전송 성공");
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("이메일 전송 실패");
		}
	}
	
	
	
}
