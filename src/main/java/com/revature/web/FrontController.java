package com.revature.web;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.authorization.AuthService;
import com.revature.controller.AccountController;
import com.revature.controller.UserController;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.IllegalParameterException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.templates.AccountTemplate;
import com.revature.templates.LoginTemplate;
import com.revature.templates.MessageTemplate;
import com.revature.templates.TransferTemplate;
import com.revature.templates.UserAccountTemplate;

public class FrontController extends HttpServlet {
	
	private static final long serialVersionUID = -4854248294011883310L;
	private static final UserController userController = new UserController();
	private static final AccountController accountController = new AccountController();
	private static final ObjectMapper  om= new ObjectMapper();
	private static final  UserService service=new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException , IOException{
		res.setContentType("application/json");
		res.setStatus(404);
		final String URI=req.getRequestURI().replace("/rocp-project", "").replaceFirst("/","");
				
		String[] portions= URI.split("/");
		
		try {
			switch(portions[0].toLowerCase()) {
			case "users":
				if(portions.length==2) {
					//Delegate to a Controller method to handle obtaining a User by ID
					
					int id= Integer.parseInt(portions[1]);
					AuthService.guard(req.getSession(false),id, "Employee","Admin");
					User u= userController.findUserById(id);
											res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(u));
						
					} else {

						AuthService.guard(req.getSession(false), "Employee","Admin");
						List<User> all=userController.findAllUsers();
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(all));
					}	
				
				break; // case user end
			case "accounts":
				if(portions.length==3) { // check for search request by owner or by status
					//System.out.println("inside account length 3");
					int id=Integer.parseInt(portions[2]);
					portions[1]=portions[1].toLowerCase();
					if(portions[1].equals("status")) { // check for search request by statusId
						//System.out.println("before authorization");
						
						AuthService.guard(req.getSession(false), "Employee","Admin"); // check permission
					//	System.out.println("before find by status");
						List<Account> allAcc=accountController.findByStatus(id); //return all accounts of status ID requested
						//System.out.println("inside account status");
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(allAcc));
					}
					else if(portions[1].equals("owner")) { // check for search made by owner 
						//System.out.println("before authorization");
						
						AuthService.guard(req.getSession(false),id,"Employee","Admin"); // check premission
						//System.out.println("after authorization");
						
						List<Account> Acc=accountController.findByUId(id); //return all accounts of User ID requested
						System.out.println("after search");
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(Acc));
																			
					}
					else {
						res.setStatus(400);
						MessageTemplate message= new MessageTemplate("Not proper request");
						res.getWriter().println(om.writeValueAsString(message));
						//res.getWriter().println(om.writeValueAsString("Not proper request"));

					}
				} else if(portions.length==2) { // check for request by Account id
					int accId=Integer.parseInt(portions[1]);
					List<Integer> uId=accountController.findUId(accId);
					AuthService.guard(req.getSession(false),uId,"Employee","Admin" );
					Account Acc=accountController.findByAccId(accId); //return  account of Acc ID requested
					/*if(Acc==null){
						 res.setStatus(400);
						 MessageTemplate message= new MessageTemplate("No such account exist");
							res.getWriter().println(om.writeValueAsString(message));
						// res.getWriter().println("No such Account Exist");
					}
					else {*/
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(Acc));
					//}
					
				}
				else if(portions.length==1){
				AuthService.guard(req.getSession(false),"Employee","Admin" );
				List<Account> accList=accountController.findAllAccounts();
				res.setStatus(200);
				res.getWriter().println(om.writeValueAsString(accList));
				}
					break; // case account end
				default: 	res.setStatus(400);
						MessageTemplate message= new MessageTemplate("Not proper request");
						res.getWriter().println(om.writeValueAsString(message));
							//res.getWriter().println(om.writeValueAsString("Not proper request"));
			}//swtich end
		}// try end	
		catch(NotLoggedInException e) {
			res.setStatus(401);
			MessageTemplate message= new MessageTemplate("You are not logged in");
			res.getWriter().println(om.writeValueAsString(message));
		}catch(IllegalParameterException e){
			res.setStatus(400);
			MessageTemplate message= new MessageTemplate(e.getMessage());
			res.getWriter().println(om.writeValueAsString(message));
		
		} catch(AuthorizationException e) {	
			res.setStatus(401);
			MessageTemplate message= new MessageTemplate("You are not authorized");
			res.getWriter().println(om.writeValueAsString(message));
		}catch(NumberFormatException e) {
			res.setStatus(400);
			MessageTemplate message= new MessageTemplate("Invalid request");
			res.getWriter().println(om.writeValueAsString(message));


		}
		
	
	}	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException , IOException{
			res.setContentType("application/json");
			res.setStatus(404);
			HttpSession session=req.getSession();
			final String URI=req.getRequestURI().replace("/rocp-project", "").replaceFirst("/","");
		//	System.out.println(URI);
			
			String[] portions= URI.split("/");
			
			BufferedReader reader=req.getReader();
			StringBuilder sb =new StringBuilder();
			String line;
			while( (line = reader.readLine())!= null ) {
				sb.append(line);
			}
			String body = sb.toString();
	
			try {
				switch(portions[0].toLowerCase()) {
				case "logout":
					if(userController.logout(req.getSession(false))) {
						res.setStatus(200);
						res.getWriter().println("you have benn successfully logged out");
					} else {
						res.setStatus(400);
						res.getWriter().println("You were not logged in to begin with");
					}
				break;
				
				case "login":
						User current=(User)session.getAttribute("currentUser");
						// Already Logged in
						if(current!=null) {
							
							res.setStatus(400);
							res.getWriter().println(current.getUsername()+ " is already logged in as user ");
							break;
						}
							
						 LoginTemplate lt=om.readValue(body, LoginTemplate.class);
				 
					    User u = service.login(lt);
					    PrintWriter writer=res.getWriter();
				 
				 		 if(u==null) {
				 			 res.setStatus(400);
				 			 MessageTemplate message= new MessageTemplate("Invalid Credentials");
				 			 res.getWriter().println(om.writeValueAsString(message));
				 			 
				 		 } 
				 		 else {
				 		 session.setAttribute("currentUser",u);
				 		 res.setStatus(200);
				 		 writer.println(om.writeValueAsString(u));
				 		 }
				 		 break; // end of login request
				 		 
				case "accounts":
						if(portions.length==1) {
							UserAccountTemplate uat= om.readValue(body,UserAccountTemplate.class);
							System.out.println(uat); 
							boolean status=userController.checkStatus(uat.getUserId());
							if (status== true){
								AuthService.guard(req.getSession(false), uat.getUserId(),"Admin"); // check permission

								int accId=accountController.insert(uat);
								System.out.println("generated account " + accId);
								accountController.insertUserAccount(uat.getAccId(),accId);
								res.setStatus(200);
								 MessageTemplate message=new MessageTemplate("Account CREATED : "+accId);
						 		 res.getWriter().println(om.writeValueAsString(message));

							}else {
								throw new IllegalParameterException("User Id not found");

							}
								break;
						}
						//AccountTemplate at=om.readValue(body, AccountTemplate.class);
						if((portions[1].toLowerCase()).equals("withdraw")) {
							AccountTemplate at=om.readValue(body, AccountTemplate.class);

							int accId=at.getid();
							List<Integer> uId=accountController.findUId(accId);
							AuthService.guard(req.getSession(false),uId,"Admin" );
							//Account Acc=accountController.findByAccId(accId); //return  account of Acc ID requested
							int success=accountController.withdraw(at.getid(),at.getAmount());
							if (success==1){
								 res.setStatus(200);
								 MessageTemplate message=new MessageTemplate(at.getAmount()+" has been withdrawn from Account "+at.getid());
						 		 res.getWriter().println(om.writeValueAsString(message));
							
							}
							else {
								System.out.println(success);
								throw new IllegalParameterException("Invalid request");
							}
								
						}
						else if((portions[1].toLowerCase()).equals("deposit")) {
							AccountTemplate at=om.readValue(body, AccountTemplate.class);

							int accId=at.getid();
							List<Integer> uId=accountController.findUId(accId);
							AuthService.guard(req.getSession(false),uId,"Admin" );
							//Account Acc=accountController.findByAccId(accId); //return  account of Acc ID requested
							int success=accountController.deposit(at.getid(),at.getAmount());
							if (success==1){
								 res.setStatus(200);
								 MessageTemplate message=new MessageTemplate(at.getAmount()+" has been deposited to Account "+at.getid());
						 		 res.getWriter().println(om.writeValueAsString(message));
							
							}
							else {
								//System.out.println(success);
								throw new IllegalParameterException("Invalid request");
							}
								
						}
						else if ((portions[1].toLowerCase()).equals("transfer")) {
							//AccountTemplate at=om.readValue(body, TransferTemplate.class);

							TransferTemplate tt=om.readValue(body, TransferTemplate.class);
							List<Integer> uId=accountController.findUId(tt.getSourceAccountId());
							AuthService.guard(req.getSession(false),uId,"Admin" );
							boolean sa=accountController.checkStatus(tt.getSourceAccountId());
							boolean st=accountController.checkStatus(tt.getTargetAccountId());
							if (sa==true && st== true) {
								if((accountController.findByAccId(tt.getSourceAccountId())).getBalance()>tt.getAmount()	) {				

									accountController.deposit(tt.getTargetAccountId(),tt.getAmount());
									accountController.withdraw(tt.getSourceAccountId(),tt.getAmount());
									res.setStatus(200);
									MessageTemplate message=new MessageTemplate(" ${"+tt.getAmount()+"} has been transferred from "
											+ "Account #{"+ tt.getSourceAccountId()+"} to Account #{"+tt.getTargetAccountId()+"}");
							 		res.getWriter().println(om.writeValueAsString(message));

								}else
									throw new IllegalParameterException("Not enough balance in source A/C");
							}
							else 
								throw new IllegalParameterException("soruce or target A/C status not open ");
							
						}else
							throw new IllegalParameterException("Invalid request");
						break;
						
					default: throw new IllegalParameterException("Invalid request");
				
				}
			}catch(NotLoggedInException e) {
					res.setStatus(401);
					MessageTemplate message= new MessageTemplate("You are not login ");
					res.getWriter().println(om.writeValueAsString(message));
			}catch(IllegalParameterException e) {
				res.setStatus(400);
				MessageTemplate message= new MessageTemplate(e.getMessage());
				res.getWriter().println(om.writeValueAsString(message));

			} catch(AuthorizationException e) {	
				res.setStatus(401);
				MessageTemplate message= new MessageTemplate("You are not authorized");
				res.getWriter().println(om.writeValueAsString(message));
			}catch(NumberFormatException e) {
				//System.out.println("inside number excep");
				res.setStatus(400);
				MessageTemplate message= new MessageTemplate("Invalid request");
				res.getWriter().println(om.writeValueAsString(message));


			}
								
}
			
				
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res)
			throws ServletException , IOException{
		res.setContentType("application/json");
		res.setStatus(404);
		BufferedReader reader=req.getReader();
		StringBuilder sb =new StringBuilder();
		String line;
		int success;
		while( (line = reader.readLine())!= null ) {
			sb.append(line);
		}
		String body = sb.toString();
		
		final String URI=req.getRequestURI().replace("/rocp-project", "").replaceFirst("/","");
				
		String[] portions= URI.split("/");
		
		try {
			switch(portions[0].toLowerCase()) {
			
			case "users":
				
				User u=om.readValue(body, User.class);
				System.out.println(u);

				/*String requestData = req.getReader().lines().collect(Collectors.joining());
				System.out.println(requestData);
				User u = om.readValue(requestData, User.class);*/
				//int id=Integer.parseInt(req.getParameter("id"));
				//int id=u.getId();
				AuthService.guard(req.getSession(false), u.getId(),"Admin"); // check permission

				success =userController.update(u);
				if (success==1) {
					res.setStatus(200);
					MessageTemplate message= new MessageTemplate("User record Updated");
					res.getWriter().println(om.writeValueAsString(message));
				}
					else 
						throw new IllegalParameterException("Invalid request");
				break;
				
			case "accounts":
				Account acc=om.readValue(body, Account.class);
				System.out.println(acc);

				AuthService.guard(req.getSession(false), "Admin"); // check permission
				success=accountController.update(acc);
				if (success==1) {
					res.setStatus(200);
					MessageTemplate message= new MessageTemplate("Account record Updated");
					res.getWriter().println(om.writeValueAsString(message));
				}
					else 
						throw new IllegalParameterException("Invalid request");
				break;
			default:
				throw new IllegalParameterException("Invalid request");
				
			}
		}catch(NotLoggedInException e) {
			res.setStatus(401);
			MessageTemplate message= new MessageTemplate("You are not logged in");
			res.getWriter().println(om.writeValueAsString(message));
		}catch(IllegalParameterException e){
			res.setStatus(400);
			MessageTemplate message= new MessageTemplate(e.getMessage());
			res.getWriter().println(om.writeValueAsString(message));
		
		} catch(AuthorizationException e) {	
			res.setStatus(401);
			MessageTemplate message= new MessageTemplate("You are not authorized");
			res.getWriter().println(om.writeValueAsString(message));
		}catch(NumberFormatException e) {
			//System.out.println("inside number excep");
			res.setStatus(400);
			MessageTemplate message= new MessageTemplate("Invalid request");
			res.getWriter().println(om.writeValueAsString(message));


		}
	
	}
}
