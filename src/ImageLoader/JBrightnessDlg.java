package ImageLoader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JBrightnessDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JSlider slider;
	private JTextField textField;
	private MainFrame parent;
	private ImagePanel parentImagePanel;
	private BufferedImage originalImage = null;
	
	/**
	 * Create the dialog.
	 */
	public JBrightnessDlg(MainFrame frame) {
		
		super(frame, true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				if (originalImage==null){
					originalImage = parentImagePanel.getImage();
					slider.setValue(0);
					textField.setText(""+0);
				}
			}
			@Override
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});
		
		
		parent = frame;
		parentImagePanel = parent.getImagePanel();
		originalImage = parentImagePanel.getImage();
		
		
		
		setTitle("Brightness");
		setBounds(100, 100, 450, 155);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		slider = new JSlider();
		slider.setMajorTickSpacing(85);
		slider.setMinimum(-255);

		slider.setMaximum(255);
		slider.setValue(0);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		
		slider.setPaintLabels(true);
		
		slider.setBounds(23, 29, 294, 49);
		contentPanel.add(slider);
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				onSlide();
			}
		});
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(327, 29, 86, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onOK(e);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onCancel();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void onSlide() {
		textField.setText(""+slider.getValue());
		parentImagePanel.setImage(ImageUtil.brightnessV1(originalImage, slider.getValue()));
		
	}
	private void onOK(ActionEvent e){
		originalImage = null;
		dispose();
	}
	private void onCancel(){
		parentImagePanel.setImage(originalImage);
		originalImage = null;
		dispose();
	}	
}
