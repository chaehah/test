package com.kjca.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.kjca.UserDAO;
import com.kjca.UserDTO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/list.do")
public class ListController extends HttpServlet{
	private static final long serialVersionUID = 5357749784901835832L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getContextPath();
		
		ArrayList<UserDTO> list = new ArrayList<UserDTO>();
		UserDAO dao = new UserDAO();
		list = dao.selectAllUser();
		
		// 게시물 총 수
		int listSize = list.size();
		// 어플리케이션 내장객체 사용 
		ServletContext application = req.getServletContext();
		// 한 페이지당 게시물 수는 10개
		int postCnt = Integer.parseInt(application.getInitParameter("postCnt"));
		// 현재 기본 페이지는 1
		int currentPage = 1;
		String pageReq = req.getParameter("pageNum");	// 몇번 페이지를 선택했는지 갖고오기
		if(pageReq != null) {
			currentPage = Integer.parseInt(pageReq);	// 선택한 페이지가 있으면 현재 페이지로 설정
		}
		// 1페이지는 0 ~  9
		// 2페이지는 10 ~ 19
		// 2페이지는 20 ~ 29
		int start = currentPage*postCnt - postCnt;
		int end = start + postCnt - 1;
		
		int pageSize = (int) Math.ceil(1.0*listSize/postCnt);	// 총 페이지 수( ex)118개를 10개씩 쪼개면 11.8 ->올림해서 12페이지
		
		// 한 페이지에 보여줄 페이지 수
		int pageCnt = Integer.parseInt(application.getInitParameter("pageCnt"));
		
		// 한 페이지에 보여줄 페이지 수 시작번호 계산
		int startPage = ((currentPage-1)/pageCnt)*pageCnt +1;
//		int startPage = currentPage;
//		if(startPage>pageSize-pageCnt+1) {
//			startPage = pageSize-pageCnt+1;
//		}
		// 한 페이지에 보여줄 페이지 수 끝 번호 계산
		int endPage = startPage + pageCnt-1;
		if(endPage > pageSize) {
			endPage = pageSize;
		}
		
		req.setAttribute("userList", list);
		req.setAttribute("start", start);
		req.setAttribute("end", end);
		req.setAttribute("pageSize", pageSize); // 전체 페이지 수
		req.setAttribute("pageCnt", pageCnt);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		req.setAttribute("currentPage", currentPage);
		
		req.getRequestDispatcher("/list.jsp").forward(req, resp);
	}
	
	
}
