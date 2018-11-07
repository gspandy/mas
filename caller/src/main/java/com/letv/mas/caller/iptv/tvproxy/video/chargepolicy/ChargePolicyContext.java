package com.letv.mas.caller.iptv.tvproxy.video.chargepolicy;

public class ChargePolicyContext {

    private ChargePolicy chargePolicy;

    public ChargePolicyContext(ChargePolicy chargePolicy) {
        this.chargePolicy = chargePolicy;
    }

    public Integer charge() {
        return this.chargePolicy.getChargePolicy();
    }
}
