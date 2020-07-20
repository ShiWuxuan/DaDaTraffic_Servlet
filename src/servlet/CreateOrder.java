/*张余青 2020/7/17*/
package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.OrderService;


@WebServlet(description = "创建订单", urlPatterns = { "/CreateOrder" })
public class CreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreateOrder() {
    	super();
    	}
    
    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getMethod();
		if ("GET".equals(method)) {
			System.out.println("请求方法：GET");
			doGet(request, response);
		} else if ("POST".equals(method)) {
			System.out.println("请求方法：POST");
			doPost(request, response);
		} else {
			System.out.println("请求方法分辨失败！");
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 先设置请求、响应报文的编码格式  */
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String phonenumber = request.getParameter("phonenumber"); // 从 request 中获取名为 phoneNumber 的参数的值
		String start = request.getParameter("start"); // 从 request 中获取名为 password 的参数的值
		String destination = request.getParameter("destination"); // 从 request 中获取名为 userName 的参数的值
 
		String code="000";
		// 访问数据库  
        try {
			code = OrderService.CreateOrder(phonenumber,start,destination);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        response.getWriter().append(code);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
		
	@Override
	public void destroy() {
		System.out.println("RegisterServlet destory.");
		super.destroy();
	}


}
