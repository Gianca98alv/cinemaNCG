package com.mycompany.cinemancg.model;
/**
 *
 * @author User
 */
import com.mycompany.cinemancg.model.data.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    
    public List<Funcion> getAll() throws Exception {
        List<Funcion> funciones = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Funcion";
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                funciones.add(map(rs));
            }
            return funciones;
        } catch(Exception e) {
           throw new Exception("Exception: " + e.getMessage()); 
        }
    }
    
    public List<Funcion> getAllByPelicula(String pelicula) throws Exception {
        List<Funcion> funciones = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Funcion WHERE idPelicula = '%s'";
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                funciones.add(map(rs));
            }
            return funciones;
        } catch(Exception e) {
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
        Float Precio = rs.getFloat("Precio");
        Date fechaInicio = rs.getDate("fechaInicio");
        Date fechaFin = rs.getDate("fechaFin");
        Funcion funcion = new Funcion(idFuncion, Precio, fechaInicio);
        PeliculaDAO peliculaDAO = new PeliculaDAO();
        Pelicula pelicula = peliculaDAO.get(idPelicula);
        SalaDAO salaDAO = new SalaDAO();
        Sala sala = salaDAO.get(idSala);
        funcion.setPelicula(pelicula);
        funcion.setSala(sala);
       
        return funcion;
    }
     
}
