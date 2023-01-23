package Views;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controllers.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        contentPane.setBackground(new Color(0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //Message pour choisir le nouveau pseudo
        JLabel lblNewLabel = new JLabel("Choisis votre nouveau pseudo\r\n");
        lblNewLabel.setForeground(new Color(204, 223, 255));
        lblNewLabel.setBackground(new Color(94, 155, 194));
        lblNewLabel.setFont(new Font("Comfortaa", Font.BOLD, 15));
        lblNewLabel.setBounds(114, 65, 240, 43);
        contentPane.add(lblNewLabel);


        //textField pour taper son nouveau pseudo
        textField = new JTextField();
        textField.setBackground(new Color(255, 255, 240));
        textField.setBounds(114, 102, 224, 36);
        contentPane.add(textField);
        textField.setColumns(10);
        textField.addMouseListener(new MouseListener() {
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
    public class Connect implements ActionListener {

        private ChangerPseudo window;

        public Connect(ChangerPseudo window)
        {
            this.window = window;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String pseudo=textField.getText();

            if(pseudo.length()>12) {
                JTextPane txtlongpseudo = new JTextPane();
                txtlongpseudo.setText("Pseudo est trop long.");
                txtlongpseudo.setForeground(new Color(255, 51, 51));
                txtlongpseudo.setFont(new Font("Bahnschrift", Font.BOLD | Font.ITALIC, 11));
                txtlongpseudo.setBackground(SystemColor.menu);
                txtlongpseudo.setBounds(114, 75, 240, 20);
                contentPane.add(txtlongpseudo);
                textField.addMouseListener(new MouseListener() {
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
            }
            else {
                /*if (app.getcSystem().editNickname(pseudo, 4445)) {
                    app.getActu().setPseudo(pseudo);
                    General.pseudoModif();

                    General.dispose(); //ferme la fenetre
                }*/
                if (!app.getUserManager().appartient(pseudo)) {
                    app.getActu().setPseudo(pseudo);
                    General.pseudoModif();
                    // Mettre ici la méthode pour envoyer le broadcast aux autres utilisateur pour signaler le changement de pseudo
                    window.dispose();
                }else {
                    JTextPane txtpnPseudonymAlreadyIn = new JTextPane();
                    txtpnPseudonymAlreadyIn.setText("Pseudo déjà utilisé. Veuillez en choisir un autre");
                    txtpnPseudonymAlreadyIn.setBackground(new Color(0,0,0));
                    txtpnPseudonymAlreadyIn.setForeground(new Color(94, 155, 194));
                    txtpnPseudonymAlreadyIn.setFont(new Font("Bahnschrift", Font.BOLD | Font.ITALIC, 11));
                    txtpnPseudonymAlreadyIn.setBounds(103, 80, 350, 14);

                    contentPane.add(txtpnPseudonymAlreadyIn);
                    textField.addMouseListener(new MouseListener() {
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
                }

            }
        }
    }

}
