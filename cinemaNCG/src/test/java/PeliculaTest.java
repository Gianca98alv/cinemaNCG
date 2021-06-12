
import com.mycompany.cinemancg.model.Pelicula;
import com.mycompany.cinemancg.model.PeliculaDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class PeliculaTest {
    public static void main(String[] args) {
        try {
            String stringDate = "01:40";
            Date duracion = new SimpleDateFormat("HH:mm").parse(stringDate);
            //Pelicula pelicula = new Pelicula("The Turning", null, duracion, "R", 1);
            PeliculaDAO peliculaDAO = new PeliculaDAO();
            //int respuesta = peliculaDAO.add(pelicula);
            //peliculas = peliculaDAO.get(peliculas.getIdPelicula());
            List<Pelicula> peliculas = peliculaDAO.getAll();
            List<Pelicula> peliculasEstrenadas = peliculaDAO.getAllByEstreno();
            //System.out.print(respuesta);
            System.out.println(peliculas);
            System.out.println(peliculasEstrenadas);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
