
import com.mycompany.cinemancg.model.Tiquete;
import com.mycompany.cinemancg.model.TiqueteDAO;
import com.mycompany.cinemancg.model.Pelicula;
import com.mycompany.cinemancg.model.PeliculaDAO;
import com.mycompany.cinemancg.model.Sala;
import com.mycompany.cinemancg.model.SalaDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author User
 */

public class TiqueteTest {
    public static void main(String[] args) {
        try {
            
            PeliculaDAO peliculaDAO = new PeliculaDAO();
            SalaDAO salaDAO = new SalaDAO();
            Pelicula pelicula = peliculaDAO.get("The Turning");
            Sala sala = salaDAO.get(1);
            TiqueteDAO tiqueteDAO = new TiqueteDAO();
            
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
