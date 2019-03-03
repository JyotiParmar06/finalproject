/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author yaadvir
 */
@Path("main")
public class GenericResource {

    @Context
    private UriInfo context;
    private Object sq;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    @GET
    @Path("Doctor")
    @Produces("application/json")
    public String getXml()  {
        JSONObject doctordata = new JSONObject();
       
      
        doctordata.accumulate("status","OK");
        
         doctordata.accumulate("TIMESTAMP",17985462);

          JSONArray mainJSON = new JSONArray();
          try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            String sql = "Select USER_ID,Firstname,Lastname,Specialization From Users WHERE USER_TYPE='Doctor' ";
            System.out.println();
            ResultSet rs = stm.executeQuery(sql);
            
            JSONObject singleUser = new JSONObject();
           int uid;
            String fname,lname ,specialization;
            
            while (rs.next()) {
               uid=rs.getInt("USER_ID");
                fname = rs.getString("Firstname");
                lname = rs.getString("Lastname");
                specialization = rs.getString("Specialization");
               
                singleUser.accumulate("USER_ID",uid);
            singleUser.accumulate("FirstName", fname);
            singleUser.accumulate("LastName", lname);
             singleUser.accumulate("Specialization", specialization);
             
             
            
                mainJSON.add(singleUser);
                doctordata.accumulate("singleUser", singleUser);

                singleUser.clear();
                mainJSON.clear();
           if(fname=="null"){
              doctordata .accumulate("STATUS", "WRONG");
            doctordata .accumulate("TIMESTAMP", 17985462);
             doctordata .accumulate("Message", "Wrong User Type");
             
           }
            }
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
             doctordata .accumulate("STATUS", "ERROR");
           doctordata .accumulate("TIMESTAMP", 17985462);
             doctordata .accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return doctordata.toString();

                        
}
    @GET
    @Path("Patient")
    @Produces("application/json")
    public String getXml1()  {
        JSONObject patientdata = new JSONObject();
       
      
        patientdata.accumulate("status","OK");
        
         patientdata.accumulate("TIMESTAMP",17985462);

          JSONArray mainJSON = new JSONArray();
          try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            String sql = "Select User_Id,Firstname,Lastname,Gender,Dateofbirth From Users WHERE USER_TYPE='Patient' ";
            System.out.println();
            ResultSet rs = stm.executeQuery(sql);
            
            JSONObject singleUser = new JSONObject();
           int userid;
            String fname,lname ,gender,dob;
            
            while (rs.next()) {
                userid = rs.getInt("user_id");
                fname = rs.getString("Firstname");
                lname = rs.getString("Lastname");
                dob = rs.getString("DateOfBirth");
               gender = rs.getString("Gender");
        
               singleUser.accumulate("User_id", userid);
            singleUser.accumulate("FirstName", fname);
            singleUser.accumulate("LastName", lname);
             singleUser.accumulate("DateOfBirth", dob);
             singleUser.accumulate("Gender", gender);
             
            
                mainJSON.add(singleUser);
                patientdata.accumulate("singleUser", singleUser);

                singleUser.clear();
                mainJSON.clear();
                if(fname=="null"){
              patientdata .accumulate("STATUS", "WRONG");
             patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Wrong User Type");
             
           }
            }
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata .accumulate("STATUS", "ERROR");
            patientdata  .accumulate("TIMESTAMP", 17985462);
             patientdata  .accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return patientdata.toString();

                        
}
    
     @GET
    @Path("PatientAppointment&{USER_ID}")
    @Produces("application/json")
    public String getJson2(@PathParam ("USER_ID") int uid)   {
        
     JSONObject patientdata = new JSONObject();
       
      

          JSONArray mainJSON = new JSONArray();
          try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            String sql = "Select PATIENT_ID, APPOINTMENT_TIME,APPOINTMENT_DATE,DOCTOR_ID, FIRSTNAME, LASTNAME, SPECIALIZATION\n" +
"FROM Users INNER JOIN Appointment ON Users.User_ID= Appointment.Doctor_ID\n" +
"WHERE Patient_ID='"+uid+"'";
            System.out.println();
            ResultSet rs = stm.executeQuery(sql);
            
            
        patientdata.accumulate("status","OK");
        
         patientdata.accumulate("TIMESTAMP",17985462);
            patientdata.accumulate("USER_ID",uid);
            
            JSONObject singleUser = new JSONObject();
         
            String fname,lname ,Apptime,spec,Appdate;
            
             int u_id=0;
            while (rs.next()) {
               u_id=rs.getInt("DOCTOR_ID");
               
                fname = rs.getString("Firstname");
                lname = rs.getString("Lastname");
               spec = rs.getString("SPECIALIZATION");
               Apptime = rs.getString("APPOINTMENT_TIME");
               Appdate = rs.getString("APPOINTMENT_DATE");
        
            
            singleUser.accumulate("DoctorFirstName", fname);
            singleUser.accumulate("DoctorLastName", lname);
             singleUser.accumulate("Specialization", spec);
             singleUser.accumulate("Appointment_Time", Apptime);
              singleUser.accumulate("Appointment_Date", Appdate);
             
            
                mainJSON.add(singleUser);
                patientdata.accumulate("Appointments", singleUser);

                singleUser.clear();
                mainJSON.clear();
                if(u_id==0){
                  patientdata .clear();   
                 patientdata .accumulate("STATUS", "WRONG");
             patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Wrong User Type");   
                }
            }
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
             patientdata .accumulate("STATUS", "ERROR");
            patientdata  .accumulate("TIMESTAMP", 17985462);
             patientdata  .accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return patientdata.toString();

                        
}
     @GET
    @Path("DoctorAppointment&{USER_ID}")
    @Produces("application/json")
    public String getJson1(@PathParam ("USER_ID") int uid)   {
        
     JSONObject patientdata = new JSONObject();
       
      

          JSONArray mainJSON = new JSONArray();
          try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            String sql = "Select APPOINTMENT_ID,DOCTOR_ID, APPOINTMENT_TIME,PATIENT_ID, FIRSTNAME, LASTNAME, APPOINTMENT_DATE\n" +
"FROM Users INNER JOIN Appointment ON Users.User_ID= Appointment.PATIENT_ID\n" +
"WHERE DOCTOR_ID='"+uid+"'";
            System.out.println();
            ResultSet rs = stm.executeQuery(sql);
            
            
        patientdata.accumulate("status","OK");
        
         patientdata.accumulate("TIMESTAMP",17985462);
            patientdata.accumulate("USER_ID",uid);
            
            JSONObject singleUser = new JSONObject();
         
            String fname,lname ,Apptime,Appdate,Appid;
            
            int u_id=0;
            while (rs.next()) {
               u_id=rs.getInt("Patient_ID");
               
                fname = rs.getString("Firstname");
                lname = rs.getString("Lastname");
             
               Apptime = rs.getString("APPOINTMENT_TIME");
               Appdate = rs.getString("APPOINTMENT_DATE");
                Appid = rs.getString("APPOINTMENT_ID");
            singleUser.accumulate("PatientUser_id", u_id);
            singleUser.accumulate("PatientFirstName", fname);
            singleUser.accumulate("PatientLastName", lname);
           
             singleUser.accumulate("Appointment_Time", Apptime);
             singleUser.accumulate("Appointment_Date", Appdate);
             singleUser.accumulate("Appointment_Id", Appid);
             
            
                mainJSON.add(singleUser);
                patientdata.accumulate("Appointments", singleUser);

                singleUser.clear();
                mainJSON.clear();
                  if(u_id==0){
                      patientdata.clear();
                 patientdata .accumulate("STATUS", "WRONG");
             patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Wrong User Type");   
                  }
            }
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
             patientdata .accumulate("STATUS", "ERROR");
            patientdata  .accumulate("TIMESTAMP", 17985462);
             patientdata  .accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return patientdata.toString();

                        
}
   @GET
    @Path("doctorprofile&{USER_ID}")
    @Produces("application/json")
    public String getJson3(@PathParam ("USER_ID") int uid)   {
         JSONObject profile = new JSONObject();
       
            try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select USER_ID,FIRSTNAME, LASTNAME, GENDER,SPECIALIZATION from USERS WHERE USER_ID=" + uid);
            
               System.out.println(rs);
           
            String fname,lname,gen,spec;
             
            int u_id=0;
            while (rs.next()) {
               u_id=rs.getInt("USER_ID");
      
        fname = rs.getString("firstname");
         lname = rs.getString("lastname");
        gen = rs.getString("GENDER");
         spec = rs.getString("SPECIALIZATION");
          
        
                  
        
              
          profile.accumulate("STATUS","OK");
          profile.accumulate("Timestamp",1234567890);
          profile.accumulate("Firstname",fname);
          profile.accumulate("Lastname",lname);
          profile.accumulate("GENDER",gen);
          profile.accumulate("SPECIALIZATION",spec);
            
          if(u_id==0){
              profile.clear();
                profile.accumulate("STATUS", "WRONG");
             profile.accumulate("TIMESTAMP", 17985462);
              profile.accumulate("Message", "Wrong User Type");   
                  }
              }
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
             profile .accumulate("STATUS", "ERROR");
           profile  .accumulate("TIMESTAMP", 17985462);
            profile .accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return profile.toString();

                        
}

     @GET
    @Path("patientprofile&{USER_ID}")
    @Produces("application/json")
    public String getJson4(@PathParam ("USER_ID") int uid)   {
         JSONObject profile = new JSONObject();
          
            try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select USER_ID,FIRSTNAME, LASTNAME, GENDER,DATEOFBIRTH from USERS WHERE USER_ID='" + uid+"'");
            
               System.out.println(rs);
           
            String fname,lname,gen,dob;
            
            int u_id;
            while (rs.next()) {
               u_id=rs.getInt("USER_ID");
      
        fname = rs.getString("FIRSTNAME");
         lname = rs.getString("LASTNAME");
        gen = rs.getString("GENDER");
       dob= rs.getString("DATEOFBIRTH");
          
        
                  
        
              
          profile.accumulate("STATUS","OK");
          profile.accumulate("Timestamp",1234567890);
          profile.accumulate("Firstname",fname);
          profile.accumulate("Lastname",lname);
          profile.accumulate("GENDER",gen);
          profile.accumulate("DATEOFBIRTH",dob);
          
           if(u_id==0){
               profile.clear();
                profile.accumulate("STATUS", "WRONG");
             profile.accumulate("TIMESTAMP", 17985462);
              profile.accumulate("Message", "Wrong User Type");   
                  }
            
              }
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
             profile .accumulate("STATUS", "ERROR");
            profile  .accumulate("TIMESTAMP", 17985462);
             profile  .accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return profile.toString();

                        
}
 @GET
    @Path("Familyreport&{USER_ID}")
    @Produces("application/json")
    public String getJson5(@PathParam ("USER_ID") int uid)   {
        
     JSONObject patientdata = new JSONObject();
       
      

          JSONArray mainJSON = new JSONArray();
          try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            String sql = "SELECT USER_ID1, USER_ID2, FIRSTNAME, LASTNAME, NAMEOFRELATION\n" +
"FROM USERS INNER JOIN  RELATIONTYPE ON USERS.USER_ID=RELATIONTYPE.USER_ID2\n" +
"WHERE USER_ID2 ='"+uid+"'";
            System.out.println();
            ResultSet rs = stm.executeQuery(sql);
            
            
        patientdata.accumulate("status","OK");
        
         patientdata.accumulate("TIMESTAMP",17985462);
            
            
            JSONObject singleUser = new JSONObject();
            int uid2;
            String fname,lname ,NameofRelation;
            
            int u_id=0;
            while (rs.next()) {
               u_id=rs.getInt("USER_ID1");
                uid2=rs.getInt("USER_ID2");
                fname = rs.getString("Firstname");
                lname = rs.getString("Lastname");
             
               NameofRelation = rs.getString("NAMEOFRELATION");
        
            singleUser.accumulate("User_id", uid2);
            singleUser.accumulate("FirstName", fname);
            singleUser.accumulate("LastName", lname);
           
             singleUser.accumulate("NAMEOFRELATION",  NameofRelation);
             
            
                mainJSON.add(singleUser);
               

                singleUser.clear();
               
                
                  if(u_id==0){
                      patientdata.clear();
               patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
             patientdata.accumulate("Message", "Wrong User Type");   
                  }
            }
            patientdata.accumulate("FamilyMember", mainJSON);
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
          patientdata  .accumulate("TIMESTAMP", 17985462);
            patientdata  .accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return patientdata.toString();

                        
}
  @GET
    @Path("healthsummary&{USER_ID}")
    @Produces("application/json")
    public String getJson6(@PathParam ("USER_ID") int uid)   {
        
     JSONObject patientdata = new JSONObject();
     
       JSONArray mainJSON = new JSONArray();
          try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            String sql = "SELECT FIRSTNAME, USER_ID , LASTNAME, GENDER, TEST_NAME, TESTRESULT "
                    + "FROM TESTRESULT NATURAL JOIN TREATEMENT  NATURAL JOIN APPOINTMENT INNER JOIN USERS ON APPOINTMENT.PATIENT_ID= USERS.USER_ID"
                    + " WHERE USER_ID='"+uid+"'";
            System.out.println();
            ResultSet rs = stm.executeQuery(sql);
            
         JSONObject singleUser = new JSONObject();
         
          patientdata.accumulate("status","OK");
        
         patientdata.accumulate("TIMESTAMP",17985462);
           
           
            String fname ,lname,gen ,testname ,testresult;
             
            int u_id=0;
            while (rs.next()) {
               u_id=rs.getInt("USER_ID");
              uid=rs.getInt("USER_ID");
              fname = rs.getString("Firstname");
             lname = rs.getString("Lastname");
             gen = rs.getString("GENDER");
              testname = rs.getString("TEST_NAME");
              testresult = rs.getString("TESTRESULT");
            
             
        
       singleUser.accumulate("USER_ID",uid);
         singleUser.accumulate("FirstName", fname);
         singleUser.accumulate("LastName", lname);
         singleUser.accumulate("Gender", gen);
              singleUser.accumulate("TESTNAME",testname);
              singleUser.accumulate("TESTRESULT",testresult);
             
            
              mainJSON.add(singleUser);
              patientdata.accumulate("TESTS", singleUser);

            singleUser.clear();
            mainJSON.clear();
            
                  if(u_id==0){
                      patientdata.clear();
               patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
             patientdata.accumulate("Message", "Wrong User Type");   
                  }
             }
           
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
          patientdata  .accumulate("TIMESTAMP", 17985462);
            patientdata  .accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return patientdata.toString();
    }

                        

    
     @GET
    @Path("inbox&{USER_ID}")
    @Produces("application/json")
    public String getJson(@PathParam("USER_ID") int uid)   {
         JSONObject newdata = new JSONObject();
         
     
          JSONArray mainJSON = new JSONArray();
          try {
            Class.forName("oracle.jdbc.OracleDriver");

             Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("Select USER_ID, FIRSTNAME,content \n" +
"                   FROM USERS INNER JOIN MESSAGE ON USERS.USER_ID=MESSAGE.USER_ID_SENDER \n" +
"                  WHERE USER_ID_RECEIVER=" +uid);

              newdata.accumulate("status","OK");
         newdata.accumulate("Timestamp",1567894563);
            JSONObject singleUser = new JSONObject();
        
            String Content,fname;
                    int u_id=0;
            while (rs.next()) {
               u_id=rs.getInt("USER_ID");
                fname = rs.getString("Firstname");
               
                Content = rs.getString("CONTENT");
               
                 singleUser.accumulate("FirstName", fname);
            singleUser.accumulate("CONTENT", Content);
             
            
             
             
            
                
              mainJSON.add(singleUser);
             

            singleUser.clear();
            
                
                
                 
            }
             if(u_id==0){
                 newdata.clear();
                newdata.accumulate("STATUS", "WRONG");
            newdata.accumulate("TIMESTAMP", 17985462);
             newdata.accumulate("Message", "Wrong User Type");   
                  }
              newdata.accumulate("List", mainJSON);
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
             newdata.accumulate("STATUS", "ERROR");
         newdata .accumulate("TIMESTAMP", 17985462);
            newdata .accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return newdata.toString();
         

                        
}
    @GET
    @Path("prescription&{USER_ID}")
    @Produces("application/json")
    public String getJson8(@PathParam ("USER_ID") int uid)   {
         JSONObject  patientdata = new JSONObject();
          
         
         
      
            JSONArray mainJSON = new JSONArray();
           
            try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT USER_ID,NAMEOFMEDICINE,DOSE,TIMING\n" +
"FROM MEDICINE NATURAL JOIN DETAIL NATURAL JOIN PRESCRIPTION NATURAL JOIN TREATEMENT  NATURAL JOIN APPOINTMENT INNER JOIN USERS ON APPOINTMENT.PATIENT_ID= USERS.USER_ID\n" +
"WHERE USER_ID='"+uid+"'");
            
               System.out.println(rs);
                  
         patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           
                       JSONObject med = new JSONObject();
           int dose;
            String medname,time;
            
              int u_id=0;
            
              while (rs.next()) {
                u_id=rs.getInt("USER_ID");
                medname = rs.getString("NAMEOFMEDICINE");
                dose = rs.getInt("DOSE");
                time= rs.getString("TIMING");
          
        
                  
        med.accumulate("USER_ID",uid);
      
            med.accumulate("NAMEOFMEDICINE",medname);
            med.accumulate("DOSE",dose);
            med.accumulate("TIMING",time);
                          
                mainJSON.add(med);
                
            
                    med.clear();
           
               
                  if(u_id==0){
                      patientdata.clear();
               patientdata.accumulate("STATUS", "WRONG");
           patientdata.accumulate("TIMESTAMP", 17985462);
             patientdata.accumulate("Message", "Wrong User Type");   
             
                  }
            
                 patientdata.accumulate("Prescription", mainJSON);
              }
             
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
         patientdata .accumulate("TIMESTAMP", 17985462);
            patientdata .accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return  patientdata.toString();

                        
}
    @GET
   @Path("userlogin&{USER_ID}&{PASSWORD}")
    @Produces("application/json")
    public String getJson6(@PathParam ("USER_ID") int uid ,@PathParam("PASSWORD") String Pass)  {
       
        JSONObject profile = new JSONObject();
          try {
            Class.forName("oracle.jdbc.OracleDriver");

             Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            
  String fname,user_type="";
            
             int u_id=0;
              System.out.println("select FIRSTNAME,USER_ID,PASSWORD,USER_TYPE from USERS WHERE USER_ID='" + uid+ "'And PASSWORD='"+Pass+"'");
            ResultSet rs = stm.executeQuery("select FIRSTNAME,USER_ID,PASSWORD,USER_TYPE from USERS WHERE USER_ID='" + uid+ "'And PASSWORD='"+Pass+"'");
        
            while (rs.next()) {
               u_id=rs.getInt("USER_ID");
            user_type=rs.getString("USER_TYPE");
               
      
        fname = rs.getString("FIRSTNAME");
       
        Pass= rs.getString("PASSWORD");
       
           
        
                  
        
              
          profile.accumulate("STATUS","OK");
          profile.accumulate("Timestamp",1234567890);
          profile.accumulate("Firstname",fname);
          profile.accumulate ("USER_ID",u_id);
          profile.accumulate("PASSWORD",Pass);
           profile.accumulate("USER_TYPE",user_type);
   
                  if(u_id==0){
                      profile.clear();
                profile.accumulate("STATUS", "WRONG");
            profile.accumulate("TIMESTAMP", 17985462);
             profile.accumulate("Message", "Check your userid or password");   
                  }
            
              } 
            // rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
              profile.accumulate("STATUS", "ERROR");
          profile .accumulate("TIMESTAMP", 17985462);
             profile.accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return profile.toString();

                        
}
     @GET
    @Path("patientupcomingappointment&{USER_ID}")
    @Produces("application/json")
    public String getJson7(@PathParam ("USER_ID") int uid)   {
         JSONObject  patientdata = new JSONObject();
          
         
         
         patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("USER_ID",uid);
            JSONArray mainJSON = new JSONArray();
           
            try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT PATIENT_ID,DOCTOR_ID,FIRSTNAME,LASTNAME,APPOINTMENT_TIME,APPOINTMENT_DATE\n" +
"FROM APPOINTMENT INNER JOIN USERS ON APPOINTMENT.DOCTOR_ID=USERS.USER_ID\n" +
"WHERE PATIENT_ID='"+uid+"'");
            
               System.out.println(rs);
                       JSONObject app = new JSONObject();
           int did;
            String fname,lname,date,apptime;
            
             int u_id=0;
            while (rs.next()) {
               u_id=rs.getInt("USER_ID");
      did=rs.getInt("DOCTOR_ID");
        fname = rs.getString("FIRSTNAME");
         lname = rs.getString("LASTNAME");
         date = rs.getString("APPOINTMENT_DATE");
     
      apptime= rs.getString("APPOINTMENT_TIME");
          
        
                  
        
           app.accumulate("DOCTOR_ID",did);
           app.accumulate("FIRSTNAME",fname);
            app.accumulate("LASTNAME",lname);
          app.accumulate("APPOINTMENT_DATE",date);
          
          app.accumulate("APPOINTMENT_TIME",apptime);
                          
                mainJSON.add(app);
                patientdata.accumulate("Appointment", mainJSON);

               app.clear();
                patientdata.clear();
              if(u_id==0){
                  patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Wrong User Type");   
                  }
              }
              
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return  patientdata.toString();

                        
}
    
     @GET
    @Path("doctorupcomingappointment&{USER_ID}")
    @Produces("application/json")
    public String getJson9(@PathParam ("USER_ID") int uid)   {
         JSONObject  patientdata = new JSONObject();
          
         
         
         patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("USER_ID",uid);
            JSONArray mainJSON = new JSONArray();
           
            try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT PATIENT_ID,DOCTOR_ID,FIRSTNAME,LASTNAME,APPOINTMENT_TIME,APPOINTMENT_DATE\n" +
"FROM APPOINTMENT INNER JOIN USERS ON APPOINTMENT.PATIENT_ID=USERS.USER_ID\n" +
"WHERE DOCTOR_ID='"+uid+"'");
            
               System.out.println(rs);
                       JSONObject app = new JSONObject();
           int did;
            String fname,lname,date,apptime;
            
             int u_id=0;
            while (rs.next()) {
               u_id=rs.getInt("USER_ID");
      did=rs.getInt("DOCTOR_ID");
        fname = rs.getString("FIRSTNAME");
         lname = rs.getString("LASTNAME");
         date = rs.getString("APPOINTMENT_DATE");
     
      apptime= rs.getString("APPOINTMENT_TIME");
          
        
                  
        
           app.accumulate("PATIENT_ID",did);
           app.accumulate("FIRSTNAME",fname);
            app.accumulate("LASTNAME",lname);
          app.accumulate("APPOINTMENT_DATE",date);
          
          app.accumulate("APPOINTMENT_TIME",apptime);
                          
                mainJSON.add(app);
                
            patientdata.accumulate("Appointment", mainJSON);
               app.clear();
            patientdata.clear();
            
            if(u_id==0){
                patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Wrong User Type");   
                  }
              }
             
             rs.close();
            stm.close();
            con.close();
            } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         return  patientdata.toString();

                        
}
      
      
    @GET
    @Path("Registration&{USER_ID}&{FIRSTNAME}&{LASTNAME}&{PASSWORD}&{SPECIALIZATION}&{DATEOFBIRTH}&{USER_TYPE}&{GENDER}")
    @Produces("application/json")
    public String getJsonregister(@PathParam("USER_ID") int uid , @PathParam("FIRSTNAME") String fname, @PathParam("LASTNAME") String lname 
            ,@PathParam("PASSWORD") String pass ,@PathParam("SPECIALIZATION") String spec ,@PathParam("DATEOFBIRTH") String dob ,
      @PathParam("USER_TYPE") String user_type, @PathParam("GENDER") String gen ) {
      
        JSONObject  patientdata = new JSONObject();
        try {
           Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
           
                String abc="INSERT INTO  USERS VALUES("+ uid+ ",'" + fname + "',' " +lname  +"','" +pass+ "','" +spec+"','" +dob+"','" +user_type+ "','" +gen+  "')";
           System.out.println(abc);
           
            int number = stm.executeUpdate(abc);
            
         System.out.println(number);
            if(number==1){
             patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("Message","Registered");
            }
             if(number==0){
                patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Wrong User Type");   
                  }
       
       stm.close();
            con.close();
    }catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return  patientdata.toString();
    }

   

 @GET
    @Path("UpdateHealthSummary&{TREATEMENT_ID}&{TREATEMENT_TYPE}&{APPOINTMENT_ID}&{DESCRIPTIONOFTREATEMENT}")
    @Produces("application/json")
    public String getJson10(@PathParam("TREATEMENT_ID") int tid , @PathParam("TREATEMENT_TYPE") String type, @PathParam("APPOINTMENT_ID") int Aid 
            ,@PathParam("DESCRIPTIONOFTREATEMENT") String des ) {
      
        JSONObject  patientdata = new JSONObject();
        try {
           Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
           
                String abc="INSERT INTO  TREATEMENT VALUES("+ tid+ ",'" + type + "',' " +Aid  +"','" +des+ "')";
           System.out.println(abc);
           
            int number = stm.executeUpdate(abc);
            
         System.out.println(number);
            if(number==1){
             patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("Message","updated");
            }
             if(number==0){
                patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Try Again");   
                  }
       
       stm.close();
            con.close();
    }catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return  patientdata.toString();
    }
    
     @GET
    @Path("bookappointment&{APPOINTMENT_ID}&{PATIENT_ID}&{DOCTOR_ID}&{APPOINTMENT_TIME}&{APPOINTMENT_DATE}")
    @Produces("application/json")
    public String getJson11(@PathParam("APPOINTMENT_ID") int aid , @PathParam("PATIENT_ID") int pid, @PathParam("DOCTOR_ID") int did 
            ,@PathParam("APPOINTMENT_TIME") String time ,  @PathParam("APPOINTMENT_DATE") String date ) {
      
        JSONObject  patientdata = new JSONObject();
        try {
           Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
           
                String abc="INSERT INTO APPOINTMENT VALUES("+ aid+ "," + pid + ", " +did +",'" +time+ "','" +date+ "')";
           System.out.println(abc);
            System.out.println("INSERT INTO APPOINTMENT VALUES('"+ aid+ "','" + pid + "',' " +did +"','" +time+ "','" +date+ "')");
            int number = stm.executeUpdate(abc);
            
         System.out.println(number);
            if(number==1){
             patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("Message","updated");
            }
             if(number==0){
                patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Try Again");   
                  }
       
       stm.close();
            con.close();
    }catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return  patientdata.toString();
    }
    @GET
    @Path("UpdateDetail&{MEDICINE_ID}&{DETAIL_ID}&{DOSE}&{TIMING}")
    @Produces("application/json")
    public String getJson12(@PathParam("MEDICINE_ID") int mid , @PathParam("DETAIL_ID") int detail, @PathParam("DOSE") int dose
            ,@PathParam("TIMING") String time ) {
      
        JSONObject  patientdata = new JSONObject();
        try {
           Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
           
                String abc="INSERT INTO  DETAIL VALUES("+ mid+ ",'" + detail + "',' " +dose  +"','" +time+ "')";
           System.out.println(abc);
           
            int number = stm.executeUpdate(abc);
            
         System.out.println(number);
            if(number==1){
             patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("Message","updated");
            }
             if(number==0){
                patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Try Again");   
                  }
       
       stm.close();
            con.close();
    }catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return  patientdata.toString();
    }
         
     @GET
    @Path("updateMedicine&{MEDICINE_ID}&{NAMEOFMEDICINE}")
    @Produces("application/json")
    public String getJson13(@PathParam("MEDICINE_ID") int mid ,  @PathParam("NAMEOFMEDICINE") String medicinename ) {
      
        JSONObject  patientdata = new JSONObject();
        try {
           Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
           
                String abc="INSERT INTO MEDICINE VALUES("+ mid+ ",'" + medicinename + "')";
           System.out.println(abc);
           
            int number = stm.executeUpdate(abc);
            
         System.out.println(number);
            if(number==1){
             patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("Message","updated");
            }
             if(number==0){
                patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Try Again");   
                  }
       
       stm.close();
            con.close();
    }catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return  patientdata.toString();
    }
     @GET
    @Path("SendMessage&{MESSAGE_ID}&{USER_ID_SENDER}&{USER_ID_RECEIVER}&{CONTENT}")
    @Produces("application/json")
    public String getJson14(@PathParam("MESSAGE_ID") int mid , @PathParam("USER_ID_SENDER") int uidsender, @PathParam("USER_ID_RECEIVER") int uidreceiver
            ,@PathParam("CONTENT") String content ) {
      
        JSONObject  patientdata = new JSONObject();
        try {
           Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
           
                String abc="INSERT INTO MESSAGE VALUES("+ mid+ ",'" + uidsender + "',' " +uidreceiver  +"','" +content+ "')";
           System.out.println(abc);
           
            int number = stm.executeUpdate(abc);
            
         System.out.println(number);
            if(number==1){
             patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("Message","message sent");
            }
             if(number==0){
                patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Try Again");   
                  }
       
       stm.close();
            con.close();
    }catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return  patientdata.toString();
    }
    
     @GET
    @Path("Prescription&{TREATEMENT_ID}&{PRESCRIPTION_ID}&{PRESCRIPTION_TIME}")
    @Produces("application/json")
    public String getJson15(@PathParam("TREATEMENT_ID") int tpid, @PathParam("PRESCRIPTION_ID") int pid, @PathParam("PRESCRIPTION_TIME") int ptime) {
      
        JSONObject  patientdata = new JSONObject();
        try {
           Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
           
                String abc="INSERT INTO  PRESCRIPTION VALUES("+ tpid+ "," + pid + ", " +ptime  +")";
           System.out.println(abc);
           
            int number = stm.executeUpdate(abc);
            
         System.out.println(number);
            if(number==1){
             patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("Message","Updated");
            }
             if(number==0){
                patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Try Again");   
                  }
       
       stm.close();
            con.close();
    }catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return  patientdata.toString();
    }
    
    
    
     @GET
    @Path("RelationType&{NAMEOFRELATION}&{USER_ID1}&{USER_ID2}")
    @Produces("application/json")
    public String getJson16( @PathParam("NAMEOFRELATION") String relationname,@PathParam("USER_ID1") int uid1, @PathParam("USER_ID2") int uid2) {
      
        JSONObject  patientdata = new JSONObject();
        try {
           Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
           
                String abc="INSERT INTO RELATIONTYPE VALUES("+ relationname+ ",'" + uid1 + "',' " + uid2 +"')";
           System.out.println(abc);
           
            int number = stm.executeUpdate(abc);
            
         System.out.println(number);
            if(number==1){
             patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("Message","Updated");
            }
             if(number==0){
                patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Try Again");   
                  }
       
       stm.close();
            con.close();
    }catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return  patientdata.toString();
    }
    
     @GET
    @Path("UpdateTestresult&{TREATEMENT_ID}&{TEST_NAME}&{TESTRESULT}&{DESCRIPTIONOFTESTRESULT}")
    @Produces("application/json")
    public String getJson15(@PathParam("TREATEMENT_ID") int tid , @PathParam("TEST_NAME") String testname, @PathParam("TESTRESULT") String testresult
            ,@PathParam("DESCRIPTIONOFTESTRESULT") String description ) {
      
        JSONObject  patientdata = new JSONObject();
        try {
           Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "mad304p2", "mad303p2pw");

            java.sql.Statement stm = con.createStatement();
           
                String abc="INSERT INTO TESTRESULT VALUES("+ tid+ ",'" + testname + "',' " +testresult  +"','" +description+ "')";
           System.out.println(abc);
           
            int number = stm.executeUpdate(abc);
            
         System.out.println(number);
            if(number==1){
             patientdata.accumulate("status","OK");
         patientdata.accumulate("TIMESTAMP",17985462);
           patientdata.accumulate("Message"," updated");
            }
             if(number==0){
                patientdata.clear();
                patientdata.accumulate("STATUS", "WRONG");
            patientdata.accumulate("TIMESTAMP", 17985462);
            patientdata.accumulate("Message", "Try Again");   
                  }
       
       stm.close();
            con.close();
    }catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            patientdata.accumulate("STATUS", "ERROR");
           patientdata .accumulate("TIMESTAMP", 17985462);
              patientdata.accumulate("Message", "Database Connection Failed");
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return  patientdata.toString();
    }
}            
               
               
               
               
               
               
               
               
               
               
               
               
               
               
   