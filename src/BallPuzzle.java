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

    public static final boolean DEBUG = true; //Debug variable that shows or hides tracing statements.

    //Variables for main menu.
    JFrame mainFrame = new JFrame("Main Menu");
    JButton startGame = new JButton("Start Game");

    //Variables for game window.
    JFrame gameFrame = new JFrame("Ball Puzzle");
    JButton gameBackButton = new JButton("Back to main menu");

    public String direction = ""; //String that changes based on the direction selected by the user (via arrow keys).
    Timer movement; //Initializing timer that is mentioned in constructor.

    public int levelNumber = 0;
    public static char[][] level = new char[20][20]; //Char array that tracks the blocks on the level.
    public static char[][] ballPosition = new char[20][20]; //Char array that tracks the position of the ball on the level.

    gameDrawing canvas = new gameDrawing(); //Instance of class to call paint method

    BallPuzzle() {
        //Calling the paint method to draw the canvas initially.
        canvas.validate();
        canvas.repaint();

        mainMenu();

        //TODO fix east and south ball movement
        //TODO don't let the ball go out of bound in the first place

        //Timer that controls the movement of the ball on the board.
        movement = new Timer(500, e -> {
            checkCondition();

            canvas.validate();
            canvas.repaint();

            //TODO use direction that the ball is currently moving in and if the ball hits the edge of the screen, then set it to the other side of the screen.
            switch (direction) {
                case "north": //When the user presses the up arrow key.
                    for (int y = 0; y < 20; y++) {
                        for (int x = 0; x < 20; x++) {
                            if (ballPosition[x][y] == '1') {
                                ballPosition[x][y] = '0';

                                try {
                                    ballPosition[x][y - 1] = '1';
                                } catch (Exception ex) {
                                    ballPosition[x][19] = '1';
                                    if (DEBUG) System.out.println("Ball hit " + direction + " wall");

                                }

//                                if (DEBUG) System.out.println("Ball moved" + direction);
                                canvas.validate();
                                canvas.repaint();
                            }
                        }
                    }
                    break;
                case "south": //When the user presses the down arrow key.
                    for (int y = 0; y < 20; y++) {
                        for (int x = 0; x < 20; x++) {
                            if (ballPosition[x][y] == '1') {
                                ballPosition[x][y] = '0';

                                try {
                                    ballPosition[x][y + 1] = '1';
                                } catch (Exception ex) {
                                    ballPosition[x][0] = '1';
                                    if (DEBUG) System.out.println("Ball hit " + direction + " wall");
                                }

//                                if (DEBUG) System.out.println("Ball moved" + direction);
                                canvas.validate();
                                canvas.repaint();
                            }
                        }
                    }
                    break;
                case "east": //When the user presses the right arrow key.
                    for (int y = 0; y < 20; y++) {
                        for (int x = 0; x < 20; x++) {
                            if (ballPosition[x][y] == '1') {
                                ballPosition[x][y] = '0';

                                try {
                                    ballPosition[x + 1][y] = '1';
                                } catch (Exception ex) {
                                    ballPosition[0][y] = '1';
                                    if (DEBUG) System.out.println("Ball hit " + direction + " wall");
                                }

//                                if (DEBUG) System.out.println("Ball moved" + direction);
                                canvas.validate();
                                canvas.repaint();
                            }
                        }
                    }
                    break;
                case "west": //When hte user presses the left arrow key.
                    for (int y = 0; y < 20; y++) {
                        for (int x = 0; x < 20; x++) {
                            if (ballPosition[x][y] == '1') {
                                ballPosition[x][y] = '0';

                                try {
                                    ballPosition[x - 1][y] = '1';
                                } catch (Exception ex) {
                                    ballPosition[19][y] = '1';
                                    if (DEBUG) System.out.println("Ball hit " + direction + " wall");
                                }

                                /**/if (DEBUG) System.out.println("Ball moved" + direction);
                                canvas.validate();
                                canvas.repaint();
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
        canvas.setFocusable(false);

        gameFrame.add(gameBackButton, BorderLayout.SOUTH);
        if (gameBackButton.getActionListeners().length < 1) gameBackButton.addActionListener(this);

        canvas.setSize(800, 800);
        gameFrame.add(canvas, BorderLayout.CENTER);

        nextLevel();

        //Sets the starting position of the ball to wherever 's' is in the array that stores the level information.

        gameFrame.setVisible(true);
        mainFrame.setVisible(false);
    }

    //Loads in the next level when the method is called.
    public void nextLevel() {
        //TODO show a transition screen like a JOptionPane to indicate to the user that the level has changed.
        levelNumber++;

        File levelFile = new File("./levels/level" + levelNumber + "raw.txt");
        String levelTemp;

        try {
//            levelTemp = new String(Files.readAllBytes(levelFile.toPath()), StandardCharsets.UTF_8);

            levelTemp = Files.readString(levelFile.toPath());
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

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                ballPosition[x][y] = '0';
                if (level[x][y] == 's') {
                    ballPosition[x][y] = '1';
                }
            }
        }

        direction = "";
    }

    public void checkCondition() {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                if (ballPosition[x][y] == '1' && level[x][y] == 'f') {
                    nextLevel();
                }

                if (ballPosition[x][y] == '1' && level[x][y] == 't') {
                    for (int y2 = 0; y2 < 20; y2++) {
                        for (int x2 = 0; x2 < 20; x2++) {
                            if (level[x2][y2] == 'T') {
                                ballPosition[x2][y2] = '1';
                            }
                        }
                    }

                    ballPosition[x][y] = '0';
                }

                try {
                    if (ballPosition[x][y] == '1' && level[x][y - 1] == 'x' && direction.equals("north")) {
                        direction = "";
                    }
                    if (ballPosition[x][y] == '1' && level[x][y + 1] == 'x' && direction.equals("south")) {
                        direction = "";
                    }
                    if (ballPosition[x][y] == '1' && level[x + 1][y] == 'x' && direction.equals("east")) {
                        direction = "";
                    }
                    if (ballPosition[x][y] == '1' && level[x - 1][y] == 'x' && direction.equals("west")) {
                        direction = "";
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("solid block detection hit edge");
                }

            }
        }
    }

    //ActionListener method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGame) {
            gameStart();
        }

        if (e.getSource() == gameBackButton) {
            mainMenu();
        }
    }

    //KeyListener methods
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

    //WindowListener methods
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

//Paint class
class gameDrawing extends Canvas {

    //Colours

    Color darkGreen = new Color(0, 100, 0);
    Color darkBlue = new Color(0, 90, 150);
    public boolean currentlyDarkGreen = false;

    gameDrawing() {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        if (BallPuzzle.DEBUG) g.drawRect(0, 0, 800, 800);

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 800, 800);

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {

                g.setColor(Color.white);
                g.drawLine(x * 40, 0, x * 40, 800);
                g.drawLine(0, y * 40, 800, y * 40);
                //Draws blue blocks wherever there is a solid block.
                if (BallPuzzle.level[x][y] == 'x') {
                    g.setColor(darkBlue);
                    g.fillRect(x * 40, y * 40, 40, 40);
                }

                //TODO make the green box change colours from dark green to light green (look at game on website).

                //Draws a green box where the end point of the level is.
                if (BallPuzzle.level[x][y] == 'f') {
                    //TODO toggle colour so that the ending position shows an animation.

                    if (currentlyDarkGreen) {
                        g.setColor(Color.green);
                        currentlyDarkGreen = !currentlyDarkGreen;
                    } else {
                        g.setColor(darkGreen);
                        currentlyDarkGreen = !currentlyDarkGreen;
                    }
                    g.fillRect(x * 40, y * 40, 40, 40);

                    if (currentlyDarkGreen) {
                        g.setColor(Color.green);
                        currentlyDarkGreen = !currentlyDarkGreen;
                    } else {
                        g.setColor(darkGreen);
                        currentlyDarkGreen = !currentlyDarkGreen;
                    }
                    g.fillRect((x * 40) + 5, (y * 40) + 5, 30, 30);

                    if (currentlyDarkGreen) {
                        g.setColor(Color.green);
                        currentlyDarkGreen = !currentlyDarkGreen;
                    } else {
                        g.setColor(darkGreen);
                        currentlyDarkGreen = !currentlyDarkGreen;
                    }
                    g.fillRect((x * 40) + 10, (y * 40) + 10, 20, 20);

                    if (currentlyDarkGreen) {
                        g.setColor(Color.green);
                        currentlyDarkGreen = !currentlyDarkGreen;
                    } else {
                        g.setColor(darkGreen);
                        currentlyDarkGreen = !currentlyDarkGreen;
                    }
                    g.fillRect((x * 40) + 15, (y * 40) + 15, 10, 10);
//                    g.setColor(Color.green);
//                    g.fillRect((x * 40) + 20, (y * 40) + 20, 5, 5);
                }

                if (BallPuzzle.level[x][y] == 't' || BallPuzzle.level[x][y] == 'T') {
                    //TODO paint teleport block
                    g.setColor(Color.magenta);
                    g.fillRect(x * 40, y * 40, 40, 40);
                }

                //Draws the ball whenever it moves.
                if (BallPuzzle.ballPosition[x][y] == '1') {
                    g.setColor(Color.red);
                    g.fillOval(x * 40, y * 40, 40, 40);
                }
            }
        }
    }
}