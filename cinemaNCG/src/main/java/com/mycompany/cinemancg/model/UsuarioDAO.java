package com.mycompany.cinemancg.model;
/**
 *
 * @author User
 */
import com.mycompany.cinemancg.model.data.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    
    private final ConnectionDB db = ConnectionDB.instance();

    public UsuarioDAO() {
    }
    
    public Usuario get(String idUsuario) throws Exception {
        try{
            String sql = "SELECT * FROM Usuario WHERE idUsuario = '%s'";
            sql = String.format(sql, idUsuario);
            ResultSet rs = db.executeQuery(sql);
            if(rs.next()) {
                return map(rs);
            }
            throw new SQLException("/usuario/?=" + idUsuario + " Does not exist in DataBase");
        } catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer add(Usuario usuario) throws Exception {
        try {
            String sql = "INSERT INTO Usuario(idUsuario, contrasena, nombre, tipo) "
                    + "VALUES('%s','%s','%s',%d)";
            sql = String.format(sql, usuario.getIdUsuario(), usuario.getContrasena(), usuario.getNombre(), usuario.getTipo());
            return db.executeInsert(sql);
        } catch(Exception e) {
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer update(Usuario usuario) throws Exception {
        try{
            String sql="UPDATE Usuario SET contrasena='%s', nombre='%s', tipo=%d WHERE idUsuario='%s'";
            sql=String.format(sql, usuario.getContrasena(), usuario.getNombre(), usuario.getTipo(), usuario.getIdUsuario());
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("/Usuario/{" + usuario.getIdUsuario() + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    public Integer delete(String idUsuario) throws Exception {
        try{
            String sql="DELETE FROM Usuario WHERE idUsuario='%s'";
            sql = String.format(sql, idUsuario);
            int result = db.executeUpdate(sql);
            if(result == 0){
                throw new Exception("Usuario/{" + idUsuario + "} Does not exist in DataBase");
            }
            return result;
        }catch(Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }
    
    private Usuario map(ResultSet rs) throws Exception{
        String idUsuario = rs.getString("idUsuario");
        String contrasena = rs.getString("contrasena");
        String nombre = rs.getString("nombre");
        Integer tipo = rs.getInt("tipo");
        Usuario usuario = new Usuario(idUsuario, contrasena, nombre, tipo);
        
        return usuario;
    }
}
