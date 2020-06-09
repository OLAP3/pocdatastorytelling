package fr.univtours.info.simpleStory;

import fr.univtours.info.model.intentional.Character;

import java.util.Objects;

public class SimpleCharacter implements Character {
    String theText;
    String name;

    @Override
    public void addText(String aText) {
        this.theText=aText.substring(1,aText.length()-1).replace("\\n","\n");

//        this.theText=aText;
    }

    @Override
    public String getText() {
        return theText;
    }


    @Override
    public String toString() {
        return "Character: " + theText;

    }


    @Override
    public void setName(String name) {

        this.name=name;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleCharacter that = (SimpleCharacter) o;
        return Objects.equals(theText, that.theText) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theText, name);
    }
}
