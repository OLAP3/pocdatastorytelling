package fr.univtours.info.pocdatastory;

import java.util.ArrayList;

public class NarrativeRecall {

    private int code;
    private String goal;
    private int nbActs;
    private int[] nbEpisodes;
    private String[] listCharacters;

    public String[] getListCharacters() {
        return listCharacters;
    }

    public void setListCharacters(String[] listCharacters) {
        this.listCharacters = listCharacters;
    }

    public String[] getListMeasures() {
        return listMeasures;
    }

    public void setListMeasures(String[] listMeasures) {
        this.listMeasures = listMeasures;
    }

    private String[] listMeasures;

    public NarrativeRecall(int code, String goal, int nbActs, int[] nbEpisodes, String[] listCharacters, String[] listMeasures) {
        this.code = code;
        this.goal = goal;
        this.nbActs = nbActs;
        this.nbEpisodes = nbEpisodes;
        this.listCharacters = listCharacters;
        this.listMeasures = listMeasures;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getNbActs() {
        return nbActs;
    }

    public void setNbActs(int nbActs) {
        this.nbActs = nbActs;
    }

    public int[] getNbEpisodes() {
        return nbEpisodes;
    }

    public void setNbEpisodes(int[] nbEpisodes) {
        this.nbEpisodes = nbEpisodes;
    }




}
