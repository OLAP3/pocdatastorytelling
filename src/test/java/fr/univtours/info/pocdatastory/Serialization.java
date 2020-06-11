package fr.univtours.info.pocdatastory;

import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Episode;
import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.model.factual.Finding;
import fr.univtours.info.model.intentional.Character;
import fr.univtours.info.model.intentional.*;
import fr.univtours.info.simpleStory.*;
import org.junit.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

public class Serialization {
    public Plot setUP() {
        final Plot p = new SimplePlot();

        final AnalyticalQuestion q1 = new SimpleAnalyticalQuestion();
        final Finding f1 = new SimpleDescribeFinding(new byte[]{}, "");

        final Goal g1 = new SimpleGoal();
        q1.poses(g1);

        final Character c1 = new SimpleCharacter();
        c1.addText("c1_foo");
        c1.setName("c1_name");

        final Measure m1 = new SimpleMeasure();
        m1.addText("m1_foo");
        m1.setValue(0);
        m1.setName("m1_name");

        final Message ms1 = new SimpleMessage();
        ms1.addText("ms1_foo");
        ms1.generates(q1);
        ms1.poses(q1);
        ms1.produces(f1);
        ms1.bringsOut(c1);
        ms1.includes(m1);

        final Episode e1 = new SimpleEpisode();
        e1.addText("episode1_foo");
        e1.playsIn(c1);
        e1.refersTo(m1);
        e1.narrates(ms1);

        final Act a1 = new SimpleAct();
        a1.addText("act_foo");
        a1.includes(e1);

        p.addText("plot_foo");
        p.includes(a1);
        return p;
    }


    /**
     * Test if the serialized plot equals to the de-serialized one (i.e., all the fields have been correctly stored/retrieved)
     */
    @Test
    public void testPlot() {
        final Plot p = setUP(); // create a plot example
        final String filename = "serializedPlot";
        // Object serialization
        try (final FileOutputStream fileOut = new FileOutputStream(filename); // where to write the serialized object
             final ObjectOutputStream out = new ObjectOutputStream(fileOut);  // object writer (write object content to bytes)
        ) {
            out.writeObject(p);
        } catch (IOException i) {
            i.printStackTrace();
            fail();
        }

        // Object de-serialization
        Plot d = null;
        try (final FileInputStream fileIn = new FileInputStream(filename);
             final ObjectInputStream in = new ObjectInputStream(fileIn);) {
            d = (Plot) in.readObject();
        } catch (Exception i) {
            i.printStackTrace();
            fail();
        }

        assertEquals(p, d);
    }

    /**
     * Store/read a plot to/from a SQL table
     */
    @Test
    public void testDBSerialization() {
        final SimplePlot p = (SimplePlot) setUP(); // create a plot example
        String id = null;
        DBservices dbs=new DBservices();
        try {
            //p.connectToPostgresql(); // open connection and create `Plots` if it does not exist
            final Connection c = dbs.getConnection();
            assertFalse(c.isClosed());
            final Statement stmt = c.createStatement();
            stmt.executeQuery("select * from plots"); // check that this query works (i.e., that the table Plots exists)
            stmt.close();
            id = dbs.store(p); // serialize the plot to the database
            System.out.println("Stored plot: " + id);
        } catch (final Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        final Plot d = dbs.restore(id); // read the object from the database
        assertEquals(p, d);
    }
}
