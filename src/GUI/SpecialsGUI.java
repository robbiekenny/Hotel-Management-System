package GUI;
/**
 * @author Robert Kenny
 * @author Derek Mulhern
 */
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Color;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;

import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import Database.Queries;
import Database.SpecialsOperations;
import Model.Special;

public class SpecialsGUI extends JPanel implements ActionListener{
	
	private JButton addSpecials,back;
	private Color color = new Color(227,99,26);
	private Font font;
	private JLabel addSomething,price;
	private Double priceField = 0.0;
	private int bookingid;
	private DefaultTableModel model;
	private ArrayList<Object[]> specialList;
	private Object[][] array2d;
	private Object[] columnNames = { "Name", "Cost"};
	private JTable specialsList;
	private SpecialsOperations s = new SpecialsOperations();
	private String usersID;
	private JPanel specials;
	private int[] selection;
	public SpecialsGUI(String usersID,int bookingid){
		setLayout(null);	
		 specials = new JPanel();
		specials.setBounds(125,210, 743, 288);
		add(specials);
		specials.setLayout(null);
		font = new Font("Veranda", font.PLAIN, 18);
		this.bookingid = bookingid;
		this.usersID = usersID;
		
		addSomething = new JLabel("Add something extra to your stay with us,"
	 		      + " by selecting from our range of available specials");
	  addSomething.setFont(font);
	 addSomething.setBounds(10, 0, 760, 36);
	 specials.add(addSomething);
		
	 	JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(200, 50, 350, 120);
		specials.add(scrollPane);
		fillTable();
		model = new DefaultTableModel(array2d, columnNames){
			 @Override //used to make all cells in jtable uneditable
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		};

		specialsList = new JTable(model);
		specialsList.getTableHeader().setBackground(color);
		specialsList.setBorder(null);
		specialsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		specialsList.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(specialsList);
		specialsList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				priceField = 0.0;
				selection = specialsList.getSelectedRows(); //used to get the rows the user has selected
				
				for (int i = 0; i < selection.length; i++) {
					priceField = priceField + Double.parseDouble(specialsList.getValueAt(selection[i], 1).toString());
					price.setText("Price: €" + priceField);
				}
			}
		});
		 
		 price = new JLabel("Price: €" + priceField);
		 price.setFont(font);
		price.setBounds(317, 190, 140, 14);
		specials.add(price);
		
		 addSpecials = new JButton("Add Specials");
		 addSpecials.setFont(font);
		addSpecials.setBackground(color);
		addSpecials.addActionListener(this);
		addSpecials.setBounds(206, 230, 150, 23);
		specials.add(addSpecials);
		
		back = new JButton("Back");
		 back.setFont(font);
		back.setBackground(color);
		back.addActionListener(this);
		back.setBounds(386, 230, 150, 23);
		specials.add(back);
	}
	
	private void fillTable() {
		// gets the specials in the system
			SpecialsOperations s = new SpecialsOperations();
			specialList = new ArrayList<Object[]>(s.getSpecials());
			array2d = specialList.toArray(new Object[specialList.size()][]);
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addSpecials){
			if(selection.length == 0){
				JOptionPane.showMessageDialog(null, "Please select a special","Select a special",
						JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				String[] names = new String[selection.length]; //sends the name of the specials the user has
																//chosen to a database query
				for (int i = 0; i < selection.length; i++) {
					names[i] = specialsList.getValueAt(selection[i], 0).toString();
				}
				
				s.addSpecials(names,bookingid,priceField);
				ManageBookingGUI a = new ManageBookingGUI(usersID);
				specials.setVisible(false);
				a.setVisible(true);
				add(a);
			}
		}
		else
		{
			ManageBookingGUI a = new ManageBookingGUI(usersID);
			specials.setVisible(false);
			a.setVisible(true);
			add(a);
		}
		
	}

}
