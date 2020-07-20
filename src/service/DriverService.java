/*������ 2020-7-16*/
package service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataBase.DBManager;

public class DriverService {
	public static String LOGIN_FAILED = "100"; //��¼ʧ��
    public static String LOGIN_SUCCEEDED = "200";//��¼�ɹ�
    public static String REGISTER_FAILED = "100";//ע��ʧ��
    public static String REGISTER_SUCCEEDED = "200";//ע��ɹ�

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
			if (result.next()) { // �ܲ鵽���˺ţ�˵���Ѿ�ע�����
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
			
			java.sql.Statement statement = connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��
			ResultSet result;
			
			String sqlQuery = "select * from " + DBManager.TABLE_DRIVERINFO + " where phonenumber='" + phonenumber + "'";
			
			// ��ѯ���������һ��ResultSet���ϣ�û�в鵽���ʱResultSet�ĳ���Ϊ0
			result = ((java.sql.Statement) statement).executeQuery(sqlQuery); // �Ȳ�ѯͬ�����˺ţ������ֻ��ţ��Ƿ����
			if(result.next()){ // �Ѵ���
				return REGISTER_FAILED;
			} else { // ������
				String sqlInsertPass = "insert into " + DBManager.TABLE_DRIVERINFO +" (phonenumber,password,drivername,carname) values ('"+phonenumber+"','"+password+"','"+userName+"','"+carnumber+"')";
				//�������������һ��int���͵�ֵ������ò���Ӱ�쵽������
				statement.executeUpdate(sqlInsertPass); // �����ʺ���������
				return REGISTER_SUCCEEDED;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "000";
	}
}
