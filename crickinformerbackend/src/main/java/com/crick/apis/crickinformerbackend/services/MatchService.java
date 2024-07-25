package com.crick.apis.crickinformerbackend.services;

import java.util.List;

import com.crick.apis.crickinformerbackend.entities.Match;

public interface MatchService {

    List<Match> getAllMatches();

    List<Match> getLiveMatches();

    List<List<String>> getPointTable();
}
