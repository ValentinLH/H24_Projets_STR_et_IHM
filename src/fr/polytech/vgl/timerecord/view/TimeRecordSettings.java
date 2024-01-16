package fr.polytech.vgl.timerecord.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import fr.polytech.vgl.timerecord.view.*;
/*
public class TimeRecordSettings extends JPanel {

	GridLayout grid;

	public static final int weight = 9;
	public static final int height = 4;
	public static JButton inputButton = new JButton("Send");
    public static JTextArea editTextArea = new JTextArea("Type Here!");
	
	public TimeRecordSettings() {	
		grid = new GridLayout(weight, height,10,10);
		setLayout(grid);


		for (int i = 0; i < weight; i++) {
			for (int j = 0; j < height; j++) {
				if (i == 4 && j == 1)
				{
					add(editTextArea);
				}
				else if (i == 4 && j == 2)
				{
					add(inputButton);
				}
				else
				{
					add(new TileView());	
				}
			}
		}

	}

	class TileView extends JLabel {

		TileView() {
			setPreferredSize(new Dimension(100, 100));
			setOpaque(true);
			
		}
	}
}
*/