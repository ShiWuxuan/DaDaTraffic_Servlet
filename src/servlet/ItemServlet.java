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
 * Servlet implementation class ItemServlet
 */
@WebServlet("/ItemServlet")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* ������������Ӧ���ĵı����ʽ  */
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String type = request.getParameter("type"); // �� request �л�ȡ��Ϊ type �Ĳ�����ֵ���������ֶ���Ʒ�Ĳ�������
		
		String code = "";//���ظ��ͻ��˵���Ϣ
		String message = "";//��ʾ�������Ϣ
		
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
			
			if(type!=null&&type.equals("buy")) {
				String phoneStr = request.getParameter("phonenumber");// �� request �л�ȡ��Ϊphonenumber�Ĳ�����ֵ����ʾ������û�
				String titleStr = request.getParameter("title");// �� request �л�ȡ��Ϊtitle�Ĳ�����ֵ����ʾ�������Ʒ��
				int count = Integer.parseInt(request.getParameter("count"));// �� request �л�ȡ��Ϊcount�Ĳ�����ֵ����ʾ�������Ʒ������
				int icon = Integer.parseInt(request.getParameter("icon"));
				
				//��ѯ��ǰ�������Ʒ֮ǰ�Ƿ����
				String querySqlStr = "select title from "+DBManager.TABLE_ITEM+" where phonenumber='"+phoneStr+"' and title='"+titleStr+"'";
				result = statement.executeQuery(querySqlStr);
				
				if(result.next()) {//֮ǰ���������������
					String buySqlStr = "update "+ DBManager.TABLE_ITEM + " set count=count+" + count +" where phonenumber='"+phoneStr+"' and title='"+titleStr+"';";
					int row = statement.executeUpdate(buySqlStr);
					if(row>0) {
						code = "200";
						message = "����ɹ�";
					}
					else {
						code = "100";
						message = "����ʧ��";
					}
				}
				else {//֮ǰδ�����������¼�¼
					String buySqlStr = "insert into "+ DBManager.TABLE_ITEM + "(phoneNumber,title,count,icon) values('"+phoneStr+"','"+titleStr+"',"+ count+","+icon +")";
					int row = statement.executeUpdate(buySqlStr);
					
					if(row>0) {
						code = "200";
						message = "����ɹ�";
					}
					else {
						code = "100";
						message = "����ʧ��";
					}
				}
				response.getWriter().append(code);
			}
			
			else if (type!=null&&type.equals("queryitems")) {
				String phoneStr = request.getParameter("phonenumber");// �� request �л�ȡ��Ϊphonenumber�Ĳ�����ֵ����ʾ��ѯ���û�
				//��ѯ�û������������Ʒ
				String countSqlStr = "select count(*) from "+DBManager.TABLE_ITEM+" where phonenumber='"+phoneStr+"'";
				result = statement.executeQuery(countSqlStr);
				result.next();
				response.getWriter().append(result.getInt(1)+";");
				
				//��ѯ��ǰ�û��������������Ʒ
				String querySqlStr = "select * from "+DBManager.TABLE_ITEM+" where phonenumber='"+phoneStr+"'";
				result = statement.executeQuery(querySqlStr);
				while(result.next()) {
					response.getWriter().append(result.getString("title")+";");
					response.getWriter().append(result.getString("count")+";");
					response.getWriter().append(result.getString("icon")+";");
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
	}
		
		System.out.println(message);
		//response.getWriter().append(code);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("��֧��POST����");
		doGet(request, response);
	}

}
