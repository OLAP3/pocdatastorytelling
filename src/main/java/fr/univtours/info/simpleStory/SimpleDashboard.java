package fr.univtours.info.simpleStory;

import fr.univtours.info.model.discursal.Act;
import fr.univtours.info.model.presentational.Dashboard;
import fr.univtours.info.model.presentational.DashboardComponent;

import java.util.Collection;

public class SimpleDashboard implements Dashboard {
    @Override
    public void renders(Act anAct) {

    }

    @Override
    public void contains(DashboardComponent aDashboardComponent) {

    }

    @Override
    public Collection<DashboardComponent> contains() {
        return null;
    }

    @Override
    public Act renders() {
        return null;
    }
}
