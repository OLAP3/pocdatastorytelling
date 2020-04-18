package fr.univtours.info.simpleStory;

import fr.univtours.info.model.intentional.Protagonist;

public class SimpleProtagonist implements Protagonist {
    String text;
    String name;

    @Override
    public void addText(String aText) {

        this.text=aText;
    }


    @Override
    public String getText( ) {
        return text;
    }


    @Override
    public void setName(String name) {

        this.name=name;
    }

    @Override
    public String getName() {

        return name;
    }
}
