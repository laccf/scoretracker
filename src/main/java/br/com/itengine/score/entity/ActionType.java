package br.com.itengine.score.entity;

/**
 * Created by thiag.
 */
public enum ActionType {

    GOAL("GOAL"),
    FAUL("FAUL"),
    ASSIST("ASSIST"),
    SUSPENSION("SUSPENSION");

    private final String label;

    private ActionType(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }

}
