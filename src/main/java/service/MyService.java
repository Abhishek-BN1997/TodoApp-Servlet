package service;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MyDao;
import dto.Customer;
import helper.AES;

public class MyService {
	
	public void saveUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		long mobile = Long.parseLong(req.getParameter("mobile"));
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		
		int age = LocalDate.now().getYear() - dob.getYear();
		if (age < 18) {
			resp.getWriter().print("<h1 align='center' style='color:red'>Sorry! You are not eligible for Creating Accoun</h1>");
			req.getRequestDispatcher("signup.html").include(req, resp);
		}
		
		else {
			Customer customer= new Customer();
			customer.setName(name);
			customer.setAge(age);
			customer.setDob(dob);
			customer.setEmail(email);
			customer.setMobile(mobile);
			customer.setPassword(AES.encrypt(password,"123"));
			
			MyDao dao = new MyDao();
			Customer customer2=dao.findByEmail(email);
			
			if (customer2 == null) {
				resp.getWriter().print("<h1 align='center' style='color:green'> Account Created Success</h1>");
				req.getRequestDispatcher("login.html").include(req, resp);
			}
			
			else {
				resp.getWriter().print("<h1 align='center' style='red'>Account Already Exists with the Email: "+ email+"</h1>");
				req.getRequestDispatcher("signup.html").include(req, resp);
			}
		}
	}
	
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		MyDao dao=new MyDao();
		Customer customer=dao.findByEmail(email);
		if (customer==null) {
			resp.getWriter().print("<h1 align='center' style='color:red'>Invalid Email</h1>");
			req.getRequestDispatcher("login.html").include(req, resp);
		}
		
		else {
			if (password.equals(AES.decrypt(customer.getPassword(),"123"))) {
				resp.getWriter().print("<h1 align='center' style='green'>Login Success</h1>");
				req.getRequestDispatcher("Home.jsp").include(req, resp);
			}
		
		else {
			resp.getWriter().print("<h1 align='center' style='red'>Invalid Password</h1>");
			req.getRequestDispatcher("login.html").include(req, resp);
		}
	}
		
	}
	
}
