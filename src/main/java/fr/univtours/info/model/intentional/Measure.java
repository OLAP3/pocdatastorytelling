package fr.univtours.info.model.intentional;


import java.io.Serializable;
import java.util.Collection;

public interface Measure extends Serializable {
    public void addText(String aText);
    public String getText();

    public String toString();

    public void setName(String name);
    public String getName();

    public float getValue();
    public void setValue(float value);
//    public void bringsOut(Message anMessage);
//    public Collection<Message> bringsOut();
}
