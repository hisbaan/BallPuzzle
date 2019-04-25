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

    public final boolean DEBUG = true;

    JFrame mainFrame = new JFrame("Main Menu");
    JButton startGame = new JButton("Start Game");

    JFrame gameFrame = new JFrame("Ball Puzzle");
    JButton gameBackButton = new JButton("Back to main menu");

    public String direction = "";

    public int levelNumber = 1;
    public char[][] level = new char[20][20];
    public char[][] ballPosition = new char[20][20];

    JPanel gameGridPannel = new JPanel();

    BallPuzzle() {
        mainMenu();

//        canvas = new gameDrawing();

        east = new Timer(100, e -> {
            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
                    if (ballPosition[x][y] == '1') {
                        ballPosition[x][y] = '0';
                        ballPosition[x + 1][y] = '1';
                    }
                }
            }
        });

        west = new Timer(100, e -> {
            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
                    if (ballPosition[x][y] == '1') {
                        ballPosition[x][y] = '0';
                        ballPosition[x - 1][y] = '1';
                    }
                }
            }
        });

        north = new Timer(100, e -> {
            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
                    if (ballPosition[x][y] == '1') {
                        ballPosition[x][y] = '0';
                        ballPosition[x][y - 1] = '1';
                    }
                }
            }
        });

        south = new Timer(100, e -> {
            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
                    if (ballPosition[x][y] == '1') {
                        ballPosition[x][y] = '0';
                        ballPosition[x][y + 1] = '1';
                    }
                }
            }
        });

    }

    Timer east;
    Timer west;
    Timer north;
    Timer south;

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

        gameFrame.setSize(800, 800);
        if (gameFrame.getWindowListeners().length < 1) gameFrame.addWindowListener(this);
        gameFrame.setLayout(new BorderLayout());

        gameFrame.add(gameBackButton, BorderLayout.SOUTH);
        if (gameBackButton.getActionListeners().length < 1) gameBackButton.addActionListener(this);

        gameFrame.add(gameGridPannel, BorderLayout.CENTER);
        gameGridPannel.setLayout(new GridLayout(20, 20));
        gameGridPannel.setSize(800, 800);

        readLevel();

        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                if (level[x][y] == 's') {

                }
            }
        }
        //use draw method to pain the scene of the level.
        //paint the ball moving across the screes

        gameFrame.setVisible(true);
        mainFrame.setVisible(false);
    }

    public void readLevel() {
        File levelFile = new File("./levels/level" + levelNumber + ".txt");
        String levelTemp;

        try {
            levelTemp = new String(Files.readAllBytes(levelFile.toPath()), StandardCharsets.UTF_8);

            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
                    level[x][y] = levelTemp.charAt(y);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (DEBUG) {
            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
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
            north.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            south.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            west.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            east.start();
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

    class gameDrawing extends Canvas {
        @Override
        public void paint(Graphics g) {

        }
    }
}