import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GhostyWindow extends JFrame {
	private JButton chooseAFile;
	
	public GhostyWindow ()
	{
		// Setting up the windows
	this.setTitle("Ghosty by Nekyle & Nek");
	this.setSize(400, 500);
	this.setLocationRelativeTo(null);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	
	this.build();
	
    
    JFileChooser fileChooser = new JFileChooser();
    
    this.add(fileChooser);
    
	
    // Pack all layouts and components
    this.pack();
    
	// To set at the end
		this.setVisible(true);
	}
	
	/**
	 * Build the window
	 */
	private void build()
	{
		// Added the main pannel
	    JPanel mainPanel = new JPanel();
	    this.setContentPane(mainPanel);
	    
	    // Added the main layout (Y_AXIS)
	    this.setLayout(
	    	new BoxLayout(
	    		this.getContentPane(),
	    		BoxLayout.Y_AXIS
	    	)
	    );
	    
	    // Adding the button
	    this.chooseAFile = new JButton("Choisissez un fichier");
	    this.chooseAFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Choissez un fichier");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getCurrentDirectory(): "
							+ fileChooser.getCurrentDirectory());
					System.out.println("getSelectedFile() : "
							+ fileChooser.getSelectedFile());
				} else {
					System.out.println("No Selection ");
				}
			}
		});
	}

}
