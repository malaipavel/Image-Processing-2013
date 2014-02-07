package ImageLoader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class JAboutDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblAplicatieDeProcesare;
	private JLabel lblInfoAnul;
	private JLabel lblNewLabel;
	private JLabel lblVisitHttpscodegooglecompimageloaderinfo;


	/**
	 * Create the dialog.
	 */
	public JAboutDlg() {
		setModal(true);
		setTitle("About");
		setBounds(100, 100, 361, 164);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblAplicatieDeProcesare = new JLabel("Image processing application");
		lblAplicatieDeProcesare.setHorizontalAlignment(SwingConstants.CENTER);
		lblAplicatieDeProcesare.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAplicatieDeProcesare.setBounds(60, 11, 224, 22);
		contentPanel.add(lblAplicatieDeProcesare);
		
		lblInfoAnul = new JLabel("<html>\r\nCopyright Info III, 2013 <br>\r\nEmail sibiuu@yahoo.com\r\n</html>");
		lblInfoAnul.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoAnul.setBounds(131, 36, 153, 28);
		contentPanel.add(lblInfoAnul);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(JAboutDlg.class.getResource("/ImageLoader/icons24x24/home_green_24.png")));
		lblNewLabel.setBounds(70, 36, 34, 28);
		contentPanel.add(lblNewLabel);
		
		lblVisitHttpscodegooglecompimageloaderinfo = new JLabel("Visit https://code.google.com/p/image-loader-info-2013 ");
		lblVisitHttpscodegooglecompimageloaderinfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblVisitHttpscodegooglecompimageloaderinfo.setBounds(30, 75, 284, 14);
		contentPanel.add(lblVisitHttpscodegooglecompimageloaderinfo);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
