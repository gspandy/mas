package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.util.List;

public class LiveChatRoomHistoryResponse {
    private String code;
    private DataNode data;
    public static final String SUCCESS_CODE = "200";

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataNode getData() {
        return this.data;
    }

    public String getServerAddr() {
        if (this.data != null && this.data.getResult() != null && this.data.getResult().getServer() != null) {
            return this.data.getResult().getServer().getServer();
        }
        return null;

    }

    public static final class DataNode {
        private String errorMessage;
        private Result result;

        public String getErrorMessage() {
            return this.errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public Result getResult() {
            return this.result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

    }

    public void setData(DataNode data) {
        this.data = data;
    }

    public static final class Result {
        private ServerNode server;
        private List<Object> list;

        public Result(String string) {
        }

        public Result() {
        }

        public ServerNode getServer() {
            return this.server;
        }

        public void setServer(ServerNode server) {
            this.server = server;
        }

        public List<Object> getList() {
            return this.list;
        }

        public void setList(List<Object> list) {
            this.list = list;
        }

    }

    public static final class ServerNode {
        private String server;

        public String getServer() {
            return this.server;
        }

        public void setServer(String server) {
            this.server = server;
        }

    }
}
