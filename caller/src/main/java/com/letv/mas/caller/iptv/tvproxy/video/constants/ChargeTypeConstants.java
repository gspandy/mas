package com.letv.mas.caller.iptv.tvproxy.video.constants;

public class ChargeTypeConstants {

    public interface chargePolicy {

        public static final int FREE = 1;// 免费

        public static final int CHARGE_BY_CONTENT = 2;// 按内容收费

        public static final int CHARGE_SMALL_WINDOW = 3;// 小窗试看(电视剧、动漫非第一集)

        public static final int CHARGE_YUAN_XIAN = 4;// 院线收费

        public static final int CHARGE_BY_STREAM = 5;// 按码流收费

        public static final int CHARGE_BY_VIP = 6;// 按会员购买收费，美国Le.com使用

        public static final int CHARGE_NO_COPYRIGTH = 7;// 因内容未设置付费策略，提示无版权观看，美国Le.com使用
    }

    public static final String CHARGE = "1";

    public static final String NOT_CHARGE = "0";
}
