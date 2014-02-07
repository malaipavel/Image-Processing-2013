package ImageLoader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GaussDialog extends JDialog {
	private static final long serialVersionUID = -929443488604548288L;

	private final JPanel contentPanel = new JPanel();
	private MainFrame parent;
	private ImagePanel parentImagePanel;
	private ImagePanel imagePanel;
	private JTextField mtextField;
	JSlider slider;
	JLabel mlblKernel;
	JComboBox comboBox;
	private JLabel mlabel;
	private JButton mbtnLoadKernel;
	final private JFileChooser dialog = new JFileChooser("res/kernels");

	public GaussDialog(MainFrame parent) {
				
		this.parent = parent;
		this.parentImagePanel = this.parent.getImagePanel();

		setBounds(100, 100, 676, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblThreshold = new JLabel("Gauss Blur");
			lblThreshold.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblThreshold, BorderLayout.NORTH);
		}
		{
			imagePanel = new ImagePanel();
			contentPanel.add(imagePanel, BorderLayout.CENTER);
			imagePanel.setLayout(null);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.EAST);
			{
				mlabel = new JLabel("");
			}
			{
				mbtnLoadKernel = new JButton("Load Kernel");
				mbtnLoadKernel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onLoadKernel();
					}
				});
				mbtnLoadKernel.setHorizontalAlignment(SwingConstants.TRAILING);
			}
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel.add(mlabel);
			
						JButton btnSave = new JButton("Save Kernel");
						btnSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								onSaveKernel();
							}
						});
						panel.add(btnSave);
			panel.add(mbtnLoadKernel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						onOK();
					}
				});
				{
					slider = new JSlider();
					slider.setValue(1);
					slider.setMinimum(1);
					slider.setMinorTickSpacing(1);
					slider.setSnapToTicks(true);
					slider.setPaintLabels(true);
					slider.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent arg0) {
							onSlide();
						}
					});
					{
						mlblKernel = new JLabel("Kernel");
						buttonPane.add(mlblKernel);
					}

					comboBox = new JComboBox();
					comboBox.setModel(new DefaultComboBoxModel(new String[] {"3", "5", "7", "9", "11", "13", "15", "17", "19", "21", "23", "25", "27", "29", "31", "33", "35", "37", "39", "41"}));
					buttonPane.add(comboBox);

					JLabel label = new JLabel("         ");
					buttonPane.add(label);

					JLabel lblWeight = new JLabel("Weight");
					buttonPane.add(lblWeight);
					buttonPane.add(slider);

					mtextField = new JTextField();
					buttonPane.add(mtextField);
					mtextField.setColumns(10);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		mtextField.setText(1+"");

		if (parentImagePanel != null) {

			BufferedImage temp = null;

			temp = ImageUtil.convolve(parentImagePanel.getImage(),slider.getValue(), Integer.parseInt(comboBox.getSelectedItem().toString()));

			if(temp == null){
				System.out.print("image is null");
			}
			else{
				imagePanel.setImage(temp);
				pack();
			}
		}
	}

	protected void onOK(){
		parent.getImagePanel().setImage(imagePanel.getImage());
		dispose();
	}
	private void onSlide(){
		float value = slider.getValue();
		int size = Integer.parseInt(comboBox.getSelectedItem().toString());
		mtextField.setText(value+"");
		imagePanel.setImage(ImageUtil.convolve(parentImagePanel.getImage(), value, size));
	}

	private void onSaveKernel(){
		String filename = JOptionPane.showInputDialog("Kernel name:");
		int size = Integer.parseInt(comboBox.getSelectedItem().toString());
		int weight = slider.getValue();
		float[] kernel = ImageUtil.calculateKernel(size, weight);


		try {
			File file = new File("res\\kernels\\"+filename+".txt");
			file.getParentFile().mkdirs();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(weight+" ");
			bw.write(size+" ");
			
			for(int i = 0; i<kernel.length; i++)
				bw.write(kernel[i]+" ");

			bw.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private void onLoadKernel(){
		
		File f = null;
		int weight, size = 0;
		ArrayList<Float> kernel = new ArrayList<Float>();

		if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			f = dialog.getSelectedFile();
		}

		if(f != null && f.getName().toString().endsWith(".txt")){
			String filename = f.getName();
			Scanner sc = null;
			try {
				sc = new Scanner(new File("res/kernels/"+filename));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			weight = sc.nextInt();
			size = sc.nextInt();
			
			while(sc.hasNextFloat()){
				kernel.add(sc.nextFloat());
			}
		}
		
		// from list to []
		float [] temp = new float[kernel.size()];
		for(int i = 0; i<kernel.size(); i++)
			temp[i] = kernel.get(i);
		
		BufferedImage dest = null;
		
		Kernel k = new Kernel(size, size, temp);
		BufferedImageOp op = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP,null);
		dest = op.filter(imagePanel.getImage(), dest);
		
		imagePanel.setImage(dest);
	}
}
