package fr.univtours.info.model.intentional;

import fr.univtours.info.model.discursal.Story;
import fr.univtours.info.model.factual.*;

import java.util.Collection;


public interface Goal {
    public void addText(String aText);
    public String toString(); // replace by getText()?

    public Story has();
    public void has(Story theStory);

    public void solves(Exploration anExploration);
    public void poses(AnalyticalQuestion anAnalyticalQuestion);

    Collection<Exploration> solves();
    Collection<AnalyticalQuestion> poses();

    public void bringOut(Protagonist aProtagonist);
    public void bringOut(Message aMessage);

}
