package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Collector;
import fr.univtours.info.model.factual.Finding;

import java.util.Arrays;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleDescribeFinding that = (SimpleDescribeFinding) o;
        return Arrays.equals(describeResult, that.describeResult) &&
                Objects.equals(stringResult, that.stringResult) &&
                Objects.equals(theCollector, that.theCollector);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(stringResult, theCollector);
        result = 31 * result + Arrays.hashCode(describeResult);
        return result;
    }
}
