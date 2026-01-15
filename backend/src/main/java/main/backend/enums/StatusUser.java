package main.backend.enums;

public enum StatusUser {
    CREATED("CREATED"),
    CONFIRMATION("CONFIRMATION"),
    BLOCK("BLOCK"),
    BAN("BAN");

    private final String filter;

    StatusUser(String filter) {
        this.filter = filter;
    }

    public String getSort() {
        return filter;
    }

    public static StatusUser fromStatus(String code) {
        for (StatusUser sort : values()) {
            if (sort.getSort().equals(code)) {
                return sort;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
