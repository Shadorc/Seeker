package com.shadorc.seeker;

import com.shadorc.seeker.graphics.SeekerFrame;

import javax.swing.SwingUtilities;
import java.awt.Frame;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final Frame frame = new SeekerFrame("Seeker", 1200, 800);
            frame.setVisible(true);
        });
    }
}
