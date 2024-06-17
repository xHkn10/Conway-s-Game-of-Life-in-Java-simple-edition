package GameOfLife;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        /*
        live cell --> 0, 1, 4, 5, 6, 7, 8 neighbors: dead
        live cell --> 2, 3 neighbors: survive
        dead cell --> 3 neighbors: awaken
         */

        final boolean[] isGameRunning = {false};
        Life life = new Life();

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.yellow);

        JButton actionButton = new JButton("START");
        actionButton.setPreferredSize(new Dimension(100, 30));
        actionButton.addActionListener(e -> {
            if (!isGameRunning[0]) {
                isGameRunning[0] = true;
                Life.startGame();
                actionButton.setText("STOP");
            }
            else {
                isGameRunning[0] = false;
                Life.stopGame();
                actionButton.setText("START");
            }
        });
        JButton resetButton = new JButton("RESET");
        resetButton.setPreferredSize(new Dimension(100, 30));
        resetButton.addActionListener(e -> {
            Life.resetGame();
            actionButton.setText("START");
            isGameRunning[0] = false;
        });

        controlPanel.add(actionButton);
        controlPanel.add(resetButton);

        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(life, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.pack();
    }
}
