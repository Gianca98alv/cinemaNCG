package com.mycompany.cinemancg.model.data;

/* import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties; */
import java.sql.*;

public class ConnectionDB{
    // private static final String PROPERTIES_FILE_NAME = "db.properties";
    private final Connection connection;
    private static ConnectionDB _instance;

    public static ConnectionDB instance(){
        if(_instance == null){
            _instance = new ConnectionDB();
        }
        return _instance;
    }
    
    private ConnectionDB(){
        connection = getConnection();
    }

    private Connection getConnection(){
        try {
            // Properties prop = new Properties();
            // URL resourceUrl = getClass().getResource(PROPERTIES_FILE_NAME);
            // File file = new File(resourceUrl.toURI());            
            // prop.load(new BufferedInputStream(new FileInputStream(file)));
            String driver = "com.mysql.cj.jdbc.Driver"; // prop.getProperty("database_driver");
            String server = "localhost"; // prop.getProperty("database_server");
            String port = "3306"; // prop.getProperty("database_port");
            String user = "root"; // prop.getProperty("database_user");
            String password = "root"; // prop.getProperty("database_password");
            String database = "cinedb"; // prop.getProperty("database_name");
            String URL_conexion="jdbc:mysql://"+ server+":"+port+"/"+database+"?useTimezone=true&serverTimezone=UTC&user="+user+"&password="+password+"&useSSL=false&allowPublicKeyRetrieval=true";
            Class.forName(driver).newInstance();
            return DriverManager.getConnection(URL_conexion);
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int executeUpdate(String statement) throws Exception {
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate(statement);
            return stm.getUpdateCount();
        } catch (SQLException e) {
            throw new Exception("Error SQLException: " + e.getMessage());
        }
    }

    public int executeInsert(String query) throws Exception {
        try {
            PreparedStatement pstm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.execute();
            ResultSet rs = pstm.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            return generatedKey;
        } catch (SQLException e) {
            throw new Exception("Error SQLException: " + e.getMessage());
        }
    }
    
    public ResultSet executeQuery(String statement) throws Exception {
        try {
            Statement stm = connection.createStatement();
            return stm.executeQuery(statement);
        } catch (SQLException e) {
            throw new Exception("Error SQLException: " + e.getMessage());
        }
    }

}