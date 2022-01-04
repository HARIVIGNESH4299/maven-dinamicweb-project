package com.pizzahut.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.Dao.Admindao;
import com.Dao.Cartdao;
import com.Dao.Invoicebilldao;
import com.Dao.Orderdao;
import com.Dao.Productdao;
import com.Dao.Userdao;
import com.model.Admin;
import com.model.Cart;
import com.model.Invoicebill;
import com.model.Order;
import com.model.Product;
import com.model.User;

public class Textmain {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Userdao udao=new Userdao();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-mm-yyyy");
		Scanner scan = new Scanner(System.in);
		System.out.println("\n1.Register\n2.Login\n3.adminlogin \nEnter your chioce");
		int choice = Integer.parseInt(scan.nextLine());
		Userdao currentUser = null;
		Admindao currentadmin = null;
		switch (choice) {
		case 1:
			String username = null;
			long phonenumber = 0;
			String email = null;
			String address = null;
			String password = null;
			do {
				System.out.println("enter the user name");
				username = scan.nextLine();
				if (!username.matches("[a-zA-Z]{3,}")) {
					System.out.println("must be provide name in above 3 leters");
				}
				if (username.isEmpty()) {
					System.out.println("must be provide name");
				}
			} while (!username.matches("[a-zA-Z]{3,}"));
			String tempphonenumber = null;
			do {
				System.out.println("enter the phone number");
				tempphonenumber = scan.nextLine();
				if (!tempphonenumber.matches("[6-9]{1}[0-9]{9}")) {
					System.out.println("must be provide 10 values");
				}
				if (tempphonenumber.isEmpty()) {
					System.out.println("must be provide phone numnber");
				}
			} while (!tempphonenumber.matches("[6-9]{1}[0-9]{9}"));
			phonenumber = Long.parseLong(tempphonenumber);
			do {
				System.out.println("enter the email");
				email = scan.nextLine();
				if (!email.matches("[a-z]+[a-z0-9]*[@][a-z]+[.][a-z]{2,}")) {
					System.out.println("must be provide valide emailid");
				}
				if (email.isEmpty()) {
					System.out.println("must be provide email");
				}
			} while (!email.matches("[a-z]+[a-z0-9]*[@][a-z]+[.][a-z]{2,}"));
			do {
				System.out.println("enter the address");
				address = scan.nextLine();
				if (address.isEmpty()) {
					System.out.println("must be enter address");
				}
			} while (address.isEmpty());
			do {
				System.out.println("enter the password");
				password = scan.nextLine();
				if (!password.matches("[A-Za-z0-9]+[@][A-za-z0-9]*")) {
					System.out.println("must be provide 8 characters and use some special characters for safty purpose");
				}
				if (password.isEmpty()) {
					System.out.println("must be provide password");
				}
			} while (!password.matches("[A-Za-z0-9]+[@][A-Za-z0-9]*"));
			User user = new User(username, phonenumber, email, address, null,password);
			Userdao userdao=new Userdao();
			userdao.insert(user);
			
		case 2:
			user = new User();
			do {
				System.out.println("user login");
				System.out.println("\nenter the register emailid");
				String emailid = scan.nextLine();
				System.out.println("\nenter the currect password");
				String password1 = scan.nextLine();
				currentUser = new Userdao();
				user = currentUser.validateUser(emailid, password1);
				if (user != null) {
					System.out.println("register successed " + user.getUsername());
				} else {
					System.out.println("invalid entry");
				}				
			}while(user==null);
			System.out.println("list of products");
			Productdao proDao1 = new Productdao();
			List<Product> Products1 = proDao1.showProduct();
			for (int i = 0; i < Products1.size(); i++) {
				System.out.println(Products1.get(i));
			}
				System.out.println("\n1.order products\n2.view orders\n3.update order\n4.Cancel order\n5.view cart\n6.recharge wallet");
				int choice3=Integer.parseInt(scan.nextLine());
				switch(choice3)		{				
				//order product
				case 1:					
					char stop;
					do {
					 System.out.println("enter product name ");
						String proname1=scan.nextLine();
						System.out.println("enter product size ");
						String prosize1=scan.nextLine();
						Productdao proDao2=new Productdao();
					    Product product=proDao2.findProduct(proname1, prosize1);
					    System.out.println(product);
//					    int productid1=proDao2.findProductId(product2);
//					    System.outprintln(productid1);					    
					    System.out.println("enter the mailId to find user");
						String useremail=scan.nextLine();
						Userdao userdao1 =new Userdao();
						User user1=userdao1.finduser(useremail);
						System.out.println(user1);
//						int userid1=userdao1.finduserid(user);
//						System.out.println(userid1);
//						
						System.out.println("enter the quantity");
						int quantity=Integer.parseInt(scan.nextLine());			
						System.out.println(" the date");
						Date today=new Date();		
						Order order=new Order(user1,product,quantity,null, today);
						Orderdao orderdao=new Orderdao();
						orderdao.orderproduct(order);
						System.out.println("order success");
						System.out.println("you need extra (y/n)");
						stop=scan.nextLine().charAt(0);
					}while(stop =='Y' || stop =='y');	
					System.out.println("invoice bill");
					
					
					Invoicebilldao invoicedao=new Invoicebilldao();
		            ResultSet rs= invoicedao.showBill(user);
		            System.out.println("InvoiceBill");
		            System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n","userName","mobileno","email","address","productid","quantity","price","date");
		            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
		 			try {
		 				while(rs.next()) {
		 					System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n",rs.getString(1),rs.getLong(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getDouble(7),rs.getDate(8));
		 					
		 				}
		 			} catch (SQLException e) {
		 				// TODO Auto-generated catch block
		 				e.printStackTrace();
		 			}
//		            try {
//						while (rs.next()) {
//							
//							rs.get
//						}
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
		 			break;
					//list orders
				case 2:	
						Orderdao orderDao = new Orderdao();
						System.out.println("list of orders");
						List<Order> Orders = orderDao.showorder();
						for (int i = 0; i < Orders.size(); i++) {
							System.out.println(Orders.get(i));
						}
						break;						
				case 5:
					 System.out.println("enter product name ");
						String proname1=scan.nextLine();
						System.out.println("enter product size ");
						String prosize1=scan.nextLine();
						Productdao proDao2=new Productdao();
					    Product product=proDao2.findProduct(proname1, prosize1);
					    System.out.println(product);
//					    int productid1=proDao2.findProductId(product);
//					    System.out.println(productid1);
					    System.out.println("enter the mailId to find user");
						String useremail=scan.nextLine();
						Userdao userdao1 =new Userdao();
						User user1=userdao1.finduser(useremail);
						System.out.println(user1);
//						int userid1=userdao1.finduserid(user);
//						System.out.println(userid1);
						System.out.println("enter the quantity");
						int quantity=Integer.parseInt(scan.nextLine());										
						Cart cart=new Cart(user,product,quantity,null);
						Cartdao cartdao=new Cartdao();
						cartdao.insertCart(cart);
						
						System.out.println("----------------------");
						System.out.println("list cart");
						Cartdao cartdao1=new Cartdao();
						
						List<Cart> carts = cartdao1.showcart();
						for (int i = 0; i < carts.size(); i++) {
							System.out.println(carts.get(i));
						}	
						System.out.println("you go to add somethings (y/n)");
						stop=scan.nextLine().charAt(0);					
				}	
		case 6:
				System.out.println("recharge your wallet");
				System.out.println("enter user email");
				String useremail=scan.nextLine();
				System.out.println("recharge amount");
				Double wallet=Double.parseDouble(scan.nextLine());
				Userdao userdao1 =new Userdao();
				Double user1=userdao1.update(wallet, useremail);
				System.out.println(user1);			
				break;

		// admin login details
		case 3:
			Admin admin = null;
			do {
				System.out.println("Admin login");
				System.out.println("\nenter the register emailid");
				String email1 = scan.nextLine();
				System.out.println("\nenter the  password");
				String pass = scan.nextLine();
				admin = new Admin();
				currentadmin = new Admindao();
				admin = currentadmin.validateadmin(email1, pass);
				if (admin != null) {
					System.out.println("welcome " + admin.getAdminname());
					System.out.println("\n1.add products\n2.update products\n3.delete products\n4.view user orders");
					int choice1 = Integer.parseInt(scan.nextLine());
					String productname = null;
					String size = null;
					Double price = 0.0;
					
					//add product
					switch (choice1) {
					case 1:
						char stop;
						do {
						
							do {
							System.out.println("enter the product name");
							productname = scan.nextLine();
							if (productname.isEmpty()) {
								System.out.println("must be provide product name");
							}
						} while (productname.isEmpty());
						do {
							System.out.println("enter the product size");
							size = scan.nextLine();
							if (size.isEmpty()) {
								System.out.println("must be provide product size");
							}
						} while (size.isEmpty());

						String tempprice = null;
						do {
							System.out.println("enter the product price");
							tempprice = scan.nextLine();
							if (tempprice.isEmpty()) {
								System.out.println("must be provide product price");
							}
						} while (tempprice.isEmpty());
						price = Double.parseDouble(tempprice);
						Product product = new Product(productname, size, price);
						Productdao productdao=new Productdao();
						productdao.insertproduct(product);
						System.out.println("you need to add another product(y/n)");
						stop=scan.nextLine().charAt(0);
					}while(stop =='Y' || stop =='y');
						
					break;
						//update product
					case 2:
						Productdao proDao = new Productdao();
						System.out.println("list of products");
						List<Product> Products = proDao.showProduct();
						for (int i = 0; i < Products.size(); i++) {
							System.out.println(Products.get(i));
						}	
						do {
							
						System.out.println("change productname");
						String name = scan.nextLine();
						System.out.println("change productsize");
						String size1 = scan.nextLine();
						System.out.println("change price");
						Double price1 = Double.parseDouble(scan.nextLine());
						System.out.println("change product id");
						int  productid=Integer.parseInt(scan.nextLine());
					
						int prodid=proDao.updated(name,size1,price1,productid);						

						System.out.println("you need to update any other products price(y/n)");
						stop=scan.nextLine().charAt(0);
						}while(stop =='Y' || stop =='y');
						break;
						
						//delete product
					case 3:
						do {
						Productdao proDao11 = new Productdao();
						System.out.println("change product id");
						int  productid=Integer.parseInt(scan.nextLine());
						int prod1d=proDao11.delete(productid);
						
						System.out.println("dp you went to delete any other product(y/n)");							
						stop=scan.nextLine().charAt(0);
					}while(stop =='Y' || stop =='y');
						break;	
					case 4:
						Orderdao orderDao = new Orderdao();
						System.out.println("list of orders");
						List<Order> Orders = orderDao.showorder();
						for (int i = 0; i < Orders.size(); i++) {
						System.out.println(Orders.get(i));
						}
					}
					break;
				} else {
					System.out.println("invalid entry");
				}
			} while (admin == null);
			
		}
	}
}