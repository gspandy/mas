package com.letv.mas.caller.iptv.tvproxy.video;

import com.letv.mas.caller.iptv.tvproxy.video.constants.MobileConstant;
import org.apache.commons.lang.ArrayUtils;

public enum ServerConfPath {
    LEAD("lead"),
    LECOM("lecom");

    private String terminal;

    private ServerConfPath(String terminal) {
        this.terminal = terminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public static ServerConfPath getTerminal(String terminalApplication) {
        ServerConfPath result = null;
        if (ArrayUtils.contains(MobileConstant.LECOM_TERMINAL, terminalApplication)) {
            result = LECOM;
        } else {
            result = LEAD;
        }
        return result;
    }
}
