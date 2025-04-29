package com.jgg.catchup.flights_jgg.models.dto;

public class ResultOptionDTO {
    private boolean can_buy;
    private String reason;

    public ResultOptionDTO(boolean can_buy, String reason) {
        this.can_buy = can_buy;
        this.reason = reason;
    }

    public boolean getCan_buy() {
        return can_buy;
    }

    public void setCan_buy(boolean can_buy) {
        this.can_buy = can_buy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
