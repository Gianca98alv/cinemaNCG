package com.mycompany.cinemancg.model.data;

import java.sql.*;
import java.util.Locale;

public class ConnectionDB{
    private final Connection connection;
    private static ConnectionDB _instance;

    public static ConnectionDB instance(){
        if(_instance == null){
            _instance = new ConnectionDB();
        }
        return _instance;
    }
    
    private ConnectionDB(){
        Locale.setDefault(Locale.US);
        connection = getConnection();
    }

    private Connection getConnection(){
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String server = "localhost";
            String port = "3306";
            String user = "root";
            String password = "root";
            String database = "cinedb";
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