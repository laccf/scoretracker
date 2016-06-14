package br.com.itengine.score.entity;

/**
 * Created by thiag.
 */
public enum Role {
    ROLE_ROOT("ROLE_ROOT"),
    ROLE_LEAGUE("ROLE_LEAGUE"),
    ROLE_TEAM("ROLE_TEAM"),
    ROLE_DELEGATE("ROLE_DELEGATE");

    private final String label;

    private Role(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
