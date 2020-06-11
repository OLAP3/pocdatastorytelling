package fr.univtours.info.pocdatastory;

import fr.univtours.info.model.Structural.Plot;
import fr.univtours.info.simpleStory.SimplePlot;

import java.io.*;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class DBservices {

    private transient Connection conn;

    public DBservices(){
        /*
        try {
            connectToPostgresql();
        }
        catch (Exception e){
            e.printStackTrace();
        }

         */
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
    public  Plot restoreFromId(final String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Wrong identifier: " + id);
        }
        SimplePlot p = new SimplePlot();
        try {
            // p.connectToPostgresql() ;
            //final Connection conn = p.getConnection();

            connectToPostgresql();
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


    /**
     * Restore Plot from the database
     *
     * @param text object id
     * @return deserialized plot
     */
    public  Plot restore(final String text) {
        /*
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Wrong identifier: " + text);
        }

         */
        SimplePlot p = new SimplePlot();
        try {
            // p.connectToPostgresql() ;
            //final Connection conn = p.getConnection();

            connectToPostgresql();
            final String sql = "select id, text, plot from Plots where text = \'" + text +"\'";
            final PreparedStatement pstmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next() == false) {
               return null;
            }
            else{
                rs.beforeFirst();
                rs.next();
                final byte[] buf = rs.getBytes(3);
                final ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                p = (SimplePlot) objectIn.readObject();
                rs.close();
                pstmt.close();
                conn.close();
                return p;
            }
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }





    // TODO update (overwrite) if id/text already saved!
    public String store(Plot p) {
        try{
            connectToPostgresql();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
             final ObjectOutputStream oos = new ObjectOutputStream(baos);
             final PreparedStatement preparedStatement = conn.prepareStatement("insert into Plots(text, plot) values(?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);) {
            oos.writeObject(p);
            preparedStatement.setString(1, p.toString()); // p.toString() was text
            preparedStatement.setBytes(2, baos.toByteArray());
            preparedStatement.executeUpdate();
            final ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            String res=rs.getInt(1) + "";
            rs.close();
            preparedStatement.close();
            conn.close();
            return res;
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
         System.out.println(url);
        conn = DriverManager.getConnection(url);
        try (final Statement stmt = conn.createStatement();) {
            final String sqlCreate = "CREATE TABLE IF NOT EXISTS Plots (id SERIAL primary key, text TEXT, plot bytea)";
            stmt.execute(sqlCreate);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }


}
