package main.backend.enums;

import lombok.AllArgsConstructor;

public enum StatusVisibility {
    OPEN("OPEN"),
    DEVELOPMENT("DEVELOPMENT"),
    UPDATE("UPDATE"),
    CLOSED("CLOSED"),
    DELETE("DELETE");

    private final String status;

    StatusVisibility(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static StatusVisibility fromStatus(String code) {
        for (StatusVisibility status : values()) {
            if (status.getStatus().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
