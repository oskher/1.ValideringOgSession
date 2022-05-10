function validerOgLagreKunde(){
    const navnOK = validerNavn($("#navn").val());
    const adresseOK = validerAdresse($("#adresse").val());

    if(navnOK && adresseOK){
        lagreKunde();
    }
}

function lagreKunde() {
    const kunde = {
        navn : $("#navn").val(),
        adresse : $("#adresse").val(),
    };
    $.post( "/lagreKunde", kunde, function() {
           window.location.href = '/';
    })
    .fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

