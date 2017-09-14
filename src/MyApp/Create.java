package MyApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;


import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import utils.Connector;


import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Button;
import java.awt.Label;

public class Create{

	private JFrame frame;
	private JTextField txtName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Create window = new Create();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Create() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		UtilDateModel model = new UtilDateModel();
		model.setDate(1990, 8, 24);
		model.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		 
		frame.add(datePicker);
		frame.setBounds(100, 100, 500, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtName = new JTextField();
		txtName.setText("");
		txtName.setBounds(268, 113, 114, 19);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		
		Button button = new Button("Submit");
		
		button.setBounds(198, 200, 86, 23);
		frame.getContentPane().add(button);
		
		
		Label label = new Label("Name");
		label.setBounds(134, 111, 68, 21);
		frame.getContentPane().add(label);
		
		Label label_1 = new Label("Sex");
		label_1.setBounds(134, 154, 68, 21);
		frame.getContentPane().add(label_1);
		
		final ButtonGroup bg = new ButtonGroup();
		final JRadioButton rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(268, 152,78, 23);
		frame.getContentPane().add(rdbtnMale);
		
		final JRadioButton rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(347, 152,959, 23);
		frame.getContentPane().add(rdbtnFemale);
		bg.add(rdbtnMale);
		bg.add(rdbtnFemale);
		
		
		button.addActionListener(new ActionListener() {
			Connector connection = new Connector();
			public void actionPerformed(ActionEvent arg0) {
				String display_message = "";
				display_message = "Welcome, Dear ";
				String sex = "";

				if(rdbtnFemale.isSelected()){
					display_message = "Ms/Mrs. ";
					sex = "Feale";
				}else {
					if(rdbtnMale.isSelected()){
						display_message += "Mr. ";
						sex = "Male";
					}
				}
				display_message += txtName.getText();
				
				boolean result = connection.insertRecord(txtName.getText(), sex);
				if(result==false){
					JOptionPane.showMessageDialog(frame, "Record already exists");
				}else{
					JOptionPane.showMessageDialog(frame, display_message);
				}
			}
		});
		Button recordSetButton = new Button("View Records");
		
		recordSetButton.setBounds(290, 200, 100, 23);
		frame.getContentPane().add(recordSetButton);
		recordSetButton.addActionListener(new ActionListener() {
			Connector connection = new Connector();
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ResultSet rs = connection.fetchRecords();
				// It creates and displays the table
				try{
					JTable table = new JTable(Connector.buildTableModel(rs));
					JOptionPane.showMessageDialog(null, new JScrollPane(table)); // or can you other swing component
				}catch(Exception e){
					System.out.println(e);
				}
			}
		});
       }
  }