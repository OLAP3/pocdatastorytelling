package fr.univtours.info.model.presentational;

import fr.univtours.info.model.structural.*;
import java.util.Collection;

public interface VisualStory {

    public void print();

    public void renders(Story aStory);
    public void add(Dashboard aDashboard);

    public Collection<Dashboard> contains(); //getDashboard
    public Story renders();

}
