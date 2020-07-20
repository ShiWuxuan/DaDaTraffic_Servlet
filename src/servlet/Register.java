/*������ 2020/7/17*/
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DriverService;
import service.UserService;


@WebServlet(description = "ע��", urlPatterns = { "/Register" })
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
			System.out.println("���󷽷���GET");
			doGet(request, response);
		} else if ("POST".equals(method)) {
			System.out.println("���󷽷���POST");
			doPost(request, response);
		} else {
			System.out.println("���󷽷��ֱ�ʧ�ܣ�");
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* ������������Ӧ���ĵı����ʽ  */
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String phonenumber = request.getParameter("phonenumber"); // �� request �л�ȡ��Ϊ phoneNumber �Ĳ�����ֵ
		String password = request.getParameter("password"); // �� request �л�ȡ��Ϊ password �Ĳ�����ֵ
		String username = request.getParameter("username"); // �� request �л�ȡ��Ϊ userName �Ĳ�����ֵ
		String type=request.getParameter("type");//��ȡ��¼����
 
		String code="000";
		// �������ݿ�  
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
