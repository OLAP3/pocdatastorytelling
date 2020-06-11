package fr.univtours.info.pocdatastory;

import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.simpleStory.SimplePlot;

import java.io.*;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class DBservices {

    private static transient Connection conn=null;

    public DBservices(){
        try {
            connectToPostgresql();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @return connection to the postgres instance
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Restore Plot from the database
     *
     * @param id object id
     * @return deserialized plot
     */
    public  Plot restore(final String id) { //was static?
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Wrong identifier: " + id);
        }
        SimplePlot p = new SimplePlot();
        try {
            // p.connectToPostgresql() ;
            //final Connection conn = p.getConnection();
            final String sql = "select id, text, plot from Plots where id = " + id;
            final PreparedStatement pstmt = conn.prepareStatement(sql);
            final ResultSet rs = pstmt.executeQuery();
            rs.next();
            final byte[] buf = rs.getBytes(3);
            final ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
            p = (SimplePlot) objectIn.readObject();
            rs.close();
            pstmt.close();
            conn.close();
            return p;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String store(Plot p) {

        // TODO update if id/text already saved!!!!

        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
             final ObjectOutputStream oos = new ObjectOutputStream(baos);
             final PreparedStatement preparedStatement = conn.prepareStatement("insert into Plots(text, plot) values(?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);) {
            oos.writeObject(p);
            preparedStatement.setString(1, p.toString()); // p.toString() was text
            preparedStatement.setBytes(2, baos.toByteArray());
            preparedStatement.executeUpdate();
            final ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1) + "";
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
        final String url = props.getProperty("spring.datasource2.url") + "?user=" + user + "&password=" + passwd;;
        // System.out.println(url);
        conn = DriverManager.getConnection(url);
        try (final Statement stmt = conn.createStatement();) {
            final String sqlCreate = "CREATE TABLE IF NOT EXISTS Plots (id SERIAL primary key, text TEXT, plot bytea)";
            stmt.execute(sqlCreate);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }


}
