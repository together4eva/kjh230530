package org.kosta.product.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 * 	Singleton Design Pattern : 시스템 상에서 단 하나의 객체를 생성하여 외부에 공유해 사용하기 위한 설계 패턴 
 */
public class ProductDAO {
	private static ProductDAO instance=new ProductDAO();
	private ProductDAO() {}
	public static ProductDAO getInstance() {
		return instance;
	}
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DbConfig.DB_URL,DbConfig.USER_NAME, DbConfig.USER_PASS);
	}
	public void closeAll(PreparedStatement pstmt,Connection con) throws SQLException {
		if(pstmt!=null)
			pstmt.close();
		if(con!=null)
			con.close();
	}
	public void closeAll(ResultSet rs,PreparedStatement pstmt,Connection con) throws SQLException {
		if(rs!=null)
			rs.close();
		closeAll(pstmt, con);
	}
	public ProductVO findByNoProduct(long productNo) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ProductVO vo=null;
		try {
			con=getConnection();
			String sql="SELECT name,maker,price FROM mvc_product WHERE product_no=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setLong(1, productNo);
			rs=pstmt.executeQuery();
			if(rs.next())
				vo=new ProductVO(productNo, rs.getString(1), rs.getString(2), rs.getLong(3));
		}finally {
			closeAll(rs, pstmt, con);
		}
		return vo;
	}
	public void register(ProductVO productVO) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=getConnection();
			String sql=
				"INSERT INTO mvc_product(product_no,name,maker,price) VALUES(mvc_product_seq.nextval,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, productVO.getName());
			pstmt.setString(2, productVO.getMaker());
			pstmt.setLong(3, productVO.getPrice());
			int result=pstmt.executeUpdate();
			System.out.println("insert result:"+result);
		}finally {
			closeAll(pstmt, con);
		}
	}
	public ArrayList<ProductVO> findAllProductList() throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<ProductVO> list=new ArrayList<>();
		try {
			con=getConnection();
			String sql=
					"SELECT product_no,name,maker,price FROM mvc_product ORDER BY product_no DESC";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				list.add(new ProductVO(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getLong(4)));
			}
		}finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}
}

















