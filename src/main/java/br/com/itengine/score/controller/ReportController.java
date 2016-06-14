package br.com.itengine.score.controller;

import br.com.itengine.score.domain.*;
import br.com.itengine.score.entity.*;
import br.com.itengine.score.repository.LeagueRepository;
import br.com.itengine.score.repository.MatchRepository;
import br.com.itengine.score.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by thiag.
 */
@RestController
@RequestMapping("/rest/report")
public class ReportController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    LeagueRepository leagueRepository;

    @RequestMapping(value="",method = RequestMethod.GET)
    public List<Report> findAll() {
        List<League> leagues = leagueRepository.findAll();
        List<Report> reports = new LinkedList<>();

        for (League league: leagues) {
            List<Action> actions = getActions(matchRepository.findByLeague(league));
            List<Team> teams = teamRepository.findByLeague(league);
            List<Match> matches = matchRepository.findByLeague(league);

            List<TeamReport> retorno = new LinkedList<>();
            List<MatchInfo> matchInfoList = new LinkedList<>();

            for (Team team: teams ) {
                TeamReport teamReport = getTeamReport(team,actions);
                retorno.add(teamReport);
            }

            for (Match match: matches) {
                if(!matchInfoList.contains(match)){
                    MatchInfo info = new MatchInfo(match);
                    matchInfoList.add(info);
                }
            }

            Report report = new Report();
            LeagueInfo leagueInfo = new LeagueInfo();
            leagueInfo.setName(league.getName());
            leagueInfo.setDate(league.getDate());
            report.setMatches(matchInfoList);
            report.setLeague(leagueInfo);
            report.setTeams(retorno);
            reports.add(report);
        }
        return reports;
    }

    private List<Action> getActions(List<Match> matchs){
        List<Action> actions = new LinkedList<Action>();
        for (Match match: matchs) {
            actions.addAll(match.getActions());
        }
        return actions;
    }

    private TeamReport getTeamReport(Team team, List<Action> actions){

        TeamInfo teamInfo = new TeamInfo(team);
        TeamReport teamReport = new TeamReport();
        teamReport.setTeam(teamInfo);

        Map<ActionType, Integer> map = new HashMap<>();
        map.put(ActionType.ASSIST,0);
        map.put(ActionType.FAUL,0);
        map.put(ActionType.GOAL,0);
        map.put(ActionType.SUSPENSION,0);

        for (Action action: actions){
            if(teamRepository.findByPlayersContaining(action.getPlayer()).getId() == team.getId()){
                map.put(action.getActionType(),1+map.get(action.getActionType()));
            }
        }
        teamReport.setActions(map);

        return teamReport;
    }

}
