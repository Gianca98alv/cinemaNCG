
import com.mycompany.cinemancg.model.Funcion;
import com.mycompany.cinemancg.model.FuncionDAO;
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

public class FuncionTest {
    public static void main(String[] args) {
        try {
            String stringDate = "7/9/2021 02:00";
            Date inicio = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(stringDate);
            PeliculaDAO peliculaDAO = new PeliculaDAO();
            SalaDAO salaDAO = new SalaDAO();
            Pelicula pelicula = peliculaDAO.get("It");
            Sala sala = salaDAO.get(1);
            Funcion funcion = new Funcion(1, 3000.0f, inicio);
            FuncionDAO funcionDAO = new FuncionDAO();
            funcion.setPelicula(pelicula);
            funcion.setSala(sala);
            int respuesta = funcionDAO.add(funcion);
            //funcions = funcionDAO.get(funcions.getIdFuncion());
            List<Funcion> funcions = funcionDAO.getAll();
            //System.out.print(respuesta);
            //System.out.println(funcions);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
