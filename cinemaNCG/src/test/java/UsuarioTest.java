
import com.mycompany.cinemancg.model.Usuario;
import com.mycompany.cinemancg.model.UsuarioDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class UsuarioTest {
    public static void main(String[] args) {
        try {
            Usuario usuario = new Usuario("1", "abc", "David Villalobos", 1);
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            //int respuesta = usuarioDAO.add(usuario);
            usuario = usuarioDAO.get(usuario.getIdUsuario());
            System.out.print(usuario);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
