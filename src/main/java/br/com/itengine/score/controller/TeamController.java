package br.com.itengine.score.controller;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import br.com.itengine.score.entity.*;
import br.com.itengine.score.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by thiag.
 */

@RestController
@RequestMapping("/rest/teams")
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    UserRepository userRepository;

    //TODO Garantir que o get corresponde a um tipo do usuario logado (Principal)
    @PreAuthorize("hasAnyRole('ROLE_LEAGUE',ROLE_TEAM)")
    @RequestMapping(value="",method = RequestMethod.GET)
    public ResponseEntity<List<Team>> findAll(Principal principal) {
        User user  = userRepository.findByUsername(principal.getName());
        List<League> leagues = leagueRepository.findByLeagueAdmin(user);
        List<Team> teams = new LinkedList<>();

        for (League league: leagues) {
            teams.addAll(teamRepository.findByLeague(league));
        }
        return new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
    }

    //TODO Garantir que o get corresponde a um tipo do usuario logado (Principal)
    @PreAuthorize("hasAnyRole('ROLE_LEAGUE',ROLE_TEAM)")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ResponseEntity<Team> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<Team>(teamRepository.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_LEAGUE')")
    @RequestMapping(value="",method = RequestMethod.PUT)
    public ResponseEntity<Team> update(@RequestBody Team team) {
        return new ResponseEntity<Team>(teamRepository.save(team), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_LEAGUE')")
    @RequestMapping(value="",method = RequestMethod.POST)
    public ResponseEntity<Team> create(@RequestBody Team team) {
        return new ResponseEntity<Team>(teamRepository.save(team), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_LEAGUE')")
    @RequestMapping(value="",method = RequestMethod.DELETE)
    public ResponseEntity<Team> delete(Team team) {
        if(teamRepository.exists(team.getId())){
            teamRepository.delete(team);
            return new ResponseEntity<Team>(team, HttpStatus.OK);
        }else{
            return new ResponseEntity<Team>(team, HttpStatus.NOT_FOUND);
        }
    }

    //TODO Garantir que a deleção corresponde a um tipe do usuario logado (Principal)
    @PreAuthorize("hasRole('ROLE_LEAGUE')")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Team> delete(@PathVariable Integer id) {
        if(teamRepository.exists(id)){
            Team team = teamRepository.findOne(id);
            List<Match> matches = matchRepository.findByTeamVisitorOrTeamHome(team,team);
            for (Match match: matches ) {
                matchRepository.delete(match);
            }
            for (Player player: team.getPlayers()
                    ) {
                playerRepository.delete(player);

            }
            teamRepository.delete(team);
            return new ResponseEntity<Team>(team, HttpStatus.OK);
        }else{
            return new ResponseEntity<Team>(new Team(), HttpStatus.NOT_FOUND);
        }
    }
}
