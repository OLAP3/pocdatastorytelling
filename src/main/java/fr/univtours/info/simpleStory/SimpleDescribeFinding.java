package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Finding;


public class SimpleDescribeFinding implements Finding {
    private byte[] describeResult;
    private String stringResult;

    public SimpleDescribeFinding(byte[] aResultImg, String base64){
        this.describeResult=aResultImg;
        this.stringResult=base64;
    }

    @Override
    public String toString() {
        return stringResult;
        //return describeResult.toString();
    }
}
