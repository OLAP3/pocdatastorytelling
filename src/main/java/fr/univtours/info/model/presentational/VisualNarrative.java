package fr.univtours.info.model.presentational;

import fr.univtours.info.model.Structural.*;
import java.util.Collection;

public interface VisualNarrative {

    public String toString();

    public void renders(Plot aPlot);
    public void contains(Dashboard aDashboard);

    public Collection<Dashboard> contains(); //getDashboard
    public Plot renders();

}
