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

public class BallPuzzle implements ActionListener, WindowListener, KeyListener, MouseListener {
    public static void main(String[] args) {
        new BallPuzzle();
    }

    public static final boolean DEBUG = true; //Debug variable that shows or hides tracing statements.

    //Variables for main menu.
    JFrame mainFrame = new JFrame("Main Menu");
    JPanel mainPanel = new JPanel();
    JButton startGame = new JButton("Start Game");
    JButton levelEditorButton = new JButton("Level Editor");

    //Variables for level editor
    JFrame levelEditorFrame = new JFrame("Level Editor");
    JPanel levelEditorPanel = new JPanel();
    JButton levelEditorBackButton = new JButton("Back");
    JButton levelEditorSaveButton = new JButton("Save");
    JButton levelEditorResetButton = new JButton("Reset");

    //Variables for game window.
    JFrame gameFrame = new JFrame("Ball Puzzle");
    JButton gameBackButton = new JButton("Back to main menu");

    public String direction = ""; //String that changes based on the direction selected by the user (via arrow keys).
    Timer movement; //Initializing timer that is mentioned in constructor.

    public int levelNumber = 0;
    public static char[][] level = new char[20][20]; //Char array that tracks the blocks on the level.
    public static char[][] ballPosition = new char[20][20]; //Char array that tracks the position of the ball on the level.

    gameDrawing canvas = new gameDrawing(); //Instance of class to call paint method
    levelEditorDrawing levelEditorDrawing = new levelEditorDrawing();

    BallPuzzle() {
        //Calling the paint method to draw the canvas initially.
        canvas.validate();
        canvas.repaint();

        mainMenu();

        //Timer that controls the movement of the ball on the board.
        movement = new Timer(100, e -> {
            canvas.validate();
            canvas.repaint();

            checkCondition();

            canvas.validate();
            canvas.repaint();

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

                                canvas.validate();
                                canvas.repaint();
                            }
                        }
                    }
                    break;
                case "south": //When the user presses the down arrow key.
                    for (int y = 19; y > -1; y--) {
                        for (int x = 19; x > -1; x--) {
                            if (ballPosition[x][y] == '1') {
                                ballPosition[x][y] = '0';

                                try {
                                    ballPosition[x][y + 1] = '1';
                                } catch (Exception ex) {
                                    ballPosition[x][0] = '1';
                                    if (DEBUG) System.out.println("Ball hit " + direction + " wall");
                                }

                                canvas.validate();
                                canvas.repaint();
                            }
                        }
                    }
                    break;
                case "east": //When the user presses the right arrow key.
                    for (int y = 19; y > -1; y--) {
                        for (int x = 19; x > -1; x--) {
                            if (ballPosition[x][y] == '1') {
                                ballPosition[x][y] = '0';

                                try {
                                    ballPosition[x + 1][y] = '1';
                                } catch (Exception ex) {
                                    ballPosition[0][y] = '1';
                                    if (DEBUG) System.out.println("Ball hit " + direction + " wall");
                                }

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

        mainFrame.add(mainPanel, BorderLayout.SOUTH);

        mainPanel.setLayout(new GridLayout(1, 3));
        mainPanel.add(startGame);
        if (startGame.getActionListeners().length < 1) startGame.addActionListener(this);

        mainPanel.add(levelEditorButton);
        if (levelEditorButton.getActionListeners().length < 1) levelEditorButton.addActionListener(this);

        mainFrame.setVisible(true);
        gameFrame.setVisible(false);
        levelEditorFrame.setVisible(false);
    }

    public void levelEditor() {
        levelEditorFrame.setSize(900, 850);
        levelEditorFrame.setLayout(new BorderLayout());

        levelEditorFrame.add(canvas, BorderLayout.CENTER);
        levelEditorFrame.add(levelEditorDrawing, BorderLayout.EAST);

        canvas.setSize(800, 800);
        levelEditorDrawing.setSize(100, 800);

        levelEditorFrame.add(levelEditorPanel, BorderLayout.SOUTH);

        levelEditorPanel.setLayout(new GridLayout(1, 3));
        levelEditorPanel.add(levelEditorBackButton);
        if (levelEditorBackButton.getActionListeners().length < 1) levelEditorBackButton.addActionListener(this);
        levelEditorPanel.add(levelEditorSaveButton);
        if (levelEditorSaveButton.getActionListeners().length < 1) levelEditorSaveButton.addActionListener(this);
        levelEditorPanel.add(levelEditorResetButton);
        if (levelEditorResetButton.getActionListeners().length < 1) levelEditorResetButton.addActionListener(this);

        levelEditorFrame.setVisible(true);
        mainFrame.setVisible(false);
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
        levelEditorFrame.setVisible(false);
    }

    //Loads in the next level when the method is called.
    public void nextLevel() {
        if (levelNumber > 0)
            JOptionPane.showMessageDialog(gameFrame, "Level " + levelNumber + " complete!\nPress okay to advance:");
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
                    if (ballPosition[x][y] == '1' && level[x][y + 1] == '^' && direction.equals("south")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }
                try {
                    if (ballPosition[x][y] == '1' && level[x][y - 1] == 'v' && direction.equals("north")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }
                try {
                    if (ballPosition[x][y] == '1' && level[x + 1][y] == '<' && direction.equals("east")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }
                try {
                    if (ballPosition[x][y] == '1' && level[x - 1][y] == '>' && direction.equals("west")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }


                try {
                    if (ballPosition[x][y] == '1' && level[x][y - 1] == 'x' && direction.equals("north")) {
                        direction = "";
                    }
                } catch (Exception e) {
                }
                try {
                    if (ballPosition[x][y] == '1' && level[x][y + 1] == 'x' && direction.equals("south")) {
                        direction = "";
                    }
                } catch (Exception e) {
                }
                try {
                    if (ballPosition[x][y] == '1' && level[x + 1][y] == 'x' && direction.equals("east")) {
                        direction = "";
                    }
                } catch (Exception e) {
                }
                try {
                    if (ballPosition[x][y] == '1' && level[x - 1][y] == 'x' && direction.equals("west")) {
                        direction = "";
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    //ActionListener method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGame) {
            levelNumber = 0;
            gameStart();
        }

        if (e.getSource() == levelEditorButton) {
            //TODO JOption Pane where you can select if you want to create a new level or work on a level that is not done yet.
            //TODO if you select a new file, then name it.
            //TODO if you want to edit a previous file, enter it's name. or do a dropdown menu with all the current files.
            levelEditor();
        }

        if (e.getSource() == levelEditorResetButton) {
            //TODO add the reset method that sets everything to zero
        }

        if (e.getSource() == levelEditorSaveButton) {
            //TODO add a save method that checks whether the level includes a start and end tile and other requirements
        }

        if (e.getSource() == gameBackButton || e.getSource() == levelEditorBackButton) {
            mainMenu();
        }
    }

    //KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (direction.equals("")) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                direction = "north";
                if (DEBUG) System.out.println("Up arrow key pressed");
                movement.start();
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                direction = "south";
                if (DEBUG) System.out.println("Down arrow key pressed");
                movement.start();
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                direction = "east";
                if (DEBUG) System.out.println("right arrow key pressed");
                movement.start();
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                direction = "west";
                if (DEBUG) System.out.println("Left arrow key pressed");
                movement.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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
        if (e.getSource() == levelEditorFrame)
            JOptionPane.showMessageDialog(levelEditorFrame, "Thank you for playing!\nGood Bye!");
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

    Color darkGreen = new Color(0x006400);
    Color darkBlue = new Color(0x005994);
    Color darkOrange = new Color(0xCD3E0E);
    Color purple = new Color(0xA100F7);

    public boolean currentlyDarkGreen = false;

    private int counter = 0;

    gameDrawing() {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        counter++;

        g.setColor(Color.cyan);
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

                //Draws a green box where the end point of the level is.
                if (BallPuzzle.level[x][y] == 'f') {
                    if (counter % 2 == 0) {
                        g.setColor(Color.green);
                    } else {
                        g.setColor(darkGreen);
                    }

                    g.fillRect(x * 40, y * 40, 40, 40);

                    if (counter % 2 == 0) {
                        g.setColor(darkGreen);
                    } else {
                        g.setColor(Color.green);
                    }

                    g.fillRect((x * 40) + 5, (y * 40) + 5, 30, 30);

                    if (counter % 2 == 0) {
                        g.setColor(Color.green);
                    } else {
                        g.setColor(darkGreen);
                    }

                    g.fillRect((x * 40) + 10, (y * 40) + 10, 20, 20);

                    if (counter % 2 == 0) {
                        g.setColor(darkGreen);
                    } else {
                        g.setColor(Color.green);
                    }

                    g.fillRect((x * 40) + 15, (y * 40) + 15, 10, 10);
                }

                if (BallPuzzle.level[x][y] == 't' || BallPuzzle.level[x][y] == 'T') {
                    g.setColor(darkOrange);
                    g.fillRect(x * 40, y * 40, 40, 40);
                    g.setColor(Color.white);
                    g.fillRect((x * 40) + 5, (y * 40) + 5, 30, 30);
                }

                if (BallPuzzle.level[x][y] == 'T') {
                    g.setColor(Color.orange);
                    g.fillRect(x * 40, y * 40, 40, 40);
                    g.setColor(Color.white);
                    g.fillRect((x * 40) + 5, (y * 40) + 5, 30, 30);
                }
                
                if (BallPuzzle.level[x][y] == '^') {
                    g.setColor(purple);
                    g.fillRect(x * 40, y * 40, 40, 40);
                    g.setColor(Color.magenta);
                    Polygon upTriangle = new Polygon();
                    upTriangle.addPoint((x * 40), (y * 40) + 40);
                    upTriangle.addPoint((x * 40) + 20, (y * 40));
                    upTriangle.addPoint((x * 40) + 40, (y * 40) + 40);
                    g.fillPolygon(upTriangle);
                }
                if (BallPuzzle.level[x][y] == 'v') {
                    g.setColor(purple);
                    g.fillRect(x * 40, y * 40, 40, 40);
                    g.setColor(Color.magenta);
                    Polygon downTriangle = new Polygon();
                    downTriangle.addPoint((x * 40), (y * 40));
                    downTriangle.addPoint((x * 40) + 20, (y * 40) + 40);
                    downTriangle.addPoint((x * 40) + 40, (y * 40));
                    g.fillPolygon(downTriangle);
                }
                if (BallPuzzle.level[x][y] == '>') {
                    g.setColor(purple);
                    g.fillRect(x * 40, y * 40, 40, 40);
                    g.setColor(Color.magenta);
                    Polygon rightTriangle = new Polygon();
                    rightTriangle.addPoint((x * 40), (y * 40));
                    rightTriangle.addPoint((x * 40) + 40, (y * 40) + 20);
                    rightTriangle.addPoint((x * 40), (y * 40) + 40);
                    g.fillPolygon(rightTriangle);
                }
                if (BallPuzzle.level[x][y] == '<') {
                    g.setColor(purple);
                    g.fillRect(x * 40, y * 40, 40, 40);
                    g.setColor(Color.magenta);
                    Polygon leftTriangle = new Polygon();
                    leftTriangle.addPoint((x * 40) + 40, (y * 40));
                    leftTriangle.addPoint((x * 40), (y * 40) + 20);
                    leftTriangle.addPoint((x * 40) + 40, (y * 40) + 40);
                    g.fillPolygon(leftTriangle);
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

class levelEditorDrawing extends Canvas {

    Color darkGreen = new Color(0x006400);
    Color darkBlue = new Color(0x005994);
    Color darkOrange = new Color(0xCD3E0E);
    Color purple = new Color(0xA100F7);

    levelEditorDrawing() {
        repaint();
    }

    public void paint(Graphics g) {
        //0 block
        g.setColor(Color.cyan);
        g.fillRect(30, 20, 40, 40);

        //x block
        g.setColor(darkBlue);
        g.fillRect(30, 80, 40, 40);

        //s block
        g.setColor(Color.red);
        g.fillRect(30, 140, 40, 40);

        //f block
        g.setColor(darkGreen);
        g.fillRect(30, 200, 40, 40);
        g.setColor(Color.green);
        g.fillRect(35, 205, 30, 30);
        g.setColor(darkGreen);
        g.fillRect(40, 210, 20, 20);
        g.setColor(Color.green);
        g.fillRect(45, 215, 10, 10);

        //t block
        g.setColor(darkOrange);
        g.fillRect(30, 260, 40, 40);
        g.setColor(Color.white);
        g.fillRect(35, 265, 30, 30);

        //T block
        g.setColor(Color.orange);
        g.fillRect(30, 320, 40, 40);
        g.setColor(Color.white);
        g.fillRect(35, 325, 30, 30);

        //^ block
        g.setColor(purple);
        g.fillRect(30, 380, 40, 40);
        g.setColor(Color.magenta);
        Polygon upTriangle = new Polygon();
        upTriangle.addPoint(30, 420);
        upTriangle.addPoint(50, 380);
        upTriangle.addPoint(70, 420);
        g.fillPolygon(upTriangle);

        //v block
        g.setColor(purple);
        g.fillRect(30, 440, 40, 40);
        g.setColor(Color.magenta);
        Polygon downTriangle = new Polygon();
        downTriangle.addPoint(30, 440);
        downTriangle.addPoint(50, 480);
        downTriangle.addPoint(70, 440);
        g.fillPolygon(downTriangle);

        //> block
        g.setColor(purple);
        g.fillRect(30, 500, 40, 40);
        g.setColor(Color.magenta);
        Polygon rightTriangle = new Polygon();
        rightTriangle.addPoint(30, 500);
        rightTriangle.addPoint(70, 520);
        rightTriangle.addPoint(30, 540);
        g.fillPolygon(rightTriangle);

        //< block
        g.setColor(purple);
        g.fillRect(30, 560, 40, 40);
        g.setColor(Color.magenta);
        Polygon leftTriangle = new Polygon();
        leftTriangle.addPoint(70, 560);
        leftTriangle.addPoint(30, 580);
        leftTriangle.addPoint(70, 600);
        g.fillPolygon(leftTriangle);
    }
}