package fr.univtours.info.model.intentional;

public interface Protagonist {
    public void addText(String aText);
    public String toString(); //was getText

    public void setName(String name);
    public String getName();
}
