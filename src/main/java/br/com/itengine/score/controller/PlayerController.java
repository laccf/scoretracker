package br.com.itengine.score.controller;

import br.com.itengine.score.entity.League;
import br.com.itengine.score.entity.Player;
import br.com.itengine.score.repository.LeagueRepository;
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
@RequestMapping("/rest/players")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    //TODO Garantir que o get corresponde a um tipo do usuario logado (Principal)
    @PreAuthorize("hasAnyRole('ROLE_TEAM','ROLE_DELEGATE')")
    @RequestMapping(value="",method = RequestMethod.GET)
    public ResponseEntity<List<Player>> findAll() {
        return new ResponseEntity<List<Player>>(playerRepository.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DELEGATE')")
    @RequestMapping(value="/team",method = RequestMethod.GET)
    public ResponseEntity<List<Player>> findByRole(@RequestParam Integer id) {
        return new ResponseEntity<List<Player>>(playerRepository.findByTeamId(id), HttpStatus.OK);
    }

    //TODO Garantir que o get corresponde a um tipo do usuario logado (Principal)
    @PreAuthorize("hasRole('ROLE_TEAM')")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ResponseEntity<Player> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<Player>(playerRepository.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TEAM')")
    @RequestMapping(value="",method = RequestMethod.PUT)
    public ResponseEntity<Player> update(@RequestBody Player player) {
        return new ResponseEntity<Player>(playerRepository.save(player), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TEAM')")
    @RequestMapping(value="",method = RequestMethod.POST)
    public ResponseEntity<Player> create(@RequestBody Player player) {
        if(null == player.getId()){
            return new ResponseEntity<Player>(playerRepository.save(player), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<Player>(player, HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasRole('ROLE_TEAM')")
    @RequestMapping(value="",method = RequestMethod.DELETE)
    public ResponseEntity<Player> delete(Player player) {
        if(playerRepository.exists(player.getId())){
            playerRepository.delete(player);
            return new ResponseEntity<Player>(player, HttpStatus.OK);
        }else{
            return new ResponseEntity<Player>(player, HttpStatus.NOT_FOUND);
        }
    }

    //TODO Garantir que o get corresponde a um tipo do usuario logado (Principal)
    @PreAuthorize("hasRole('ROLE_TEAM')")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Player> delete(@PathVariable Integer id) {
        if(playerRepository.exists(id)){
            Player player = playerRepository.findOne(id);
            playerRepository.delete(id);
            return new ResponseEntity<Player>(player, HttpStatus.OK);
        }else{
            return new ResponseEntity<Player>(new Player(), HttpStatus.NOT_FOUND);
        }
    }

}
