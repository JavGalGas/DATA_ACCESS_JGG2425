package com.jgg.catchup.flights_jgg.models.dto;

public class DropDownMenuOptionDTO {
    private String code;
    private String title;

    public DropDownMenuOptionDTO(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
