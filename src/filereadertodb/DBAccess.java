/**
 * This Class is for accessing the database using the JDBC and SQL scripts 
 * as well as the fileReader class we created previously to handle the .data
 * files.
 * 
 * See SQL Files, Create_Tables for details on SQL used
 * @author Aled Davies
 */

package filereadertodb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBAccess {
    
    private Statement SQLStatement;
    private Connection connectionToDB;
    private String tableColumns;
    private String tableData;
    private fileReader read;
    
    /**
     * This is the class that connects to the database this will need to change
     * depending on the database you are using and the password
     */
    DBAccess(){
        try{
            //get the drivers for MySQL
            Class.forName("com.mysql.jdbc.Driver");
            
            //connect to the database
            connectionToDB = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/smartstream",
                                 "root",
                                 ""
                                );
            connectionToDB.setAutoCommit(true);
        
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
            
        }        
    }
    
    
    /**
     * This Method hands the information from the 
     * 
     * @throws SQLException
     * @throws IOException 
     */    
    void inputOrderData() throws SQLException, IOException{
        
        int i = 1;
        read = new fileReader("Order.data");
        
        while(i<read.fileContentArrayList.size()){
            
            //gets the contents of fileContentArrayList in a usable form
            tableColumns=(String) read.fileContentArrayList.get(0);
            tableData=(String) read.fileContentArrayList.get(i);
            
            // Takes the infromation from the strings above and inserts them into
            // the database using SQL statments
            SQLStatement = connectionToDB.createStatement();
            SQLStatement.executeUpdate(String.format("insert into `order`("+ tableColumns + ")"
                                                        + "Values(" + tableData + ")"));
            i++;
            
        }    
    }
    
    /**
     * Method for inserting information into person table works similar to the
     * method above apart from it has to format strings using the method in 
     * fileReader
     * 
     * @throws SQLException
     * @throws IOException 
     */
    void inputPersonData() throws SQLException, IOException{
        
        int i = 1;
        read = new fileReader("Person.data");
        //Formatting to run the text as an SQL statement
        read.formatStrings();
        
        while(i<read.fileContentArrayList.size()){
            tableColumns=(String) read.fileContentArrayList.get(0);
            tableData=(String) read.fileContentArrayList.get(i);
                        
            SQLStatement = connectionToDB.createStatement();
            SQLStatement.executeUpdate(String.format("insert into person("+ tableColumns + ")"
                                                + "Values(" + tableData + ")"));
            i++;
        }
    }
    
    /**
     * This query is used to get the information stored in the table using and 
     * The purpose of it is to get all the users which have an order with one
     * or more orders
     */
    void personsWithOneOrder(){
        ResultSet rs;
        try {
            SQLStatement = connectionToDB.createStatement();
            
            //See comment in SQL File
            rs = SQLStatement.executeQuery(String.format(
                    "SELECT p.first_name, p.last_name,p.person_id,o.person_id, o.order_no\n" +
                    "from person p, `order` o \n" +
                    "where o.person_id=p.person_id \n" + 
                    "group by p.first_name"
                    ));
            
            System.out.println("User who have one order or more");
            System.out.println("First_Name \t| Last_Name \t\t| Order_No");
            
            //loop to get the information from the ResultSet above
            while(rs.next()){
                String fName =rs.getString("first_name");
                String lName =rs.getString("last_name");
                int orderNo = rs.getInt("order_no");
                
                System.out.printf("%s \t\t| %s \t\t| %d \n", fName, lName, orderNo);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
