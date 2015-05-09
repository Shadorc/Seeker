package me.shadorc.pathfindor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		GridPanel grid = new GridPanel();
		OptionsPanel options = new OptionsPanel(grid);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(grid, BorderLayout.CENTER);
		panel.add(options, BorderLayout.EAST);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Frame(panel);
			}
		});
	}

	Frame(JPanel panel) {
		super("Seeker");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(panel);
		this.pack();
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}