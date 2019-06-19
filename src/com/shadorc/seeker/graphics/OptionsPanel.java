package com.shadorc.seeker.graphics;

import javax.swing.*;
import java.awt.*;


public class OptionsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final JLabel infoLabel = new JLabel("", SwingConstants.CENTER);
    private static JSlider waitingTimeSlider;
    private static JSlider wallRatioSlider;
    private static JCheckBox diagonalCheckBox;

    private final JTextField columnsField;
    private final JTextField rowsField;

    private final GridPanel gridPanel;

    public OptionsPanel(GridPanel gridPanel) {
        super(new GridLayout(10, 0, 0, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.gridPanel = gridPanel;

        final JPanel columnPanel = new JPanel(new BorderLayout());
        final JLabel columnLabel = new JLabel("Columns (1-100) : ");
        columnPanel.add(columnLabel, BorderLayout.LINE_START);

        this.columnsField = new JTextField(Integer.toString(SeekerFrame.DEFAULT_COLUMNS));
        columnPanel.add(this.columnsField, BorderLayout.CENTER);
        this.add(columnPanel);

        final JPanel rowPanel = new JPanel(new BorderLayout());
        final JLabel rowLabel = new JLabel("Rows (1-100) : ");
        rowPanel.add(rowLabel, BorderLayout.LINE_START);

        this.rowsField = new JTextField(Integer.toString(SeekerFrame.DEFAULT_ROWS));
        rowPanel.add(this.rowsField, BorderLayout.CENTER);
        this.add(rowPanel);

        final JPanel wallPanel = new JPanel(new GridLayout(2, 0));
        final JLabel wallLabel = new JLabel("Walls (%) :", SwingConstants.CENTER);
        wallPanel.add(wallLabel);

        wallRatioSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 75, 25);
        wallRatioSlider.setMajorTickSpacing(25);
        wallRatioSlider.setPaintLabels(true);
        wallPanel.add(wallRatioSlider);
        this.add(wallPanel);

        final JPanel waitPanel = new JPanel(new GridLayout(2, 0));
        final JLabel waitLabel = new JLabel("Time between each actions (ms) :", SwingConstants.CENTER);
        waitPanel.add(waitLabel);

        waitingTimeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 1000, 100);
        waitingTimeSlider.setMajorTickSpacing(250);
        waitingTimeSlider.setPaintLabels(true);
        waitPanel.add(waitingTimeSlider);
        this.add(waitPanel);

        final JButton wallsButton = new JButton("Generate walls");
        wallsButton.setBackground(Color.LIGHT_GRAY);
        wallsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        wallsButton.addActionListener((event) -> this.gridPanel.generateRandomWalls());
        this.add(wallsButton);

        final JButton gridButton = new JButton("New Grid");
        gridButton.setBackground(Color.LIGHT_GRAY);
        gridButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gridButton.addActionListener((event) -> this.setGridSize());
        this.add(gridButton);

        final JButton clearButton = new JButton("Clear");
        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        clearButton.addActionListener((event) -> this.gridPanel.generateGrid());
        this.add(clearButton);

        final JButton startButton = new JButton("Start");
        startButton.setBackground(Color.LIGHT_GRAY);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        startButton.addActionListener((event) -> this.gridPanel.start());
        this.add(startButton);

        diagonalCheckBox = new JCheckBox("Diagonal movements");
        this.add(diagonalCheckBox);

        this.add(infoLabel);
    }

    private void setGridSize() {
        final int width;
        final int height;
        try {
            width = Integer.parseInt(this.columnsField.getText());
            height = Integer.parseInt(this.rowsField.getText());
            if (width > 100 || width < 1 || height > 100 || height < 1) {
                OptionsPanel.setText("The size should be between 1 and 100", true);
            } else {
                this.gridPanel.setGridSize(width, height);
            }
        } catch (final NumberFormatException ignored) {
            OptionsPanel.setText("Invalid number", true);
        }
    }

    public static int getWaitingTime() {
        return waitingTimeSlider.getValue();
    }

    public static int getWallRatio() {
        return wallRatioSlider.getValue();
    }

    public static boolean checkDiagonal() {
        return diagonalCheckBox.isSelected();
    }

    public static void setText(String text, boolean isError) {
        infoLabel.setForeground(isError ? Color.RED : Color.BLACK);
        infoLabel.setText(text);
    }
}
