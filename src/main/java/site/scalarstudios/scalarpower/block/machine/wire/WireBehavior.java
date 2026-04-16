package site.scalarstudios.scalarpower.block.machine.wire;

public enum WireBehavior {
    INPUT_OUTPUT("Input/Output"),
    INPUT("Input"),
    OUTPUT("Output"),
    DISABLED("Disabled");

    private final String displayName;

    WireBehavior(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public WireBehavior next() {
        WireBehavior[] values = WireBehavior.values();
        return values[(this.ordinal() + 1) % values.length];
    }

    public static WireBehavior fromOrdinal(int ordinal) {
        WireBehavior[] values = WireBehavior.values();
        return values[ordinal % values.length];
    }
}

