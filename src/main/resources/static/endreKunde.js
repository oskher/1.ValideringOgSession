$(function(){
    // hent kunden med kunde-id fra url og vis denne i skjemaet
    const id = window.location.search.substring(1);
    const url = "/hentEnKunde?"+id;
    $.get(url,function(kunde){
        $("#id").val(kunde.id); // må ha med id inn skjemaet, hidden i html
        $("#navn").val(kunde.navn);
        $("#adresse").val(kunde.adresse);
    })
    .fail(function() {
        $("#feil").html("Feil i db - prøv igjen senere");
    });
});

function endreKunden() {
    const kunde = {
        id : $("#id").val(), // må ha med denne som ikke har blitt endret for å vite hvilken kunde som skal endres
        navn : $("#navn").val(),
        adresse : $("#adresse").val()
    };
    $.post("/endreEnKunde",kunde,function(){
        window.location.href = "/";
    })
    .fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

