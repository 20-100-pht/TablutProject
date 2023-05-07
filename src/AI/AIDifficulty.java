package AI;

public enum AIDifficulty {
    RANDOM(0x0),
    EASY(0x1),
    MID(0x2),
    HARD(0x3),
    HUMAN(0x4);

    private int value;
    AIDifficulty(int value) {this.value = value;}
    public int getValue() {
        return value;
    }

}
