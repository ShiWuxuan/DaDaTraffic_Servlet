/*ʩ���� 2020/7/17*/
package servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import DataBase.DBManager;

/**
 * 
 *  ����˾����ʼ�ӵ���ÿ��һ��ʱ��ѯ�ʷ������Ƿ��еȴ��Ķ����Լ���ѯ˾�������Ϣ
 */
@WebServlet("/DriverServlet")
public class DriverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DriverServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* ������������Ӧ���ĵı����ʽ  */
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String type = request.getParameter("type"); // �� request �л�ȡ��Ϊ type �Ĳ�����ֵ���������ֲ�������
		
		try {
			Connection connect = null;
			try {
				connect = DBManager.getConnect();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement statement = connect.createStatement();
			ResultSet result;
			if(type!=null&&type.equals("check")) {
				String sqlQueryStr = "select * from "+DBManager.TABLE_ORDERINFO+" where orderstate='waitting'";
				result = statement.executeQuery(sqlQueryStr);
				if(result.next()) {//�еȴ��еĶ���
					//����Ϣ�����ͻ���
					response.getWriter().append("200;");
					response.getWriter().append(result.getString("phonenumber")+";");//��ȡ��ѯ���ĵ�һ��Order���û��ֻ���
					response.getWriter().append(result.getString("startpoint")+";");//��ȡ��ѯ���ĵ�һ��Order����ʼ�ص�
					response.getWriter().append(result.getString("destination")+";");
					response.getWriter().append(result.getString("orderid")+";");
				}
				else{//�޵ȴ��еĶ���
					response.getWriter().append("100");
				}
			}
			
			else if (type!=null&&type.equals("getinfo")) {
				String phoneStr = request.getParameter("phonenumber");
				String sqlQueryStr = "select * from "+DBManager.TABLE_DRIVERINFO +" where phonenumber='"+phoneStr+"'";
				result = statement.executeQuery(sqlQueryStr);
				if(result.next()) {
					response.getWriter().append(result.getString("drivername")+";");//��ȡ��ѯ����ǰ��¼˾��������
					response.getWriter().append(result.getString("carnumber")+";");//��ȡ��ѯ����ǰ��¼˾���ĳ��ƺ�
					response.getWriter().append(result.getString("score")+";");
				}
				else {
					response.getWriter().append("100");
				}
			}
			}catch (SQLException e) {
				e.printStackTrace();
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("��֧��POST����");
		doGet(request, response);
	}

}
