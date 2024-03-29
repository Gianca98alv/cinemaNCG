package com.mycompany.cinemancg.model;

/**
 *
 * @author User
 */

import com.mycompany.cinemancg.model.data.ConnectionDB;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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
    
    public Pelicula getSimple(String idPelicula) throws Exception {
        try{
            String sql = "SELECT * FROM Pelicula WHERE idPelicula = '%s'";
            sql = String.format(sql, idPelicula);
            ResultSet rs = db.executeQuery(sql);
            if(rs.next()) {
                return mapSimple(rs);
            }
            throw new SQLException("/pelicula/?=" + idPelicula + " Does not exist in DataBase");
        } catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public List<Pelicula> getAll() throws Exception {
        List<Pelicula> peliculas = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Pelicula";
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                peliculas.add(map(rs));
            }
            return peliculas;
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public List<Pelicula> getAllByNombre(String nombre) throws Exception {
        List<Pelicula> peliculas = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Pelicula WHERE idPelicula like '%%%s%%'";
            sql = String.format(sql, nombre);
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                peliculas.add(map(rs));
            }
            return peliculas;
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public List<Pelicula> getAllByEstreno() throws Exception {
        List<Pelicula> peliculas = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Pelicula WHERE Estreno = 1";
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                peliculas.add(map(rs));
            }
            return peliculas;
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer add(Pelicula pelicula) throws Exception {
        try {
            String sql = "INSERT INTO Pelicula(idPelicula, poster, duracion, clasificacion, estreno) " + "VALUES('%s','%s','%s','%s',%d)";
            sql = String.format(sql, pelicula.getIdPelicula(), pelicula.getPoster(), pelicula.getStringDuracion(), pelicula.getClasificacion(), pelicula.getEstreno());
            return db.executeInsert(sql);
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer update(Pelicula pelicula) throws Exception {
        try{
            String sql="UPDATE Pelicula SET poster='%s', duracion='%s', clasificacion='%s', estreno=%d WHERE idPelicula='%s'";
            //byte[] decoded = Base64.getDecoder().decode(pelicula.getFotoBase64());
            String resultado = "null"; //+ String.format("%040x", new BigInteger(1, decoded));
            
            sql = String.format(sql, resultado, pelicula.getStringDuracion(), pelicula.getClasificacion(), pelicula.getEstreno(), pelicula.getIdPelicula());
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
        //byte[] poster = rs.getBytes("poster");
        String string = rs.getString("poster"); //Base64.getEncoder().encodeToString(poster);
        Date duracion = rs.getTime("duracion");
        String clasificacion = rs.getString("clasificacion");
        Integer estreno = rs.getInt("estreno");
        Pelicula pelicula = new Pelicula(idPelicula, string, duracion, clasificacion, estreno);
        FuncionDAO funcionDAO = new FuncionDAO();
        pelicula.setFuncionList(funcionDAO.getAllByPelicula(idPelicula));
        
        return pelicula;
    }
    
    private Pelicula mapSimple(ResultSet rs) throws Exception {
        String idPelicula = rs.getString("idPelicula");
        byte[] poster = rs.getBytes("poster");
        String string = Base64.getEncoder().encodeToString(poster);
        Date duracion = rs.getTime("duracion");
        String clasificacion = rs.getString("clasificacion");
        Integer estreno = rs.getInt("estreno");
        Pelicula pelicula = new Pelicula(idPelicula, string, duracion, clasificacion, estreno);
        return pelicula;
    }
}