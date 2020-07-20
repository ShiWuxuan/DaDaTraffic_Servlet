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
 * 
 *  用于司机开始接单后，每隔一段时间询问服务器是否有等待的订单以及查询司机相关信息
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
			if(type!=null&&type.equals("check")) {
				String sqlQueryStr = "select * from "+DBManager.TABLE_ORDERINFO+" where orderstate='waitting'";
				result = statement.executeQuery(sqlQueryStr);
				if(result.next()) {//有等待中的订单
					//将信息传给客户端
					response.getWriter().append("200;");
					response.getWriter().append(result.getString("phonenumber")+";");//读取查询到的第一条Order的用户手机号
					response.getWriter().append(result.getString("startpoint")+";");//读取查询到的第一条Order的起始地点
					response.getWriter().append(result.getString("destination")+";");
					response.getWriter().append(result.getString("orderid")+";");
				}
				else{//无等待中的订单
					response.getWriter().append("100");
				}
			}
			
			else if (type!=null&&type.equals("getinfo")) {
				String phoneStr = request.getParameter("phonenumber");
				String sqlQueryStr = "select * from "+DBManager.TABLE_DRIVERINFO +" where phonenumber='"+phoneStr+"'";
				result = statement.executeQuery(sqlQueryStr);
				if(result.next()) {
					response.getWriter().append(result.getString("drivername")+";");//读取查询到当前登录司机的姓名
					response.getWriter().append(result.getString("carnumber")+";");//读取查询到当前登录司机的车牌号
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
		System.out.println("不支持POST方法");
		doGet(request, response);
	}

}
