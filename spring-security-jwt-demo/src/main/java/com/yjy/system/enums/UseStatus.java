package com.yjy.system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UseStatus {
    CAN_USE("可用"),
    CAN_NOT_USE("不可用");

    private String status;

    UseStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return this.status;
    }

    @JsonCreator
    public static UseStatus fromRole(String status) {
        for (UseStatus type : UseStatus.values()) {
            if (type.getName().equals(status)) {
                return type;
            }
        }
        return null;
    }
}
