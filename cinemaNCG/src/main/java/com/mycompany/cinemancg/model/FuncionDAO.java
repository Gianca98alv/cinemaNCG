package com.mycompany.cinemancg.model;
/**
 *
 * @author User
 */
import com.mycompany.cinemancg.model.data.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionDAO {
    
    private final ConnectionDB db = ConnectionDB.instance();

    public FuncionDAO() {
    }
    
    public Funcion get(String idFuncion) throws Exception {
        try{
            String sql = "SELECT * FROM Funcion WHERE idFuncion = %d";
            sql = String.format(sql, idFuncion);
            ResultSet rs = db.executeQuery(sql);
            if(rs.next()) {
                return map(rs);
            }
            throw new SQLException("/funcion/?=" + idFuncion + " Does not exist in DataBase");
        } catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer add(Funcion funcion) throws Exception {
        try {
            String sql = "INSERT INTO Funcion(idFuncion, idPelicula, idSala, Precio, fechaInicio, fechaFin) "
                    + "VALUES(%d,'%s',%d,%f,'%s','%s')";
            sql = String.format(sql, funcion.getIdFuncion(), funcion.getPelicula(), funcion.getSala(), funcion.getPrecio(), funcion.getFechaInicio(), funcion.getFechaFin());
            return db.executeInsert(sql);
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer update(Funcion funcion) throws Exception {
        try{
            String sql="UPDATE Funcion SET idPelicula='%s', idSala=%d, Precio=%f, fechaInicio='%s', fechaFin='%s' WHERE idFuncion=%d";
            sql = String.format(sql, funcion.getIdFuncion(), funcion.getPelicula(), funcion.getSala(), funcion.getPrecio(), funcion.getFechaInicio(), funcion.getFechaFin());
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("/Funcion/{" + funcion.getIdFuncion() + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer delete(String idFuncion) throws Exception {
        try{
            String sql="DELETE FROM Funcion WHERE idFuncion=%d";
            sql = String.format(sql, idFuncion);
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("Funcion/{" + idFuncion + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    private Funcion map(ResultSet rs) throws Exception{
        Integer idFuncion = rs.getInt("idFuncion");
        String idPelicula = rs.getString("idPelicula");
        Integer idSala = rs.getInt("idSala");
        Integer Precio = rs.getInt("Precio");
        String fechaInicio = rs.getString("fechaInicio");
        String fechaFin = rs.getString("fechaFin");
        Funcion funcion = new Funcion(idFuncion, idPelicula, idSala, Precio, fechaInicio, fechaFin);
        
        return funcion;
    }
    
    
}
