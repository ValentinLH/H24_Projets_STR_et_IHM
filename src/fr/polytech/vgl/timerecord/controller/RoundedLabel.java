package fr.polytech.vgl.timerecord.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.Timer;

import fr.polytech.vgl.model.Record;

public class RoundedLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	Record rec = new Record(LocalDateTime.now());
	
	public RoundedLabel() {
		rec.setRecord(LocalDateTime.now());
		setText("let's say : "+rec.getRecord().format(formatter));

		Timer t = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rec.setRecord(LocalDateTime.now());
				setText(LocalDateTime.now().format(formatter));
				setText("let's say : "+rec.getRecord().format(formatter));
			}
		});
		t.setRepeats(true);
		t.setCoalesce(true);
		t.setInitialDelay(0);
		t.start();
	}
}
