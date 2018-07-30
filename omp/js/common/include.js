(function (window, document, undefined) {
    var Include39485748323 = function () {
    }
    Include39485748323.prototype = {
        //倒序循环
        forEach: function (array, callback) {
            var size = array.length;
            for (var i = size - 1; i >= 0; i--) {
                callback.apply(array[i], [i]);
            }
        },
        getFilePath: function () {
            var curWwwPath = window.document.location.href;
            var pathName = window.document.location.pathname;
            var localhostPaht = curWwwPath.substring(0, curWwwPath.indexOf(pathName));
            var projectName = pathName.substring(0, pathName.substr(1).lastIndexOf('/') + 1);
            return localhostPaht + projectName;
        },
        //获取文件内容
        getFileContent: function (url) {
            url = utils.wrapDomain(url);
            var ie = navigator.userAgent.indexOf('MSIE') > 0;
            var o = ie ? new ActiveXObject('Microsoft.XMLHTTP') : new XMLHttpRequest();
            o.open('get', url, false);
            o.send(null);
            return o.responseText;
        },
        parseNode: function (content) {
            var objE = document.createElement("div");
            objE.innerHTML = content;
            return objE.childNodes;
        },
        executeScript: function (content) {
            var SCRIPT_REGEX = /<script[^>]*>([\s\S]*?)<\/script>/g;
//            var SCRIPT_REGEX = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi;
            var r = "";
            while (r = SCRIPT_REGEX.exec(content)) {
//                console.log(r[1]);
                eval(r[1]);
            }
        },
        getHtml: function (content) {
            var mac = /<script>([\s\S]*?)<\/script>/g;
            content.replace(mac, "");
            return content;
        },
        getPrevCount: function (src) {
            var mac = /\.\.\//g;
            var count = 0;
            while (mac.exec(src)) {
                count++;
            }
            return count;
        },
        getRequestUrl: function (filePath, src) {
            if (/http:\/\//g.test(src)) {
                return src;
            }
            var prevCount = this.getPrevCount(src);
            while (prevCount--) {
                filePath = filePath.substring(0, filePath.substr(1).lastIndexOf('/') + 1);
            }
            return filePath + "/" + src.replace(/\.\.\//g, "");
        },
        replaceIncludeElements: function () {
            var $this = this;
            var filePath = $this.getFilePath();
            var includeTals = document.getElementsByTagName("include");
//            console.log(includeTals);

            if (!includeTals) {
                return;
            }

            for (var n = 0; n < includeTals.length; n++) {
                //拿到路径
                var src = includeTals[n].getAttribute("src");
                src = utils.wrapDomain(src);

                //拿到文件内容
                var content = $this.getFileContent($this.getRequestUrl(filePath, src));
                //将文本转换成节点
                var parent = includeTals[n].parentNode;
//                console.log(parent);
                var includeNodes = $this.parseNode($this.getHtml(content));
                var size = includeNodes.length;
                for (var i = 0; i < size; i++) {
                    parent.insertBefore(includeNodes[0], includeTals[n]);
                }
                //执行文本中的javascript
                $this.executeScript(content);
                //parent.removeChild(includeTals[n]);
                //替换元素
                //includeTals[n].parentNode.replaceChild(includeNodes[1], includeTals[n]);
            }
            // 清除include节点
            for (var n = includeTals.length - 1; n >= 0; n--) {
                var parent = includeTals[n].parentNode;
                parent.removeChild(includeTals[n]);
            }
// 加载倒序问题！
//            this.forEach(includeTals, function () {
//                //拿到路径
//                var src = this.getAttribute("src");
//                //拿到文件内容
//                var content = $this.getFileContent($this.getRequestUrl(filePath, src));
//                //将文本转换成节点
//                var parent = this.parentNode;
//                var includeNodes = $this.parseNode($this.getHtml(content));
//                var size = includeNodes.length;
//                for (var i = 0; i < size; i++) {
//                    parent.insertBefore(includeNodes[0], this);
//                }
//                //执行文本中的javascript
//                $this.executeScript(content);
//                parent.removeChild(this);
//                //替换元素 this.parentNode.replaceChild(includeNodes[1], this);
//                console.log(src);
//            })
        }
    }
    window.onload = function () {
        new Include39485748323().replaceIncludeElements();
    }
})(window, document)