package com.chatserver.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YychatDbUtil {
	public static final String MYSQLDRIVER="com.mysql.jdbc.Driver";
	public static final String URL="jdbc:mysql://127.0.0.1:3306/yychat";;
	public static final String DBUSER="root";
	public static final String DBPASS="";
	
	public static void loadDriver() {
		//1、加载驱动程序
		try {
			Class.forName(MYSQLDRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	} 
	public static Connection  getConnection(){
		loadDriver();
		Connection conn = null;
	    try {
			conn=DriverManager.getConnection(URL,DBUSER,DBPASS);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
		return conn;
	} 
	public static boolean loginValidate(String userName,String passWord) {
		boolean loginSuccess=false;
		Connection conn=getConnection();
		String user_Login_Sql="select * from user where username=? and password=?";
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		try {
			ptmt=conn.prepareStatement(user_Login_Sql);
			ptmt.setString(1, userName);
			ptmt.setString(2, passWord);
			rs=ptmt.executeQuery();
			loginSuccess=rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			 closeDB(conn,ptmt,rs);
		 }
		return loginSuccess;
	}
	//3建立一个PreparedStatement
	//4执行
	//5、根据结果集来判断是否能登录
	
	public static String getFriengString(String userName){
		Connection conn=getConnection();
		String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		String friendString="";
		try {
			ptmt=conn.prepareStatement(friend_Relation_Sql);
			ptmt.setString(1, userName);
			rs=ptmt.executeQuery();
		    while(rs.next()){
			friendString=friendString+rs.getString("slaveuser")+" ";
		 }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			 closeDB(conn,ptmt,rs);
		 }
	return friendString;
	}
	public static void closeDB(Connection conn,PreparedStatement ptmt,ResultSet rs) {
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if(ptmt!=null)
				try {
					ptmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
	}
}