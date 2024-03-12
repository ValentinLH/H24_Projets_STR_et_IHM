package fr.polytech.vgl.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel;

public class TestLAF {
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
                    UIManager.setLookAndFeel(new SubstanceGeminiLookAndFeel());
				} catch(Exception e) {
					e.printStackTrace();
				}
				JFrameTest t = new JFrameTest();
				t.setVisible(true);
			}
		});
	}
}
