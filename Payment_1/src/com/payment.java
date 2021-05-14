package com;

import java.sql.*; 

public class payment {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	public String readItems() 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for reading."; 
	 } 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr> "
	 +"<th>UserID</th><th>Method</th>"
	 + "<th>price</th> "
	 +"<th>Update</th><th>Remove</th></tr>"; 
	 String query = "select * from paytable"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
	 String pid = Integer.toString(rs.getInt("pid")); 
	 String userid = rs.getString("userid"); 
	 String pMethod = rs.getString("pMethod");
	 String totalPrice = Double.toString( 
	 rs.getDouble("totalPrice")); 
	
	 // Add into the html table
	 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + pid
	 + "'>" + userid+ "</td>"; 
	 output += "<td>" + pMethod+ "</td>"; 
	 output += "<td>" + totalPrice + "</td>"; 
	
	//buttons
	output += "<td><input name='btnUpdate' "
	+"type='button' value='Update' "
	+"class='btnUpdate btn btn-secondary'></td>"
	+ "<td><input name='btnRemove' "
	+"type='button' value='Remove' "
	+"class='btnRemove btn btn-danger' "
	+"data-pid='"
	+ pid + "'>" + "</td></tr>"; } 
	 con.close(); 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while reading the items."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	
	//inserting
	public String insertItem( String userid, String pMethod, String totalPrice) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into paytable (`pid`,`userid`,`pMethod`,`totalPrice`)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2,userid);
			preparedStmt.setString(3, pMethod);
			preparedStmt.setDouble(4, Double.parseDouble(totalPrice));
		
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String updateItem(String pid, String userid, String pMethod, String totalPrice) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE paytable SET userid=?,pMethod=?,totalPrice=? WHERE pid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, userid);
			preparedStmt.setString(2, pMethod);
			preparedStmt.setDouble(3, Double.parseDouble(totalPrice));
			
			preparedStmt.setInt(4, Integer.parseInt(pid));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String deleteItem(String pid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from paytable where pid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pid));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}


}
