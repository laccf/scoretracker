package br.com.itengine.score.controller;

import br.com.itengine.score.entity.*;
import br.com.itengine.score.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by thiag.
 */
@RestController
@RequestMapping("/rest/leagues")
public class LeagueController {

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PlayerRepository playerRepository;

    @PreAuthorize("hasAnyRole('ROLE_ROOT','ROLE_LEAGUE')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<League>> findAll(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        if(Role.ROLE_ROOT.toString().equals(user.getRole())){
            return new ResponseEntity<List<League>>(leagueRepository.findAll(), HttpStatus.OK);
        }else{
            return new ResponseEntity<List<League>>(leagueRepository.findByLeagueAdmin(user), HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ROLE_ROOT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<League> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<League>(leagueRepository.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ROOT')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<League> update(@RequestBody League league) {

        return new ResponseEntity<League>(leagueRepository.save(league), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ROOT')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<League> create(@RequestBody League league) {
        return new ResponseEntity<League>(leagueRepository.save(league), HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ROLE_ROOT')")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<League> delete(League league) {
        if (leagueRepository.exists(league.getId())) {
            League leagueFind = leagueRepository.findOne(league.getId());
            List<Match> matches = matchRepository.findByLeague(leagueFind);
            List<Team> teams = teamRepository.findByLeague(leagueFind);

            for (Match match: matches
                    ) {
                matchRepository.delete(match);
            }
            for (Team team: teams
                    ) {
                for (Player player: team.getPlayers()
                        ) {
                    playerRepository.delete(player);

                }
                teamRepository.delete(team);
            }
            leagueRepository.delete(leagueFind);


            return new ResponseEntity<League>(leagueFind, HttpStatus.OK);
        } else {
            return new ResponseEntity<League>(new League(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ROOT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<League> delete(@PathVariable Integer id) {
        if (leagueRepository.exists(id)) {
            League league = leagueRepository.findOne(id);
            List<Match> matches = matchRepository.findByLeague(league);
            List<Team> teams = teamRepository.findByLeague(league);

            for (Match match: matches
                 ) {
                matchRepository.delete(match);
            }
            for (Team team: teams
                    ) {
                for (Player player: team.getPlayers()
                     ) {
                    playerRepository.delete(player);

                }
                teamRepository.delete(team);
            }
            leagueRepository.delete(id);


            return new ResponseEntity<League>(league, HttpStatus.OK);
        } else {
            return new ResponseEntity<League>(new League(), HttpStatus.NOT_FOUND);
        }
    }


}
