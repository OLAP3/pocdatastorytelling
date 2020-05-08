package fr.univtours.info.simpleStory;

import fr.univtours.info.model.intentional.Character;

public class SimpleCharacter implements Character {
    String theText;
    String name;

    @Override
    public void addText(String aText) {
        this.theText=aText.substring(1,aText.length()-1).replace("\\n","\n");

//        this.theText=aText;
    }


    @Override
    public String toString() {

        return "Protagonist: " + theText;
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
