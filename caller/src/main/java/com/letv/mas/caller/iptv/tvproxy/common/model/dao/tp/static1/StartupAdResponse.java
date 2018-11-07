/**
 * 
 */
package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yangjz
 */
@XmlRootElement(name = "result")
public class StartupAdResponse<T> {
    private Integer status;
    private String msg;
    private String postAd;
    private List<T> items;
    private String loadingPic;

    public StartupAdResponse() {
        super();
    }

    public StartupAdResponse(Integer status, String msg, String postAd) {
        this.status = status;
        this.msg = msg;
        this.postAd = postAd;
    }

    public String getLoadingPic() {
        return loadingPic;
    }

    public void setLoadingPic(String loadingPic) {
        this.loadingPic = loadingPic;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPostAd() {
        return this.postAd;
    }

    public void setPostAd(String postAd) {
        this.postAd = postAd;
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}
