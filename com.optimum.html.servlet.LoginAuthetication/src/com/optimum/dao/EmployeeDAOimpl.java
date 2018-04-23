package com.optimum.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.optimum.connection.DatabaseUtility;
import com.optimum.connection.SendEmail;
import com.optimumn.pojo.Employee;

public class EmployeeDAOimpl implements EmployeeDAO {

	private boolean loginStatus;
	private Connection connection;
	
	final static Logger logger = Logger.getLogger(EmployeeDAOimpl.class);

	public EmployeeDAOimpl() {
		connection = DatabaseUtility.getConnection();
	}

	// step 1: database connection
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	/**Login page**/
	@Override
	public boolean loginAdmin(Employee refEmployee) {
		if(refEmployee.getEmail().equals("admin")&&refEmployee.getPassword().equals("admin123")) {
			loginStatus=true;
			logger.info(refEmployee.getEmail() + " has logged in.");
		}else
			//this should not occur as admin never forget log in details
		{
			loginStatus=false;
			logger.info(refEmployee.getEmail() + " has failed to logged in.");
		}
		return loginStatus;
	}//end of loginAdmin

	@Override
	public boolean loginEmployee(String email, String pass) {
		// step 2: // sql query
		pass=hashPassword(pass);
		try {
			statement = connection.createStatement();
			String query = "SELECT email,password,attempts,status,full_name FROM employee;";
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				String employeeEmail = resultSet.getString("email");
				String password = resultSet.getString("password");
				int attempts=resultSet.getInt("attempts");
				int status=resultSet.getInt("status");
				String name=resultSet.getString("full_name");
				/*if employee is locked*/
				if(status==1) {
					loginStatus=false;
					break;
				}
				/*if employee has tried and failed 3 times*/
				else if(attempts==3) {
					String update = "UPDATE employee set status='1' WHERE email='" + email + "';";
					PreparedStatement updateStatement = connection.prepareStatement(update);
					updateStatement.executeUpdate();
					
					update = "UPDATE employee set attempts='0' WHERE email='" + email + "';";
					updateStatement = connection.prepareStatement(update);
					updateStatement.executeUpdate();
					new SendEmail().lockMail(name, employeeEmail);
					loginStatus=false;
					break;
				}
				
				/*if login successful*/
				else if (email.equals(employeeEmail) && pass.equals(password)) {
					String update = "UPDATE employee set attempts='0' WHERE email='" + email + "';";
					PreparedStatement updateStatement = connection.prepareStatement(update);
					updateStatement.executeUpdate();
					loginStatus=true;
					logger.info(email+","+name + " has logged in.");

					/*if login fail*/
				} else if((email.equals(employeeEmail) && pass.equals(password)==false)){
					attempts=attempts+1;
					String update = "UPDATE employee set attempts='"+attempts+"' WHERE email='" + email + "';";
					PreparedStatement updateStatement = connection.prepareStatement(update);
					updateStatement.executeUpdate();
					loginStatus=false;
					logger.info(email+","+name + " has failed to logged in.");
				}
			}
		}catch(Exception e) {		
			logger.error("Unable to execute loginEmployee.");
			e.getMessage();
		}
		return loginStatus;
	}// end of loginEmployee
	
	@Override
	public boolean checkStatus(String email ) {
		int status=1;
		try {
			statement = connection.createStatement();
			String query = "SELECT status FROM employee WHERE email='" + email + "';";
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				status=resultSet.getInt("status");
			}
		}catch(SQLException e) {
			logger.error("Unable to execute checkStatus.");
			e.getMessage();
			
		/*check if employee is unlocked*/ 
		}if(status==0) {
			loginStatus=true;
		}else {
			loginStatus=false;
		}
		return loginStatus;
	}//end of checkStatus
	

	@Override
	public String createNewPassword(String email) {
		String newPassword=null;
		String hashedPassword=null;
		try {
			statement = connection.createStatement();
			String query = "SELECT nric,mobile FROM employee WHERE email='" + email + "';";
			resultSet = statement.executeQuery(query);
			String nric = null;
			String mobile = null;
			
			while (resultSet.next()) {
				nric=resultSet.getString("nric");
				mobile=resultSet.getString("mobile");
				
				//create temp password for employee based on mobile and nric
				String firstHalf = nric.substring(1, 5);
				String lastHalf = mobile.substring(4, 8);
				newPassword=firstHalf + lastHalf;
				hashedPassword=hashPassword(newPassword);
			}
			/*update database with new hashed password*/
			if(newPassword!=null) {
				String update = "UPDATE employee set password='"+hashedPassword+"' WHERE email='" + email + "';";
				PreparedStatement updateStatement = connection.prepareStatement(update);
				updateStatement.executeUpdate();
				logger.info(email+" has changed password.");
			}
		}catch(SQLException e) {
			newPassword=null;	
			logger.error("Unable to execute createNewPassword.");
			e.getMessage();
		}
		return newPassword;		
	}//end of createNewPasword
	
	/*retrieve employee's full name from database*/
	@Override
	public String getEmployeeName(String email) {
		String name=null;
		try {
			statement = connection.createStatement();
			String query = "SELECT full_name FROM employee WHERE email='" + email + "';";
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				name=resultSet.getString("full_name");
				logger.debug(email+" able to retieve "+name +".");
			}
			}catch (Exception e) {
				logger.error("Unable to execute getEmployeeName.");
				e.getMessage();
			}		 
		return name;				
	}//end of getEmployeeName

	/**Admin LoginPage**/
	/*create new employee iframe*/
	@Override
	public boolean createNewEmployee(Employee ref) {
		// step 2: // sql query
		boolean failure=false;
		String hashedPassword=null;
		try {
			statement = connection.createStatement();
			String query = "INSERT INTO employee values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,default)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, ref.getEmpID());			
			preparedStatement.setString(2, ref.getFullName());
			preparedStatement.setString(3, ref.getNric());
			preparedStatement.setString(4, ref.getGender());
			preparedStatement.setString(5, ref.getDateOfBirth());
			preparedStatement.setString(6, ref.getAddress());
			preparedStatement.setString(7, ref.getCountry());
			preparedStatement.setString(8, ref.getQualification());
			preparedStatement.setBlob(9, ref.getCertificate().getInputStream());						
			preparedStatement.setString(10, ref.getDepartment());					
			preparedStatement.setString(11, ref.getEmail());
			preparedStatement.setString(12, ref.getMobile());
			hashedPassword=hashPassword(ref.getPassword());
			preparedStatement.setString(13, hashedPassword);
			preparedStatement.setString(14, ref.getRole());
			preparedStatement.setLong(15, ref.getStatus());
			preparedStatement.setLong(16, ref.getAttempts());
			preparedStatement.executeUpdate();
			logger.debug(ref.getEmail()+" has been successfully added into the database.");
			failure=false;
		}catch(Exception e) {
			failure=true;
			logger.error("Unable to execute createNewEmployee.");
			e.getMessage();
		}
		return failure;
	}// end of createNewEmployee

	/*view employee list tab*/
	@Override
	public ArrayList<String> viewEmployeeList(){
		// step 2: // sql query
		ArrayList<String> toReturn = new ArrayList<String>();
		try {
			statement = connection.createStatement();
			String query = "SELECT employee_id,full_name,nric,department,date_of_birth,mobile,email FROM employee WHERE status=0";
			resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
				String empID = resultSet.getString("employee_id");
				String fullName = resultSet.getString("full_name");
				String nric = resultSet.getString("nric");
				String department = resultSet.getString("department");
				String date = resultSet.getString("date_of_birth");
				String mobile = resultSet.getString("mobile");			
				String email = resultSet.getString("email");

				String details=empID+"/"+fullName+"/"+nric+"/"+department+"/"+date+"/"+mobile+"/"+email;
				toReturn.add(details);				
			}
			logger.debug("Able to retrieve all information from the database.");
			}catch (Exception e) {
				logger.error("Unable to execute viewEmployeeList.");
				e.getMessage();
			}		 		
		return toReturn;
	}// end of viewEmployeeList

	@Override
	public ArrayList<String> searchEmployee(String search, String type){
		// step 2: // sql query
		ArrayList<String> toReturn = new ArrayList<String>();

		try {
			statement = connection.createStatement();
			String query = "SELECT employee_id,full_name,nric,department,date_of_birth,mobile,email FROM employee WHERE "+type+ " LIKE '%"+search+"%' AND status=0";
			resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
				String empID = resultSet.getString("employee_id");
				String fullName = resultSet.getString("full_name");
				String nric = resultSet.getString("nric");
				String department = resultSet.getString("department");
				String date = resultSet.getString("date_of_birth");
				String mobile = resultSet.getString("mobile");			
				String email = resultSet.getString("email");

				String details=empID+"/"+fullName+"/"+nric+"/"+department+"/"+date+"/"+mobile+"/"+email;
				toReturn.add(details);				
			}
			logger.debug("Able to retrieve all relavant information from the database.");
		}catch (Exception e) {
			logger.error("Unable to excute searchEmployee.");
			e.getMessage();
		}
		return toReturn;
	}//end of searchEmployee
	
	@Override
	public void lockEmployee(String empID) {
		try {
			statement = connection.createStatement();
			
			String update = "UPDATE employee set status='1' WHERE employee_id='" + empID + "';";
			PreparedStatement updateStatement = connection.prepareStatement(update);
			updateStatement.executeUpdate();
			
			String query = "SELECT full_name,email,nric,mobile FROM employee WHERE employee_id='" + empID + "';";
			resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
				String fullName = resultSet.getString("full_name");
				String email = resultSet.getString("email");
				logger.info(email+" has been locked. Procceeding to send email notification.");
				new SendEmail().lockMail(fullName, email);
			}			
		}catch (Exception e) {
			logger.error("Unable to execute lockEmployee.");
			e.getMessage();
		}			
	}

	/*view unlock request tab*/
	@Override
	public ArrayList<String> viewRequestStatus(){
		// step 2: // sql query
		ArrayList<String> toReturn = new ArrayList<String>();

		try {
			statement = connection.createStatement();
			String query = "SELECT employee_id,full_name,nric,department,date_of_birth,mobile,email,status FROM employee WHERE status=1";
			resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
				String empID = resultSet.getString("employee_id");
				String fullName = resultSet.getString("full_name");
				String nric = resultSet.getString("nric");
				String department = resultSet.getString("department");
				String date = resultSet.getString("date_of_birth");
				String mobile = resultSet.getString("mobile");			
				String email = resultSet.getString("email");
				String status = resultSet.getString("status");
				if (status.equals("1")) {
					status = "LOCKED";
				}
				String details=empID+"/"+fullName+"/"+nric+"/"+department+"/"+date+"/"+mobile+"/"+email+"/"+status;
				toReturn.add(details);				
			}
			logger.debug("Able to retrieve all relavant information from the database.");
		}catch (Exception e) {
			logger.error("Unable to execute viewRequestStatus.");
			e.getMessage();
		}		 
		return toReturn;
	}// end of viewRequestStatus

	@Override
	public void unlockEmployee(String empID) {
		String hashedPassword=null;
		String newPassword=null;
		try {
			statement = connection.createStatement();
			
			String update = "UPDATE employee set status='0' WHERE employee_id='" + empID + "';";
			PreparedStatement updateStatement = connection.prepareStatement(update);
			updateStatement.executeUpdate();
			
			String query = "SELECT full_name,email,nric,mobile FROM employee WHERE employee_id='" + empID + "';";
			resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
				String fullName = resultSet.getString("full_name");
				String email = resultSet.getString("email");
				String nric = resultSet.getString("nric");
				String mobile = resultSet.getString("mobile");
				
				//create temp password for employee based on mobile and nric
				String firstHalf = nric.substring(1, 5);
				String lastHalf = mobile.substring(4, 8);
				newPassword=firstHalf + lastHalf;
				hashedPassword=hashPassword(newPassword);
				
				//update new passsword
				update = "UPDATE employee set password='"+hashedPassword+"' WHERE email='" + email + "';";
				updateStatement = connection.prepareStatement(update);
				updateStatement.executeUpdate();
				logger.info(email+" has been unlocked. Procceeding to send email notification.");
				new SendEmail().unlockMail(fullName, email, newPassword);
			}			
		}catch (Exception e) {
			logger.error("Unable to execute unlockEmployee.");
			e.getMessage();
		}	
	}//end of unlockEmployee
	
	/**Employee LoginPage**/

	/*update profile*/
	@Override
	public void updateProfilePic(Employee ref) {
		try {
			statement = connection.createStatement();
			String update = "UPDATE employee set profile_picture=? WHERE email=? ";
			PreparedStatement updateStatement = connection.prepareStatement(update);
			updateStatement.setBlob(1, ref.getProfilePicture().getInputStream());
			updateStatement.setString(2, ref.getEmail());
			updateStatement.executeUpdate();
			logger.info(ref.getEmail()+" has been updated profile picture.");
		}catch (Exception e) {
			logger.error("Unable to execute updateProfilePic.");
			e.getMessage();
		}	
	}//end of updateProfilePic
	
	@Override
	public void updateAddress(Employee ref) {
		try {
			statement = connection.createStatement();
			String update = "UPDATE employee set address='"+ref.getAddress() +"' WHERE email='" + ref.getEmail() + "';";
			PreparedStatement updateStatement = connection.prepareStatement(update);
			updateStatement.executeUpdate();
			logger.info(ref.getEmail()+" has been updated address.");
		}catch (Exception e) {
			logger.error("Unable to execute updateAddress.");
			e.getMessage();
		}	
	}//end of updateAddress
	
	@Override
	public void updateQualification(Employee ref) {
		try {
			statement = connection.createStatement();
			String update = "UPDATE employee set qualification='"+ref.getQualification() +"' WHERE email='" + ref.getEmail() + "';";
			PreparedStatement updateStatement = connection.prepareStatement(update);
			updateStatement.executeUpdate();
			logger.info(ref.getEmail()+" has been updated qualification.");
		}catch (Exception e) {
			logger.error("Unable to execute updateQualification.");
			e.getMessage();
		}	
	}//end of updateQualification
	
	@Override
	public void updateCertificate(Employee ref) {
		try {
			statement = connection.createStatement();
			String update = "UPDATE employee set certificate=? WHERE email=? ";
			PreparedStatement updateStatement = connection.prepareStatement(update);
			updateStatement.setBlob(1, ref.getCertificate().getInputStream());
			updateStatement.setString(2, ref.getEmail());
			updateStatement.executeUpdate();	
			logger.info(ref.getEmail()+" has been updated certificate.");
		}catch (Exception e) {
			logger.error("Unable to execute updateCertificate.");
			e.getMessage();
		}	
	}//end of updateQualification
	
	@Override
	public void updateMobile(Employee ref) {
		try {
			statement = connection.createStatement();
			String update = "UPDATE employee set mobile='"+ref.getMobile() +"' WHERE email='" + ref.getEmail() + "';";
			PreparedStatement updateStatement = connection.prepareStatement(update);
			updateStatement.executeUpdate();
			logger.info(ref.getEmail()+" has been updated mobile number.");
		}catch (Exception e) {
			logger.error("Unable to execute updateMobile.");
			e.getMessage();
		}	
	}//end of updateQualification
	
	/*view profile*/
	
	@Override
	public Employee viewProfile(String email) {
		Employee refEmployee=new Employee();
		try {
			statement = connection.createStatement();
			String query = "SELECT * FROM employee WHERE email='" + email + "';";
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				//get Employee info from database
				String name=resultSet.getString("full_name");
				String nric=resultSet.getString("nric");
				String gender=resultSet.getString("gender");
				String doB=resultSet.getString("date_of_birth");
				String address=resultSet.getString("address");
				String country=resultSet.getString("country");
				String qualification=resultSet.getString("qualification");
				String department=resultSet.getString("department");
				String mobile=resultSet.getString("mobile");
				String empID=resultSet.getString("employee_id");
							
				//set information to employee
				refEmployee.setFullName(name);
				refEmployee.setNric(nric);
				refEmployee.setGender(gender);
				refEmployee.setDateOfBirth(doB);
				refEmployee.setAddress(address);
				refEmployee.setCountry(country);
				refEmployee.setQualification(qualification);
				refEmployee.setDepartment(department);
				refEmployee.setEmail(email);
				refEmployee.setMobile(mobile);
				refEmployee.setEmpID(empID);
				logger.debug(email+" has successfully viewed profile.");
			}
			}catch (Exception e) {
				logger.error("Unable to execute viewProfile.");
				e.getMessage();
			}		 		
		return refEmployee;		
	}	 //end of viewProfile
	

	@Override
	public byte[] getImage(String email) {		
		byte[] profilePic = null;		
		try {
			Statement statement = connection.createStatement();
			String query="SELECT profile_picture FROM employee WHERE email='" + email + "';";
			// To store the results from the search
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				profilePic = resultSet.getBytes("profile_picture");
			}
			System.out.println("Can get image");
		}catch(Exception e) {
			System.out.println("Cannot get image");
			logger.error("Unable to execute getImage.");
			e.getMessage();
		}
		return profilePic;		
		
	} // end of getImage
	
	@Override
	public String hashPassword(String password) {
		
        String passwordToHash = password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            
            //Get the hash's bytes
            byte[] bytes = md.digest();
            
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }           
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
        	//log4j.error("Cannot hash the password.");
        	logger.error("Unable to execute hashPassword.");
			e.getMessage();
            e.printStackTrace();
        }
        System.out.println("The password generated is: " + generatedPassword);		
		return generatedPassword;		
	} // end of hashPassword 
	

}
