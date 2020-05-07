package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Insight;


public class SimpleDescribeInsight implements Insight {
    private byte[] describeResult;
    private String stringResult;

    public SimpleDescribeInsight(byte[] aResultImg, String base64){
        this.describeResult=aResultImg;
        this.stringResult=base64;
    }

    @Override
    public String toString() {
        return stringResult;
        //return describeResult.toString();
    }
}
