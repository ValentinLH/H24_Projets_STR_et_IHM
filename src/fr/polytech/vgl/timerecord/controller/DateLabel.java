package fr.polytech.vgl.timerecord.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * DateLabel is a custom Label showing date 
 * @author Touret Lino - L'Hermite Valentin
 *
 */
public class DateLabel extends JLabel {


	
	private static final long serialVersionUID = 1L;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public DateLabel() {
		
		setText(LocalDateTime.now().format(formatter));

		Timer t = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setText(LocalDateTime.now().format(formatter));
			}
		});
		t.setRepeats(true);
		t.setCoalesce(true);
		t.setInitialDelay(0);
		t.start();
	}
	
	public DateLabel(String format) {
		formatter = DateTimeFormatter.ofPattern(format);
		setText(LocalDateTime.now().format(formatter));
		
		Timer t = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setText(LocalDateTime.now().format(formatter));
			}
		});
		t.setRepeats(true);
		t.setCoalesce(true);
		t.setInitialDelay(0);
		t.start();
	}
	
	public DateLabel(boolean MinutesHour) {
		formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		setText(LocalDateTime.now().format(formatter));

		Timer t = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setText(LocalDateTime.now().format(formatter));
			}
		});
		t.setRepeats(true);
		t.setCoalesce(true);
		t.setInitialDelay(0);
		t.start();
	}
	
	
}
