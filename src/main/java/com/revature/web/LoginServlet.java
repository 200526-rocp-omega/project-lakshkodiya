package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.templates.LoginTemplate;
import com.revature.templates.MessageTemplate;

public class LoginServlet extends HttpServlet {
	
	private static final ObjectMapper  om= new ObjectMapper();
	private static final  UserService service=new UserService();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			 			throws ServletException, IOException {
		res.setStatus(404);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			 			throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		res.setContentType("application/json");

		
		User current=(User)session.getAttribute("currentUser");
				
				// Already Logged in
				if(current!=null) {
					
					res.setStatus(400);
					//System.out.println("login insider");
					res.getWriter().println(current.getUsername()+ " is already logged in as user ");
					return;
				}
					
		BufferedReader reader=req.getReader();
		
		StringBuilder sb =new StringBuilder();
		
		String line;
		
		while( (line = reader.readLine())!= null ) {
			sb.append(line);
		}
		/* the (line= reader.readerLine()) part obtains the value for a single line 
		 * from the body of the request and stores it into the line variable
		 * 
		 * then the != null part compares the value of the string is null
		 * 
		 * if the readLine() method is null, that means we are at the end
		 * */
		
		 String body = sb.toString();
		 
		 // System.out.println("body "+body);
		 LoginTemplate lt=om.readValue(body, LoginTemplate.class);
		 
		 //System.out.println("LoginTemplate "+lt);
		
		 User u = service.login(lt);
		 PrintWriter writer=res.getWriter();
		 
		 if(u==null) {
			 // Login Failed
			 res.setStatus(400);
			 MessageTemplate message= new MessageTemplate("Invalid Credentials");
			 res.getWriter().println(om.writeValueAsString(message));
			 //writer.println("Username or password was incorrect");
			 return;
			 
			 
		 }
		
				 
		 session.setAttribute("currentUser",u);
		 
		// res.setStatus(200);
		 		
		res.setStatus(200);
		
		writer.println(om.writeValueAsString(u));
			}
	
	
}
