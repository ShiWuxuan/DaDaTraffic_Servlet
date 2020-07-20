/*施武轩 2020/7/17*/
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
		/* 先设置请求、响应报文的编码格式  */
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String type = request.getParameter("type"); // 从 request 中获取名为 type 的参数的值，用于区分操作类型
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
			if(type!=null&&type.equals("checkstate")) {//查询当前订单是否已有司机接单
				String orderid = request.getParameter("orderid");
				String sqlQueryStr = "select * from "+DBManager.TABLE_ORDERINFO+" where orderstate='进行中' and orderid='" + orderid +"'";
				result = statement.executeQuery(sqlQueryStr);
				if(result.next()) {//订单已经在进行中
					response.getWriter().append("200;");
					response.getWriter().append(result.getString("drivername")+";");//读取查询当前Order的接单的司机姓名
					response.getWriter().append(result.getString("carnumber")+";");//读取查询当前Order的接单的司机车牌
				}
				else {
					response.getWriter().append("100");
				}
			}
			else if(type!=null&&type.equals("cost")) {//查询当前订单是否已有司机接单
				String phoneStr = request.getParameter("phonenumber");
				int cost = Integer.parseInt(request.getParameter("costcredit"));
				String sqlUpdateStr = "update "+DBManager.TABLE_USERINFO+" set score=score-"+ cost +" where phonenumber='"+ phoneStr +"'";
				int rows = statement.executeUpdate(sqlUpdateStr);
				if(rows>0) {//扣除积分成功
					response.getWriter().append("200");
				}
				else {
					response.getWriter().append("100");
				}
			}
			else if(type!=null&&type.equals("getinfo")) {//查询当前登录的用户的信息
				String phoneStr = request.getParameter("phonenumber");
				String sqlQueryStr = "select * from "+ DBManager.TABLE_USERINFO+" where phonenumber='"+phoneStr +"'";
				result = statement.executeQuery(sqlQueryStr);
				if(result.next()) {//当前用户存在

					response.getWriter().append(result.getString("username")+";");//读取查询当前用户的姓名并加入返回信息
					response.getWriter().append(result.getString("score")+";");//读取当前用户的积分并加入返回信息
				}
				else {//查找失败
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
		System.out.println("不支持POST方法");
		doGet(request, response);
	}

}
