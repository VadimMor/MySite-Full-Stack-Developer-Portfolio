package main.backend.enums;

public enum SortVisibility {
    UP("UP"),
    DOWN("DOWN");

    private final String filter;

    SortVisibility(String filter) {
        this.filter = filter;
    }

    public String getSort() {
        return filter;
    }

    public static SortVisibility fromStatus(String code) {
        for (SortVisibility sort : values()) {
            if (sort.getSort().equals(code)) {
                return sort;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
