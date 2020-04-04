package model;

import java.sql.*;



public class Appointment { // A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthmanagement.sql", "root", ""); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertAppointments(String date,String stime,String etime,String reason,String duration,String doctor){
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into appointment(`Appointment_ID`, `Date`, `Start_Time`,`End_Time`,`Reason`,`Duration`,`Doctor_ID`)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, stime);
			preparedStmt.setString(4, etime);
			preparedStmt.setString(5, reason);
			preparedStmt.setString(6, duration);
			preparedStmt.setString(7, doctor);
			
//execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readAppointments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Date</th>"+"<th>Start_Time</th>"
					+ "<th>End_time</th>"+"<th>Reason</th>"+"<th>Duration</th>"+"<th>Doctor_ID</th>"+ "<th>Update</th><th>Remove</th></tr>";
			String query = "select * from appointment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
// iterate through the rows in the result set
			while (rs.next()) {
				String Appointment_ID = Integer.toString(rs.getInt("Appointment_ID"));
				String Date = rs.getString("Date");
				String Start_Time = rs.getString("Start_Time");
				String End_Time = rs.getString("End_Time");
				String Reason = rs.getString("Reason");
				String Duration = rs.getString("Duration");
				String Doctor_ID = rs.getString("Doctor_ID");
				
				
// Add into the html table
				output += "<tr><td>" + Date + "</td>";
				output += "<td>" + Start_Time + "</td>";
				output += "<td>" + End_Time + "</td>";
				output += "<td>" + Reason + "</td>";
				output += "<td>" + Duration + "</td>";
				output += "<td>" + Doctor_ID + "</td>";				
				
				
// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"Appointments.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
						+ "<input name=\"itemID\" type=\"hidden\" value=\"" + Appointment_ID + "\">" + "</form></td></tr>";
			}
			con.close();
// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the appointments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateAppointments(String ID, String date, String stime, String etime, String reason,String duration,String doctor) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
// create a prepared statement
			String query = "UPDATE appointment SET Date=?,Start_Time=?,End_Time=?,Reason=?,Duration=?,Doctor_ID=? WHERE Appointment_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setString(1, date);
			preparedStmt.setString(2, stime);
			preparedStmt.setString(3, etime);
			preparedStmt.setString(4, reason);
			preparedStmt.setString(5, duration);
			preparedStmt.setString(6, doctor);
			preparedStmt.setInt(7, Integer.parseInt(ID));
// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Appointment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteAppointment(String Appointment_ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
// create a prepared statement
			String query = "delete from appointment where Appointment_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, Integer.parseInt(Appointment_ID));
// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
} 