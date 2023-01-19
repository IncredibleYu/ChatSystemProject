package Models;

import Controllers.Controller;
import Controllers.UserManager;

import java.sql.*;
import java.util.*;

public class DataBase
{
    private static Controller app;

    //private String DB_URL = "jdbc:sqlite:/home/yduan/4as7/JavaProjet/java.db";
    private String DB_URL = "jdbc:sqlite:/home/babonnea/Documents/4A/UML-Java/Java/ProgressProject/v2/JavaProjet/java.db";

    /**
     * Constructeur de la classe DataBase :
     * @param app Controller
     */
    public DataBase(Controller app) {
        this.setApp(app);
        createNewDatabase();
    }

    public static Controller getApp()
    {
        return app;
    }

    public void setApp(Controller app) {
        DataBase.app = app;
    }

    public void createNewDatabase()
    {
        Connection conn = null;
        try {

            // create a connection to the database
            conn = DriverManager.getConnection(this.DB_URL);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private Connection connect()
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }



    public void createTable()
    {
        String sql= "CREATE TABLE conversation(\n"
                + "    time text NOT NULL, \n"
                + " message text NOT NULL, \n"
                + " senderPseudo varchar(30) NOT NULL, \n"
                + " receiverPseudo varchar(30) NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(this.DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("erreur at createTableConversation\n");
            System.out.println(e.getMessage());
        }
    }
    /**
     * Methode pour recuperer l'historique
     *
     */
    public ArrayList<Message> recupHistory()
    {
        ArrayList<Message> historique = new ArrayList<Message>();
        String sql = "SELECT date, message, senderPseudo, receiverPseudo FROM conversation";


        try (Connection conn = this.connect(); Statement stmt  = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                String sender = rs.getString("senderPseudo");
                String receiver = rs.getString("receiverPseudo");
                Message msg= new Message();
                msg.setContenu(rs.getString("message"));
                msg.setDate(rs.getString("date"));
                msg.setEmetteur(UserManager.getUserfromPseudo(sender));
                msg.setDestinataire(UserManager.getUserfromPseudo(receiver));
                historique.add(msg);
            }
        }
        catch (SQLException e)
        {
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


        try (Connection conn =  this.connect() ; PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, msg.getDate());
            pstmt.setString(3, msg.getEmetteur().getPseudo()); //J'envoie le message
            pstmt.setString(4, msg.getDestinataire().getPseudo()); //Je recois le message

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error at addMessage\n");
            System.out.println(e.getMessage());
        }
    }

}