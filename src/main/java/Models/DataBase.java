package Models;

import Controllers.UserManager;

import java.sql.*;
import java.util.*;

public class DataBase {

    public static String url = "jdbc:sqlite:Database/java.db";


    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Le nom de driver c'est " + meta.getDriverName());
                System.out.println("Création de database avec succès.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTable() {
        String sql = "DROP TABLE conversation;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void createNewTable() {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS conversation (\n"
                + " senderPseudo varchar(30) NOT NULL, \n"
                + " receiverPseudo varchar(30) NOT NULL, \n"
                + " message text NOT NULL, \n"
                + " date text NOT NULL"
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
    public static ArrayList<Message> recupHistory() {
        ArrayList<Message> historique = new ArrayList<Message>();
        String sql = "SELECT senderPseudo, receiverPseudo, message, date FROM conversation";


        try (Connection conn = DriverManager.getConnection(url);
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
            System.out.println("erreur de récuperation l'historique\n");
            System.out.println(e.getMessage());
        }
        return historique;
    }


    /**
     * Methode pour ajouter un message a la table
     * @param ip
     * @param msg String
     */

    public static void addMessage(Message msg) {
        String sql = "INSERT INTO conversation (senderPseudo, receiverPseudo, message, date) VALUES(?,?,?,?)";



        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, msg.getEmetteur().getPseudo());
            pstmt.setString(2, msg.getDestinataire().getPseudo());
            pstmt.setString(3, msg.getContenu());
            pstmt.setString(4, msg.getDate());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error at addMessage\n");
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        createNewDatabase();
        deleteTable();
        createNewTable();
        UserManager userManager = new UserManager();
        User user1 = new User(1,"toto",1234);
        User user2 = new User(2,"tata",1235);
        userManager.addMember(user1);
        userManager.addMember(user2);
        Message msg1 = new Message(user1,user2,"Hello user2");
        Message msg2 = new Message(user2,user1,"Hello user1");
        addMessage(msg1);
        addMessage(msg2);
        ArrayList<Message> msgtable = recupHistory();
        for(int i = 0; i<msgtable.size();i++){
            Message msg3=msgtable.get(i);
            System.out.println(msg3.toString());
        }
    }

}
