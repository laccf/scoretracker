package br.com.itengine.score.domain;

import br.com.itengine.score.entity.ActionType;
import br.com.itengine.score.entity.Match;
import br.com.itengine.score.entity.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thiag.
 */
public class TeamReport {
    TeamInfo team;
    Map<ActionType, Integer> actions;

    public TeamReport() {
        actions = new HashMap<ActionType, Integer>();
    }

    public TeamInfo getTeam() {
        return team;
    }

    public void setTeam(TeamInfo team) {
        this.team = team;
    }

    public Map<ActionType, Integer> getActions() {
        return actions;
    }

    public void setActions(Map<ActionType, Integer> actions) {
        this.actions = actions;
    }

}
