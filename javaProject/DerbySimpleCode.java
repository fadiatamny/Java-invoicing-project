import java.sql.*;

public class DerbySimpleCode {
/*
    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String protocol = "jdbc:derby:compStoreDB;create=true";


    public static void main(String[] args){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try
        {
            connection = null;
            Class.forName(driver);
            connection = DriverManager.getConnection(protocol );
            statement = connection.createStatement();
            statement.execute("DROP TABLE users");
            String s = "CREATE TABLE  users( id INT PRIMARY KEY, fullname VARCHAR(256), password VARCHAR(256), budget DOUBLE)";
            statement.execute(s);
      //      statement.execute("CREATE TABLE  invoces( id INT , userID INT, amount INT, description TEXT(1000),  date Date, FOREIGN KEY (userID) REFERENCES users(id))");
            statement.execute("insert into  users values (1,'eilon','cohen',2000)");
            statement.execute("insert into  users values (2,'fadi','utmany',23000)");
            statement.execute("insert into  users values (3,'tal','adivi',1000)");
            rs = statement.executeQuery("SELECT * FROM users ORDER BY id");
            while(rs.next())
            {
                System.out.println("id="+rs.getInt("id")+" fullname="+rs.getString("fullname") + " password="+rs.getString("password") + " budget="+rs.getDouble("budget"));
            }
            statement.execute("CREATE TABLE invoces( id INT , userID INT, amount INT, description VARCHAR(256),  date Date)");
            statement.execute("DROP TABLE users");
        }
        catch(Exception e) { e.printStackTrace(); }
        finally
        {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }

    */
public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String protocol = "jdbc:derby:compStoreDB;create=true";


    public static void main(String[] args){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try
        {
            connection = null;
//Instantiating the dirver class will indirectly register
//this driver as an available driver for DriverManager
            Class.forName(driver);
//Getting a connection by calling getConnection
            connection = DriverManager.getConnection(protocol );
            statement = connection.createStatement();
            statement.execute("create table inventory(id int, fee double)");
            statement.execute("insert into inventory values (100212,2.5)");
            statement.execute("insert into inventory values (100213,1.2)");
            statement.execute("insert into inventory values (100214,4.2)");
            rs = statement.executeQuery(
                    "SELECT id,fee FROM inventory ORDER BY id");
            while(rs.next())
            {
                System.out.println("id="+rs.getInt("id")+" fee="+rs.getDouble("fee"));
            }
            statement.execute("DROP TABLE inventory");
        }
        catch(Exception e) { e.printStackTrace(); }
        finally
        {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{rs.close();}catch(Exception e){}
        }
    }
}
