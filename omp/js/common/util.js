/**
 * Created by leeco on 18/7/27.
 */
// v1.0.0
var utils = {

    wrapDomain: function(url) {
        if (url.indexOf("http") != 0) {
            var host = window.location.protocol + "//" + window.location.host;

//            if (window.location.port) {
//                host += ":" + window.location.port;
//            }

            if (url.indexOf("/") > 0) {
                url = host + "/" + url;
            }
        }
        return url;
    },

    /**
     * 去除空格
     * @param str
     * @param type type 1-所有空格  2-前后空格  3-前空格 4-后空格
     * @returns {*}
     */
    trim: function (str, type) {
        switch (type) {
            case 1:
                return str.replace(/\s+/g, "");
            case 2:
                return str.replace(/(^\s*)|(\s*$)/g, "");
            case 3:
                return str.replace(/(^\s*)/g, "");
            case 4:
                return str.replace(/(\s*$)/g, "");
            default:
                return str;
        }
    },

    loadScript: function (url, callback) {
        var script = document.createElement("script");
        script.type = "text/javascript";

        url = utils.wrapDomain(url);

        if (script.readyState) {
            script.onreadystatechange = function () {
                if (script.readyState == "loaded" || script.readyState == "complete") {
                    script.onreadystatechange = null;
                    if (callback && typeof callback != 'undefined' && callback != undefined) {
                        callback();
                    }
                }
            }
        } else {
            script.onload = function () {
                if (callback && typeof callback != 'undefined' && callback != undefined) {
                    callback();
                }
            }
        }

        script.src = url;
        document.getElementsByTagName("head")[0].appendChild(script);
    }
}