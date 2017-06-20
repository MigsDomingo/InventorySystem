import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;	
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUISample
{
	public GUISample()
	{
		JFrame guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Example GUI");
        guiFrame.setSize(300,250);
		
		 //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);
		
		JPanel panel_1 = new JPanel();
		JTextField textField1 = new JTextField();
		panel_1.add(textField1);
		guiFrame.add(panel_1, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		JButton button1 = new JButton();
		panel_2.add(button1);
		guiFrame.add(panel_2, BorderLayout.SOUTH);
		button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
               System.out.println("Scanned: " + textField1.getText());
            }
        });
		
		guiFrame.setVisible(true);
	}
}