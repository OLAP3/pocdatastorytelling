package fr.univtours.info.model.intentional;

import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.model.factual.*;

import java.util.Collection;


public interface Goal {
    public void addText(String aText);
    public String toString(); // replace by getText()?

    public Plot has();
    public void has(Plot thePlot);

    public void solves(Exploration anExploration);
    public void poses(AnalyticalQuestion anAnalyticalQuestion);

    Collection<Exploration> solves();
    Collection<AnalyticalQuestion> poses();

    public void bringOut(Character aCharacter);

}
