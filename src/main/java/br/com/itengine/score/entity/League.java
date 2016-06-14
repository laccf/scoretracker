package br.com.itengine.score.entity;//
// This file was generated by the JPA Modeler
//

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class League implements Serializable {

    @Id
    @GeneratedValue(generator = "id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id")
    private Integer id;

    private String name;

    private String logo;

    private Integer date;

    @ManyToOne(targetEntity = User.class)
    private User leagueAdmin;

    @OneToMany(targetEntity = Team.class, mappedBy = "league")
    @JsonManagedReference
    private List<Team> teams;

    public League() {

    }

    public Integer getDate() {
        return this.date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public User getLeagueAdmin() {
        return this.leagueAdmin;
    }

    public void setLeagueAdmin(User leagueAdmin) {
        this.leagueAdmin = leagueAdmin;
    }

    public List<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
