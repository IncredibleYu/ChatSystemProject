package fr.insa.gei.ChatSystemProject.Views;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import fr.insa.gei.ChatSystemProject.Controllers.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe qui represente la fenetre pour changer le pseudp
 * Cette fenetre est affichee lorsque l'utilisateur clique sur le bouton "Changer Pseudo"
 */

public class ChangerPseudo extends JFrame {


    private static Controller app;
    private JPanel contentPane;
    private JTextField textField;

    /**
     * Constructeur de la classe ChangerPseudo
     */
    public ChangerPseudo(Controller app) {
        setBackground(new Color(204, 204, 255));
        this.app = app;
        initialize();
    }


    public void initialize() {
        //frame
        setTitle("Changer Pseudo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 516, 275);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(238,238,238));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //Message pour choisir le nouveau pseudo
        JLabel lblNewLabel = new JLabel("Choisis votre nouveau pseudo\r\n");
        lblNewLabel.setForeground(new Color(0,0,0));
        lblNewLabel.setBackground(new Color(0,0,0));
        lblNewLabel.setFont(new Font("Comfortaa", Font.BOLD, 15));
        lblNewLabel.setBounds(114, 65, 240, 43);
        contentPane.add(lblNewLabel);


        //textField pour taper son nouveau pseudo
        textField = new JTextField();
        textField.setBackground(new Color(255, 255, 240));
        textField.setBounds(114, 102, 224, 36);
        contentPane.add(textField);
        textField.setColumns(10);
        textField.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField texteField = ((JTextField)e.getSource());
                texteField.setText("");
                texteField.getFont().deriveFont(Font.PLAIN);
                texteField.setForeground(Color.black);
                texteField.removeMouseListener(this);
            }
        });
        textField.addActionListener(new Connect(this));

        //Bouton Confirmer
        JButton btnNewButton = new JButton("Confirmer");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(new Color(94, 155, 194));
        btnNewButton.setFont(new Font("Comfortaa", Font.BOLD, 13));
        btnNewButton.setBounds(345, 102, 103, 36);
        btnNewButton.addActionListener(new Connect(this));
        contentPane.add(btnNewButton);
        setVisible(true);
    }

    /**
     * Methode pour verifier l'unicité du pseudo
     */
    public class Connect implements ActionListener
    {

        private ChangerPseudo window;

        public Connect(ChangerPseudo window)
        {
            this.window = window;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            String pseudo=textField.getText();

            //JTextPane txtPseudo= new JTextPane();
            JTextPane txtPseudo= new JTextPane();
            txtPseudo.setFont(new Font("Bahnschrift", Font.BOLD | Font.ITALIC, 11));
            contentPane.add(txtPseudo);

            if(pseudo.length() < 6)
            {
                txtPseudo.setText("ERREUR : Votre pseudo doit contenir au minimum 6 caractères.");
                txtPseudo.setForeground(new Color(255, 51, 51));
                txtPseudo.setBackground(SystemColor.menu);
                txtPseudo.setBounds(114, 145, 224, 50);
                return;
            }

            if(pseudo.length() > 12)
            {
                txtPseudo.setText("ERREUR : Votre pseudo doit contenir au maximum 12 caractères.");
                txtPseudo.setForeground(new Color(255, 51, 51));
                txtPseudo.setBackground(SystemColor.menu);
                txtPseudo.setBounds(114, 145, 224, 50);
                return;
            }

            Matcher matcherPseudo = Pattern.compile("[^a-zA-Z0-9]").matcher(pseudo);
            if(matcherPseudo.find()) // Si le pseudo contient des caractères spéciaux
            {
                txtPseudo.setText("ERREUR : Votre pseudo contient des caractères spéciaux. Seuls les lettres de l'alphabet et les chiffres sont autorisés.");
                txtPseudo.setForeground(new Color(255, 51, 45));
                txtPseudo.setBackground(SystemColor.menu);
                txtPseudo.setBounds(114, 145, 224, 50);
                return;
            }

            if (app.getUserManager().appartient(pseudo))
            {
                txtPseudo.setText("ERREUR : Ce pseudo déjà utilisé par un autre utilisateur, veuillez choisir un autre pseudo.");
                txtPseudo.setForeground(new Color(255, 51, 45));
                txtPseudo.setBackground(SystemColor.menu);
                txtPseudo.setBounds(114, 145, 224, 50);
                return;
            }
            else
            {
                app.getcSystem().editNickname(pseudo);
                window.dispose();
            }
        }
    }
}
