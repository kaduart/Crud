package com.hepta.modelo.persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionJDBCManager implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";


	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/produtos_db?serverTimezone=America/Sao_Paulo&autoReconnect=true&useSSL=false";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";



	Connection dbConnection = null;

	public static Connection getDBConnection() throws Exception {
		return getDBConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
	}

	public static Connection getDBConnection(String conn, String user, String pass) throws Exception {
		Connection dbConnection = null;
		Class.forName(DB_DRIVER);
		dbConnection = DriverManager.getConnection(conn, user, pass);
		return dbConnection;
	}

}