package com.mycompany.cinemancg.model;

/**
 *
 * @author User
 */

import com.mycompany.cinemancg.model.data.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PeliculaDAO {
    
    private final ConnectionDB db = ConnectionDB.instance();

    public PeliculaDAO() {
    }
    
    public Pelicula get(String idPelicula) throws Exception {
        try{
            String sql = "SELECT * FROM Pelicula WHERE idPelicula = '%s'";
            sql = String.format(sql, idPelicula);
            ResultSet rs = db.executeQuery(sql);
            if(rs.next()) {
                return map(rs);
            }
            throw new SQLException("/pelicula/?=" + idPelicula + " Does not exist in DataBase");
        } catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer add(Pelicula pelicula) throws Exception {
        try {
            String sql = "INSERT INTO Pelicula(idPelicula, poster, duracion, clasificacion, estreno) "
                    + "VALUES('%s','%s','%s','%s',%d)";
            sql = String.format(sql, pelicula.getIdPelicula(), pelicula.getPoster(), pelicula.getDuracion(), pelicula.getClasificacion(), pelicula.getEstreno());
            return db.executeInsert(sql);
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer update(Pelicula pelicula) throws Exception {
        try{
            String sql="UPDATE Pelicula SET poster='%s', duracion='%s', clasificacion='%s', estreno=%d WHERE idPelicula='%s'";
            sql = String.format(sql, pelicula.getIdPelicula(), pelicula.getPoster(), pelicula.getDuracion(), pelicula.getClasificacion(), pelicula.getEstreno());
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("/Pelicula/{" + pelicula.getIdPelicula() + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer delete(String idPelicula) throws Exception {
        try{
            String sql="DELETE FROM Pelicula WHERE idPelicula='%s'";
            sql = String.format(sql, idPelicula);
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("Pelicula/{" + idPelicula + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    private Pelicula map(ResultSet rs) throws Exception {
        String idPelicula = rs.getString("idPelicula");
        byte[] poster = rs.getBytes("poster");
        Date duracion = rs.getDate("duracion");
        String clasificacion = rs.getString("clasificacion");
        String estreno = rs.getString("estreno");
        Pelicula pelicula = new Pelicula(idPelicula, poster, duracion, clasificacion, estreno);
        
        return pelicula;
    }
}