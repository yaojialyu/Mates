package me.dao;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import me.config.Config;

import org.nutz.dao.impl.SimpleDataSource;

/**
 *
 * @author elvis
 */

public class Mysql {
    private static final String url = "jdbc:mysql://";
    private static String name = Config.SQLUSERNAME;
    private static  String password = Config.SQLPASSWORD;
    private static String jdbcUrl = Config.JDBCURL;
    private static Connection conn = null;
    
    public static Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, name, password);
        } catch (SQLException ex) {
        	ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
        	ex.printStackTrace();
            System.out.println("JDBC error");
        }
        if(conn != null)
            System.out.println("database connect successfully");
        return conn;
    }
    
    public static void close(){
    	try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Mysql.close error");
		}
    }
    
    public static void main(String[] arg){
        Mysql.connect();
    }
    
    public static SimpleDataSource sds(){
    	SimpleDataSource ds = new SimpleDataSource();
    	try {
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setJdbcUrl(jdbcUrl);
	    	ds.setUsername(name);
	    	ds.setPassword(password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return ds;
    }
}
