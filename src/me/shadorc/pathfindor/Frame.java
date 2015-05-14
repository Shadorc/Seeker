package me.shadorc.pathfindor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Frame();
			}
		});
	}

	Frame() {
		super("Seeker");

		GridPanel gridPanel = new GridPanel();
		OptionsPanel options = new OptionsPanel(gridPanel);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(gridPanel, BorderLayout.CENTER);
		panel.add(options, BorderLayout.EAST);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(panel);
		this.pack();
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}