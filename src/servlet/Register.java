/*张余青 2020/7/17*/
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DriverService;
import service.UserService;


@WebServlet(description = "注册", urlPatterns = { "/Register" })
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Register() {
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
		String password = request.getParameter("password"); // 从 request 中获取名为 password 的参数的值
		String username = request.getParameter("username"); // 从 request 中获取名为 userName 的参数的值
		String type=request.getParameter("type");//获取登录类型
 
		String code="000";
		// 访问数据库  
        if(type.equals("driver")) {
        	String carnumber=request.getParameter("carnumber");
        	code=DriverService.register(phonenumber,password,username,carnumber);
        }else {
            code = UserService.register(phonenumber, password,username);
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
