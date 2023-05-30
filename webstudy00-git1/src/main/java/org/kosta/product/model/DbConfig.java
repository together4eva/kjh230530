package org.kosta.product.model;

public interface DbConfig {
	String DRIVER="oracle.jdbc.OracleDriver";
	String DB_URL="jdbc:oracle:thin:@192.168.0.16:1521:xe";
	String USER_NAME="scott";
	String USER_PASS="tiger";
}
