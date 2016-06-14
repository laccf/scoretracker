package br.com.itengine.score.repository;


import br.com.itengine.score.entity.League;
import br.com.itengine.score.entity.Player;
import br.com.itengine.score.entity.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by .
 */
public interface TeamRepository extends CrudRepository<Team, Integer> {

    Team findByName(String name);
    List<Team> findByNameIgnoreCase(String name);
    List<Team> findAll();
    Team findById(Integer id);
    Team findByPlayersContaining(Player player);
    List<Team> findByLeague(League league);
    List<Team> findByTeamAdmin(Team Admin);

}
