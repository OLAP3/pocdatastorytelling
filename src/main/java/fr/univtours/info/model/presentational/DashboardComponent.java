package fr.univtours.info.model.presentational;


import fr.univtours.info.model.Structural.*;

import java.io.Serializable;


public interface DashboardComponent extends Serializable {
    public void renders(Episode anEpisode); //addEpisode

    public Episode render(); //getEpisode


}
