package fr.univtours.info.simpleStory;

import fr.univtours.info.model.factual.Exploration;
import fr.univtours.info.model.intentional.AnalyticalQuestion;
import fr.univtours.info.model.intentional.Goal;
import fr.univtours.info.model.intentional.Message;
import fr.univtours.info.model.intentional.Protagonist;

import java.util.Collection;

public class SimpleGoal implements Goal {
    @Override
    public Collection<Exploration> solves() {
        return null;
    }

    @Override
    public Collection<AnalyticalQuestion> poses() {
        return null;
    }

    @Override
    public void bringOut(Protagonist aProtagonist) {

    }

    @Override
    public void bringOut(Message aMessage) {

    }
}
