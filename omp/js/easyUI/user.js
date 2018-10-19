$(function () {
    $('#ss').searchbox({
        searcher:function(value,name){
            $.ajax({
                url: "http://127.0.0.1:8901/acl/findUsersByMail",
                type: "get",
                data:{
                    mail:value
                },
                dataType: 'jsonp',
                jsonp: "jsoncallback",
                jsonpCallback: 'callback',
                success: function (data) {
                    $('#dg').datagrid('loadData',data);
                },
                error: function (xhr) {
                    console.log(xhr.responseText);
                }
            })
        },
        prompt:'请输入邮箱'
    });
    var myIndex;//全局变量，记录当前选中行的索引
    $('#dg').datagrid({
        loader:function(param,success,error){
            //跨域请求数据
            $.ajax({
                url:"http://127.0.0.1:8901/acl/allUsers",
                type:"get",
                data:{
                    pageNum: $("#dg" ).datagrid("getPager" ).data("pagination" ).options.pageNumber,
                    pageSize: $("#dg" ).datagrid("getPager" ).data("pagination" ).options.pageSize
                },
                dataType: 'jsonp',
                jsonp:"jsoncallback",
                jsonpCallback:'callback',
                success: function (res) {
                    success(res);
                },
                error: function (xhr) {
                    error(xhr.responseText);
                }
            })
        },
        pagination : true,
        pageSize : 10,
        pageList : [10,20,30,40,50],
        fit : true,
        rownumbers:true,
        singleSelect:true,
        columns:[[
            {field:'mail',title:'Mail',width:200},
            {field:'type',title:'Type',width:50,editor:{
                    type:'text',
                    options:{
                        required:true
                    }
                },formatter:function(value,row,index){
                    if(value == 2){
                        return "管理员";
                    }else {
                        return "用户";
                    }
                }},
            {field:'code',title:'Code',width:297,editor:{
                    type:'text',
                    options:{
                        required:true
                    }
                }}
        ]],
        //定义表格的工具栏
        toolbar:[
            {text:'添加',iconCls:'icon-add',handler:function(){
                    //添加一行
                    $("#dg").datagrid("insertRow",{
                        index:0,//在第一行插入
                        row:{}//空行
                    });
                    myIndex = 0;
                    //开启编辑
                    $("#dg").datagrid("beginEdit",0);
                }},//每个json表示一个按钮
            {text:'删除',iconCls:'icon-remove',handler:function(){
                    //获得当前选中行的索引
                    var rows = $("#dg").datagrid("getSelections");
                    if(rows.length == 1){
                        var row = rows[0];
                        myIndex = $("#dg").datagrid("getRowIndex",row);
                        //删除行
                        $("#dg").datagrid("deleteRow",myIndex);
                    }
                }},
            {text:'保存',iconCls:'icon-save',handler:function(){
                    //结束编辑
                    $("#dg").datagrid("endEdit",myIndex);
                }},
            {text:'修改',iconCls:'icon-edit',handler:function(){
                    //获得当前选中行的索引
                    var rows = $("#dg").datagrid("getSelections");
                    if(rows.length == 1){
                        document.getElementById("menuTree").style.visibility="visible";
                        var setting = {
                            data : {
                                key : {
                                    title : "权限列表"
                                },
                                simpleData : {
                                    enable : true//使用简单格式json数据
                                }
                            },
                            check : {
                                enable : true//启用复选框效果
                            }
                        };

                        //跨域请求数据
                        $.ajax({
                            url:'http://127.0.0.1:8901/acl/allAclsAsTree',
                            type:"get",
                            data:{
                                pageNum: $("#dg" ).datagrid("getPager" ).data("pagination" ).options.pageNumber,
                                pageSize: $("#dg" ).datagrid("getPager" ).data("pagination" ).options.pageSize,
                                name: rows[0].mail
                            },
                            dataType: 'jsonp',
                            jsonp:"jsoncallback",
                            jsonpCallback:'callback',
                            success: function (data) {
                                $("#myIndex").val($('#dg').datagrid('getRowIndex',rows[0]));
                                $("#updateId").val(rows[0].id);
                                $("#mailName").html("正在修改邮箱为<br/>"+rows[0].mail+"的权限");
                                if(rows[0].type == 2){
                                    document.getElementById('radio2').checked=true;
                                }else {
                                    document.getElementById('radio1').checked=true;
                                }
                                var zNodes = eval(data);
                                $.fn.zTree.init($("#menuTree"), setting, zNodes);
                                document.getElementById("mailName").style.visibility="visible";
                                document.getElementById("saveBtn").style.visibility="visible";
                                document.getElementById("updateType").style.visibility="visible";
                            },
                            error: function (xhr) {
                                console.log(xhr);
                            }
                        });
                    }
                }}
        ]
    });
});
function saveAcl() {
    var treeObj = $.fn.zTree.getZTreeObj("menuTree");
    var nodes = treeObj.getCheckedNodes(true);
    var str = '';
    if( nodes != null && nodes.length != 0){
        for (var i = 0 ; i < nodes.length ; i++){
            if(i == (nodes.length - 1)){
                str += nodes[i].id;
            }else {
                str += nodes[i].id+",";
            }
        }
    }
    document.getElementById("mailName").style.visibility="hidden";
    document.getElementById("menuTree").style.visibility="hidden";
    document.getElementById("saveBtn").style.visibility="hidden";
    document.getElementById("updateType").style.visibility="hidden";
    var myIndex = $("#myIndex").val();
    var updateId = $("#updateId").val();
    var type = document.getElementsByName("userType");
    if(type[0].checked == true){
        type = type[0].value;
    }else {
        type = type[1].value;
    }
    $('#dg').datagrid('updateRow',{
        index: myIndex,
        row: {
            code: str
        }
    });
    $.ajax({
        url:"http://127.0.0.1:8901/acl/updateUserAcl",
        type:"get",
        data:{
            id:updateId,
            type:type,
            code:str
        },
        dataType: 'jsonp',
        jsonp:"jsoncallback",
        jsonpCallback:'callback',
        success: function (res) {
            if(res == 1){
                $.messager.show({
                    title:'修改状态',
                    msg:'修改成功',
                    timeout:5000,
                    showType:'slide'
                });
            }else {
                $.messager.show({
                    title:'修改状态',
                    msg:'修改失败',
                    timeout:5000,
                    showType:'slide'
                });
            }
            $("#dg").datagrid('reload');
        },
        error: function (xhr) {
            console.log(xhr);
        }
    });

}
