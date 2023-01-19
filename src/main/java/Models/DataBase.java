package Models;

import Controllers.UserManager;

import java.sql.*;
import java.util.*;

public class DataBase {
    public static String url = "jdbc:sqlite:/home/yduan/4as7/JavaProjet/java.db";

    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connectf() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void createNewTable() {


        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS conversation (\n"
                + " time text NOT NULL, \n"
                + " message text NOT NULL, \n"
                + " senderPseudo varchar(30) NOT NULL, \n"
                + " receiverPseudo varchar(30) NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Methode pour recuperer l'historique
     *
     */
    public ArrayList<Message> recupHistory() {
        ArrayList<Message> historique = new ArrayList<Message>();
        String sql = "SELECT time, message, senderPseudo, receiverPseudo FROM conversation";


        try (Connection conn = this.connectf();

             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                String sender = rs.getString("senderPseudo");
                String receiver = rs.getString("receiverPseudo");
                Message msg= new Message();
                msg.setContenu(rs.getString("message"));
                msg.setDate(rs.getString("date"));
                msg.setEmetteur(UserManager.getUserfromPseudo(sender));
                msg.setDestinataire(UserManager.getUserfromPseudo(receiver));
                historique.add(msg);
            }
        } catch (SQLException e) {
            System.out.println("error at recupHistory\n");
            System.out.println(e.getMessage());
        }
        return historique;
    }


    /**
     * Methode pour ajouter un message a la table
     * @param msg String
     */
    public void addMessage(Message msg) {
        String sql = "INSERT INTO conversation (date,message,senderPseudo, receiverPseudo) VALUES(?,?,?,?)";


        try (Connection conn =  this.connectf() ;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, msg.getDate());
            pstmt.setString(2, msg.getContenu());
            pstmt.setString(3, msg.getEmetteur().getPseudo()); //J'envoie le message
            pstmt.setString(4, msg.getDestinataire().getPseudo()); //Je recois le message

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error at addMessage\n");
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        createNewDatabase();
        createNewTable();
    }

}
