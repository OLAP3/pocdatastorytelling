package fr.univtours.info.model.intentional;

import java.io.Serializable;

public interface Character extends Serializable {
    public void addText(String aText);
    public String getText();
    public String toString();


    public void setName(String name);
    public String getName();
}
