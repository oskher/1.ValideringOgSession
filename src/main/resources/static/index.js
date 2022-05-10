$(function(){
    hentAlleKunder();
});

function hentAlleKunder() {
    $.get( "/hentKunder", function( kunder ) {
        formaterKunder(kunder);
    })
    .fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

function formaterKunder(kunder){
    let ut = "<table class='table table-striped'>" +
        "<tr>" +
            "<th>Navn</th><th>Adresse</th><th></th><th></th>" +
        "</tr>";
    for(const kunde of kunder ){
        ut+="<tr>" +
                "<td>"+kunde.navn+"</td>"+
                "<td>"+kunde.adresse+"</td>"+
                "<td> <a class='btn btn-primary' href='endreKunde.html?id="+kunde.id+"'>Endre</a></td>"+
                "<td> <button class='btn btn-danger' onclick='slettEnKunde("+kunde.id+")'>Slett</button></td>"+
            "</tr>";
    }
    $("#kundene").html(ut);
}

function slettEnKunde(id) {
    const url = "/slettEnKunde?id="+id;
    $.get( url, function() {
        window.location.href = "/";
    })
    .fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

function slettKundene() {
    $.get( "/slettAlleKunder", function() {
        window.location.href = "/";
    })
    .fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}