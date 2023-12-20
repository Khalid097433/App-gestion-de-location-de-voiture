package Admin;
import java.sql.*;

/**
 *
 * @author hp
 */
public class ConnexionMySQL {
    
    public static Connection ConnectDb(){
        
        Connection con = null;
        
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
                 con = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3667801", "sql3667801", "qRPcqKGZYd");
          
        }
        catch(Exception e){
System.out.println(e);
            
        }
           return con;
    }
    
   
    
    
    
    
    
    
    
    
    
    
}
