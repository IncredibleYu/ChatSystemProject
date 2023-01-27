package fr.insa.gei.ChatSystemProject.Views;

import fr.insa.gei.ChatSystemProject.Controllers.Controller;
import fr.insa.gei.ChatSystemProject.Models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        JLabel lblChatbox = new JLabel("ChatYourFriends");
        lblChatbox.setForeground(new Color(0,0,0));
        lblChatbox.setAlignmentY(5.0f);
        lblChatbox.setAlignmentX(5.0f);
        lblChatbox.setFont(new Font("Comfortaa", Font.BOLD | Font.ITALIC, 16));
        lblChatbox.setBackground(new Color(204, 204, 255));
        lblChatbox.setBounds(120, 100, 240, 43);
        frame.getContentPane().add(lblChatbox);


        JLabel lblcreateur = new JLabel("GEI - 4IR ");
        lblcreateur.setForeground(new Color(0,0,0));
        lblcreateur.setAlignmentY(1.0f);
        lblcreateur.setAlignmentX(1.0f);
        lblcreateur.setFont(new Font("Comfortaa", Font.BOLD , 15));
        lblcreateur.setBackground(new Color(204, 204, 255));
        lblcreateur.setBounds(160, 150, 240, 43);
        frame.getContentPane().add(lblcreateur);

        frame.setSize(dimWindow);
        frame.setLocation(new Point(750, 250));

        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });

        choiceLabel = new JLabel("Veuillez saisir votre pseudonyme ci-dessous :", SwingConstants.LEFT);
        choiceLabel.setBackground(new Color(94, 155, 194));
        choiceLabel.setFont(new Font("Comfortaa", Font.BOLD, 15));
        choiceLabel.setBounds(114, 65, 240, 43);

        pseudoTextField = new JTextField();
        pseudoTextField.setPreferredSize(new Dimension(250,25));
        pseudoTextField.setBackground(new Color(255, 255, 240));
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
        button.setBackground(new Color(94, 155, 194));
        button.setFont(new Font("Comfortaa", Font.BOLD, 13));

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

                if(pseudo.length() > 12)
                {
                    messageLabel.setText(textWithAlign("ERREUR : Votre pseudo doit contenir au maximum 12 caractères."));
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

                    User myUser = new User(pseudo);

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

                    messageLabel.setText(textWithAlign("REUSSI : Excellent choix!"));
                    messageLabel.setForeground(Color.GREEN);
                    messageLabel.setVisible(true);
                    pseudoTextField.setVisible(false);
                    choiceLabel.setVisible(false);

                    frame.setVisible(false);

                    app.getcSystem().Connexion(myUser);

                    new General(app);
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
