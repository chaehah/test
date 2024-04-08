package com.kjca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	private Connection con;			// 데이터베이스와 연결 담당
	private PreparedStatement psmt;	// 인파라미터가 있는 동적 쿼리문을 실행할 때 사용
	private ResultSet rs;			// SELECT 쿼리문의 결과를 저장할 때 사용
	
	public void getConnection() {
		try {
			Context initCtx = new InitialContext();
			Context ctx = (Context)initCtx.lookup("java:comp/env");	// lookup은 리턴타입이 Object 이므로 Context로 강제 형변환
			DataSource source = (DataSource)ctx.lookup("dbcp_mysql");
			
			con = source.getConnection();
			
			System.out.println("DB 커넥션 풀 연결 성공");
		} catch (Exception e) {
			System.out.println("DB 커넥션 풀 연결 실패");
			e.printStackTrace();
		}
	}
	
	public int userInsert(UserDTO dto) {
		getConnection();
		String query = "INSERT INTO user(userId,userPw,userName,userAge,userEmail,userPhone,userFile) VALUES(?,?,?,?,?,?,?)";
		int result = 0;
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getUserId());
			psmt.setString(2, dto.getUserPw());
			psmt.setString(3, dto.getUserName());
			psmt.setInt(4, dto.getUserAge());
			psmt.setString(5, dto.getUserEmail());
			psmt.setString(6, dto.getUserPhone());
			psmt.setString(7, dto.getUserFile());
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	public UserDTO loginUser(String userId,String userPw) {
		getConnection();
		UserDTO dto = null;
		String query = "SELECT * FROM user WHERE userId=? AND userPw=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,userId);
			psmt.setString(2,userPw);
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				dto = new UserDTO();
				dto.setUserId(rs.getString("userId"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return dto;
	}
	
	public ArrayList<UserDTO> selectAllUser(){
		getConnection();
		ArrayList<UserDTO> userList = new ArrayList<UserDTO>();
		String query = "SELECT * FROM user ORDER BY userNum DESC";
		try {
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();
			while(rs.next()) {
				UserDTO dto = new UserDTO();
				dto.setUserNum(rs.getInt("userNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserPw(rs.getString("userPw"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserAge(rs.getInt("userAge"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setUserPhone(rs.getString("userPhone"));
				userList.add(dto);
			}
		} catch (Exception e) {
			System.out.println("유저리스트 조회중 예외발생");
			e.printStackTrace();
		} finally {
			close();
		}
		
		return userList;
	}
	
	public UserDTO selectUser(int userNum) {
		getConnection();
		UserDTO dto = null;
		String query = "SELECT * FROM user WHERE userNum=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1,userNum);
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				dto = new UserDTO();
				dto.setUserNum(rs.getInt("userNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserPw(rs.getString("userPw"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserAge(rs.getInt("userAge"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setUserPhone(rs.getString("userPhone"));
				dto.setUserFile(rs.getString("userFile"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("유저 한명 조회 오류");
		} finally {
			close();
		}
		return dto;
	}

	public int checkUserId(String userId) {
		getConnection();
		UserDTO dto = null;
		String query = "SELECT * FROM user WHERE userId=?";
		int result = 0;
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,userId);
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				result++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("유저다오:아이디 중복체크 조회 오류");
		} finally {
			close();
		}
		return result;
	}

	public int updateUser(UserDTO dto) {
		getConnection();
		String query = "UPDATE user SET userPw=?,userName=?,userAge=?,userEmail=?,userPhone=?,userFile=? WHERE userNum=?";
		int result = 0;
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getUserPw());
			psmt.setString(2, dto.getUserName());
			psmt.setInt(3, dto.getUserAge());
			psmt.setString(4, dto.getUserEmail());
			psmt.setString(5, dto.getUserPhone());
			psmt.setString(6, dto.getUserFile());
			psmt.setInt(7, dto.getUserNum());
			result = psmt.executeUpdate();
			System.out.println("성공여부"+result);
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println("유저업데이트 실패");
		} finally {
			close();
		}
		return result;
	}
	
	public int deleteUser(int userNum) {
		getConnection();
		String query = "DELETE FROM user WHERE userNum=?";
		int result = 0;
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, userNum);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println("삭제 실패");
		} finally {
			close();
		}
		return result;
	}
	
	public void close() {	// close를 안 하면 자원낭비가 됨.
		try {
			if(rs != null) rs.close();
			if(psmt != null) psmt.close();
			if(con != null) con.close();
			
			System.out.println("DB 커넥션 풀 자원 반납");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
