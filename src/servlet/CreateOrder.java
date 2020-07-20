/*������ 2020/7/17*/
package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.OrderService;


@WebServlet(description = "��������", urlPatterns = { "/CreateOrder" })
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
		String start = request.getParameter("start"); // �� request �л�ȡ��Ϊ password �Ĳ�����ֵ
		String destination = request.getParameter("destination"); // �� request �л�ȡ��Ϊ userName �Ĳ�����ֵ
 
		String code="000";
		// �������ݿ�  
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
