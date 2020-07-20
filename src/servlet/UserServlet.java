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
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
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
			if(type!=null&&type.equals("checkstate")) {//��ѯ��ǰ�����Ƿ�����˾���ӵ�
				String orderid = request.getParameter("orderid");
				String sqlQueryStr = "select * from "+DBManager.TABLE_ORDERINFO+" where orderstate='������' and orderid='" + orderid +"'";
				result = statement.executeQuery(sqlQueryStr);
				if(result.next()) {//�����Ѿ��ڽ�����
					response.getWriter().append("200;");
					response.getWriter().append(result.getString("drivername")+";");//��ȡ��ѯ��ǰOrder�Ľӵ���˾������
					response.getWriter().append(result.getString("carnumber")+";");//��ȡ��ѯ��ǰOrder�Ľӵ���˾������
				}
				else {
					response.getWriter().append("100");
				}
			}
			else if(type!=null&&type.equals("cost")) {//��ѯ��ǰ�����Ƿ�����˾���ӵ�
				String phoneStr = request.getParameter("phonenumber");
				int cost = Integer.parseInt(request.getParameter("costcredit"));
				String sqlUpdateStr = "update "+DBManager.TABLE_USERINFO+" set score=score-"+ cost +" where phonenumber='"+ phoneStr +"'";
				int rows = statement.executeUpdate(sqlUpdateStr);
				if(rows>0) {//�۳����ֳɹ�
					response.getWriter().append("200");
				}
				else {
					response.getWriter().append("100");
				}
			}
			else if(type!=null&&type.equals("getinfo")) {//��ѯ��ǰ��¼���û�����Ϣ
				String phoneStr = request.getParameter("phonenumber");
				String sqlQueryStr = "select * from "+ DBManager.TABLE_USERINFO+" where phonenumber='"+phoneStr +"'";
				result = statement.executeQuery(sqlQueryStr);
				if(result.next()) {//��ǰ�û�����

					response.getWriter().append(result.getString("username")+";");//��ȡ��ѯ��ǰ�û������������뷵����Ϣ
					response.getWriter().append(result.getString("score")+";");//��ȡ��ǰ�û��Ļ��ֲ����뷵����Ϣ
				}
				else {//����ʧ��
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
