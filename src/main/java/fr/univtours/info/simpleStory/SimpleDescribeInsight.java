package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Insight;


public class SimpleDescribeInsight implements Insight {
    private byte[] describeResult;

    public SimpleDescribeInsight(byte[] aResultImg){
        this.describeResult=aResultImg;

    }

    @Override
    public String toString() {
        return describeResult.toString();
    }
}
