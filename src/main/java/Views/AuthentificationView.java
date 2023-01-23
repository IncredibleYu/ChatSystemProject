package Views;

import Controllers.Controller;
import Controllers.ControllerChat;
import Controllers.UserManager;
import Models.DataBase;
import Models.User;
import Packet.Packet;
import Protocols.TCPServer;
import Protocols.UDPReceiver;
import Protocols.UDPSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthentificationView
{
    private JFrame frame;
    private JPanel panel;
    private JTextField pseudoTextField;
    private JButton button;
    private Container contentPane;
    private JLabel choiceLabel, messageLabel;
    private Dimension dimWindow;
    private Controller app;

    public AuthentificationView(Controller app)
    {
        this.app = app;
        this.launchFrame();
    }
    void launchFrame()
    {
        /* initialization */
        dimWindow = new Dimension(400, 600);
        frame = new JFrame("ChatYourFriends - Connexion");
        frame.setSize(dimWindow);
        frame.setLocation(new Point(750, 250));

        choiceLabel = new JLabel("Veuillez saisir votre pseudonyme ci-dessous :", SwingConstants.LEFT);
        pseudoTextField = new JTextField();
        pseudoTextField.setPreferredSize(new Dimension(250,25));
        pseudoTextField.setHorizontalAlignment(JTextField.CENTER);
        messageLabel = new JLabel("", SwingConstants.LEFT);
        messageLabel.setVisible(false);

        this.addButton();

        //add components to panelFlowLayout by default
        panel = new JPanel();
        panel.add(choiceLabel);
        panel.add(pseudoTextField);
        panel.add(messageLabel);
        panel.add(button);
        panel.setBorder(BorderFactory.createEmptyBorder(
                200, //top
                50, //left
                50, //bottom
                50) //right
        );

        /* add components to contentPane BorderLayout */
        contentPane = frame.getContentPane();
        contentPane.add(panel, BorderLayout.CENTER);
        //frame.pack(); //Size of frame based on components
        frame.setVisible(true);
    }

    public void addButton()
    {
        button = new JButton("OK");
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                messageLabel.setVisible(false);
                messageLabel.setText("");
                String pseudo = pseudoTextField.getText();

                if(pseudo.length() < 6)
                {
                    messageLabel.setText(textWithAlign("ERREUR : Votre pseudo doit contenir au minimum 6 caractères."));
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setVisible(true);
                    return;
                }

                Matcher matcherPseudo = Pattern.compile("[^a-zA-Z0-9]").matcher(pseudo);
                if(matcherPseudo.find()) // Si le pseudo contient des caractères spéciaux
                {
                    messageLabel.setText(textWithAlign("ERREUR : Votre pseudo contient des caractères spéciaux. Seuls les lettres de l'alphabet et les chiffres sont autorisés."));
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setVisible(true);
                    return;
                }
                button.setVisible(false);

                Object[] options = {"Oui, je choisis celui-là",
                        "Non, je préfère changer"};

                int n = JOptionPane.showOptionDialog(frame, "Vous avez choisi ce pseudo :\n" + pseudo + "\n" + "Êtes-vous sûr de vouloir choisir celui-là ?", "Confirmation du pseudonyme", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

                if (n == JOptionPane.YES_OPTION)
                {
                    messageLabel.setText(textWithAlign("Tentative de connexion en cours..."));
                    messageLabel.setForeground(Color.ORANGE);
                    messageLabel.setVisible(true);
                    messageLabel.updateUI();
                    pseudoTextField.setVisible(false);
                    choiceLabel.setVisible(false);
                    //Label not updating : https://stackoverflow.com/questions/31207463/label-not-updating-in-same-instance-of-gui



                    User myUser = new User(0, pseudo, 1234);


                    boolean PseudoIsUsed = app.getUserManager().checkUserExists(myUser.getPseudo());
                    if (PseudoIsUsed)
                    {
                        messageLabel.setText(textWithAlign("ERREUR : Pseudo déjà utilisé. Veuillez en choisir un autre."));
                        messageLabel.setForeground(Color.RED);
                        messageLabel.setVisible(true);
                        pseudoTextField.setVisible(true);
                        choiceLabel.setVisible(true);
                        button.setVisible(true);
                        return;
                    }
                    //app.getUserManager().addMember(myUser);
                    //Thread UDPClient = myThreadManager.newThread("UDP", "CLIENT");
                    /*Packet packet = new Packet(myUser);
                    udp.send(packet);*/

                    messageLabel.setText(textWithAlign("REUSSI : Excellent choix!"));
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setVisible(true);
                    pseudoTextField.setVisible(false);
                    choiceLabel.setVisible(false);

                    frame.setVisible(false);
                    Point coords = frame.getLocation();
                    //new DirectoryView(userManager, myUser, coords);
                    /*Controller app = new Controller(myUser);
                    //app.setServerUDP(new UDPReceive(app));
                    app.setcSystem(new ControllerChat(app));
                    app.setDb(new DataBase(app));*/

                    app.setActu(myUser);

                    //app.getUserManager().addMember(myUser);
                    UDPReceiver udp = new UDPReceiver("SERVEUR", app);
                    TCPServer tcp = new TCPServer("SERVEUR", app);

                    udp.start();
                    tcp.start();

                    Packet packet = new Packet();
                    packet.setMessage("Pseudo");
                    packet.setUser(myUser);
                    app.getUdpSender().broadcast(packet);

                    new General(app);

                    //myUDPServer.closeConnection();
                }
                else
                {
                    button.setVisible(true);
                }
            }
        });
    }

    public String textWithAlign(String message)
    {
        return String.format("<html><div style=\"text-align:center; width:%dpx;\">%s</div></html>", (int)(dimWindow.getWidth() - 100), message);
    }
}
