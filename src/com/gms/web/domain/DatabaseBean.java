package com.gms.web.domain;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseBean implements Serializable {
	private static final long serialVersionUID = 1L;  //외부에서 호출할때 UID값으로 Bean을 찾는거
	private String driver, url, username, password;
	private Connection connection;
	public DatabaseBean(String driver, String url, String username, String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	public Connection getConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

}
