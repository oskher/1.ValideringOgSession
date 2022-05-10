package oslomet.webprog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
public class KundeController {

    @Autowired
    private KundeRepository rep;

    private boolean validerNavn (Kunde kunde){
        String regexpNavn =  "[a-zæøåA-ZÆØÅ. \\-]{2,30}";  // Java valid-språk:  "[ \\]{}"  "=starter ,   deler mellom talla og tegn= \\ ,    og  slutter="
        String regexpAdresse = "[0-9a-zæøåA-ZÆØÅ. \\-]{2,50}";

        boolean navnOK = kunde.getNavn().matches(regexpNavn);             //  HUSK!   sessionFunksjon: matches()  sammenligner med JS/klient.
        boolean adresseOK = kunde.getAdresse().matches(regexpAdresse);

        if(navnOK && adresseOK){
            return true;
        } else {
            return false;
        }
    }      // Husk å lag if-setning validering i lagreKunde i controller:

    @PostMapping("/lagreKunde")
    public void lagreKunde(Kunde kunde, HttpServletResponse response) throws IOException {
        if (!validerNavn(kunde)){ //    Husk !  = Not correct
            response.sendError(HttpStatus.NOT_ACCEPTABLE.value()); // får feilmld nr.29? hvis feil
        }
        else { // else = hvis alt er ok gjør..:
            if (!rep.lagreKunde(kunde)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
            }
        }
    }

    @GetMapping("/hentKunder")
    public List<Kunde> hentAlle (HttpServletResponse response) throws IOException {
//        legg til session logout:
        if (session.getAttribute("innlogget")!=null) { // Betyr du er logget inn!

            List<Kunde> alleKunder = rep.hentAlleKunder();
            if(alleKunder==null) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
            }
            return alleKunder;
        } else {                    //  else gi feilmelding ved feil
            response.sendError(HttpStatus.NOT_FOUND.value());
            
        }
    }

    @GetMapping("/hentEnKunde")
    public Kunde hentEnKunde(int id, HttpServletResponse response) throws IOException {
        Kunde kunden = rep.hentEnKunde(id);
        if(kunden == null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
        return kunden;
    }

    @PostMapping("/endreEnKunde")
    public void endreEnKunde(Kunde kunde,HttpServletResponse response) throws IOException{
        if(!rep.endreEnKunde(kunde)){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
    }

    @GetMapping("/slettEnKunde")
    public void slettEnKunde(int id,HttpServletResponse response) throws IOException{
        if(!rep.slettEnKunde(id)){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
    }

    @GetMapping("/slettAlleKunder")
    public void slettAlle(HttpServletResponse response) throws IOException{
        if(!rep.slettAlleKunder()){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
    }

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public boolean login(Kunde kunde){
        if (rep.sjekkNavnOgPassord(kunde)){
            session.setAttribute("innlogget", kunde);
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/logout")
    public void logOut(){
        session.removeAttribute("innlogget");

    }
}