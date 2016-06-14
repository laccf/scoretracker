package br.com.itengine.score.repository;

import br.com.itengine.score.entity.Action;
import br.com.itengine.score.entity.League;
import br.com.itengine.score.entity.Match;
import br.com.itengine.score.entity.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by .
 */
public interface MatchRepository extends CrudRepository<Match, Integer> {
    List<Match> findAll();
    Match findById(Integer id);
    List<Match> findByLeague(League league);
    List<Match> findByTeamVisitorOrTeamHome(Team teamHome,Team teamVisitor);
    Match findByActionsContaining(Action player);
    List<Match> findByTeamHome(Team team);

}
