package fr.univtours.info.simpleStory;

import fr.univtours.info.model.intentional.Goal;
import fr.univtours.info.model.Structural.Act;
import fr.univtours.info.model.Structural.Plot;

import java.io.*;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

public class SimplePlot implements Plot {

    private String text;
    private ArrayList<Act> theActs;
    private Goal theGoal;
    private transient Connection conn;

    public SimplePlot(){
        theActs=new ArrayList<Act>();
    }

    /**
     * @return connection to the postgres instance
     */
    public Connection getConnection() {
        return conn;
    }

    @Override
    public void addText(String theText) {

        this.text=theText;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public void includes(Act anAct) {
        this.theActs.add(anAct);
    }


    @Override
    public void has(Goal aGoal) {

        this.theGoal=aGoal;
    }

    @Override
    public Collection<Act> includes() {

        return this.theActs;
    }

    @Override
    public Goal has() {

        return this.theGoal;
    }

    /**
     * Restore Plot from the database
     * @param id object id
     * @return deserialized plot
     */
    public static Plot restore(final String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Wrong identifier: " + id);
        }
        SimplePlot p = new SimplePlot();
        try {
            p.connectToPostgresql();
            final Connection conn = p.getConnection();
            final String sql = "select id, text, plot from Plots where id = " + id;
            System.out.println(sql);
            final PreparedStatement pstmt = conn.prepareStatement(sql);
            final ResultSet rs = pstmt.executeQuery();
            rs.next();
            final byte[] buf = rs.getBytes(3);
            final ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
            p = (SimplePlot) objectIn.readObject();
            rs.close();
            pstmt.close();
            return p;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String store() {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            // final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            final PreparedStatement preparedStatement = conn.prepareStatement("insert into Plots(text, plot) values(?, ?)");
            preparedStatement.setString(1, text);
            preparedStatement.setBytes(2, baos.toByteArray());
            return preparedStatement.executeUpdate() + "";
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Connect to postgres and create the `Plots` table if it does not exist
     * @throws Exception in case of error
     */
    public void connectToPostgresql() throws Exception {
        final FileReader fr = new FileReader(new File("src/main/resources/application.properties"));
        final Properties props = new Properties();
        props.load(fr);
        final String passwd = props.getProperty("spring.datasource.password");
        final String user = props.getProperty("spring.datasource.user");
        final String url = props.getProperty("spring.datasource.url") + "?user=" + user + "&password=" + passwd;;
        System.out.println(url);
        conn = DriverManager.getConnection(url);
        try (final Statement stmt = conn.createStatement();) {
            // final String sqlDrop = "DROP TABLE IF EXISTS Plots";
            // stmt.execute(sqlDrop);
            final String sqlCreate = "CREATE TABLE IF NOT EXISTS Plots (id SERIAL primary key, text TEXT, plot bytea)";
            stmt.execute(sqlCreate);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplePlot that = (SimplePlot) o;
        return Objects.equals(text, that.text) &&
                Objects.equals(theActs, that.theActs) &&
                Objects.equals(theGoal, that.theGoal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, theActs, theGoal);
    }
}
