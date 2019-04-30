//File: BallPuzzle.java
//Created: 23/04/2019
//Finished: 23/04/2019
//Name: Hisbaan Noorani
//
//This program

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.*;

public class BallPuzzle implements ActionListener, WindowListener, KeyListener {
    public static void main(String[] args) {
        new BallPuzzle();
    }

    public static final boolean DEBUG = true;

    JFrame mainFrame = new JFrame("Main Menu");
    JButton startGame = new JButton("Start Game");

    JFrame gameFrame = new JFrame("Ball Puzzle");
    JButton gameBackButton = new JButton("Back to main menu");

    public String direction = "";
    Timer movement;

    public int levelNumber = 0;
    public static char[][] level = new char[20][20];
    public static char[][] ballPosition = new char[20][20];

    Canvas gameCanvas = new Canvas();

    gameDrawing canvas = new gameDrawing();

    BallPuzzle() {
        mainMenu();

        canvas.validate();
        canvas.repaint();
        //TODO fix paint method and how the objects are displayed on the gameFrame.

        movement = new Timer(500, e -> {
            canvas.validate();
            canvas.repaint();

            switch (direction) {
                case "north":
                    for (int y = 0; y < 20; y++) {
                        for (int x = 0; x < 20; x++) {
                            if (ballPosition[x][y] == '1') {
                                ballPosition[x][y] = '0';
                                try {
                                    ballPosition[x][y - 1] = '1';
                                } catch (Exception e1) {
                                    ballPosition[x][19] = '1';
                                }
                            }
                        }
                    }
                    break;
                case "south":
                    for (int y = 0; y < 20; y++) {
                        for (int x = 0; x < 20; x++) {
                            if (ballPosition[x][y] == '1') {
                                ballPosition[x][y] = '0';
                                try {
                                    ballPosition[x][y + 1] = '1';
                                } catch (Exception e1) {
                                    ballPosition[x][0] = '1';
                                }
                            }
                        }
                    }
                    break;
                case "east":
                    for (int y = 0; y < 20; y++) {
                        for (int x = 0; x < 20; x++) {
                            if (ballPosition[x][y] == '1') {
                                ballPosition[x][y] = '0';
                                try {
                                    ballPosition[x + 1][y] = '1';
                                } catch (Exception e1) {
                                    ballPosition[0][y] = '1';
                                }
                            }
                        }
                    }
                    break;
                case "west":
                    for (int y = 0; y < 20; y++) {
                        for (int x = 0; x < 20; x++) {
                            if (ballPosition[x][y] == '1') {
                                ballPosition[x][y] = '0';
                                try {
                                    ballPosition[x - 1][y] = '1';
                                } catch (Exception e1) {
                                    ballPosition[19][y] = '1';
                                }
                            }
                        }
                    }
                    break;
            }
        });
    }


    public void mainMenu() {
        if (DEBUG) System.out.println("mainMenu ran");

        mainFrame.setSize(800, 800);
        if (mainFrame.getWindowListeners().length < 1) mainFrame.addWindowListener(this);
        mainFrame.setLayout(new BorderLayout());

        mainFrame.add(startGame, BorderLayout.SOUTH);
        if (startGame.getActionListeners().length < 1) startGame.addActionListener(this);

        mainFrame.setVisible(true);
        gameFrame.setVisible(false);
    }

    public void gameStart() {
        if (DEBUG) System.out.println("gameStart ran");

        gameFrame.setSize(800, 850);
        if (gameFrame.getWindowListeners().length < 1) gameFrame.addWindowListener(this);
        if (gameFrame.getKeyListeners().length < 1) gameFrame.addKeyListener(this);
        gameFrame.setLayout(new BorderLayout());

        gameFrame.setFocusable(true);
        gameBackButton.setFocusable(false);

        gameFrame.add(gameBackButton, BorderLayout.SOUTH);
        if (gameBackButton.getActionListeners().length < 1) gameBackButton.addActionListener(this);

        gameFrame.add(canvas, BorderLayout.CENTER);
        gameCanvas.setSize(800, 800);

        nextLevel();

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                if (level[x][y] == 's') {
                    ballPosition[x][y] = '1';
                }
            }
        }
        //use draw method to pain the scene of the level.
        //paint the ball moving across the screes

        gameFrame.setVisible(true);
        mainFrame.setVisible(false);
    }

    public void nextLevel() {
        levelNumber++;
        File levelFile = new File("./levels/level" + levelNumber + "raw.txt");
        String levelTemp;

        try {
            levelTemp = new String(Files.readAllBytes(levelFile.toPath()), StandardCharsets.UTF_8);

            int z = 0;

            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 20; x++) {
                    level[x][y] = levelTemp.charAt(z);
                    z++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (DEBUG) {
            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 20; x++) {
                    System.out.print(level[x][y] + " ");
                }
                System.out.println();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGame) {
            gameStart();
        }

        if (e.getSource() == gameBackButton) {
            mainMenu();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            direction = "north";
            System.out.println("Up arrow key pressed");
            movement.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            direction = "south";
            System.out.println("Down arrow key pressed");
            movement.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direction = "east";
            System.out.println("right arrow key pressed");
            movement.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            direction = "west";
            System.out.println("Left arrow key pressed");
            movement.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (e.getSource() == mainFrame)
            JOptionPane.showMessageDialog(mainFrame, "Thank you for playing!\nGood Bye!");
        if (e.getSource() == gameFrame)
            JOptionPane.showMessageDialog(gameFrame, "Thank you for playing!\nGood Bye!");
//        if (e.getSource() == helpFrame)
//            JOptionPane.showMessageDialog(helpFrame, "Thank you for playing!\nGood Bye!");
//        if (e.getSource() == highScoreFrame)
//            JOptionPane.showMessageDialog(highScoreFrame, "Thank you for playing!\nGood Bye!");

        System.exit(0);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }
}

class gameDrawing extends Canvas {
    gameDrawing() {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        if (BallPuzzle.DEBUG) g.drawRect(0, 0, 800, 800);

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                if (BallPuzzle.level[x][y] == 'x') {
                    g.setColor(Color.blue);
                    g.fillRect(x * 40, y * 40, 40, 40);
                }

                if (BallPuzzle.level[x][y] == 'f') {
                    g.setColor(Color.green);
                    g.fillRect(x * 40, y * 40, 40, 40);
                }

                if (BallPuzzle.ballPosition[x][y] == '1') {
                    g.setColor(Color.red);
                    g.fillOval(x * 40, y * 40, 40, 40);
                }
            }
        }
    }
}