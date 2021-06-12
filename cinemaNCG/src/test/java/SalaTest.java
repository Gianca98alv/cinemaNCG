
import com.mycompany.cinemancg.model.Sala;
import com.mycompany.cinemancg.model.SalaDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class SalaTest {
    public static void main(String[] args) {
        try {
            Sala sala = new Sala(2, 2, 2);
            SalaDAO salaDAO = new SalaDAO();
            //int respuesta = salaDAO.add(sala);
            //sala = salaDAO.get(sala.getIdSala());
            List<Sala> salas = salaDAO.getAll();
            //System.out.print(respuesta);
            System.out.print(salas);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
