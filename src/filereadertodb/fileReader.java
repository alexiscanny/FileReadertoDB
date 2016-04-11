/**
 *The purpose of this class is to access the information from the .data files and
 * then present this in a way which can be used to upload to the database.
 * 
 * @author Aled Davies
 * 
 */

package filereadertodb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class fileReader {
    
    
    private File file;  
    private Scanner input;
    List fileContentArrayList;
    
    fileReader(String fileAddress) throws FileNotFoundException{
        file = new File(fileAddress) ;
        fileContentArrayList = new ArrayList<>();
        input = new Scanner(file);
        int i = 0;

        /*
        * Loop is used to store contents of the .data files into the ListArray
        * while this is done it is also formatted so that it can be used in
        * SQL Scripts
        */
        while(input.hasNextLine()){
            //formats the column headers so they can be used in the SQL Script
            // note I am working on the assumption that the column headers will
            // always occur here
            if(i==0){
                String tempString = input.nextLine(
                ).replace("|",",").replace(" ","_").replace(
                        "Number", "no");
                fileContentArrayList.add(tempString);
                i++;

                // Handles the rest of the contents of the document, incrementing
                // along fileContentArrayList
            }else{                    
                String tempString = input.nextLine().replace("|",",");
                fileContentArrayList.add(i,tempString);
                i++;
            }
        }
    }
    
    /**
     * This method is formating strings so that they can be used in the SQL statement
     * for people table. it requires that the person_id(int) alway occurs first.
     */
    void formatStrings(){
        
        int i=1;
        while(i < fileContentArrayList.size()){
            
            //removes the empty strings in the ArrayList
            if("".equals((String) fileContentArrayList.get(i))){
                fileContentArrayList.remove(i);
            
            //formats the strings by adding " ' " either side of the "," then
            // removes the extra one so that it can be used as SQL script
            }else{
                input = new Scanner((String) ( fileContentArrayList.get(i)));
                String temp =input.next().replace(",", "','").replaceFirst("'", "")+"'";
                fileContentArrayList.set(i, temp);
                i++;
            }
        }
    }
}
