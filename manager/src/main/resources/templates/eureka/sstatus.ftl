<#import "/spring.ftl" as spring />
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
    <base href="<@spring.url basePath/>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Eureka</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <link rel="stylesheet" href="eureka/css/wro.css">
    <style type="text/css">
        p{margin: 5px auto}
        .div {
            display: inline-block;
            padding: .3em .5em;
            background-image: linear-gradient(#ddd, #bbb);
            border: 1px solid rgba(0,0,0,.2);
            border-radius: .3em;
            box-shadow: 0 1px white inset;
            text-align: center;
            color:black;
            font-weight: bold;
        }
        .div:active{
            box-shadow: .05em .1em .2em rgba(0,0,0,.6) inset;
            border-color: rgba(0,0,0,.3);
            background: #bbb;
        }
    </style>
</head>

<body id="one">
    <#include "header.ftl">
<div class="container-fluid xd-container">
      <#include "navbar.ftl">
    <h1>Instances currently registered with Eureka</h1>
    <table id='instances' class="table table-striped table-hover">
        <thead>
        <tr><th>Application</th><#--<th>AMIs</th>--><th>Availability Zones</th><th>Status</th></tr>
        </thead>
        <tbody>
          <#if apps?has_content>
            <#list apps as app>
              <tr>
                  <td><b>${app.name}</b>&nbsp;&nbsp;<button class="div" onclick="allBusRefresh('${app.name}')">刷</button></td>
                  <#--<td>
                  <#list app.amiCounts as amiCount>
                      <b>${amiCount.key}</b> (${amiCount.value})<#if amiCount_has_next>,</#if>
                  </#list>
                  </td>-->
                  <td>
                  <#list app.zoneCounts as zoneCount>
                      <b>${zoneCount.key}</b> (${zoneCount.value})<#if zoneCount_has_next>,</#if>
                  </#list>
                  </td>
                  <td>
                  <#list app.instanceInfos as instanceInfo>
                    <#if instanceInfo.isNotUp>
                      <font color=red size=+1><b>
                    </#if>
                      <b>${instanceInfo.status}</b> (${instanceInfo.instances?size}) -
                    <#if instanceInfo.isNotUp>
                      </b></font>
                    </#if>
                    <#list instanceInfo.instances as instance>
                    <table
        <tbody>
        <tr>
            <td>
                <p>
                        <#if instance.zone??>
                            <font color=red size=+1>${instance.zone}</font> &nbsp;&nbsp;
                        </#if>
                        <#if instance.isHref>
                        <a href="${instance.url}" target="_blank">${instance.id}</a>
                        <#else>
                            ${instance.id}
                        </#if><#if instance_has_next></#if>
                    <#if instance.weight??>
                        <#if instance.weight?length gt 0>
                            <font color=red size=+1>(${instance.weight})</font>
                        </#if>
                    </#if>
                    &nbsp;&nbsp;<button class="div" onclick="busRefresh('${app.name}','${instance.profiles}','${instance.index}')">刷</button>&nbsp;&nbsp;<button class="div" onclick="pause('${app.name}','${instance.id}')">摘</button>&nbsp;&nbsp;<button class="div" onclick="resume('${app.name}','${instance.id}')">挂</button>
                    &nbsp;<input placeholder="weight" maxlength="4" size="6" id="${instance.id}"/> <button class="div" onclick="updateWeight('${app.name}','${instance.id}')">改</button>
                </p>
            </td>
        </tr>
        </tbody>
                    </table>
                    </#list>
                  </#list>
                  </td>
              </tr>
            </#list>
          <#else>
            <tr><td colspan="4">No instances available</td></tr>
          </#if>

        </tbody>
    </table>

    <h1>General Info</h1>

    <table id='generalInfo' class="table table-striped table-hover">
        <thead>
        <tr><th>Name</th><th>Value</th></tr>
        </thead>
        <tbody>
          <#list statusInfo.generalStats?keys as stat>
            <tr>
                <td>${stat}</td><td>${statusInfo.generalStats[stat]!""}</td>
            </tr>
          </#list>
          <#list statusInfo.applicationStats?keys as stat>
            <tr>
                <td>${stat}</td><td>${statusInfo.applicationStats[stat]!""}</td>
            </tr>
          </#list>
        </tbody>
    </table>

    <h1>Instance Info</h1>

    <table id='instanceInfo' class="table table-striped table-hover">
        <thead>
        <tr><th>Name</th><th>Value</th></tr>
        <thead>
        <tbody>
          <#list instanceInfo?keys as key>
          <tr>
              <td>${key}</td><td>${instanceInfo[key]!""}</td>
          </tr>
          </#list>
        </tbody>
    </table>
</div>
<script type="text/javascript" src="eureka/js/wro.js" ></script>
<script type="text/javascript">
    $(document).ready(function() {
        $('table.stripeable tr:odd').addClass('odd');
        $('table.stripeable tr:even').addClass('even');
    });
    /*$(document).on("click", '#reflush', function () {

        $.ajax({
            type: "POST",
            url:  "http://config:config2018@config.mas.letv.cn/bus/refresh?destination=letv-mas-manager:prod:10.129.29.90",
            success: function (data) {
                alert("请求完毕！");
            },
            error: function (data) {
                alert("请求完毕！");
            }

        });
    });*/
    function busRefresh(app,profiles,index){

        var r=confirm("确定要刷新 app："+app+" index："+index+" profiles："+profiles+" 的配置吗？")

        if (r==true)
        {
            if(!app){
                alert("参数（app）不能为空")
                return;
            }
            if(!profiles){
                alert("参数（profiles）不能为空")
                return;
            }
            if(!index){
                alert("参数（index）不能为空")
                return;
            }
            //var url = "/reflush?app="+app+"&profiles="+profiles+"&index="+index;
            $.ajax({
                type: "POST",
                //url:  "http://config:config2018@config.mas.letv.cn/bus/refresh?destination=letv-mas-manager:prod:10.129.29.90",
                url: "/busRefresh",
                data:{
                    "app":app,
                    "profiles":profiles,
                    "index":index
                },
                success: function (data) {
                    if(data){
                        alert("请求成功！");
                    }else{
                        alert("请求失败！");
                    }
                },
                error: function (data) {
                    alert("请求失败！");
                }

            });
        }
    }
    function allBusRefresh(app){
        var r=confirm("确定要刷新 app："+app+" 下所有实例的配置吗？")

        if (r==true) {
            $.ajax({
                type: "POST",
                url: "/busRefresh",
                data: {
                    "app": app
                },
                success: function (data) {
                    if (data) {
                        alert("请求成功！");
                    } else {
                        alert("请求失败！");
                    }
                },
                error: function (data) {
                    alert("请求失败！");
                }

            });
        }
    }
    function pause(app,id){

        var r=confirm("确定要摘掉 app："+app+" id："+id+" 吗？")

        if (r==true)
        {
            if(!app){
                alert("参数（app）不能为空")
                return;
            }
            if(!id){
                alert("参数（id）不能为空")
                return;
            }
            $.ajax({
                type: "POST",
                url: "/pause",
                data:{
                    "app":app,
                    "id":id
                },
                success: function (data) {
                    if(data){
                        alert("请求成功！");
                        window.location.reload();
                    }else{
                        alert("请求失败！");
                    }
                },
                error: function (data) {
                    alert("请求失败！");
                }

            });
        }
    }
    function resume(app,id){

        var r=confirm("确定要挂上 app："+app+" id："+id+" 吗？")

        if (r==true)
        {
            if(!app){
                alert("参数（app）不能为空")
                return;
            }
            if(!id){
                alert("参数（id）不能为空")
                return;
            }
            $.ajax({
                type: "POST",
                url: "/resume",
                data:{
                    "app":app,
                    "id":id
                },
                success: function (data) {
                    if(data){
                        alert("请求成功！");
                        window.location.reload();
                    }else{
                        alert("请求失败！");
                    }
                },
                error: function (data) {
                    alert("请求失败！");
                }

            });
        }
    }
    function updateWeight(app,id) {
        var weightInput = document.getElementById(id);
        var weight = weightInput.value;
        var r = confirm("确定要修改 app：" + app + " id：" + id + " 的权重为" + weight + " 吗？")

        if (r == true) {
            if (!app) {
                alert("参数（app）不能为空")
                return;
            }
            if (!id) {
                alert("参数（id）不能为空")
                return;
            }
            if (!weight) {
                alert("参数（weight）不能为空")
                return;
            }
            if (isNaN(weight)){
                alert("参数（weight）必须是数字")
                return;
            }
            var url = '/eureka/apps/' + app + '/' + id
                    + '/metadata?weight=' + weight;
            $.ajax({
                type: "PUT",
                url: url,
                success: function (data) {
                    window.location.reload();
                },
                error: function (data) {
                    alert("请求失败！");
                }

            });
        }
    }
</script>
</body>
</html>
