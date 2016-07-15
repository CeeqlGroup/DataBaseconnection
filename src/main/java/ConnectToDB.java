//STEP 1. Import required packages
import java.sql.*;


public class ConnectToDB {

    private String SUBPROTOCOL;
    private String HOST;
    private String PORT;
    private String DB_NAME;
    private String DB_URL;
    private String USERNAME;
    private String PASSWORD;



    ConnectToDB(String subProtocol, String host, String port, String userName, String password,
                String databaseName)
    {
        HOST = host;
        PORT = port;
        SUBPROTOCOL = subProtocol;
        DB_NAME = databaseName;
        USERNAME = userName;
        PASSWORD = password;
        StringBuilder urlBuilder = new StringBuilder("jdbc:");
        urlBuilder.append(SUBPROTOCOL);
        urlBuilder.append("://");
        urlBuilder.append(HOST);
        if(PORT!=null){
            urlBuilder.append(":");
            urlBuilder.append(PORT);
        }
        urlBuilder.append("/");
        urlBuilder.append(DB_NAME);
        DB_URL = urlBuilder.toString();
    }


    public Connection registerAndOpenConnect(){//String JDBCdriver){
        System.out.println("Connecting to database...");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;

    }

    public static void main(String[] args) {
        ConnectToDB connectionObject = new ConnectToDB("postgresql","localhost",null,"yonimessing","","testdb");
        Connection conn = connectionObject.registerAndOpenConnect();
        Statement stmt = null;
        try{

//            STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT first, last FROM Employees";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) System.out.println(rs.getString("first"));

            //STEP 6: Clean-up environment
//            rs.close();
//            stmt.close();
//            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            } catch (SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}


