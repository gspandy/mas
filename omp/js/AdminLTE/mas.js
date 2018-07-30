$(function () {
    /* For demo purposes */
    var demo = $("<div />").css({
        position: "fixed",
        top: "150px",
        right: "0",
        background: "rgba(0, 0, 0, 0.7)",
        "border-radius": "5px 0px 0px 5px",
        padding: "10px 15px",
        "font-size": "16px",
        "z-index": "999999",
        cursor: "pointer",
        color: "#ddd"
    }).html("<i class='fa fa-gear'></i>").addClass("no-print");

    var demo_settings = $("<div />").css({
        "padding": "10px",
        position: "fixed",
        top: "130px",
        right: "-200px",
        background: "#fff",
        border: "3px solid rgba(0, 0, 0, 0.7)",
        "width": "200px",
        "z-index": "999999"
    }).addClass("no-print");
    demo_settings.append(
            "<h4 style='margin: 0 0 5px 0; border-bottom: 1px dashed #ddd; padding-bottom: 3px;'>Layout Options</h4>"
            + "<div class='form-group no-margin'>"
            + "<div class='.checkbox'>"
            + "<label>"
            + "<input type='checkbox' onchange='change_layout();'/> "
            + "Fixed layout"
            + "</label>"
            + "</div>"
            + "</div>"
    );
    demo_settings.append(
            "<h4 style='margin: 0 0 5px 0; border-bottom: 1px dashed #ddd; padding-bottom: 3px;'>Skins</h4>"
            + "<div class='form-group no-margin'>"
            + "<div class='.radio'>"
            + "<label>"
            + "<input name='skins' type='radio' onchange='change_skin(\"skin-black\");' /> "
            + "Black"
            + "</label>"
            + "</div>"
            + "</div>"

            + "<div class='form-group no-margin'>"
            + "<div class='.radio'>"
            + "<label>"
            + "<input name='skins' type='radio' onchange='change_skin(\"skin-blue\");' checked='checked'/> "
            + "Blue"
            + "</label>"
            + "</div>"
            + "</div>"
    );

    demo.click(function () {
        if (!$(this).hasClass("open")) {
            $(this).css("right", "200px");
            demo_settings.css("right", "0");
            $(this).addClass("open");
        } else {
            $(this).css("right", "0");
            demo_settings.css("right", "-200px");
            $(this).removeClass("open")
        }
    });

    $("body").append(demo);
    $("body").append(demo_settings);

    focusItemInNavBar();
});

function change_layout() {
    $("body").toggleClass("fixed");
    fix_sidebar();
}

function change_skin(cls) {
    $("body").removeClass("skin-blue skin-black");
    $("body").addClass(cls);
}

function focusItemInNavBar() {
    var links = $(".sidebar-menu li"),
    // 取当前URL最后一个/后面的文件名，pop方法是删除最后一个元素并返回最后一个元素
        url = location.href.split("?")[0].split("/").pop();
    console.log("[mas.focusItemInNavBar]: url=" + url);
    if (url) {// 如果有取到，则进行匹配，否则默认首页（即index所指向的那个）
        var subItem = null;
        for (var i = 0; i < links.length; i++) {//遍历menu中的链接地址
            subItem = $(links[i]).children(".treeview-menu");
            $(links[i]).removeClass("active"); // 父节点去选中
            if (subItem.length > 0) {
                subItem = subItem.children("li a");
                console.log(subItem);
                for (var j = 0; j < subItem.length; j++) {//遍历subItem
                    if (subItem[j].href.indexOf(url) != -1) {
                        console.log($(subItem[j]).parent());
                        $(subItem[j]).parent().addClass("active"); // 当前子节点选中
                        $(links[i]).addClass("active"); // 当前父节点选中
                        return false; //break
                    }
                }
            } else {
                subItem = $(links[i]).children("a").first();
                if (subItem.length > 0) {
                    if (subItem[0].href.indexOf(url) != -1) {
                        $(links[i]).addClass("active"); // 当前父节点选中
                        break;
                    }
                }
            }
        }
        $(".sidebar .treeview").length > 0 && $(".sidebar .treeview").tree();
    }
}