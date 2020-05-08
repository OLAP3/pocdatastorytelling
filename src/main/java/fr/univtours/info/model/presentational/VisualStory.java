package fr.univtours.info.model.presentational;

import fr.univtours.info.model.Structural.*;
import java.util.Collection;

public interface VisualStory {

    public String toString();

    public void renders(Story aStory);
    public void contains(Dashboard aDashboard);

    public Collection<Dashboard> contains(); //getDashboard
    public Story renders();

}
