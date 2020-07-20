/*张余青 2020/7/16*/
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;
import service.DriverService;

@WebServlet(description = "登录", urlPatterns = { "/Login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String code="000";	//返回信息
	
	
       
    public Login() {
        super();
    }
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);  
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// 返回字符串  
        String responseMsg="FAILED";    
        // 设置编码形式  
        request.setCharacterEncoding("utf-8");    
        // 获取传入数据  
        String phonenumber = request.getParameter("phonenumber");//获取id  
        String password = request.getParameter("password");//获取密码
        String type=request.getParameter("type");//获取登录类型
  
        // 访问数据库  
        if(type.equals("driver")) {
        	code=DriverService.login(phonenumber,password);
        }else {
            code = UserService.login(phonenumber, password);
            if(code == UserService.LOGIN_SUCCEEDED) {  
                responseMsg = "登录成功";  
            }else
            	responseMsg="登录失败，密码不匹配或账号未注册";
        }

        System.out.println("login servlet responseMsg:" + responseMsg);  
        response.getWriter().append(code);
	}
 
}
