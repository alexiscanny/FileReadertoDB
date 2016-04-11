/**
 * This is the file used to run the program and will output the information
 * in the output screen. 
 * 
 * @author Aled Davies
 */

package filereadertodb;

import java.io.IOException;
import java.sql.SQLException;

public class ProgramRunner {
    
    public static void main(String [] args) throws SQLException, IOException{
        DBAccess db = new DBAccess();
        db.inputOrderData();
        db.inputPersonData();
        db.personsWithOneOrder();  
    }
}
