package org.kosta.product.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Singleton Design Pattern : 시스템 상에서 단 한번 하나의 객체를 생성해서
 * 								  여러 곳에서 공유해 사용하는 설계 패턴 
 */
public class MemberDAO {
	private static MemberDAO instance;

	private MemberDAO() throws ClassNotFoundException {
		Class.forName(DbConfig.DRIVER);
		System.out.println("MemberDAO 객체 생성");
	}

	public static MemberDAO getInstance() throws ClassNotFoundException {
		if (instance == null)
			instance = new MemberDAO();
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DbConfig.DB_URL, DbConfig.USER_NAME, DbConfig.USER_PASS);
	}

	public void closeAll(PreparedStatement pstmt, Connection con) throws SQLException {
		if (pstmt != null)
			pstmt.close();
		if (con != null)
			con.close();
	}

	public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException {
		if (rs != null)
			rs.close();
		closeAll(pstmt, con);
	}

	public int getTotalMemberCount() throws SQLException {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select count(*) from member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				count = rs.getInt(1);
		} finally {
			closeAll(rs, pstmt, con);
		}
		return count;
	}

	public MemberVO findMemberById(String id) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO vo = null;
		try {
			con = getConnection();
			String sql = "select name,address from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next())
				vo = new MemberVO(id, null, rs.getString(1), rs.getString(2));
		} finally {
			closeAll(rs, pstmt, con);
		}
		return vo;
	}

	public ArrayList<MemberVO> findMemberListByAddress(String address) throws SQLException {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select id,name from member where address=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, address);
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MemberVO(rs.getString(1), null, rs.getString(2), null));
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}

	public void register(MemberVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			String sql = "insert into member(id,password,name,address) values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getAddress());
			int result = pstmt.executeUpdate();
			System.out.println("register member insert row count:" + result);
		} finally {
			closeAll(pstmt, con);
		}
	}

	public MemberVO login(String id, String password) throws SQLException {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con=getConnection();
			String sql ="select name,address from member where id=? and password=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				memberVO=new MemberVO(id,password,rs.getString(1),rs.getString(2));
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return memberVO;
	}

}
