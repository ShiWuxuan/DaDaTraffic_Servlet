/*张余青 2020-7-16*/
package service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataBase.DBManager;

public class DriverService {
	public static String LOGIN_FAILED = "100"; //登录失败
    public static String LOGIN_SUCCEEDED = "200";//登录成功
    public static String REGISTER_FAILED = "100";//注册失败
    public static String REGISTER_SUCCEEDED = "200";//注册成功

	public static String login(String phonenumber, String password) throws IOException {
		Connection connect = null;
		try {
			connect = DBManager.getConnect();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			java.sql.Statement statement = connect.createStatement();
			String sql = "select * from " + DBManager.TABLE_DRIVERINFO + " where phonenumber='" + phonenumber
					+ "' and password='" + password + "'";
			ResultSet result = statement.executeQuery(sql);
			if (result.next()) { // 能查到该账号，说明已经注册过了
				return LOGIN_SUCCEEDED;
			} else {
				return LOGIN_FAILED;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "000";
	}

	public static String register(String phonenumber, String password,String userName,String carnumber) throws IOException {
		//String userPhone = "";
		try {
			Connection connect = null;
			try {
				connect = DBManager.getConnect();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			java.sql.Statement statement = connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
			ResultSet result;
			
			String sqlQuery = "select * from " + DBManager.TABLE_DRIVERINFO + " where phonenumber='" + phonenumber + "'";
			
			// 查询类操作返回一个ResultSet集合，没有查到结果时ResultSet的长度为0
			result = ((java.sql.Statement) statement).executeQuery(sqlQuery); // 先查询同样的账号（比如手机号）是否存在
			if(result.next()){ // 已存在
				return REGISTER_FAILED;
			} else { // 不存在
				String sqlInsertPass = "insert into " + DBManager.TABLE_DRIVERINFO +" (phonenumber,password,drivername,carname) values ('"+phonenumber+"','"+password+"','"+userName+"','"+carnumber+"')";
				//更新类操作返回一个int类型的值，代表该操作影响到的行数
				statement.executeUpdate(sqlInsertPass); // 插入帐号密码姓名
				return REGISTER_SUCCEEDED;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "000";
	}
}
