package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Finding;


public class SimpleDescribeFinding implements Finding {
    private byte[] describeResult;
    private String stringResult;
    private Collector theCollector;

    @Override
    public Collector fetches() {
        return theCollector;
    }

    @Override
    public void fetches(Collector theCollector) {
        this.theCollector=theCollector;
    }

    public SimpleDescribeFinding(byte[] aResultImg, String base64){
        this.describeResult=aResultImg;
        this.stringResult=base64;
    }

    @Override
    public String toString() {
        return "Finding: " + stringResult;
        //return describeResult.toString();
    }
}
