package fr.univtours.info.model.intentional;


import java.util.Collection;

public interface Measure {
    public void addText(String aText);
    public String toString();

    public void setName(String name);
    public String getName();

    public float getValue();
    public void setValue(float value);
//    public void bringsOut(Message anMessage);
//    public Collection<Message> bringsOut();
}
