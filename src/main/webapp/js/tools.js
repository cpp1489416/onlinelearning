
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

});

