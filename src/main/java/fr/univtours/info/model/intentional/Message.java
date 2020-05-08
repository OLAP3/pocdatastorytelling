package fr.univtours.info.model.intentional;

import fr.univtours.info.model.factual.*;

import java.util.Collection;

public interface Message {
    public void addText(String aText);
    public String toString();

    public void poses(AnalyticalQuestion anAnalyticalQuestion);
    public void generates(AnalyticalQuestion anAnalyticalQuestion);
    public AnalyticalQuestion generates();

    public void produces(Finding anFinding);
    public Collection<Finding> produces();

    public void bringsOut(Character aCharacter) ;
    public Collection<Character>  bringsOut();

    public void includes(Measure aMeasure) ;
    public Collection<Measure>  includes();
}
