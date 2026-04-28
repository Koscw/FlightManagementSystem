package org.example.FMSPRO;

public enum Priority {
    EMERGENCY("Emergency", 1),
    URGENT("Urgent", 2),
    ROUTINE("Routine", 3);

    private final String label;
    private final int score;

    Priority(String label, int score) {
        this.label = label;
        this.score = score;
    }

    public String getLabel() {
        return label;
    }

    public int getScore() {
        return score;
    }
}
