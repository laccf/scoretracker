package br.com.itengine.score;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.itengine.score.entity.Action;
import br.com.itengine.score.entity.ActionType;
import br.com.itengine.score.entity.League;
import br.com.itengine.score.entity.Match;
import br.com.itengine.score.entity.Player;
import br.com.itengine.score.entity.Role;
import br.com.itengine.score.entity.Team;
import br.com.itengine.score.entity.User;
import br.com.itengine.score.repository.ActionRepository;
import br.com.itengine.score.repository.LeagueRepository;
import br.com.itengine.score.repository.MatchRepository;
import br.com.itengine.score.repository.PlayerRepository;
import br.com.itengine.score.repository.TeamRepository;
import br.com.itengine.score.repository.UserRepository;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    public static String ROOT = "upload-dir";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner loadData(MatchRepository matchRepository,
                                      TeamRepository teamRepository,
                                      PlayerRepository playerRepository,
                                      LeagueRepository leagueRepository,
                                      ActionRepository actionRepository,
                                      UserRepository userRepository
    ) {
        return (args) -> {

            new File(ROOT).mkdir();

            /* Create Users (ROLE_ROOT) */
            User userRoot = new User();
            userRoot.setRole(Role.ROLE_ROOT.toString());
            userRoot.setUsername("root1");
            userRoot.setPassword("pass");
            userRoot.setName("Root 1");
            userRoot.setEmail("email@root1.com");
            userRoot.setPhone("33333331");

            User userRoot2 = new User();
            userRoot2.setRole(Role.ROLE_ROOT.toString());
            userRoot2.setUsername("root2");
            userRoot2.setPassword("pass");
            userRoot2.setName("Root 2");
            userRoot2.setEmail("email@root2.com");
            userRoot2.setPhone("33333332");

            /* Create Users (ROLE_LEAGUE) */
            User userLeague = new User();
            userLeague.setRole(Role.ROLE_LEAGUE.toString());
            userLeague.setUsername("league1");
            userLeague.setPassword("pass");
            userLeague.setName("League 1");
            userLeague.setEmail("email@league1.com");
            userLeague.setPhone("33333331");

            User userLeague2 = new User();
            userLeague2.setRole(Role.ROLE_LEAGUE.toString());
            userLeague2.setUsername("league2");
            userLeague2.setPassword("pass");
            userLeague2.setName("League 2");
            userLeague2.setEmail("emailTwo@league2.com");
            userLeague2.setPhone("33333332");

            /* Create Users (ROLE_TEAM) */
            User userTeam = new User();
            userTeam.setRole(Role.ROLE_TEAM.toString());
            userTeam.setUsername("team1");
            userTeam.setPassword("pass");
            userTeam.setName("Team 1");
            userTeam.setEmail("email@team1.com");
            userTeam.setPhone("33333331");

            User userTeam2 = new User();
            userTeam2.setRole(Role.ROLE_TEAM.toString());
            userTeam2.setUsername("team2");
            userTeam2.setPassword("pass");
            userTeam2.setName("Team 2");
            userTeam2.setEmail("email@team2.com");
            userTeam2.setPhone("33333332");

            /* Create Users (ROLE_DELEGATE) */
            User userDelegate = new User();
            userDelegate.setRole(Role.ROLE_DELEGATE.toString());
            userDelegate.setUsername("delegate1");
            userDelegate.setPassword("pass");
            userDelegate.setName("Delegate 1");
            userDelegate.setEmail("email@delegate1.com");
            userDelegate.setPhone("33333331");

            User userDelegate2 = new User();
            userDelegate2.setRole(Role.ROLE_DELEGATE.toString());
            userDelegate2.setUsername("delegate2");
            userDelegate2.setPassword("pass");
            userDelegate2.setName("Delegate 2");
            userDelegate2.setEmail("email@delegate2.com");
            userDelegate2.setPhone("3333332");

            /* Persist Users */
            userRoot = userRepository.save(userRoot);
            userRoot2 = userRepository.save(userRoot2);
            userLeague = userRepository.save(userLeague);
            userLeague2 = userRepository.save(userLeague2);
            userTeam = userRepository.save(userTeam);
            userTeam2 = userRepository.save(userTeam2);
            userDelegate = userRepository.save(userDelegate);
            userDelegate2 = userRepository.save(userDelegate2);

            /* Create Leagues */
            League leaguePernambucano = new League();
            leaguePernambucano.setName("Pernambucano's League");
            leaguePernambucano.setDate(2004);
            leaguePernambucano.setLeagueAdmin(userLeague);
            leaguePernambucano = leagueRepository.save(leaguePernambucano);

            League leagueParaibano = new League();
            leagueParaibano.setName("Paraibano's League");
            leagueParaibano.setDate(2016);
            leagueParaibano.setLeagueAdmin(userLeague2);
            leagueParaibano = leagueRepository.save(leagueParaibano);

            League leagueBaiano = new League();
            leagueBaiano.setName("Baiano's League");
            leagueBaiano.setDate(2017);
            leagueBaiano.setLeagueAdmin(userLeague2);
            leagueBaiano = leagueRepository.save(leagueBaiano);

            /* Create Teamss (Pernambucano's) */
            Team teamSport = new Team();
            teamSport.setName("Sport Club");
            teamSport.setLeague(leaguePernambucano);
            teamSport.setTeamAdmin(userTeam);
            teamSport.setAddress("Ilha do Retiro");

            Team teamSanta = new Team();
            teamSanta.setName("Santa Cruz");
            teamSanta.setLeague(leaguePernambucano);
            teamSanta.setTeamAdmin(userTeam);
            teamSanta.setAddress("Arruda");

            Team teamNautico = new Team();
            teamNautico.setName("Nautico");
            teamNautico.setLeague(leaguePernambucano);
            teamNautico.setTeamAdmin(userTeam);
            teamNautico.setAddress("Aflitos");

            /* Create Teamss (Paraibano's) */
            Team teamTreze = new Team();
            teamTreze.setName("Treze");
            teamTreze.setLeague(leagueParaibano);
            teamTreze.setTeamAdmin(userTeam2);
            teamTreze.setAddress("PV");

            Team teamCampinense = new Team();
            teamCampinense.setName("Campinense");
            teamCampinense.setLeague(leagueParaibano);
            teamCampinense.setTeamAdmin(userTeam2);
            teamCampinense.setAddress("Toca da Raposa");

            Team teamNacional = new Team();
            teamNacional.setName("Nacional");
            teamNacional.setLeague(leagueParaibano);
            teamNacional.setTeamAdmin(userTeam2);
            teamNacional.setAddress("Patos");

            Team teamBotafogo = new Team();
            teamBotafogo.setName("Botafogo");
            teamBotafogo.setLeague(leagueParaibano);
            teamBotafogo.setTeamAdmin(userTeam2);
            teamBotafogo.setAddress("João Pessoa");

            /* Create Teamss (Baiano's) */
            /*
            * Bahia
            * Vitoria
            * */

            /* Persist Teams */
            teamSport = teamRepository.save(teamSport);
            teamSanta = teamRepository.save(teamSanta);
            teamNautico = teamRepository.save(teamNautico);
            teamTreze = teamRepository.save(teamTreze);
            teamCampinense = teamRepository.save(teamCampinense);
            teamNacional = teamRepository.save(teamNacional);
            teamBotafogo = teamRepository.save(teamBotafogo);

            String[] sportNames = {"Durval Silva", "Sandro Goiano", "Alexandro Beti", "Tulio Vinicius", "Thiago Almeida"};
            String[] santaNames = {"Luiz Antonio", "Anderson Pablo", "Romeryto Lira", "Gabi Alves", "Pedro Hyvo", "Erick Costa"};
            String[] nauticoNames = {"Joao Arthur", "Italo Almeida", "Jorge Silva", "Dalton Serey", "Adalberto Cajueiro"};

            List<Player> sportPlayers = new LinkedList<>();
            List<Player> santaPlayers = new LinkedList<>();
            List<Player> nauticoPlayers = new LinkedList<>();

            for (int i = 0; i < sportNames.length; i++) {
                Player sportPlayer = new Player();
                Player santaPlayer = new Player();
                Player nauticoPlayer = new Player();

                sportPlayer.setName(sportNames[i].split(" ")[0]);
                sportPlayer.setLastName(sportNames[i].split(" ")[1]);
                sportPlayer.setTeam(teamSport);
                sportPlayer.setWeight(80);
                sportPlayer.setHeight(170);


                santaPlayer.setName(santaNames[i].split(" ")[0]);
                santaPlayer.setLastName(santaNames[i].split(" ")[1]);
                santaPlayer.setTeam(teamSanta);
                santaPlayer.setWeight(80);
                santaPlayer.setHeight(170);

                nauticoPlayer.setName(nauticoNames[i].split(" ")[0]);
                nauticoPlayer.setLastName(nauticoNames[i].split(" ")[1]);
                nauticoPlayer.setTeam(teamNautico);
                nauticoPlayer.setWeight(80);
                nauticoPlayer.setHeight(170);

                sportPlayer = playerRepository.save(sportPlayer);
                santaPlayer = playerRepository.save(santaPlayer);
                nauticoPlayer = playerRepository.save(nauticoPlayer);
                sportPlayers.add(sportPlayer);
                santaPlayers.add(santaPlayer);
                nauticoPlayers.add(nauticoPlayer);
            }
            teamSport.setPlayers(sportPlayers);
            teamSanta.setPlayers(santaPlayers);
            teamNautico.setPlayers(nauticoPlayers);
            teamRepository.save(teamSport);

            teamRepository.save(teamSanta);
            teamRepository.save(teamNautico);
            //TODO:implementar inicializacao

            Match matchNauticoSport = new Match();
            Match matchNauticoSanta = new Match();
            Match matchSportSanta = new Match();
            Match matchSportNautico = new Match();


            String sourceDate = "2012-02-29";
            String sourceDateTwo = "2012-03-20";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(sourceDate);
            Date dateTwo = format.parse(sourceDateTwo);


            matchNauticoSport.setTeamHome(teamNautico);
            matchNauticoSport.setTeamVisitor(teamSport);
            matchNauticoSport.setDelegate(userDelegate);
            matchNauticoSport.setLeague(leaguePernambucano);
            matchNauticoSport.setDateOfMatch(date);

            matchNauticoSanta.setTeamHome(teamNautico);
            matchNauticoSanta.setTeamVisitor(teamSanta);
            matchNauticoSanta.setDelegate(userDelegate);
            matchNauticoSanta.setLeague(leaguePernambucano);
            matchNauticoSanta.setDateOfMatch(date);

            matchSportSanta.setTeamHome(teamSport);
            matchSportSanta.setTeamVisitor(teamSanta);
            matchSportSanta.setDelegate(userDelegate2);
            matchSportSanta.setLeague(leaguePernambucano);
            matchSportSanta.setDateOfMatch(dateTwo);

            matchSportNautico.setTeamHome(teamSport);
            matchSportNautico.setTeamVisitor(teamNautico);
            matchSportNautico.setDelegate(userDelegate2);
            matchSportNautico.setLeague(leaguePernambucano);
            matchSportNautico.setDateOfMatch(dateTwo);
            matchSportNautico.setIsPlayed(true);


            matchRepository.save(matchNauticoSport);
            matchRepository.save(matchNauticoSanta);
            matchRepository.save(matchSportSanta);
            matchRepository.save(matchSportNautico);


            //Actions
            Action action1 = new Action();
            Action action2 = new Action();
            Action action3 = new Action();
            Action action4 = new Action();
            Action action5 = new Action();

            action1.setPlayer(teamNautico.getPlayers().get(0));
            action1.setMinute(10);
            action1.setActionType(ActionType.GOAL);

            action2.setPlayer(teamNautico.getPlayers().get(2));
            action2.setMinute(15);
            action2.setActionType(ActionType.GOAL);

            action3.setPlayer(teamNautico.getPlayers().get(1));
            action3.setMinute(20);
            action3.setActionType(ActionType.FAUL);

            action4.setPlayer(teamNautico.getPlayers().get(2));
            action4.setMinute(5);
            action4.setActionType(ActionType.GOAL);

            action5.setPlayer(teamNautico.getPlayers().get(0));
            action5.setMinute(44);
            action5.setActionType(ActionType.GOAL);

            action1 = actionRepository.save(action1);
            action2 = actionRepository.save(action2);
            action3 = actionRepository.save(action3);
            action4 = actionRepository.save(action4);
            action5 = actionRepository.save(action5);

            matchNauticoSport.setActions(new LinkedList<Action>());

            matchNauticoSport.getActions().add(action1);
            matchNauticoSport.getActions().add(action2);
            matchNauticoSport.getActions().add(action3);
            matchNauticoSport.getActions().add(action4);
            matchNauticoSport.getActions().add(action5);
            matchRepository.save(matchNauticoSport);


        };
    }

}
