package fr.univtours.info.model.intentional;

import fr.univtours.info.model.factual.*;
import fr.univtours.info.model.structural.*;

import java.util.Collection;


public interface Goal {

    Collection<Exploration> solves();
    Collection<AnalyticalQuestion> poses();
}
