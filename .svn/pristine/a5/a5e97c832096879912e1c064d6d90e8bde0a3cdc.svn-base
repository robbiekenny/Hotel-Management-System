package GUI;

import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JCalendar;

public class CalendarGUI extends JPanel {
	
	JLabel welcome,infoText;
	JPanel container;
	Font font = new Font("Verdana", Font.ITALIC, 20);
	
	public CalendarGUI() {
		container = new JPanel(null);
		container.setBounds(225, 200,800, 300);
		add(container);
		this.setLayout(new BorderLayout());
		
		JLabel calendarHolderE = new JLabel("                                           ");
		add(calendarHolderE,BorderLayout.EAST);
		JLabel calendarHolderW = new JLabel("                                           ");
		add(calendarHolderW,BorderLayout.WEST);	
		JCalendar bigCalendar = new JCalendar();
		bigCalendar.setSize(500,300);
			
		bigCalendar.getDayChooser();
		container.add(bigCalendar,BorderLayout.CENTER);
		
		infoText = new JLabel("                             Click a date to see our special offers");
		add(infoText,BorderLayout.SOUTH);
		infoText.setFont(font);
	}
}
