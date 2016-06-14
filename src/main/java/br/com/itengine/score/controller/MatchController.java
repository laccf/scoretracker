package br.com.itengine.score.controller;

import br.com.itengine.score.entity.Match;
import br.com.itengine.score.entity.Player;
import br.com.itengine.score.repository.MatchRepository;
import br.com.itengine.score.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by thiag.
 */
@RestController
@RequestMapping("/rest/matches")
public class MatchController {


    @Autowired
    MatchRepository matchRepository;

    //TODO Garantir que o get corresponde a um tipo do usuario logado (Principal)
    @PreAuthorize("hasAnyRole('ROLE_LEAGUE','ROLE_DELEGATE')")
    @RequestMapping(value="",method = RequestMethod.GET)
    public ResponseEntity<List<Match>> findAll() {
        return new ResponseEntity<List<Match>>(matchRepository.findAll(), HttpStatus.OK);
    }

    //TODO Garantir que o get corresponde a um tipo do usuario logado (Principal)
    @PreAuthorize("hasAnyRole('ROLE_LEAGUE','ROLE_DELEGATE')")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ResponseEntity<Match> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<Match>(matchRepository.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_LEAGUE','ROLE_DELEGATE')")
    @RequestMapping(value="",method = RequestMethod.PUT)
    public ResponseEntity<Match> update(@RequestBody Match match) {
        return new ResponseEntity<Match>(matchRepository.save(match), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_LEAGUE')")
    @RequestMapping(value="",method = RequestMethod.POST)
    public ResponseEntity<Match> create(@RequestBody Match match) {
        return new ResponseEntity<Match>(matchRepository.save(match), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_LEAGUE')")
    @RequestMapping(value="",method = RequestMethod.DELETE)
    public ResponseEntity<Match> delete(Match match) {
        if(matchRepository.exists(match.getId())){
            matchRepository.delete(match);
            return new ResponseEntity<Match>(match, HttpStatus.OK);
        }else{
            return new ResponseEntity<Match>(match, HttpStatus.NOT_FOUND);
        }
    }

    //TODO Garantir que o get corresponde a um tipo do usuario logado (Principal)
    @PreAuthorize("hasRole('ROLE_LEAGUE')")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Match> delete(@PathVariable Integer id) {
        if(matchRepository.exists(id)){
            Match match = matchRepository.findOne(id);
            matchRepository.delete(id);
            return new ResponseEntity<Match>(match, HttpStatus.OK);
        }else{
            return new ResponseEntity<Match>(new Match(), HttpStatus.NOT_FOUND);
        }
    }
}
