package br.com.itengine.score.domain;

import br.com.itengine.score.entity.Team;

/**
 * Created by thiag.
 */
public class TeamInfo {
    private Integer id;
    private String name;

    public TeamInfo (Team team){
        this.id = team.getId();
        this.name = team.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
