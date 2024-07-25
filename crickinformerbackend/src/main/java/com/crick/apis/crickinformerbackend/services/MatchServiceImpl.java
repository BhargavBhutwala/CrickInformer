package com.crick.apis.crickinformerbackend.services;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crick.apis.crickinformerbackend.entities.Match;
import com.crick.apis.crickinformerbackend.repositories.MatchRepo;

@Service
public class MatchServiceImpl implements MatchService{

    @Autowired
    private MatchRepo matchRepo;

    //match history all matches in the database
    @Override
    public List<Match> getAllMatches() {
        return this.matchRepo.findAll();
    }

    @Override
    public List<Match> getLiveMatches() {
        List<Match> matches = new ArrayList<>();
        try {
            String url = "https://www.cricbuzz.com/cricket-match/live-scores";
            Document document = Jsoup.connect(url).get();
            // Extract data from the HTML document
            Elements liveScoreElements = document.select("div.cb-mtch-lst.cb-tms-itm");
            for(Element liveMatch: liveScoreElements){

                String teamHeading = liveMatch.select("h3.cb-lv-scr-mtch-hdr").select("a").text();
                String matchNumberVenue = liveMatch.select("span").text();
                Elements matchBatTeamInfo = liveMatch.select("div.cb-hmscg-bat-txt");
                String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String battingTeamScore = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                Elements matchBowlTeamInfo = liveMatch.select("div.cb-hmscg-bwl-txt");
                String bowlingTeam = matchBowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String bowlingTeamScore = matchBowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                String liveText = liveMatch.select("div.cb-text-live").text();
                String matchLink = liveMatch.select("a.cb-lv-scrs-well.cb-lv-scrs-well-live").attr("href").toString();
                String textComplete = liveMatch.select("div.cb-text-complete").text();

                Match match = new Match();
                match.setTeamHeading(teamHeading);
                match.setMatchNumberVenue(matchNumberVenue);
                match.setBattingTeam(battingTeam);
                match.setBattingTeamScore(battingTeamScore);
                match.setBowlingTeam(bowlingTeam);
                match.setBowlingTeamScore(bowlingTeamScore);
                match.setLiveText(liveText);
                match.setMatchLink(matchLink);
                match.setTextComplete(textComplete);
                match.setMatchStatus();

                matches.add(match);

                updateMatchInDB(match);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }

    private void updateMatchInDB(Match match) {
        
        Match match2 = this.matchRepo.findByTeamHeading(match.getTeamHeading()).orElse(null);

        if (match2 == null) {
            this.matchRepo.save(match);
        } else {
            match.setMatchId(match2.getMatchId());
            this.matchRepo.save(match);
        }
    }

    @Override
    public List<List<String>> getPointTable() {
        List<List<String>> pointsTable = new ArrayList<>();
        try {
            String url = "https://www.cricbuzz.com/cricket-series/6732/icc-cricket-world-cup-2023/points-table";
            Document document = Jsoup.connect(url).get();

            Elements table=document.select("table.cb-srs-pnts");
            Elements tableHead=table.select("thead>tr>*");
            List<String> headers=new ArrayList<>();

            for(Element e:tableHead){
                headers.add(e.text());
            }   

            pointsTable.add(headers);

            Elements tableBody=table.select("tbody>*");
            tableBody.forEach(tr -> {
                List<String> points = new ArrayList<>();
                if (tr.hasAttr("class")) {
                    Elements tds=tr.select("td");
                    String team=tds.get(0).select("div.cb-col-84").text();
                    points.add(team);

                    tds.forEach(td -> {
                        if(!td.hasClass("cb-srs-pnts-name")){
                            points.add(td.text());
                        }
                    });

                    pointsTable.add(points);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pointsTable;
    }

}
