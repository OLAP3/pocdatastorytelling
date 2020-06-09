package fr.univtours.info.model.Structural;


import fr.univtours.info.model.intentional.*;
import fr.univtours.info.model.intentional.Character;

import java.io.Serializable;
import java.util.Collection;

public interface Episode extends Serializable {

    public void addText(String theText);
    public String getText();

    public String toString();

    public void narrates(Message anMessage); //addMessage
    public Message narrates(); // get Message

    public void playsIn(Character aCharacter);
    public Collection<Character> playsIn();

    public void refersTo(Measure aMeasure);
    public Collection<Measure> refersTo();

}
