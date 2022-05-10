function validerNavn(navn){
    const regexp = /^[a-zæøåA-ZÆØÅ. \-]{2,30}$/;   // JS-validering-språk:    /^starter ,  \deler? ,  og slutter $/.
    const ok = regexp.test(navn);

    if(!ok){
        $("#feilNavn").html("Navn må bestå av 2 til 30 bokstaver.");
        return false;
    }else{
        $("#feilNavn").html("");
        return true;
    }
}

function validerAdresse(adresse){
    const regexp = /^[0-9a-zæøåA-ZÆØÅ. \-]{2,50}$/;  // Husk A-ZÆØÅ   etter zæøå.     IKKE= zæøå-ZÆØÅ
    const ok = regexp.test(adresse);

    if(!ok){
        $("#feilAdresse").html("Adresse må bestå av 2-50 tegn.");
        return false;
    } else {
        $("#feilAdresse").html("");
        return true;
    }
} //  Husk å endre LagreMetode i lagreKunde.js   - må ha validerOgLagreKunde()