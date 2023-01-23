package Views;

import Controllers.Controller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Deconnexion extends JFrame {

    private static Controller app;
    private JPanel contentPane;
    private JTextField textField;

    public Deconnexion(Controller app) {
        setBackground(new Color(204, 204, 255));
        this.app=app;
        initialize();
    }

    private void initialize() {
        //frame
        setTitle("Déconnecter");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 516, 275);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //Bouton Confirmer
        JButton btnNewButton = new JButton("Confirmer");
        btnNewButton.setBackground(new Color(94, 155, 194));
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setFont(new Font("Comfortaa", Font.BOLD, 15));
        btnNewButton.setBounds(118, 107, 115, 23);
        contentPane.add(btnNewButton);

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                /*app.getUDPReceiver().setOuvert(false);
                TCPReceive.setOuvert(false);
                app.getcSystem().Deconnexion();;*/
                System.exit(0);
                //General.dispose();
            }
        });

        //Bouton Non
        JButton btnNewButtonNon = new JButton("Non");
        btnNewButtonNon.setBackground(new Color(94, 155, 194));
        btnNewButtonNon.setForeground(new Color(0, 0, 0));
        btnNewButtonNon.setFont(new Font("Comfortaa", Font.BOLD, 15));
        btnNewButtonNon.setBounds(300, 107, 115, 23);
        contentPane.add(btnNewButtonNon);

        btnNewButtonNon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });

        JLabel lblNewLabel = new JLabel("Êtes-vous certain de vouloir vous déconnecter?");
        lblNewLabel.setForeground(new Color(94, 155, 194));
        lblNewLabel.setFont(new Font("Comfortaa", Font.BOLD, 15));
        lblNewLabel.setBounds(80, 43, 377, 72);
        contentPane.add(lblNewLabel);

        setVisible(true);
    }


}