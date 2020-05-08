package fr.univtours.info.model.presentational;


import fr.univtours.info.model.Structural.*;

import java.util.Collection;


public interface Dashboard {
    public void renders(Act anAct);
    public void contains(DashboardComponent aDashboardComponent);//add

    public Collection<DashboardComponent> contains(); //getComponent
    public Act renders();


}
