package com.shadorc.seeker;

import com.shadorc.seeker.graphics.SeekerFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame frame = new SeekerFrame("Seeker", 1200, 800);
            frame.setVisible(true);
        });
    }
}
