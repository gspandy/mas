var url = 'http://10.112.32.141:8901';
var menuDate = JSON.parse(localStorage.getItem("date"));
$(function () {
    if(menuDate != undefined){
        console.log("本地缓存"+menuDate);
        loadDate(menuDate);
    }else {
        var m_tk= getRequest().m_tk;
        var cookie = $.cookie("OMP_LOGIN_USER");
        $.ajax({
            url: url+"/checkLogin",
            type: 'get',
            dataType: 'jsonp',
            jsonp:"jsoncallback",
            jsonpCallback:'callback',
            data:{
                m_tk:m_tk,
                user:cookie
            },
            success: function (date) {
                loadDate(date);
            },
            error: function (err) {
                window.setTimeout(function () {
                    if(localStorage.getItem("date") == null){
                        window.location.href="https://sso.lecommons.com/login.php?site=workbench&backurl=http://omp.mas.letv.cn/index.html";
                    }
                },100);
            }
        });
    }
});
function loadDate(date) {
    var user = date["OMP_LOGIN_USER"];
    var data = date["acl"];
    $.cookie("OMP_LOGIN_USER",user,{ expires: 1, path : '/'});
    document.getElementById("span_username").innerText = jQuery.base64.decode(user);
    document.getElementById("p_username").innerText = "Hello,"+jQuery.base64.decode(user);
    localStorage.setItem("date",JSON.stringify(date));
    $("#treeMenu").remove();
    $("#sidebar").append("<ul class=\"sidebar-menu\" id=\"treeMenu\"></ul>");
    $("#treeMenu").append("<li>\n" +
        "                <a id=\"MainPanel\">\n" +
        "                    <i class=\"fa fa-dashboard\"></i> <span>MainPanel</span>\n" +
        "                </a>\n" +
        "            </li>");
    document.getElementById("MainPanel").href="http://omp.mas.letv.cn/index.html"+window.location.search;
    for(var j = 0,len = data.length; j < len; j++) {
        if (data[j]['_parentId'] == 0) {
            var dto = data[j];
            var id = dto.id;
            $("#treeMenu").append("<li class=\"treeview\">\n" +
                "                <a href=\"#\">\n" +
                "                    <i class=\"fa fa-folder\"></i>\n" +
                "                    <span>" + dto.name + "</span>\n" +
                "                    <i class=\"fa fa-angle-left pull-right\"></i>\n" +
                "                </a>\n" +
                "                <ul class=\"treeview-menu\" id=" + data[j]['name'] + " style=\"display: block;\">\n" +
                "                </ul>\n" +
                "            </li>"
            );
            for (var k = 0, l = data.length; k < l; k++) {
                if (data[k]['_parentId'] == id) {
                    $("#" + dto.name + "").append("<li><a href=" + data[k]['path'] + "><i class=\"fa fa-angle-double-right\"></i>" + data[k]['name'] + "</a></li>");
                }
            }
        }
        if (data[j] == 2) {
            $("#treeMenu").append("<li class=\"treeview\">\n" +
                "                <a href=\"#\">\n" +
                "                    <i class=\"fa fa-folder\"></i>\n" +
                "                    <span>系统管理</span>\n" +
                "                    <i class=\"fa fa-angle-left pull-right\"></i>\n" +
                "                </a>\n" +
                "                <ul class=\"treeview-menu\" id=\"systemMenu\" style=\"display: block;\">\n" +
                "                </ul>\n" +
                "            </li>");
            $("#systemMenu").append("<li><a href=\"../../pages/mas/c_acl_ admin.html\"><i class=\"fa fa-angle-double-right\"></i>权限管理</a></li>");
            $("#systemMenu").append("<li><a href=\"../../pages/mas/c_user_admin.html\"><i class=\"fa fa-angle-double-right\"></i>用户管理</a></li>");
        }
    }
}
function getRequest() {
    var url = window.location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {

            theRequest[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);

        }
    }
    return theRequest;
}
function logout() {
    $.ajax({
        url: url+"/logout",
        type: 'get',
        data:{
          user: $.cookie("OMP_LOGIN_USER")
        },
        dataType: 'jsonp',
        jsonp:"jsoncallback",
        jsonpCallback:'callback',
        success: function (data) {
            $.removeCookie('OMP_LOGIN_USER', {path : '/'});
            localStorage.removeItem("date");
            window.location.href="https://sso.lecommons.com/login.php?site=workbench&backurl=http://omp.mas.letv.cn/index.html";
        },
        error: function (err) {
            console.log("未正常退出！");
            $.removeCookie('OMP_LOGIN_USER', {path : '/'});
            localStorage.removeItem("date");
            window.location.href="https://sso.lecommons.com/login.php?site=workbench&backurl=http://omp.mas.letv.cn/index.html";
        }
    });
}
function login() {
    var mail = $(" input[ name='mail' ] ").val();
    var password = $(" input[ name='password' ] ").val();
    $.ajax({
        url: url+"/login",
        type: 'get',
        data:{
            mail:mail,
            password:password
        },
        dataType: 'jsonp',
        jsonp:"jsoncallback",
        jsonpCallback:'callback',
        success:function(data){
            window.location.href = "http://omp.mas.letv.cn";
        },
        error:function(err){
            console.log("消息服务器网络异常！");
            $(" input[ name='msg' ] ").val("用户名或密码错误");
        }
    });
}