package com.kjca.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONObject;

import com.kjca.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/idCheck.do")
public class IdCheckController extends HttpServlet{
	private static final long serialVersionUID = -6727516084050592551L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
		String userId = req.getParameter("userId");
		UserDAO dao = new UserDAO();
		int result = dao.checkUserId(userId);
		if(result>0) {
			System.out.println("컨트롤러:아이디 중복됨");
		}else {
			System.out.println("컨트롤러:아이디 사용가능");
		}
		JSONObject json = new JSONObject();
		json.put("result", result);
		PrintWriter out = resp.getWriter();
		out.print(json);
		out.close();
	}
	
	

}
