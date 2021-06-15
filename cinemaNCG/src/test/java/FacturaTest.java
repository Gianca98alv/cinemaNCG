
import com.mycompany.cinemancg.model.Factura;
import com.mycompany.cinemancg.model.FacturaDAO;
import com.mycompany.cinemancg.model.Usuario;
import com.mycompany.cinemancg.model.UsuarioDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
*/

public class FacturaTest {
    
    public static void main(String[] args) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.get("1");
            Factura factura = new Factura(0, "zdf", "Mario Borge", "666677778888", 15000.0f);
            FacturaDAO facturaDAO = new FacturaDAO();
            int respuesta = facturaDAO.add(factura);
            factura = facturaDAO.get(3);
            System.out.print(factura);
        } catch (Exception ex) {
            Logger.getLogger(FacturaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
