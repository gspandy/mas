$(function () {
    $('#acl').datagrid({
        loader:function(param,success,error){
            //跨域请求数据
            $.ajax({
                url:"http://omp.mas.letv.cn:8901/acl/allAcls",
                type:"get",
                dataType: 'jsonp',
                jsonp:"jsoncallback",
                jsonpCallback:'callback',
                data:{
                    pageNum: $("#acl" ).datagrid("getPager" ).data("pagination" ).options.pageNumber,
                    pageSize: $("#acl" ).datagrid("getPager" ).data("pagination" ).options.pageSize
                },
                success: function (res) {
                    success(res);
                },
                error: function (xhr) {
                    error(xhr.responseText);
                }
            })
        },
        singleSelect:true,
        columns:[[
            {field:'id',title:'Id',width:40},
            {field:'name',title:'Name',width:160,editor:{
                    type:'text',
                    options:{
                        require:true
                    }
                }},
            {field:'path',title:'Path',width:340,editor: {
                    type: 'text'
                }},
            {field:'_parentId',title:'ParentId',width:77,editor: {
                    type: 'text',
                    options:{
                        require:true
                    }
                }}
        ]],
        pagination : true,
        pageSize : 10,
        pageList : [10,20,30,40,50],
        fit : true,
        rownumbers:true,
        //结束编辑后触发的事件
        onAfterEdit:function(rowIndex,rowData,changes){
            //发送ajax请求，将改后的数据提交到服务器，修改数据库
            $.ajax({
                async:false,
                url:"http://omp.mas.letv.cn:8901/acl/updateAcl",
                type:"post",
                dataType: 'jsonp',
                jsonp:"jsoncallback",
                jsonpCallback:'callback',
                data:{
                    id:rowData.id,
                    name:rowData.name,
                    path:rowData.path,
                    _parentId:rowData._parentId
                },
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
                    $("#acl").datagrid('reload');
                },
                error: function (xhr) {
                    console.log(xhr.responseText);
                    $("#acl").datagrid('reload');
                }
            });
            // console.log(rowData.code);
        },
        //定义表格的工具栏
        toolbar:[
            {text:'添加',iconCls:'icon-add',handler:function(){
                    //添加一行
                    $("#acl").datagrid("insertRow",{
                        index:0,//在第一行插入
                        row:{}//空行
                    });
                    myIndex = 0;
                    //开启编辑
                    $("#acl").datagrid("beginEdit",0);
                }},//每个json表示一个按钮
            {text:'删除',iconCls:'icon-remove',handler:function(){
                    $.messager.confirm('确认','您确认想要删除记录吗？',function(r){
                        if (r){
                            //获得当前选中行的索引
                            var rows = $("#acl").datagrid("getSelections");
                            if(rows.length == 1){
                                var row = rows[0];
                                myIndex = $("#acl").datagrid("getRowIndex",row);
                                //删除行
                                $("#acl").datagrid("deleteRow",myIndex);
                                if(row.id != undefined){
                                    $.ajax({
                                        async:false,
                                        url:"http://omp.mas.letv.cn:8901/acl/deleteAcl",
                                        type:"post",
                                        dataType: 'jsonp',
                                        jsonp:"jsoncallback",
                                        jsonpCallback:'callback',
                                        data:{
                                            id:row.id
                                        },
                                        success: function (res) {
                                            if(res == 1){
                                                $.messager.show({
                                                    title:'删除状态',
                                                    msg:'删除成功',
                                                    timeout:5000,
                                                    showType:'slide'
                                                });
                                            }else {
                                                $.messager.show({
                                                    title:'删除状态',
                                                    msg:'删除失败',
                                                    timeout:5000,
                                                    showType:'slide'
                                                });
                                            }
                                        },
                                        error: function (xhr) {
                                            console.log(xhr.responseText);
                                        }
                                    });
                                }
                            }
                        }
                    });
                }},
            {text:'保存',iconCls:'icon-save',handler:function(){
                    //结束编辑
                    $("#acl").datagrid("endEdit",myIndex);
                }},
            {text:'修改',iconCls:'icon-edit',handler:function(){
                    //获得当前选中行的索引
                    var rows = $("#acl").datagrid("getSelections");
                    if(rows.length == 1){
                        myIndex = $("#acl").datagrid("getRowIndex",rows[0]);
                        $("#acl").datagrid("beginEdit",myIndex);
                    }
                }}
        ]
    });
});
