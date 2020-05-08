package fr.univtours.info.model.presentational;


import fr.univtours.info.model.Structural.*;


public interface DashboardComponent {
    public void renders(Episode anEpisode); //addEpisode

    public Episode render(); //getEpisode


}
