var base_url = "http://192.168.1.4:8080/iRadio-0.1.0"
$(document).ready(function() {
    $.ajaxSetup({ cache: false });
    $.ajax({
        type:"GET",
        url: base_url + "/station",
        datatype: "json",
        cache: false,
        headers: { "cache-control": "no-cache" }
    }).then(function(data) {
       if(data)
        $('.current_station').text(data.name);
    });
});

function onClickPrevious() {
    $.ajax({
        type:"POST",
        url: base_url + "/previous",
        datatype: "json"
    }).then(function(data) {
       if(data)
        $('.current_station').text(data.name);
    });
}

function onClickNext() {
    $.ajax({
            type:"POST",
            url: base_url + "/next",
            datatype: "json"
        }).then(function(data) {
           if(data)
            $('.current_station').text(data.name);
        });
}

function onClickPlay() {
$.ajax({
        type:"POST",
        url: base_url + "/play",
    });
}

function onClickStop() {
 $.ajax({
         type:"POST",
         url: base_url + "/stop",
     });
}

function onClickVolumeDown(){
 $.ajax({
         type:"POST",
         datatype: "application/json",
         url: base_url + "/volume",
         "headers": {
             "content-type": "application/json",
             "cache-control": "no-cache"
           },
           "processData": false,
           "data": "false"
     });
}

function onClickVolumeUp(){
 $.ajax({
         type:"POST",
                  datatype: "application/json",
                  url: base_url + "/volume",
                  "headers": {
                      "content-type": "application/json",
                      "cache-control": "no-cache"
                    },
                    "processData": false,
                    "data": "true"
     });
}











/*



function getStation() {
alert("script called");
    var request = new XMLHttpRequest();
    request.open('GET', 'http://localhost:8080/station', true);
    //request.setRequestHeader("Content-type", "application/json");
    request.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("current_station").innerHTML = JSON.parse(this.responseText).name;
            alert(this.responseText);
        }
    }
    request.send();
}

function onClickPrevious() {
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/previous', true);
    request.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("current_station").innerHTML = JSON.parse(this.responseText).name;
        }
    }
    request.send();
}

function onClickNext() {
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/next', true);
    request.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("current_station").innerHTML = JSON.parse(this.responseText).name;
        }
    }
    request.send();
}

function onClickPlay() {
        var request = new XMLHttpRequest();
        request.open('POST', 'http://localhost:8080/play', true);
        request.send();
}

function onClickStop() {
        var request = new XMLHttpRequest();
        request.open('POST', 'http://localhost:8080/stop', true);
        request.send();
}
*/