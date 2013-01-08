package views.swing.popup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class ErrorMessagePopup extends JDialog {

		private JPanel textPanel, buttonPanel;
		private JButton OKButton;
		private JLabel errorMessage; // TODO : Replace it by JTextArea
		
		public ErrorMessagePopup(JFrame frame, String error) {
				super(frame, "Warning!", true);
				this.errorMessage = new JLabel(error, SwingConstants.TRAILING);
				this.errorMessage.setForeground(Color.RED);
				this.setSize(300, 120);
				this.setLocationRelativeTo(null);
				this.setResizable(false);
				this.setBackground(Color.WHITE);
				this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				this.setLayout(new BorderLayout());
				initComponents();
		}

		private void initComponents() {
				textPanel = new JPanel();
				textPanel.setOpaque(true);
				textPanel.add(errorMessage, BorderLayout.CENTER);
				textPanel.setBackground(Color.WHITE);
				initButtonPanel();
				add(textPanel, BorderLayout.CENTER);
				add(buttonPanel, BorderLayout.SOUTH);
		}

		private void initButtonPanel() {
				buttonPanel = new JPanel();
				buttonPanel.setOpaque(true);
				buttonPanel.setBackground(Color.LIGHT_GRAY);
				OKButton = new JButton("OK");
				OKButton.setSize(110,30);
				OKButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								dispose();
						}
				});
				buttonPanel.add(OKButton, BorderLayout.CENTER);		
		}
		
		public void showErrorMessage() {
				this.setVisible(true);
		}
}