/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mystictodo_limited.mystic_timetable.db;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.RowFilter.Entry;
import mystictodo_limited.mystic_timetable.dbInterface.DbService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Jamario_Downer
 */
public class DbTimeTablePresets extends DbConnectionManager implements DbService<DbTimeTablePresets> {
 //Constructor >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   
 public DbTimeTablePresets(){
     super(DbTimeTablePresets.class);
     CreateLog("info", "Default Constructor Triggered.", null);   
 }
    
 //Fields >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>     
 
 private int presetId;
 private String presetName;
 private String presetCategory;
 private String presetColor;
 private ArrayList<DbTimeTablePresets> presetList;
 
 //Getter/Setter >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>       
 //PresetId
 public int getPresetId(){
     return presetId;
 }
 public void setPresetId(int presetId){
     this.presetId = presetId;
 }
 
 //PresetName
 public String getPresetName(){
     return presetName;
 }
 public void setPresetName(String presetName){
    this.presetName = presetName;
 }
 
 //PresetCategory
 public String getPresetCategory(){
     return presetCategory;
 }
 public void setPresetCategory(String presetCategory){
     this.presetCategory = presetCategory;
 }
 
 //PresetColor
 public String getPresetColor(){
     return presetColor;
 }
 public void setPresetColor(String presetColor){
     this.presetColor = presetColor;
 }
 
 //Methods >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
 //Insert Entry 
    public void InsertEntry(String presetName, String presetCategory, String presetColor) throws SQLException{
        CreateLog("info", "Insert Entry Operation Triggered.", null);   
        
        try{
             //database connection 
            Connection con = Connection();
            
            //Variables and validation
            String _presetName = presetName;
            String _presetCategory = presetCategory;
            String _presetColor = presetColor;
            
            boolean isValid = true;
            
           if (isValid == true) 
           {
               //insert to database
                String insertSQL = "INSERT INTO timetablepresets "
                        + "(PresetName, PresetCategory, PresetColor) "
                        + "VALUE (?,?,?)";
                PreparedStatement ps = con.prepareStatement(insertSQL);
                ps.setString(1, _presetName);
                ps.setString(2, _presetCategory);
                ps.setString(3, _presetColor);     
            
                int rowsInserted = ps.executeUpdate();
            
                if(rowsInserted > 0) {
                   // Perform Action if not empty
                   CreateLog("info", "Entry Inserted.", null);
                   con.close(); //Close Connection
                   CreateLog("info", "Connection closed.", null);
                }
           } else 
           {
               
                // Data not saved due to validation  
                CreateLog("error", "Validation Failed. Entry Not Added.", null);  
                con.close(); //Close Connection
                CreateLog("info", "Connection closed.", null);   
           }
        }
        catch(SQLException e) {
            CreateLog("error", "Connection Failed. Entry Not Added.", e);
        }
    }
    
    //Update Entry
    public void UpdateEntrybyId(int id, String presetName, String presetCategory, String presetColor) throws SQLException{
        CreateLog("info", "Update Entry by Id operation triggered.", null);
        
        try{
             //database connection 
            Connection con = Connection();
            
            //Variables and validation
            String _presetName = presetName;
            String _presetCategory = presetCategory;
            String _presetColor = presetColor;
  
            boolean isValid = true;
            
            if (isValid == true)
            {
                // update the entry
                String updateSQL = "UPDATE timetablepresets "
                        + "SET PresetName = ? PresetCategory = ? PresetColor = ?"
                        + "WHERE PresetId = ?";
                PreparedStatement psUpdate  = con.prepareStatement(updateSQL);
                psUpdate.setString(1, _presetName);
                psUpdate.setString(2, _presetCategory);
                psUpdate.setString(3, _presetColor);
                psUpdate.setInt(4, id);
                int rowsUpdated = psUpdate.executeUpdate();
                
                if(rowsUpdated > 0) {
                    // Perform Action if not empty
                   CreateLog("info", "Entry Updated.", null);
                   con.close(); //Close Connection
                   CreateLog("info", "Connection closed.", null);
                }
            }else
            {
                
                // Data not saved due to validation
                CreateLog("error", "Validation Failed. Entry Not Updated.", null);  
                con.close(); //Close Connectionr
                CreateLog("info", "Connection closed.", null); 
            }  
        }
        catch(SQLException e) {     
            CreateLog("error", "Connection Failed. Entry Not updated.", e);
        }
    }
    
    // Delete Entry
        @Override
        public void DeleteEntryById(int id) throws SQLException {
            CreateLog("info", "Delete Entry by Id operation trigger.", null);
            
            try{
                 //database connection 
                Connection con = Connection();
                
                //Delete entry from database 
                String deleteSQL = "DELETE FROM timetablepresets WHERE PresetId = ?";
                PreparedStatement psDelete = con.prepareStatement(deleteSQL);
                psDelete.setInt(1, id);
                int rowsDeleted = psDelete.executeUpdate();
                
                if(rowsDeleted > 0){
                    // Perform Action if not empty
                   CreateLog("info", "Entry Deleted.", null);
                   con.close(); //Close Connection
                   CreateLog("info", "Connection closed.", null);
                }
            }
            catch (SQLException e){
                CreateLog("error", "Connection Failed. Entry not Deleted or Found.", e);
            }
        }
        
     //Return Entry by Id
        @Override
        public DbTimeTablePresets GetEntrybyId(int id) throws SQLException {
            CreateLog("info", "Delete Entry by Id operation trigger.", null);
            
            //Create new class instance
            DbTimeTablePresets dataStore = new DbTimeTablePresets();
            
            try {
                 //database connection 
                Connection con = Connection();
                
                // use id to locate entry
                String selectSQL = "SELECT PresetId, PresetName, PresetCategory, PresetColor "
                        + "FROM timetablepresets "
                        + "WHERE PresetId = ?";
                PreparedStatement psSelect = con.prepareStatement(selectSQL);
                psSelect.setInt(1, id);
                ResultSet rset = psSelect.executeQuery();
                
                if (rset.next()){
                    dataStore.setPresetId(rset.getInt("PresetId"));
                    dataStore.setPresetName(rset.getString("PresetName"));
                    dataStore.setPresetCategory(rset.getString("PresetCategory")); 
                    dataStore.setPresetColor(rset.getString("PresetColor"));
                    
                   CreateLog("info", "Entry returned.", null);
                   con.close(); //Close Connection
                   CreateLog("info", "Connection closed.", null);
                }else {
                    CreateLog("error", "Validation Failed. Entry Not found.", null);  
                    con.close(); //Close Connection
                    CreateLog("info", "Connection closed.", null); 
                }
            }
            catch (SQLException e) {
                CreateLog("error", "Connection Failed. No Entry loaded or Found.", e);
            }
            
            return dataStore;
        }   
        
        //Return Entry by PresetName
        public DbTimeTablePresets GetEntrybyPresetName(String presetName) throws SQLException {
            CreateLog("info", "Return Entry by PresetName operation triggered.", null);
            
            //Create new class instance
            DbTimeTablePresets dataStore = new DbTimeTablePresets();
            
            try {
                 //database connection 
                Connection con = Connection();
                
                // use name to locate entry
                String selectSQL = "SELECT PresetId, PresetName, PresetCategory, PresetColor "
                        + "FROM timetablepresets"
                        + "WHERE PresetName = ?";
                PreparedStatement psSelect = con.prepareStatement(selectSQL);
                psSelect.setString(1, presetName);
                ResultSet rset = psSelect.executeQuery();
                
                if (rset.next()){
                    dataStore.setPresetId(rset.getInt("PresetId"));
                    dataStore.setPresetName(rset.getString("PresetName"));
                    dataStore.setPresetCategory(rset.getString("PresetCategory")); 
                    dataStore.setPresetColor(rset.getString("PresetColor"));  
            
                    CreateLog("info", "Entry returned.", null);
                    con.close(); //Close Connection
                    CreateLog("info", "Connection closed.", null);
                }else {
                    CreateLog("error", "Validation Failed. Entry not found.", null);  
                    con.close(); //Close Connection
                    CreateLog("info", "Connection closed.", null); 
                }
            }
            catch (SQLException e) {
                CreateLog("error", "Connection Failed. No Entry loaded or Found.", e);  
            }
            
            return dataStore;
        }   
        
    //Return All Presets
        @Override
        public ArrayList<DbTimeTablePresets> GetAllEntries() throws SQLException {
            CreateLog("info", "Return all Entry operation triggered.", null);
            
            
            try {
                 //database connection 
                Connection con = Connection();
                
                // Return all entries
                Statement stmTest = con.createStatement();
                String SQL = "SELECT * FROM timetablepresets";
                ResultSet rset = stmTest.executeQuery(SQL);
                
                while(rset.next())
                {
                    //Create new class instance
                    DbTimeTablePresets dataStore = new DbTimeTablePresets();
                    
                    //get info from db to a variable 
                    dataStore.setPresetId(rset.getInt("PresetId"));
                    dataStore.setPresetName(rset.getString("PresetName"));
                    dataStore.setPresetCategory(rset.getString("PresetCategory")); 
                    dataStore.setPresetColor(rset.getString("PresetColor"));
    
                     // Save entry elements to datastore
                    presetList.add(dataStore);
                }
                
                CreateLog("info", "EntryList returned.", null);
                con.close(); //Close Connection
                CreateLog("info", "Connection closed.", null);
            }
            catch (SQLException e) {
                CreateLog("error", "Connection Failed. No Entry loaded or Found.", e);   
            }
            
            return presetList;
        }

    //Print all Entries
        @Override
        public void ViewAllEntryPrint() throws SQLException {
            CreateLog("info", "Print all Entry using Console operation triggered.", null);
            
            try {
                 //database connection 
                Connection con = Connection();
                
                // Variables
                int _presetId = 0;
                String _presetName = null;
                String _presetCategory = null;
                String _presetColor = null;
                
                
                // View all entries using the console
                Statement stmTest = con.createStatement();
                String SQL = "SELECT * FROM timetablepresets";
                ResultSet rset = stmTest.executeQuery(SQL);
                
                while(rset.next())
                {
          
                    //get info from db to a variable 
                    _presetId = rset.getInt("PresetId");
                    _presetName = rset.getString("PresetName");
                    _presetCategory = rset.getString("PresetCategory");
                    _presetColor  = rset.getString("PresetColor");
    
                    //print info 
                    System.out.println("\n\nPreset Entry Id: " + _presetId);
                    System.out.println("PresetName : " + _presetName );
                    System.out.println("PresetCategory : " + _presetCategory);
                    System.out.println("PresetColor : " + _presetColor);
                    System.out.println("+++++++++++++++++++++"); 
                }
                
                CreateLog("info", "EntryList printed using console.", null);
                con.close(); //Close Connection
                CreateLog("info", "Connection closed.", null);
            }
            catch (SQLException e) {
                CreateLog("error", "Connection Failed. No Entry loaded or Found.", e);  
            }
        }
}
