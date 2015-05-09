package me.shadorc.pathfindor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;


public class OptionsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static JLabel info = new JLabel("", JLabel.CENTER);
	private static JSlider waitingTime, numWall;
	private static JCheckBox diagonal;

	private JButton clear, start, generate, genWall;
	private JTextField columns, rows;

	private GridPanel grid;

	OptionsPanel(GridPanel grid) {
		super(new GridLayout(10, 0, 0, 10));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.grid = grid;

		JPanel panelCol = new JPanel(new BorderLayout());
		JLabel labelCol = new JLabel("Columns (1-100) : ");
		panelCol.add(labelCol, BorderLayout.WEST);

		columns = new JTextField("11");
		panelCol.add(columns, BorderLayout.CENTER);
		this.add(panelCol);

		JPanel panelRow = new JPanel(new BorderLayout());
		JLabel labelRow = new JLabel("Rows (1-100) : ");
		panelRow.add(labelRow, BorderLayout.WEST);

		rows = new JTextField("11");
		panelRow.add(rows, BorderLayout.CENTER);
		this.add(panelRow);

		JPanel wallPane = new JPanel(new GridLayout(2, 0));
		JLabel wallInfo = new JLabel("Obstacles (%) :", JLabel.CENTER);
		wallPane.add(wallInfo);

		numWall = new JSlider(JSlider.HORIZONTAL, 0, 75, 25);
		numWall.setMajorTickSpacing(25);
		numWall.setPaintLabels(true);
		wallPane.add(numWall);
		this.add(wallPane);

		JPanel waitPanel = new JPanel(new GridLayout(2, 0));
		JLabel waitInfo = new JLabel("Time between each actions (ms) :", JLabel.CENTER);
		waitPanel.add(waitInfo);

		waitingTime = new JSlider(JSlider.HORIZONTAL, 0, 1000, 100);
		waitingTime.setMajorTickSpacing(250);
		waitingTime.setPaintLabels(true);
		waitPanel.add(waitingTime);
		this.add(waitPanel);

		genWall = new JButton("Generate obstacles");
		genWall.setBackground(Color.LIGHT_GRAY);
		genWall.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		genWall.addActionListener(this);
		this.add(genWall);

		generate = new JButton("New Grid");
		generate.setBackground(Color.LIGHT_GRAY);
		generate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		generate.addActionListener(this);
		this.add(generate);

		clear = new JButton("Clear");
		clear.setBackground(Color.LIGHT_GRAY);
		clear.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		clear.addActionListener(this);
		this.add(clear);

		start = new JButton("Start");
		start.setBackground(Color.LIGHT_GRAY);
		start.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		start.addActionListener(this);
		this.add(start);

		diagonal = new JCheckBox("Diagonal movements");
		this.add(diagonal);

		this.add(info);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton bu = (JButton) event.getSource();

		if(bu == start) {
			grid.start();

		} else if(bu == clear) {
			grid.init();

		} else if(bu == generate) {
			int width, height;
			try {
				width = Integer.parseInt(columns.getText());
				height = Integer.parseInt(rows.getText());
				if(width > 100 || width < 1 || height > 100 || height < 1) {
					setText("The size should be between 1 and 100", true);
				} else {
					grid.setGridSize(width, height);
				}
			} catch (NumberFormatException e) {
				setText("Invalid number", true);
			}

		} else if(bu == genWall) {
			grid.generateWall();
		}

	}

	public static int getWaitingTime() {
		return waitingTime.getValue();
	}

	public static int getWalls() {
		return numWall.getValue();
	}

	public static boolean chechDiagonal() {
		return diagonal.isSelected();
	}

	public static void setText(String text, boolean error) {
		info.setForeground(error ? Color.RED : Color.BLACK);
		info.setText(text);	
	}
}
