import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;






public class Employee {

	private JFrame frame;
	private JTextField jtxtEmployeeID;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel lblNiNumber;
	private JTextField jtxtNINumber;
	private JLabel lblFirstname;
	private JTextField jtxtFirstname;
	private JLabel lblSurname;
	private JTextField jtxtSurname;
	private JLabel lblGender;
	private JTextField jtxtGender;
	private JLabel lblDob;
	private JTextField jtxtDOB;
	private JLabel lblAge;
	private JTextField jtxtAge;
	private JLabel lblSalray;
	private JTextField jtxtSalary;
	private JButton btnPrint;
	private JButton btnReset;
	private JButton btnExit;
	private JLabel lblNewLabel;
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	DefaultTableModel model = new DefaultTableModel();
	private JLabel lblNewLabel_1;
	
	/**
	 * Launch the application.
	 */
	public void updateTable()
	{
		conn = EmployeeData.ConnectDB();
		
		if(conn != null) {
			String sql = "Select EmpID, NINumber, Firstname, Surname, Gender, DOB, Age, Salary";
		
		
		try
		{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			Object[] columnData = new Object[8];
			
			while(rs.next()) {
				columnData [0] = rs.getString("EmpID");
				columnData [1] = rs.getString("NINumber");
				columnData [2] = rs.getString("Firstname");
				columnData [3] = rs.getString("Surname");
				columnData [4] = rs.getString("Gender");
				columnData [5] = rs.getString("DOB");
				columnData [6] = rs.getString("Age");
				columnData [7] = rs.getString("Salary");
				
				model.addRow(columnData);
			}
		}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	

	static class CreateLoginForm extends JFrame implements ActionListener
	{
	    // create username, password, and submit fields
	    JButton b1;
	    JPanel newPanel;
	    JLabel userLabel, passLabel;
	    final JTextField  textField1, textField2;

	    //calling constructor
	    CreateLoginForm()
	    {

	        //create label for username
	        userLabel = new JLabel();
	        userLabel.setText("Username");      //set label value for textField1

	        //create text field to get username from the user
	        textField1 = new JTextField(15);    //set length of the text

	        //create label for password
	        passLabel = new JLabel();
	        passLabel.setText("Password");      //set label value for textField2

	        //create text field to get password from the user
	        textField2 = new JPasswordField(15);    //set length for the password

	        //create submit button
	        b1 = new JButton("SUBMIT"); //set label to button

	        //create panel to put form elements
	        newPanel = new JPanel(new GridLayout(3, 1));
	        newPanel.add(userLabel);    //set username label to panel
	        newPanel.add(textField1);   //set text field to panel
	        newPanel.add(passLabel);    //set password label to panel
	        newPanel.add(textField2);   //set text field to panel
	        newPanel.add(b1);           //set button to panel

	        //set border to panel
	        add(newPanel, BorderLayout.CENTER);

	        //perform action on button click
	        b1.addActionListener(this);     //add action listener to button
	        setTitle("LOGIN FORM");         //set title to the login form
	    }

	    // button checks if username and password are correct
	    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter
	    {
	        String userValue = textField1.getText();        //get user entered username from the textField1
	        String passValue = textField2.getText();        //get user entered password from the textField2

	        //check if username and passwords match
	        if (userValue.equals("username") && passValue.equals("password")) {  
	            // if it matches it sends the user to the interface
	           
	        	EventQueue.invokeLater(new Runnable() {
	    			public void run() {
	    				try {		
	    					Employee window = new Employee();
	    					window.frame.setVisible(true);
	    				} catch (Exception e) {
	    					e.printStackTrace();
	    				}
	    			}
	    		});
	        	
	        	
	        	
	        }
	        else{
	            //show error message
	            System.out.println("Please enter valid username and password");
	        }
	    }
	}
	
	
	
	public static void main(String[] args) {
		Employee.CreateLoginForm form = new Employee.CreateLoginForm();
		form.setSize(900,200);  //set size of the frame
        form.setVisible(true);  //make form visible to the user
		
        
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {		
					Employee window = new Employee();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
				
	}

	/**
	 * Create the application.
	 */
	public Employee() {
		initialize();
		
		conn = EmployeeData.ConnectDB();
		Object col[] = {"EmpID", "NINumber", "Firstname", "Surname", "Gender", "DOB", "Age", "Salary"};
		model.setColumnIdentifiers(col);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1400, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		jtxtEmployeeID = new JTextField();
		jtxtEmployeeID.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtEmployeeID.setBounds(272, 59, 209, 28);
		frame.getContentPane().add(jtxtEmployeeID);
		jtxtEmployeeID.setColumns(10);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql = "INSERT INTO employee(EmpID, NINumber, Firstname, Surname, Gender, DOB, Age, Salary)VALUES(?,?,?,?,?,?,?,?)";
				
				try
				{
					pst = conn.prepareStatement(sql);
					pst.setString(1,  jtxtEmployeeID.getText());
					pst.setString(2,  jtxtNINumber.getText());
					pst.setString(3,  jtxtFirstname.getText());
					pst.setString(4,  jtxtSurname.getText());
					pst.setString(5,  jtxtGender.getText());
					pst.setString(6,  jtxtDOB.getText());
					pst.setString(7,  jtxtAge.getText());
					pst.setString(8,  jtxtSalary.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
				}
				catch(Exception ev)
				{
					JOptionPane.showMessageDialog(null, "System Update Completed");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {
						jtxtEmployeeID.getText(),
						jtxtNINumber.getText(),
						jtxtFirstname.getText(),
						jtxtSurname.getText(),
						jtxtGender.getText(),
						jtxtDOB.getText(),
						jtxtAge.getText(),
						jtxtSalary.getText()
				});
				if (table.getSelectedRow() == -1) {
					if (table.getRowCount() == 0) {
						JOptionPane.showMessageDialog(null, "Membership Update Confirmed", "Employee Database System", JOptionPane.OK_OPTION);
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(73, 435, 214, 47);
		frame.getContentPane().add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(635, 79, 582, 316);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EmpId", "NINumber", "Firstname", "Surname", "Gender", "DOB", "Age", "Salary"
			}
		));
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		lblNiNumber = new JLabel("NI Number");
		lblNiNumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNiNumber.setBounds(94, 98, 164, 14);
		frame.getContentPane().add(lblNiNumber);
		
		jtxtNINumber = new JTextField();
		jtxtNINumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtNINumber.setColumns(10);
		jtxtNINumber.setBounds(272, 91, 209, 28);
		frame.getContentPane().add(jtxtNINumber);
		
		lblFirstname = new JLabel("Firstname");
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFirstname.setBounds(94, 130, 164, 14);
		frame.getContentPane().add(lblFirstname);
		
		jtxtFirstname = new JTextField();
		jtxtFirstname.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtFirstname.setColumns(10);
		jtxtFirstname.setBounds(272, 123, 209, 28);
		frame.getContentPane().add(jtxtFirstname);
		
		lblSurname = new JLabel("Surname");
		lblSurname.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSurname.setBounds(94, 162, 164, 14);
		frame.getContentPane().add(lblSurname);
		
		jtxtSurname = new JTextField();
		jtxtSurname.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtSurname.setColumns(10);
		jtxtSurname.setBounds(272, 155, 209, 28);
		frame.getContentPane().add(jtxtSurname);
		
		lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblGender.setBounds(94, 194, 164, 14);
		frame.getContentPane().add(lblGender);
		
		jtxtGender = new JTextField();
		jtxtGender.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtGender.setColumns(10);
		jtxtGender.setBounds(272, 187, 209, 28);
		frame.getContentPane().add(jtxtGender);
		
		lblDob = new JLabel("DOB");
		lblDob.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDob.setBounds(94, 226, 164, 14);
		frame.getContentPane().add(lblDob);
		
		jtxtDOB = new JTextField();
		jtxtDOB.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtDOB.setColumns(10);
		jtxtDOB.setBounds(272, 219, 209, 28);
		frame.getContentPane().add(jtxtDOB);
		
		lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAge.setBounds(94, 265, 164, 14);
		frame.getContentPane().add(lblAge);
		
		jtxtAge = new JTextField();
		jtxtAge.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtAge.setColumns(10);
		jtxtAge.setBounds(272, 258, 209, 28);
		frame.getContentPane().add(jtxtAge);
		
		lblSalray = new JLabel("Salary");
		lblSalray.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSalray.setBounds(94, 297, 164, 14);
		frame.getContentPane().add(lblSalray);
		
		jtxtSalary = new JTextField();
		jtxtSalary.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtSalary.setColumns(10);
		jtxtSalary.setBounds(272, 290, 209, 28);
		frame.getContentPane().add(jtxtSalary);
		
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageFormat header = new MessageFormat("Printing in Progress");
				MessageFormat footer = new MessageFormat("Page  {0, number, integer}");
				
				try 
				{
					table.print();
				}
				catch(java.awt.print.PrinterException ev)
				{
					System.err.format("No Printer Found", ev.getMessage());
				}
				
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnPrint.setBounds(325, 435, 214, 47);
		frame.getContentPane().add(btnPrint);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtxtEmployeeID.setText(null);
				jtxtNINumber.setText(null);
				jtxtFirstname.setText(null);
				jtxtSurname.setText(null);
				jtxtGender.setText(null);
				jtxtDOB.setText(null);
				jtxtAge.setText(null);
				jtxtSalary.setText(null);
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReset.setBounds(591, 435, 214, 47);
		frame.getContentPane().add(btnReset);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit", "Employee Database System", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
				
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnExit.setBounds(869, 435, 214, 47);
		frame.getContentPane().add(btnExit);
		
		lblNewLabel = new JLabel("Employee ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(94, 69, 164, 14);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Employee Database Management System");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1.setBounds(162, 11, 748, 31);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
