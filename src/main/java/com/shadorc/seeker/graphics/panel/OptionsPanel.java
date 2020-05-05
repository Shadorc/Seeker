package com.shadorc.seeker.graphics.panel;

import com.shadorc.seeker.graphics.SeekerFrame;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;


public class OptionsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int MIN_COLUMNS = 1;
    private static final int MAX_COLUMNS = 150;
    private static final int MIN_ROWS = 1;
    private static final int MAX_ROWS = 150;

    private final SeekerFrame seekerFrame;
    private final JLabel infoLabel;
    private final JSlider waitingTimeSlider;
    private final JSlider wallRatioSlider;
    private final JCheckBox checkDiagonalsCheckBox;
    private final JTextField columnsField;
    private final JTextField rowsField;

    public OptionsPanel(SeekerFrame seekerFrame) {
        super(new GridLayout(10, 0, 0, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.seekerFrame = seekerFrame;

        final JPanel columnPanel = new JPanel(new BorderLayout());
        final JLabel columnLabel = new JLabel("Columns (" + MIN_COLUMNS + "-" + MAX_COLUMNS + ") : ");
        columnPanel.add(columnLabel, BorderLayout.LINE_START);

        this.columnsField = new JTextField(Integer.toString(SeekerFrame.DEFAULT_COLUMNS));
        columnPanel.add(this.columnsField, BorderLayout.CENTER);
        this.add(columnPanel);

        final JPanel rowPanel = new JPanel(new BorderLayout());
        final JLabel rowLabel = new JLabel("Rows (" + MIN_ROWS + "-" + MAX_ROWS + ") : ");
        rowPanel.add(rowLabel, BorderLayout.LINE_START);

        this.rowsField = new JTextField(Integer.toString(SeekerFrame.DEFAULT_ROWS));
        rowPanel.add(this.rowsField, BorderLayout.CENTER);
        this.add(rowPanel);

        final JPanel wallPanel = new JPanel(new GridLayout(2, 0));
        final JLabel wallLabel = new JLabel("Walls (%) :", SwingConstants.CENTER);
        wallPanel.add(wallLabel);

        this.wallRatioSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 75, 25);
        this.wallRatioSlider.setMajorTickSpacing(25);
        this.wallRatioSlider.setPaintLabels(true);
        wallPanel.add(this.wallRatioSlider);
        this.add(wallPanel);

        final JPanel waitPanel = new JPanel(new GridLayout(2, 0));
        final JLabel waitLabel = new JLabel("Time between each actions (ms) :", SwingConstants.CENTER);
        waitPanel.add(waitLabel);

        this.waitingTimeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 1000, 100);
        this.waitingTimeSlider.setMajorTickSpacing(250);
        this.waitingTimeSlider.setPaintLabels(true);
        waitPanel.add(this.waitingTimeSlider);
        this.add(waitPanel);

        final JButton wallsButton = new JButton("Generate walls");
        wallsButton.setBackground(Color.LIGHT_GRAY);
        wallsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        wallsButton.addActionListener((event) -> this.seekerFrame.getGridPanel().generateRandomWalls());
        this.add(wallsButton);

        final JButton gridButton = new JButton("Generate new grid");
        gridButton.setBackground(Color.LIGHT_GRAY);
        gridButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gridButton.addActionListener((event) -> this.setGridSize());
        this.add(gridButton);

        final JButton clearButton = new JButton("Clear");
        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        clearButton.addActionListener((event) -> this.seekerFrame.getGridPanel().generateGrid());
        this.add(clearButton);

        final JButton startButton = new JButton("Start");
        startButton.setBackground(Color.LIGHT_GRAY);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        startButton.addActionListener((event) -> this.seekerFrame.getGridPanel().start());
        this.add(startButton);

        this.checkDiagonalsCheckBox = new JCheckBox("Diagonal movements");
        this.add(this.checkDiagonalsCheckBox);

        this.infoLabel = new JLabel("", SwingConstants.CENTER);
        this.add(this.infoLabel);
    }

    private void setGridSize() {
        try {
            final int columns = Integer.parseInt(this.columnsField.getText());
            final int rows = Integer.parseInt(this.rowsField.getText());
            if (columns < MIN_COLUMNS) {
                this.displayError("Columns cannot be smaller than " + MIN_COLUMNS);
            } else if (columns > MAX_COLUMNS) {
                this.displayError("Columns cannot be greater than " + MAX_COLUMNS);
            } else if (rows < MIN_ROWS) {
                this.displayError("Rows cannot be smaller than " + MIN_ROWS);
            } else if (rows > MAX_ROWS) {
                this.displayError("Rows cannot be greater than " + MAX_ROWS);
            } else {
                this.seekerFrame.getGridPanel().setGridSize(columns, rows);
            }
        } catch (final NumberFormatException ignored) {
            this.displayError("Invalid number");
        }
    }

    public int getWaitingTime() {
        return this.waitingTimeSlider.getValue();
    }

    public int getWallRatio() {
        return this.wallRatioSlider.getValue();
    }

    public boolean checkDiagonals() {
        return this.checkDiagonalsCheckBox.isSelected();
    }

    public void displayText(String text) {
        this.infoLabel.setForeground(Color.BLACK);
        this.infoLabel.setText(text);
    }

    public void displayError(String error) {
        this.infoLabel.setForeground(Color.RED);
        this.infoLabel.setText(error);
    }
}
