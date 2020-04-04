package com;

import model.Appointment;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;
@Path("/Items")
public class AppointmentServices
{
 Appointment AppointmentObj = new Appointment();
@GET
@Path("/")
@Produces(MediaType.TEXT_HTML)
public String readItems()
 {
 return AppointmentObj.readAppointments();
 }

//Test
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertAppointments(@FormParam("Date") String Date,
			@FormParam("Start_Time") String Start_Time, @FormParam("End_Time") String End_Time,@FormParam("Reason") String Reason,@FormParam("Duration") String Duration,@FormParam("Doctor_ID") String Doctor_ID) {
		String output = AppointmentObj.insertAppointments(Date, Start_Time, End_Time,Reason,Duration, Doctor_ID);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAppointments(String AppointmentData) {
		// Convert the input string to a JSON object
		JsonObject AppointmentObject = new JsonParser().parse(AppointmentData).getAsJsonObject();
		// Read the values from the JSON object
		String Appointment_ID = AppointmentObject.get("Appointment_ID").getAsString();
		String Date = AppointmentObject.get("Date").getAsString();
		String Start_Time = AppointmentObject.get("Start_Time").getAsString();
		String End_Time = AppointmentObject.get("End_Time").getAsString();
		String Reason = AppointmentObject.get("Reason").getAsString();
		String Duration = AppointmentObject.get("Duration").getAsString();
		String Doctor_ID = AppointmentObject.get("Doctor_ID").getAsString();
		
		String output = AppointmentObj.updateAppointments(Appointment_ID, Date, Start_Time, End_Time, Reason,Duration,Doctor_ID);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String AppointmentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(AppointmentData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String Appointment_ID = doc.select("Appointment_ID").text();
		String output = AppointmentObj.deleteAppointment(Appointment_ID);
		return output;
	}


}

