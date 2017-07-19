package me.shadorc.seeker;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	public Frame() {
		super("Seeker");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridPanel gridPanel = new GridPanel(11, 11);
		OptionsPanel options = new OptionsPanel(gridPanel);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(gridPanel, BorderLayout.CENTER);
		panel.add(options, BorderLayout.EAST);

		this.setContentPane(panel);
		this.pack();
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}