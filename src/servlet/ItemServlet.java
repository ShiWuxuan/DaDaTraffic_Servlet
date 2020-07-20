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
		/* 先设置请求、响应报文的编码格式  */
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String type = request.getParameter("type"); // 从 request 中获取名为 type 的参数的值，用于区分对商品的操作类型
		
		String code = "";//返回给客户端的信息
		String message = "";//显示结果的信息
		
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
				String phoneStr = request.getParameter("phonenumber");// 从 request 中获取名为phonenumber的参数的值，表示购买的用户
				String titleStr = request.getParameter("title");// 从 request 中获取名为title的参数的值，表示购买的商品名
				int count = Integer.parseInt(request.getParameter("count"));// 从 request 中获取名为count的参数的值，表示购买该商品的数量
				int icon = Integer.parseInt(request.getParameter("icon"));
				
				//查询当前购买的商品之前是否购买过
				String querySqlStr = "select title from "+DBManager.TABLE_ITEM+" where phonenumber='"+phoneStr+"' and title='"+titleStr+"'";
				result = statement.executeQuery(querySqlStr);
				
				if(result.next()) {//之前购买过则增加数量
					String buySqlStr = "update "+ DBManager.TABLE_ITEM + " set count=count+" + count +" where phonenumber='"+phoneStr+"' and title='"+titleStr+"';";
					int row = statement.executeUpdate(buySqlStr);
					if(row>0) {
						code = "200";
						message = "购买成功";
					}
					else {
						code = "100";
						message = "购买失败";
					}
				}
				else {//之前未购买过则插入新记录
					String buySqlStr = "insert into "+ DBManager.TABLE_ITEM + "(phoneNumber,title,count,icon) values('"+phoneStr+"','"+titleStr+"',"+ count+","+icon +")";
					int row = statement.executeUpdate(buySqlStr);
					
					if(row>0) {
						code = "200";
						message = "购买成功";
					}
					else {
						code = "100";
						message = "购买失败";
					}
				}
				response.getWriter().append(code);
			}
			
			else if (type!=null&&type.equals("queryitems")) {
				String phoneStr = request.getParameter("phonenumber");// 从 request 中获取名为phonenumber的参数的值，表示查询的用户
				//查询用户购买过几种商品
				String countSqlStr = "select count(*) from "+DBManager.TABLE_ITEM+" where phonenumber='"+phoneStr+"'";
				result = statement.executeQuery(countSqlStr);
				result.next();
				response.getWriter().append(result.getInt(1)+";");
				
				//查询当前用户购买过的所有商品
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
		System.out.println("不支持POST方法");
		doGet(request, response);
	}

}
