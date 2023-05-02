package Model;

public enum PieceType {
    KING(0x0),
    DEFENDER(0x1),
    ATTACKER(0x2);

    private final int value;

    private PieceType(int value) {
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
            default:
                throw new IllegalArgumentException("Invalid value for TwoBitEnum: " + value);
        }
    }
}