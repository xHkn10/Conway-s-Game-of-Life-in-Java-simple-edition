package GameOfLife;

import javax.swing.*;
import java.awt.*;

public class Life extends JPanel {

    static int delayMiliSeconds = 200;
    static int xlength = 50, ylength = 50;
    static boolean[][] cells = new boolean[xlength][ylength];
    static JButton[][] buttons = new JButton[xlength][ylength];
    static Timer timer;

    Life() {
        setLayout(new GridLayout(0, xlength, 0, 0));
        setSize(100, 200);
        setVisible(true);
        setPreferredSize(new Dimension(600, 600));

        //creating new buttons and then placing them on a grid
        for (int i = 0; i < xlength * ylength; i++) {
            JButton button = new JButton();
            button.setBackground(Color.BLACK);
            button.setPreferredSize(new Dimension(2, 2));
            /*
            pressing the button triggers action listener which reverses button color
            it also updates cell array which hold our life values (true if alive false if dead).

            In order to find (x,y) position of our cell according to the i value we used in the for loop,
            we make use of some arithmetics. i's mod with xlength gives its x position and its INTEGER division
            with xlength gives us its y position.

            Also, we benefited from lambda functions.
             */
            int finalI = i;
            button.addActionListener(e -> {
                if (button.getBackground() == Color.BLACK) {
                    button.setBackground(Color.YELLOW);
                    cells[finalI % xlength][finalI / xlength] = true;
                } else {
                    button.setBackground(Color.BLACK);
                    cells[finalI % xlength][finalI / xlength] = false;
                }
            });
            //button array to access our buttons afterward
            buttons[finalI % xlength][finalI / xlength] = button;
            add(button);
            timer = new Timer(delayMiliSeconds, e -> updateGrid());
        }
    }

    //method to count neighbors of the cell at (x,y)
    static int countNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (cells[x + j][y + i]) count++;
            }
        }
        return count;
    }

    static void updateGrid() {

        //temporary array to hold our values
        boolean[][] tempCells = new boolean[xlength][ylength];

        //either kill or give birth to cells, write new values into tempCells
        for (int x = 1; x < xlength - 1; x++) {
            for (int y = 1; y < ylength - 1; y++) {
                int neighbourCountOfXY = countNeighbors(x, y);
                awakenOrPerish(neighbourCountOfXY, cells, tempCells, x, y);
            }
        }
        //update cells using tempCells
        cells = booleanArrayCopy(tempCells);

        //repaint buttons according to cells
        for (int y = 0; y < ylength; y++) {
            for (int x = 0; x < xlength; x++) {
                if (cells[x][y]) buttons[x][y].setBackground(Color.yellow);
                else buttons[x][y].setBackground(Color.BLACK);
            }
        }
    }

    //method to update cells array
    static boolean[][] booleanArrayCopy(boolean[][] source) {
        boolean[][] arr = new boolean[source.length][source[0].length];
        for (int y = 0; y < source.length; y++) {
            for (int x = 0; x < source[0].length; x++) {
                arr[y][x] = source[y][x];
            }
        }
        return arr;
    }

    static void awakenOrPerish(int nearbyAllies, boolean[][] cells, boolean[][] newCells, int x, int y) {
        if (cells[x][y]) {
            if (nearbyAllies < 2 || nearbyAllies > 3) {
                newCells[x][y] = false;
                return;
            }
            newCells[x][y] = true;
            return;
        }
        if (nearbyAllies == 3) {
            newCells[x][y] = true;
            return;
        }
        newCells[x][y] = false;
    }

    static void startGame() {
        timer.start();
    }

    static void stopGame() {
        timer.stop();
    }

    static void resetGame() {
        cells = new boolean[xlength][ylength];
        for (int x = 0; x < xlength; x++) {
            for (int y = 0; y < ylength; y++) {
                buttons[x][y].setBackground(Color.BLACK);
            }
        }
        timer.restart();
        timer.stop();
    }
}


