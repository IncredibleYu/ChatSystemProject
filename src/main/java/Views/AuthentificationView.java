package Views;

import javax.swing.*;
import java.awt.*;

public class AuthentificationView
{
    JFrame frame;
    JPanel panel;
    JTextField textField;
    JButton button;
    Container contentPane;

    public AuthentificationView()
    {
        this.launchFrame();
    }
    void launchFrame()
    {
        /* initialization */
        frame = new JFrame("ChatYourFriends");
        frame.setSize(new Dimension(120, 40));

        panel = new JPanel();
        textField = new JTextField("Veuillez saisir votre pseudonyme");
        button = new JButton("OK");
        contentPane = frame.getContentPane();

        //add components to panelFlowLayout by default
        panel.add(textField);
        panel.add(button);

        /* add components to contentPane BorderLayout */
        contentPane.add(panel, BorderLayout.CENTER);
        frame.pack();  //Size of frame based on components
        frame.setVisible(true);
    }
}
