package fr.insa.gei.ChatSystemProject.Views;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.border.EtchedBorder;

import fr.insa.gei.ChatSystemProject.Controllers.Controller;
import fr.insa.gei.ChatSystemProject.Models.Message;
import fr.insa.gei.ChatSystemProject.Models.User;
import fr.insa.gei.ChatSystemProject.Protocols.TCPSender;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JList;



import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.ComponentOrientation;
import java.awt.event.*;
import java.util.ArrayList;

/*
 * Class
 */

/*
 * Classe Home correspondant à la fenêtre principale (main) de l'application
 *
 * app : instance de la classe Application associée
 * frame : panel de contenu principal de la frame
 * panel : panel de contenu principal de la frame
 * textfield : zone de texte pour pouvoir rentrer le pseudo choisi par l'user
 * btnSend : bouton permettant l'envoi de messages
 * textArea : zone de lecture de la conversation
 * talkingto : pseudo de l'user avec qui on clavarde
 * usertalking : user avec qui on clavarde
 * txtrB : notre pseudo
 * usersconnected : liste des users connectés sur le réseau
 *
 * tcpListen : instance de TCPRunner permettant l'écoute de conversation
 *
 */

/*
 * Classe General qui correspond a la fenetre principale (nomme General)
 * Cette fenetre affiche :
 * - les utilisateurs actifs
 * - les notifications generales telles que la connexion et/ou la deconnexion des utilisateurs, ainsi que la reception des messages
 */
public class General {

    private static Controller app;
    private static JFrame frame;
    private static JEditorPane textArea;
    private static JTextArea talkingto;
    private static User usertalking;
    private static JTextArea txtrB;
    private static JEditorPane notifPane;
    private static JList<String> usersconnected;
    private static boolean isStart = false;

    /**
     * Constructeur de la classe General
     */
    public General(Controller app) {
        setApp(app);
        initialize();
        isStart = true;
    }

    public void initialize()
    {
        //frame General
        frame = new JFrame("ChatYourFriends");
        frame.setBackground(new Color(204, 204, 255));
        frame.getContentPane().setSize(new Dimension(490, 470));
        frame.getContentPane().setPreferredSize(new Dimension(1600, 900));
        frame.getContentPane().setMinimumSize(new Dimension(1600, 900));
        frame.getContentPane().setMaximumSize(new Dimension(1600, 900));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(0, 0, dim.width, dim.height);

        frame.addWindowListener(new windowClosingListener());

        frame.setLocation(dim.width / 3 - frame.getWidth() / 3, dim.height / 3 - frame.getHeight() / 3);
        frame.getContentPane().setBounds(0, 0, dim.width, dim.height);
        frame.pack();

        //Bar de Menu
        JMenuBar menuBar = new JMenuBar();
        menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        frame.setJMenuBar(menuBar);

        //Bouton pour avoir acces à la fenetre de changement de pseudo
        JButton btnChangerPseudo = new JButton("Changer Pseudo");
        btnChangerPseudo.setFont(new Font("Comfortaa", Font.BOLD, 15));
        btnChangerPseudo.setBackground(new Color(255, 255, 240));
        menuBar.add(btnChangerPseudo);

        btnChangerPseudo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ChangerPseudo(getApp());
            }
        });

        //Bouton pour avoir acces à la fenetre de deconnexion
        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.setFont(new Font("Comfortaa", Font.BOLD, 15));
        btnDeconnexion.setBackground(new Color(255, 255, 240));
        menuBar.add(btnDeconnexion);


        btnDeconnexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Deconnexion(getApp());
            }
        });

        JPanel panel = new JPanel();
        panel.setBounds(25, 12, 1262, 910);
        panel.setBackground(new Color(255, 255, 255));
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(1281, 12, 307, 888);
        panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_1.setBackground(UIManager.getColor("Button.disabledText"));
        panel_1.setLayout(null);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(41, 45, 224, 831);
        panel_1.add(scrollPane_1);

        JPanel panel_2 = new JPanel();
        panel_2.setFont(new Font("Comfortaa", Font.PLAIN, 13));
        panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(216, 191, 216), new Color(216, 191, 216)));
        panel_2.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        scrollPane_1.setViewportView(panel_2);

        JLabel lblcontacts = new JLabel("Utilisateurs en ligne");
        lblcontacts.setForeground(new Color(255, 255, 240));
        lblcontacts.setBackground(new Color(192, 192, 192));
        lblcontacts.setFont(new Font("Comfortaa", Font.BOLD, 18));
        lblcontacts.setBounds(54, 11, 199, 23);
        panel_1.add(lblcontacts);


        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(panel_1);

        // TextField pour ecrire le message a envoyer
        JTextField textField = new JTextField();
        textField.setBounds(316, 479, 806, 38);
        textField.setBorder(new LineBorder(new Color(94, 155, 194), 3));
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Bahnschrift", Font.PLAIN, 11));
        textField.setColumns(10);

        // Bouton envoyer
        JButton btnSend = new JButton("Envoyer");
        btnSend.setBounds(1134, 479, 108, 38);
        btnSend.setBorder(new LineBorder(new Color(94, 155, 194), 3));
        btnSend.setBackground(new Color(15, 172, 31));
        btnSend.setFont(new Font("Comfortaa", Font.BOLD, 15));

        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (getUsertalking()==null) {
                }
                else {
                    String msg = textField.getText();
                    TCPSender chat = new TCPSender("CLIENT", getApp(), usertalking);
                    chat.SendMessage(msg);
                    textField.setText("");
                    loadconvo(usertalking);
                }
            }
        });

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (getUsertalking()==null){
                }
                else {
                    String msg = textField.getText();
                    TCPSender chat = new TCPSender("CLIENT", getApp(), usertalking);
                    chat.SendMessage(msg);
                    textField.setText("");
                    loadconvo(usertalking);
                }
            }

        });

        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(316, 91, 916, 359);
        panel.add(scrollPane);

        textArea = new JEditorPane();
        textArea.setFont(new Font("Comfortaa", Font.PLAIN, 13));
        textArea.setForeground(new Color(255, 255, 240));
        scrollPane.setViewportView(textArea);
        textArea.setBackground(new Color(0, 0, 0));
        textArea.setEditable(false);

        //label design
        JLabel lblTalkingwith = new JLabel("est entrain de chat avec");
        lblTalkingwith.setBounds(330, 60, 500, 31);
        lblTalkingwith.setFont(new Font("Comfortaa", Font.BOLD | Font.ITALIC, 18));
        setTalkingto(new JTextArea());
        getTalkingto().setBackground(new Color(255,255,255));
        getTalkingto().setFont(new Font("Bahnschrift", Font.BOLD | Font.ITALIC, 18));
        getTalkingto().setBounds(565, 64, 126, 31);

        panel.add(getTalkingto());


        //Panel pour afficher la liste des utilisateurs et les notifications
        JScrollPane scrollnotif = new JScrollPane();
        scrollnotif.setBounds(12, 58, 292, 827);
        panel.add(scrollnotif);

        notifPane = new JEditorPane();
        notifPane.setBorder(new LineBorder(UIManager.getColor("Button.disabledText"), 3));
        scrollnotif.setViewportView(notifPane);
        notifPane.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        notifPane.setEditable(false);
        panel.add(lblTalkingwith);

        panel.add(btnSend);
        panel.add(textField);

        txtrB = new JTextArea();
        txtrB.setBounds(330, 29, 144, 25);
        txtrB.setFont(new Font("Comfortaa", Font.BOLD | Font.ITALIC, 20));
        txtrB.setBackground(new Color(255, 255, 255));
        System.out.println(General.app);
        txtrB.setText(app.getActu().getPseudo());
        panel.add(txtrB);

        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setFont(new Font("Comfortaa", Font.BOLD, 13));
        lblNewLabel.setBounds(292, 29, 48, 28);
        panel.add(lblNewLabel);
        frame.getContentPane().add(panel);

        JLabel lblNotificationsGenerales = new JLabel("Notifications generales");
        lblNotificationsGenerales.setBounds(50, 29, 289, 21);
        panel.add(lblNotificationsGenerales);
        lblNotificationsGenerales.setForeground(UIManager.getColor("Button.foreground"));
        lblNotificationsGenerales.setFont(new Font("Comfortaa", Font.BOLD, 18));
        frame.getContentPane().add(panel_1);

        //Liste des utilisateurs en ligne
        usersconnected = new JList<String>(getApp().getUserManager().getListPseudo());
        usersconnected.setBounds(0, 646, 272, -599);
        usersconnected.setBackground(new Color(204, 204, 255));
        usersconnected.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        usersconnected.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                if (evt.getValueIsAdjusting()) {
                    int userselect = usersconnected.getSelectedIndex();
                    if (userselect != -1) {
                        String usertalk = usersconnected.getSelectedValue();
                        loadconvo(getApp().getUserManager().getMemberByPseudo(usertalk));
                        getTalkingto().append("");
                        usertalking=getApp().getUserManager().getMemberByPseudo(usersconnected.getSelectedValue());
                        Chats(usertalking);
                    }
                }

            }
        });

        panel_2.add(usersconnected);

        frame.getContentPane().add(panel_1);
        frame.setVisible(true);

    }

    public static void resetSelection()
    {
        usersconnected.setSelectedIndex(-1);
        usertalking = null;
        clearMessagesArea();
    }


    /**
     * Methode pour ouvrir une conversation apres avoir appuye sur le pseudo de l'utilisateur
     */
    public void Chats(User u2) {
        getTalkingto().setText(u2.getPseudo());
    }

    /**
     * Methode pour remettre à vide la zone de conversation
     */
    public static void clearMessagesArea() {
        textArea.setText("");
    }


    /**
     * Methode pour mettre a jour la liste des utilisateurs suite a la connexion/deconnexion d'un utilisateur
     */
    public static void miseAJourContact() {
        if(isStart)
        {
            usersconnected.setListData(getApp().getUserManager().getListPseudo());
        }
    }

    /**
     * Methode pour afficher le pseudo de la personne ayant ouvert l'application
     */
    public static void pseudoModif() {
        txtrB.setText(app.getActu().getPseudo());
    }

    /**
     * Methode pour afficher les messages
     */
    public static void display(String friend) {
        if (getTalkingto().getText().equals(friend)) {
            loadconvo(usertalking);
        }
    }

    /**
     * Methode pour charger l'historique
     */
    public static void loadconvo(User u2) {
        ArrayList<Message> history = getApp().getDb().recupHistory(app.getActu(), u2);
        String messages = "";

        if(!history.isEmpty())
        {
            for (Message msg : history)
            {
                System.out.println(msg);
                if (msg.getEmetteur().equals(getApp().getActu()))
                {
                    messages += "me: " + msg.getData() + "  " + msg.getDate() + "  \n";
                }
                else
                {
                    messages += "                                                                                                                          " +
                            u2.getPseudo() + ":" + msg.getData() + "  " + msg.getDate() + "  \n";
                }

            }
            textArea.setText(messages);
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }

    }

    /**
     * Methode pour afficher une notification de la reception d'un message
     */
    public static void displayNotification(String todisplay, String IPfrom) {
        String notifs="";
        notifs+=notifPane.getText();
        notifs+=getApp().getUserManager().getPseudofromIP(IPfrom)+todisplay+"\n";

        notifPane.setText(notifs);
        notifPane.setCaretPosition(notifPane.getDocument().getLength());

    }

    /**
     * Methode pour afficher une notification de la connexion/deconnexion d'un utilisateur
     * Ou pour afficher le changement de pseudo d'un utilisateur
     */
    public static void displayNotifUsers(String pseudo, String todisplay ) {
        String notifs="";
        notifs+=notifPane.getText();
        notifs+=pseudo + todisplay+"\n";

        //on affiche et on scrolle jusqu'en bas
        notifPane.setText(notifs);
        notifPane.setCaretPosition(notifPane.getDocument().getLength());

    }

    /**
     * Window Listener pour la deconnexion d'un utilisateurs
     * Arret des sockets d'ecoute
     */

    public class windowClosingListener implements WindowListener {

        public void windowClosing(WindowEvent windowEvent) {
            getApp().getcSystem().Deconnexion();
        }

        public void windowOpened(WindowEvent arg0) {
        }

        public void windowClosed(WindowEvent arg0) {
        }

        public void windowIconified(WindowEvent arg0) {
        }

        public void windowDeiconified(WindowEvent arg0) {
        }

        public void windowActivated(WindowEvent arg0) {
        }

        public void windowDeactivated(WindowEvent arg0) {
        }

    }



    /**
     * Fermeture de la page General
     */
    public static void dispose() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.dispose();
    }


    public static User getUsertalking() {
        return usertalking;
    }

    public static JTextArea getTalkingto() {
        return talkingto;
    }

    public void setTalkingto(JTextArea talkingto) {
        this.talkingto = talkingto;
    }

    public static Controller getApp() {
        return app;
    }

    public static void setApp(Controller app) {
        General.app = app;
    }

    public static boolean isStart()
    {
        return isStart;
    }
}