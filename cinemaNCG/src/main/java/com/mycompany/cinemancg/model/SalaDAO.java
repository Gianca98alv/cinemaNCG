package com.mycompany.cinemancg.model;
/**
 *
 * @author User
 */
import com.mycompany.cinemancg.model.data.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {
    
    private final ConnectionDB db = ConnectionDB.instance();

    public SalaDAO() {
    }
    
    public Sala get(Integer idSala) throws Exception {
        try{
            String sql = "SELECT * FROM Sala WHERE idSala = %d";
            sql = String.format(sql, idSala);
            ResultSet rs = db.executeQuery(sql);
            if(rs.next()) {
                return map(rs);
            }
            throw new SQLException("/Sala/?=" + idSala + " Does not exist in DataBase");
        } catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public List<Sala> getAll() throws Exception {
        List<Sala> salas = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Sala";
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                salas.add(map(rs));
            }
            return salas;
        } catch(Exception e) {
           throw new Exception("Exception: " + e.getMessage()); 
        }
    }
    
    public Integer add(Sala sala) throws Exception {
        try {
            String sql = "INSERT INTO Sala(idSala, fila, columna) "
                    + "VALUES(%d,%d,%d)";
            sql = String.format(sql, sala.getIdSala(), sala.getFila(), sala.getColumna());
            return db.executeInsert(sql);
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer update(Sala sala) throws Exception {
        try{
            String sql="UPDATE Sala SET fila=%d, columna=%d WHERE idUsuario=%d";
            sql=String.format(sql, sala.getFila(), sala.getColumna(), sala.getIdSala());
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("/Sala/{" + sala.getIdSala() + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer delete(Integer idSala) throws Exception {
        try{
            String sql="DELETE FROM Sala WHERE idSala=%d";
            sql = String.format(sql, idSala);
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("Sala/{" + idSala + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    private Sala map(ResultSet rs) throws Exception{
        Integer idSala = rs.getInt("idSala");
        Integer fila = rs.getInt("fila");
        Integer columna = rs.getInt("columna");
        Sala sala = new Sala(idSala, fila, columna);
        
        return sala;
    }
}