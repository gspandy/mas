$(function () {
    window.setTimeout(checkLogin, 1);
});
function checkLogin() {
    var m_tk= getRequest().m_tk;
    $.ajax({
        url: "http://127.0.0.1:8901/checkLogin",
        type: 'get',
        data:{
            m_tk:m_tk
        },
        dataType: 'jsonp',
        jsonp: "jsoncallback",
        jsonpCallback: 'callback',
        success: function (data) {
            document.getElementById("span_username").innerText = data.split("@")[0];
            document.getElementById("p_username").innerText = "Hello,"+data.split("@")[0];
            $.ajax({
                url: "http://127.0.0.1:8901/loadPageByAcl",
                type: 'get',
                dataType: 'jsonp',
                jsonp:"jsoncallback",
                jsonpCallback:'callback',
                success:function(data){
//                    console.log(data);
                    $("#treeMenu").remove();
                    $("#sidebar").append("<ul class=\"sidebar-menu\" id=\"treeMenu\"></ul>");
                    $("#treeMenu").append("<li>\n" +
                                         "                <a id=\"MainPanel\">\n" +
                                         "                    <i class=\"fa fa-dashboard\"></i> <span>MainPanel</span>\n" +
                                         "                </a>\n" +
                                         "            </li>");
                   document.getElementById("MainPanel").href="http://omp.mas.letv.cn/index.html"+window.location.search;
                    for(var j = 0,len = data.length; j < len; j++) {
                        if(data[j]['parentId'] == 0){
                            $("#treeMenu").append("<li class=\"treeview\">\n" +
                                "                <a href=\"#\">\n" +
                                "                    <i class=\"fa fa-folder\"></i>\n" +
                                "                    <span>MasDev</span>\n" +
                                "                    <i class=\"fa fa-angle-left pull-right\"></i>\n" +
                                "                </a>\n" +
                                                  "                <ul class=\"treeview-menu\" id="+data[j]['name']+" style=\"display: block;\">\n" +
                                "                </ul>\n" +
                                "            </li>"
                            );
                        }
                   if(data[j]['parentId'] == 12){
                   $("#MasDev").append("<li><a href="+data[j]['path']+"><i class=\"fa fa-angle-double-right\"></i>"+data[j]['name']+"</a></li>");
                   }
                   if(data[j]['parentId'] == 17){
                   $("#MasOps").append("<li><a href="+data[j]['path']+"><i class=\"fa fa-angle-double-right\"></i>"+data[j]['name']+"</a></li>");
                   }
                    }
                   
                },
                error:function(err){
                    console.log("消息服务器网络异常！");
                }
            });
        },
        error: function (err) {
            console.log("未正常登录！");
            window.location.href="https://sso.lecommons.com/login.php?site=workbench&backurl=http://omp.mas.letv.cn/index.html";
        }
    });
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
        url: "http://127.0.0.1:8901/logout",
        type: 'get',
        dataType: 'jsonp',
        jsonp: "jsoncallback",
        jsonpCallback: 'callback',
        success: function (data) {
            console.log("正常退出！");
            window.location.href="https://sso.lecommons.com/login.php?site=workbench&backurl=http://omp.mas.letv.cn/index.html";
        },
        error: function (err) {
            console.log("未正常退出！");
            window.location.href="https://sso.lecommons.com/login.php?site=workbench&backurl=http://omp.mas.letv.cn/index.html";
        }
    });
}
function login() {
    var mail = $(" input[ name='mail' ] ").val();
    var password = $(" input[ name='password' ] ").val();
    $.ajax({
        url: "http://127.0.0.1:8901/login",
        type: 'get',
        data:{
            mail:mail,
            password:password
        },
        dataType: 'jsonp',
        jsonp:"jsoncallback",
        jsonpCallback:'callback',
        success:function(data){
//            console.log(data);
            window.location.href = "http://omp.mas.letv.cn";
        },
        error:function(err){
            console.log("消息服务器网络异常！");
            $(" input[ name='msg' ] ").val("用户名或密码错误");
        }
    });
}
