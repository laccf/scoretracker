package br.com.itengine.score.domain;

import br.com.itengine.score.entity.Action;
import br.com.itengine.score.entity.ActionType;
import br.com.itengine.score.entity.Match;

import java.util.Date;

/**
 * Created by luiz.
 */
public class MatchInfo {

    private Integer id;
    private Date dateOfMatch;
    private boolean isPlayed;

    private String homeTeam;
    private Integer homeTeamId;
    private int homeTeamScore;

    private String visitorTeam;
    private Integer visitorTeamId;
    private int visitorTeamScore;

    public MatchInfo(Match match){
        this.id = match.getId();
        this.dateOfMatch = match.getDateOfMatch();
        this.isPlayed = match.isPlayed();

        this.homeTeam = match.getTeamHome().getName();
        this.homeTeamId = match.getTeamHome().getId();

        this.visitorTeamId = match.getTeamVisitor().getId();
        this.visitorTeam = match.getTeamVisitor().getName();

        for (Action act: match.getActions()) {
            if (ActionType.GOAL.equals(act.getActionType())){
                if(act.getPlayer().getTeam().getName().equals(match.getTeamHome().getName())){
                    this.homeTeamScore = this.homeTeamScore++;
                }else if(act.getPlayer().getTeam().getName().equals(match.getTeamVisitor().getName())){
                    this.visitorTeamScore = this.visitorTeamScore++;
                }
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchInfo matchInfo = (MatchInfo) o;

        return id.equals(matchInfo.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateOfMatch() {
        return dateOfMatch;
    }

    public void setDateOfMatch(Date dateOfMatch) {
        this.dateOfMatch = dateOfMatch;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public String getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(String visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public Integer getVisitorTeamId() {
        return visitorTeamId;
    }

    public void setVisitorTeamId(Integer visitorTeamId) {
        this.visitorTeamId = visitorTeamId;
    }

    public int getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(int visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }
}
