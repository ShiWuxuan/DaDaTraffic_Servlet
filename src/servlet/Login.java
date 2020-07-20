/*������ 2020/7/16*/
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;
import service.DriverService;

@WebServlet(description = "��¼", urlPatterns = { "/Login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String code="000";	//������Ϣ
	
	
       
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
		// �����ַ���  
        String responseMsg="FAILED";    
        // ���ñ�����ʽ  
        request.setCharacterEncoding("utf-8");    
        // ��ȡ��������  
        String phonenumber = request.getParameter("phonenumber");//��ȡid  
        String password = request.getParameter("password");//��ȡ����
        String type=request.getParameter("type");//��ȡ��¼����
  
        // �������ݿ�  
        if(type.equals("driver")) {
        	code=DriverService.login(phonenumber,password);
        }else {
            code = UserService.login(phonenumber, password);
            if(code == UserService.LOGIN_SUCCEEDED) {  
                responseMsg = "��¼�ɹ�";  
            }else
            	responseMsg="��¼ʧ�ܣ����벻ƥ����˺�δע��";
        }

        System.out.println("login servlet responseMsg:" + responseMsg);  
        response.getWriter().append(code);
	}
 
}
