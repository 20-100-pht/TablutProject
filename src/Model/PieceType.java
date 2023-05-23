package Model;

public enum PieceType {

    KING(0x0),
    DEFENDER(0x1),
    ATTACKER(0x2),
    MARKER_A(0x3),
    MARKER_D(0x4);

    private final int value;

    PieceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PieceType fromValue(int value) {
        switch(value) {
            case 0x0:
                return KING;
            case 0x1:
                return DEFENDER;
            case 0x2:
                return ATTACKER;
            case 0x3:
                return MARKER_A;
            case 0x4:
                return MARKER_D;
            default:
                throw new IllegalArgumentException("Invalid value for TwoBitEnum: " + value);
        }
    }
}