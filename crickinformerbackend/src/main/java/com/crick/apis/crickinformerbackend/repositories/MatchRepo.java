package com.crick.apis.crickinformerbackend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crick.apis.crickinformerbackend.entities.Match;

public interface MatchRepo extends JpaRepository<Match,Integer>{

    Optional<Match> findByTeamHeading(String teamHeading);

}
