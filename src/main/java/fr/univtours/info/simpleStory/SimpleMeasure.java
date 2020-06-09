package fr.univtours.info.simpleStory;

import fr.univtours.info.model.intentional.Goal;
import fr.univtours.info.model.intentional.Measure;
import fr.univtours.info.model.intentional.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SimpleMeasure implements Measure {
    String theText;
    String name;
    float value;

    public SimpleMeasure(){

    }

    @Override
    public void addText(String aText) {
        this.theText=aText.substring(1,aText.length()-1).replace("\\n","\n");

       // this.theText=aText;
    }

    @Override
    public String getText() {
        return theText;
    }

    @Override
    public String toString() {
        return "Measure: "  + theText;
        //return "Measure: " + name + " with value " + value + "\n" + theText;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public void setValue(float value) {
        this.value=value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleMeasure that = (SimpleMeasure) o;
        return Float.compare(that.value, value) == 0 &&
                Objects.equals(theText, that.theText) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theText, name, value);
    }
}
