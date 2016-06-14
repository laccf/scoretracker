package br.com.itengine.score.repository;

import br.com.itengine.score.entity.Player;
import br.com.itengine.score.entity.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Thiago Almeida.
 */
public interface PlayerRepository  extends CrudRepository<Player, Integer> {

    List<Player> findByName(String name);
    List<Player> findAll();
    List<Player> findByNameAndLastNameIgnoreCase(String name, String lastName);
    Player findById(Integer id);
    List<Player> findByTeamId(Integer id);

}
