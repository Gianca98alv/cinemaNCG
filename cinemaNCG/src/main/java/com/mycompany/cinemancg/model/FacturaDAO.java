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

public class FacturaDAO {
    
    private final ConnectionDB db = ConnectionDB.instance();

    public FacturaDAO() {
    }
    
    public Factura get(Integer idFactura) throws Exception {
        try{
            String sql = "SELECT * FROM Factura WHERE idFactura = %d";
            sql = String.format(sql, idFactura);
            ResultSet rs = db.executeQuery(sql);
            if(rs.next()) {
                return map(rs);
            }
            throw new SQLException("/factura/?=" + idFactura + " Does not exist in DataBase");
        } catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public List<Factura> getAll() throws Exception {
        List<Factura> facturas = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Factura";
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                facturas.add(map(rs));
            }
            return facturas;
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public List<Factura> getAllByUsuario(String idUsuario) throws Exception {
        List<Factura> facturas = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Factura WHERE idUsuario = '%s'";
            sql = String.format(sql, idUsuario);
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                facturas.add(map(rs));
            }
            return facturas;
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer add(Factura factura) throws Exception {
        try {
            String sql = "INSERT INTO Factura(idFactura, idUsuario, cedula, nombre, numeroTarjeta, total) "
                    + "VALUES(%d,'%s','%s','%s','%s',%f)";
            sql = String.format(sql, factura.getIdFactura(), factura.getUsuario().getIdUsuario(), factura.getCedula(), factura.getNombre(), factura.getNumeroTarjeta(), factura.getTotal());
            return db.executeInsert(sql);
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer update(Factura factura) throws Exception {
        try{
            String sql="UPDATE Factura SET cedula='%s', nombre='%s', numeroTarjeta='%s', total=%f WHERE idFactura=%d";
            sql = String.format(sql, factura.getIdFactura(), factura.getUsuario().getIdUsuario(), factura.getCedula(), factura.getNombre(), factura.getNumeroTarjeta(), factura.getTotal());
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("/Factura/{" + factura.getIdFactura() + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer delete(Integer idFactura) throws Exception {
        try{
            String sql="DELETE FROM Factura WHERE idFactura=%d";
            sql = String.format(sql, idFactura);
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("Factura/{" + idFactura + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    private Factura map(ResultSet rs) throws Exception{
        Integer idFactura = rs.getInt("idFactura");
        String idUsuario = rs.getString("idUsuario");
        String cedula = rs.getString("cedula");
        String nombre = rs.getString("nombre");
        String numeroTarjeta = rs.getString("numeroTarjeta");
        Float total = rs.getFloat("total");
        Factura factura = new Factura(idFactura, cedula, nombre, numeroTarjeta, total);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.get(idUsuario);
        
        return factura;
    }
}