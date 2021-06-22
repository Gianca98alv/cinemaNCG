package com.mycompany.cinemancg.model;

import com.mycompany.cinemancg.model.data.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */

public class TiqueteDAO {
    private final ConnectionDB db = ConnectionDB.instance();

    public TiqueteDAO() {
    }
    
    public Tiquete get(String idTiquete) throws Exception {
        try{
            String sql = "SELECT * FROM Tiquete WHERE idTiquete = %d";
            sql = String.format(sql, idTiquete);
            ResultSet rs = db.executeQuery(sql);
            if(rs.next()) {
                return map(rs);
            }
            throw new SQLException("/tiquete/?=" + idTiquete + " Does not exist in DataBase");
        } catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public List<Tiquete> getTiquetesByFuncion(Integer idFuncion) throws Exception {
        List<Tiquete> tiquetes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Tiquete WHERE idFuncion = %d";
            sql = String.format(sql, idFuncion);
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                tiquetes.add(map(rs));
            }
            return tiquetes;
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public List<Tiquete> getTiquetesByFactura(Integer idFactura) throws Exception {
        List<Tiquete> tiquetes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Tiquete WHERE idFactura = %d";
            sql = String.format(sql, idFactura);
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                tiquetes.add(map(rs));
            }
            return tiquetes;
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer add(Tiquete tiquete) throws Exception {
        try {
            String sql = "INSERT INTO Tiquete(idTiquete, idFuncion, idFactura, fila, columna) "
                    + "VALUES(%d,%d,%d,%d,%d)";
            sql = String.format(sql, tiquete.getIdTiquete(), tiquete.getFuncion(), tiquete.getFactura(), tiquete.getFila(), tiquete.getColumna());
            return db.executeInsert(sql);
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer update(Tiquete tiquete) throws Exception {
        try{
            String sql="UPDATE Tiquete SET idPelicula='%s', idSala=%d, Precio=%f, fechaInicio='%s', fechaFin='%s' WHERE idTiquete=%d";
            sql = String.format(sql, tiquete.getIdTiquete(), tiquete.getFuncion(), tiquete.getFactura(), tiquete.getFila(), tiquete.getColumna());
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("/Tiquete/{" + tiquete.getIdTiquete() + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer delete(String idTiquete) throws Exception {
        try{
            String sql="DELETE FROM Tiquete WHERE idTiquete = %d";
            sql = String.format(sql, idTiquete);
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("Tiquete/{" + idTiquete + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    private Tiquete map(ResultSet rs) throws Exception{
        Integer idTiquete = rs.getInt("idTiquete");
        Integer idFuncion = rs.getInt("idFuncion");
        Integer idFactura = rs.getInt("idFactura");
        Integer fila = rs.getInt("fila");
        Integer columna = rs.getInt("columna");
        Tiquete tiquete = new Tiquete(idTiquete, fila, columna);
        FuncionDAO funcionDAO = new FuncionDAO();
        Funcion funcion = funcionDAO.get(idFuncion);
        FacturaDAO facturaDAO = new FacturaDAO();
        Factura factura = facturaDAO.get(idFactura);
        
        return tiquete;
    }
    
    
}
