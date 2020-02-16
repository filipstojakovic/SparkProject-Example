import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainAppController
{

    public JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton myButton;
    private JLabel myLabel;



    public MainAppController()
    {
        myButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                myLabel.setText("Hello");
            }
        });
    }

}
