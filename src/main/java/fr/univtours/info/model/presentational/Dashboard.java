package fr.univtours.info.model.presentational;


import fr.univtours.info.model.structural.*;

import java.util.Collection;


public interface Dashboard {
    public void renders(Act anAct);
    public void add(DashboardComponent aDashboardComponent);

    public Collection<DashboardComponent> contains(); //getComponent
    public Act renders();

}
