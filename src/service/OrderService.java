/*������ 2020-7-17*/
package service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import DataBase.DBManager;

public class OrderService {
	public static String CREATE_FAILED = "100"; //��������ʧ��
	public static String CREATE_SUCCEEDED = "200"; //���������ɹ�

	public static String CreateOrder(String phonenumber, String start, String destination) throws IOException, SQLException {
		
			Connection connect = null;
			try {
				connect = DBManager.getConnect();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			java.sql.Statement statement = connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��
			String sqlQuery="select * from "+DBManager.TABLE_ORDERINFO+" WHERE phonenumber="+phonenumber;
			ResultSet QueryResult = statement.executeQuery(sqlQuery);
			Boolean flag=true;//�����ж��û��Ƿ����ж������Ѿ�����
			String state;
			while(QueryResult.next()) {
				state=QueryResult.getString("orderstate");
				if(state!="end") {
					flag=false;
					return CREATE_FAILED;
				}
			}
			
			if(flag==true) {
				Date nowtime=new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = dateFormat.format(nowtime);			
				
				String sqlInsertPass = "insert into " + DBManager.TABLE_ORDERINFO + " (phonenumber,drivername,carnumber,createtime,startpoint,destination,orderstate,evalution) values('"+phonenumber+"','"+null+"','"+null+"','"+time+"','"+start+"','"+destination+"','"+"waitting"+"','"+null+"')";
				statement.executeUpdate(sqlInsertPass);
				String sqlQueryID="select * from "+DBManager.TABLE_ORDERINFO+" WHERE phonenumber="+phonenumber+" AND orderstate='waitting'";
				ResultSet IDResult = statement.executeQuery(sqlQueryID);
				String orderid=IDResult.getString(2);
				return orderid;
			}
			
			return CREATE_FAILED;
	}

}


























