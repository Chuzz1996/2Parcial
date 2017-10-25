var app = (function () {

    var jugador;
    var nombreJugador="NN";
    
    var stompClient = null;
    var gameid = 0;
    var photo;
    
    var dibujar = function(data){
        $("#palabra").html("<h1>" + data + "</h1>");
    }
    
    var wsconnect = function () {

            var socket = new SockJS('/stompendpoint');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                stompClient.subscribe('/topic/wupdate.'+gameid,function(eventbody){
                    dibujar(eventbody.body);
                });
                stompClient.subscribe('/topic/winner.'+gameid,function(eventbody){
                    var theObject=JSON.parse(eventbody.body);
                    document.getElementById("estado").innerHTML = "finalizado";
                    document.getElementById("ganador").innerHTML = theObject.username;
                });
            
            });

        };

    var palabra = function(data){
           $("#palabra").html("<h1>" + data + "</h1>");
    }

    return {

        loadWord: function () {
            
            gameid = $("#gameid").val();
            $.get("/hangmangames/" + gameid +"/currentword",
                    function (data) {
                        $("#palabra").html("<h1>" + data + "</h1>");
                        wsconnect();
                    }
            ).fail(
                    function (data) {
                        alert(data["responseText"]);
                    }

            );


        },

        sendLetter: function () {

            var id = gameid;

            var hangmanLetterAttempt = {letter: $("#caracter").val(), username: nombreJugador};

            var send = $.ajax({
                url: "/hangmangames/" + id + "/letterattempts",
                type: "POST",
                data: JSON.stringify(hangmanLetterAttempt),
                contentType: "application/json"
            })
            
            send.then(
                    function(){
                        $.get("/hangmangames/" + gameid +"/currentword",function(data){
                            stompClient.send('/topic/wupdate.'+gameid,{}, data);
                        });
                        
                    },function(){
                        alert("NO LLEGO");
                    }
            );

        },

        sendWord: function () {
            
            var hangmanWordAttempt = {word: $("#adivina").val(), username: nombreJugador};
            
            var id = gameid;

           
            jQuery.ajax({
                url: "/hangmangames/" + id + "/wordattempts",
                type: "POST",
                data: JSON.stringify(hangmanWordAttempt),
                dataType: "json",
                contentType: "application/json; charset=utf-8"
            }).then(
                function(){
                    alert("FALLO");
                },function(){
                    stompClient.send('/app/wupdate.'+gameid,{}, JSON.stringify({"username":nombreJugador ,"word":$("#adivina").val()}));
                }
            );

            
        },
        
        addUser: function(){
            $.get("/users/"+document.getElementById("playerid").value+"/",function(val){
                jugador = val;
                nombreJugador = jugador.name;
                photo = jugador.photoUrl;
                document.getElementById("nombreJugador").innerHTML = nombreJugador;
                document.getElementById("ImagenUsuario").src = jugador.photoUrl;
            });
        }

    };

})();

