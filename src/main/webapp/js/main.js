
function insertHeader() {
	/*
    $.ajax({
        type : "GET",
        url : "/onlinelearning/commons/header.html?random=" + Math.random(),
        cache : false,
        success : function(data) {
            $("#header").html(data);
        }
    });
    */
    $("#header").load("/onlinelearning/commonpages/header.html");
}

function showLogin() {
    $("#loginModal").modal("show");
}

function requireLogin() {
    alert("请先登陆");
    window.location.href = "/onlinelearning/index.html";
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

function getQueryInt(name) {
    var val = getQueryString(name);
    if (val == null) {
        return -1;
    }
    return parseInt(val);
}

$(function() {
    $.ajaxSetup({
        cache : false
    });
    insertHeader();

});

