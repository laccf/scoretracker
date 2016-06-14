package br.com.itengine.score.repository;

import br.com.itengine.score.entity.League;
import br.com.itengine.score.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by .
 */
public interface LeagueRepository extends CrudRepository<League,Integer> {

    List<League> findAll();
    League findById(Integer id);
    List<League> findByLeagueAdmin(User user);
}
