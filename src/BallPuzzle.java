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
import java.util.Arrays;
import javax.swing.*;
import java.awt.Cursor;

import javax.swing.JComponent.*;

public class BallPuzzle implements ActionListener, WindowListener, KeyListener, MouseListener {
    public static void main(String[] args) {
        new BallPuzzle();
    }

    public static final boolean DEBUG = true; //Debug variable that shows or hides tracing statements.

    //Variables for main menu.
    JFrame mainFrame = new JFrame("Main Menu");
    JPanel mainPanel = new JPanel();
    JLabel titleLabel = new JLabel();

    JButton startGame = new JButton("Start Game");
    JButton levelEditorButton = new JButton("Level Editor");
    JButton playCustomLevelButton = new JButton("Play Custom Levels");

    //Variables for level editor
    JFrame levelEditorFrame = new JFrame("Level Editor");
    JPanel levelEditorPanel = new JPanel();

    JButton levelEditorBackButton = new JButton("Back");
    JButton levelEditorSaveButton = new JButton("Save");
    JButton levelEditorResetButton = new JButton("Reset");

    public String newLevel = "";
    public String existingLevelName = "";
    public boolean editingNewLevel = true;
    public static boolean drawStart = false;
    public char brush = '0';

    //Variables for game window.
    public boolean canTeleport = true;

    JFrame gameFrame = new JFrame("Ball Puzzle");

    JPanel bottomGamePanel = new JPanel();
    JButton gameBackButton = new JButton("Back to main menu");
    JButton restartLevelButton = new JButton("Restart");

    JButton timer = new JButton();
    JButton levelButton = new JButton();

    public String direction = ""; //String that changes based on the direction selected by the user (via arrow keys).
    Timer movement; //Initializing timer that is mentioned in constructor.

    Timer gameTime;
    public int gameTimer = 0;
    public int gameTimerMinutes;
    public int gameTimerSeconds;

    public String gameTimerMinutesString;
    public String gameTimerSecondsString;

    public int levelNumber = 0;
    public static char[][] level = new char[20][20]; //Char array that tracks the blocks on the level.
    public static char[][] ballPosition = new char[20][20]; //Char array that tracks the position of the ball on the level.

    public String levelNumberString;

    //Variables for custom level window.
    JFrame customLevelFrame = new JFrame();
    gameDrawing canvas = new gameDrawing(); //Instance of class to call paint method
    levelEditorDrawing levelEditorDrawing = new levelEditorDrawing();

    //Variables for win screen.
    JFrame winningFrame = new JFrame();
    WinDrawing winDrawing = new WinDrawing();

    Timer winTimer;

    JPanel winningBottomPannel = new JPanel();

    JButton winningBackButton = new JButton("Main Menu");

    //Custom Cursors
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image image0 = toolkit.getImage("./mouseIcons/0.ico");
    Cursor cursor0 = toolkit.createCustomCursor(image0, new Point(0, 0), "img");

    Image imageX = toolkit.getImage("./mouseIcons/X.ico");
    Cursor cursorX = toolkit.createCustomCursor(imageX, new Point(1, 1), "img");

    Image imageS = toolkit.getImage("./mouseIcons/S.ico");
    Cursor cursorS = toolkit.createCustomCursor(imageS, new Point(1, 1), "img");

    Image imageF = toolkit.getImage("./mouseIcons/F.ico");
    Cursor cursorF = toolkit.createCustomCursor(imageF, new Point(1, 1), "img");

    Image imageT1 = toolkit.getImage("./mouseIcons/T1.ico");
    Cursor cursorT1 = toolkit.createCustomCursor(imageT1, new Point(1, 1), "img");

    Image imageT2 = toolkit.getImage("./mouseIcons/T2.ico");
    Cursor cursorT2 = toolkit.createCustomCursor(imageT2, new Point(1, 1), "img");

    Image imageUpBlock = toolkit.getImage("./mouseIcons/UpBlock.ico");
    Cursor cursorUpBlock = toolkit.createCustomCursor(imageUpBlock, new Point(1, 1), "img");

    Image imageDownBlock = toolkit.getImage("./mouseIcons/DownBlock.ico");
    Cursor cursorDownBlock = toolkit.createCustomCursor(imageDownBlock, new Point(1, 1), "img");

    Image imageLeftBlock = toolkit.getImage("./mouseIcons/LeftBlock.ico");
    Cursor cursorLeftBlock = toolkit.createCustomCursor(imageLeftBlock, new Point(1, 1), "img");

    Image imageRightBlock = toolkit.getImage("./mouseIcons/RightBlock.ico");
    Cursor cursorRightBlock = toolkit.createCustomCursor(imageRightBlock, new Point(1, 1), "img");


    BallPuzzle() {
        //Calling the paint method to draw the canvas initially.
        canvas.validate();
        canvas.repaint();

        mainMenu();

        //Timer that controls the movement of the ball on the board.
        movement = new Timer(50, e -> {
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
                                    canTeleport = true;
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
                                canTeleport = true;
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
                                canTeleport = true;
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
                                canTeleport = true;
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

        gameTime = new Timer(1000, e -> {
            gameTimer++;

            gameTimerMinutes = gameTimer / 60;
            gameTimerSeconds = gameTimer % 60;

            if (gameTimerMinutes < 10) {
                gameTimerMinutesString = "0" + gameTimerMinutes;
            } else {
                gameTimerSecondsString = "" + gameTimerMinutes;
            }

            if (gameTimerSeconds < 10) {
                gameTimerSecondsString = "0" + gameTimerSeconds;
            } else {
                gameTimerSecondsString = "" + gameTimerSeconds;
            }
            timer.setText(gameTimerMinutesString + ":" + gameTimerSecondsString);
        });

        winTimer = new Timer(1000, e -> {
            winDrawing.validate();
            winDrawing.repaint();
        });
    }

    public void mainMenu() {
        if (DEBUG) System.out.println("mainMenu ran");

        mainFrame.setSize(800, 800);
        mainFrame.setResizable(false);
        if (mainFrame.getWindowListeners().length < 1) mainFrame.addWindowListener(this);
        mainFrame.setLayout(new BorderLayout());

        titleLabel.setIcon(new ImageIcon("./images/Ball Puzzle Main Menu Splash Screen.png"));
        mainFrame.add(titleLabel, BorderLayout.CENTER);

        mainFrame.add(mainPanel, BorderLayout.SOUTH);

        mainPanel.setLayout(new GridLayout(1, 3));
        mainPanel.add(startGame);
        if (startGame.getActionListeners().length < 1) startGame.addActionListener(this);

        mainPanel.add(levelEditorButton);
        if (levelEditorButton.getActionListeners().length < 1) levelEditorButton.addActionListener(this);

        mainPanel.add(playCustomLevelButton);
        if (playCustomLevelButton.getActionListeners().length < 1) playCustomLevelButton.addActionListener(this);

        mainFrame.setVisible(true);
        gameFrame.setVisible(false);
        levelEditorFrame.setVisible(false);
        customLevelFrame.setVisible(false);
        winningFrame.setVisible(false);


        gameTimer = 0;
    }

    public void levelEditor() {
        canvas.validate();
        canvas.repaint();

        if (DEBUG) System.out.println("levelEditor ran");
        levelEditorFrame.setSize(900, 850);
        levelEditorFrame.setResizable(false);
        levelEditorFrame.setLayout(new BorderLayout());

        canvas.setFocusable(true);
        levelEditorDrawing.setFocusable(true);
        levelEditorPanel.setFocusable(false);
        levelEditorSaveButton.setFocusable(false);
        levelEditorResetButton.setFocusable(false);
        levelEditorBackButton.setFocusable(false);
        levelEditorFrame.setFocusable(false);

        levelEditorFrame.add(canvas, BorderLayout.CENTER);
        levelEditorFrame.add(levelEditorDrawing, BorderLayout.EAST);
        if (canvas.getMouseListeners().length < 1) canvas.addMouseListener(this);
        if (levelEditorDrawing.getMouseListeners().length < 1) levelEditorDrawing.addMouseListener(this);

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
        customLevelFrame.setVisible(false);
        winningFrame.setVisible(false);

        movement.start();
    }

    public void reset() {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                level[x][y] = '0';
                ballPosition[x][y] = '0';
            }
        }

        canvas.validate();
        canvas.repaint();

        if (DEBUG) System.out.println("Level Editor Reset");
    }

    public void gameStart() {
        movement.start();
        gameTime.start();
        drawStart = false;
        timer.setText("00:00");
        if (DEBUG) System.out.println("gameStart ran");

        gameFrame.setSize(800, 850);
        gameFrame.setResizable(false);
        if (gameFrame.getWindowListeners().length < 1) gameFrame.addWindowListener(this);
        if (gameFrame.getKeyListeners().length < 1) gameFrame.addKeyListener(this);
        gameFrame.setLayout(new BorderLayout());

        gameFrame.setFocusable(true);
        gameBackButton.setFocusable(false);
        canvas.setFocusable(false);
        restartLevelButton.setFocusable(false);
        timer.setFocusable(false);
        levelButton.setFocusable(false);

        canvas.setSize(800, 800);
        gameFrame.add(canvas, BorderLayout.CENTER);

        gameFrame.add(bottomGamePanel, BorderLayout.SOUTH);
        bottomGamePanel.setLayout(new GridLayout(1, 4));

        bottomGamePanel.add(timer);
        if (timer.getActionListeners().length < 1) timer.addActionListener(this);

        bottomGamePanel.add(levelButton);
        if (levelButton.getActionListeners().length < 1) levelButton.addActionListener(this);

        bottomGamePanel.add(gameBackButton);
        if (gameBackButton.getActionListeners().length < 1) gameBackButton.addActionListener(this);

        bottomGamePanel.add(restartLevelButton);
        if (restartLevelButton.getActionListeners().length < 1) restartLevelButton.addActionListener(this);

        nextLevel();

        gameFrame.setVisible(true);
        mainFrame.setVisible(false);
        levelEditorFrame.setVisible(false);
        customLevelFrame.setVisible(false);
        winningFrame.setVisible(false);
    }

    public void getCustomLevels() {
        String[] listOfFiles;

        try {
            File customFolder = new File("./customLevels/");

            FilenameFilter textFilter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".txt");
                }
            };

            File[] files = customFolder.listFiles(textFilter);
            listOfFiles = new String[files.length];

            for (int i = 0; i < files.length; i++) {
                listOfFiles[i] = files[i].toString().substring(15, files[i].toString().length() - 4);
            }

            Arrays.sort(listOfFiles);

            try {
                JFrame temp;
                if (mainFrame.isVisible()) {
                    temp = mainFrame;
                } else {
                    temp = customLevelFrame;
                }
                existingLevelName = (String) JOptionPane.showInputDialog(temp, "Select which level you want to play:", "", JOptionPane.QUESTION_MESSAGE, null, listOfFiles, listOfFiles[0]);
            } catch (HeadlessException exc) {
                existingLevelName = "";
            }
        } catch (Exception ex) {
            ex.printStackTrace();


        }

        editingNewLevel = false;

        File levelFile = new File("./customLevels/" + existingLevelName + ".txt");
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        loadCustomLevels();
    }

    public void loadCustomLevels() {
        levelButton.setText(existingLevelName);

        File levelFile = new File("./customLevels/" + existingLevelName + ".txt");
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

        //Sets the starting position of the ball to wherever 's' is in the array that stores the level information.
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

    public void playCustomLevels() {
        movement.start();
        gameTime.start();
        drawStart = false;
        timer.setText("00:00");
        if (DEBUG) System.out.println("gameStart ran");

        customLevelFrame.setSize(800, 850);
        customLevelFrame.setResizable(false);
        if (customLevelFrame.getWindowListeners().length < 1) customLevelFrame.addWindowListener(this);
        if (customLevelFrame.getKeyListeners().length < 1) customLevelFrame.addKeyListener(this);
        customLevelFrame.setLayout(new BorderLayout());

        customLevelFrame.setFocusable(true);
        gameBackButton.setFocusable(false);
        canvas.setFocusable(false);
        restartLevelButton.setFocusable(false);
        timer.setFocusable(false);
        levelButton.setFocusable(false);

        canvas.setSize(800, 800);
        customLevelFrame.add(canvas, BorderLayout.CENTER);

        customLevelFrame.add(bottomGamePanel, BorderLayout.SOUTH);
        bottomGamePanel.setLayout(new GridLayout(1, 4));

        bottomGamePanel.add(timer);
        if (timer.getActionListeners().length < 1) timer.addActionListener(this);

        bottomGamePanel.add(levelButton);
        if (levelButton.getActionListeners().length < 1) levelButton.addActionListener(this);

        bottomGamePanel.add(gameBackButton);
        if (gameBackButton.getActionListeners().length < 1) gameBackButton.addActionListener(this);

        bottomGamePanel.add(restartLevelButton);
        if (restartLevelButton.getActionListeners().length < 1) restartLevelButton.addActionListener(this);

        gameFrame.setVisible(false);
        mainFrame.setVisible(false);
        levelEditorFrame.setVisible(false);
        customLevelFrame.setVisible(true);
        winningFrame.setVisible(false);
    }

    public void restartLevel() {
        direction = "";
        movement.stop();

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                ballPosition[x][y] = '0';
                if (level[x][y] == 's') {
                    ballPosition[x][y] = '1';
                }
            }
        }

        movement.start();

        if (gameFrame.isVisible()) {
            JOptionPane.showMessageDialog(gameFrame, "Level " + levelNumber + " restarted");
        }

        if (customLevelFrame.isVisible()) {
            JOptionPane.showMessageDialog(customLevelFrame, "Level restarted");
        }
    }

    //Loads in the next level when the method is called.
    public void nextLevel() {

        if (levelNumber > 0) {
            String[] options = {"Next Level", "Replay"};
            int decision = JOptionPane.showOptionDialog(gameFrame, "Level " + levelNumber + " complete!\nWhat would you like to do?", "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);


            if (decision == 1) {
                JOptionPane.showMessageDialog(gameFrame, "Level " + levelNumber + " restarted.");
                levelNumber--;
            } else if (decision == 0) {
            }
        }

        if (levelNumber > 19) {
            System.out.println("Game Win Triggered");
            win();
        }

        levelNumber++;

        levelButton.setText("Level: " + levelNumber);

        if (levelNumber < 10) {
            levelNumberString = "0" + levelNumber;
        } else {
            levelNumberString = "" + levelNumber;
        }

        File levelFile = new File("./levels/level" + levelNumberString + ".txt");
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

        //Sets the starting position of the ball to wherever 's' is in the array that stores the level information.
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

    //checks the position of the ball in relation to the elements around it so that it can stop, teleport, etc.
    public void checkCondition() {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                if (ballPosition[x][y] == '1' && level[x][y] == 'f') {

                    direction = "";

                    if (gameFrame.isVisible()) {
                        nextLevel();
                    }

                    if (customLevelFrame.isVisible()) {
                        int temp = JOptionPane.showConfirmDialog(customLevelFrame, "Level Completed.\nWould you like to play another level?", "Continue?", JOptionPane.YES_NO_OPTION);

                        if (temp == JOptionPane.YES_OPTION) {
                            getCustomLevels();
                        } else {
                            mainMenu();
                        }
                    }
                }

                if (canTeleport) {
                    if (ballPosition[x][y] == '1' && level[x][y] == 't') {
                        for (int y2 = 0; y2 < 20; y2++) {
                            for (int x2 = 0; x2 < 20; x2++) {
                                if (level[x2][y2] == 'T') {
                                    ballPosition[x2][y2] = '1';
                                }
                            }
                        }

                        ballPosition[x][y] = '0';
                        canTeleport = false;
                    }

                    if (ballPosition[x][y] == '1' && level[x][y] == 'T') {
                        for (int y2 = 0; y2 < 20; y2++) {
                            for (int x2 = 0; x2 < 20; x2++) {
                                if (level[x2][y2] == 't') {
                                    ballPosition[x2][y2] = '1';
                                }
                            }
                        }

                        ballPosition[x][y] = '0';
                        canTeleport = false;
                    }
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
                    if (ballPosition[x][y] == '1' && level[x + 1][y] == '^' && direction.equals("east")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }
                try {
                    if (ballPosition[x][y] == '1' && level[x - 1][y] == '^' && direction.equals("west")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }
                try {
                    if (ballPosition[x][y] == '1' && level[x + 1][y] == 'v' && direction.equals("east")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }
                try {
                    if (ballPosition[x][y] == '1' && level[x - 1][y] == 'v' && direction.equals("west")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }
                try {
                    if (ballPosition[x][y] == '1' && level[x][y + 1] == '<' && direction.equals("south")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }
                try {
                    if (ballPosition[x][y] == '1' && level[x][y - 1] == '<' && direction.equals("north")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }
                try {
                    if (ballPosition[x][y] == '1' && level[x][y + 1] == '>' && direction.equals("south")) {
                        direction = "";
                    }
                } catch (Exception e) {

                }
                try {
                    if (ballPosition[x][y] == '1' && level[x][y - 1] == '>' && direction.equals("north")) {
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

    public void win() {
        //TODO code for win here --> Make a display where an animation is painted on the grid and then a JOptionPane is shown asking the user if they want to ~maybe~ play bonus levels or return to the main menu
        movement.stop();
        gameTime.stop();
        winTimer.start();


        winningFrame.setSize(800, 850);
        winningFrame.setLayout(new BorderLayout());
        winningFrame.setResizable(false);
        winningFrame.add(winDrawing, BorderLayout.CENTER);

        winDrawing.setSize(800, 800);

        winningBottomPannel.setLayout(new GridLayout(1, 2));
        winningBottomPannel.setSize(800, 50);
        winningFrame.add(winningBottomPannel, BorderLayout.SOUTH);

        winningBottomPannel.add(winningBackButton);
        if (winningBackButton.getActionListeners().length < 1) winningBackButton.addActionListener(this);

        if (winningFrame.getWindowListeners().length < 1) winningFrame.addWindowListener(this);
        winningFrame.setVisible(true);
        gameFrame.setVisible(false);
        customLevelFrame.setVisible(false);
        mainFrame.setVisible(false);
    }

    //ActionListener method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGame) {
            levelNumber = 19;
            gameStart();
        }

        if (e.getSource() == levelEditorButton) {
            String[] options = new String[]{"New Level", "Pre-existing Level"};
            int response = JOptionPane.showOptionDialog(mainFrame, "New or Pre-existing Level", "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (response == 0) {
                newLevel = JOptionPane.showInputDialog(mainFrame, "What would you like to call your level?");
                editingNewLevel = true;


                reset();
            } else if (response == 1) {

                String[] listOfFiles;

                try {
                    File customFolder = new File("./customLevels/");

                    FilenameFilter textFilter = new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".txt");
                        }
                    };

                    File[] files = customFolder.listFiles(textFilter);
                    listOfFiles = new String[files.length];

                    for (int i = 0; i < files.length; i++) {
                        listOfFiles[i] = files[i].toString().substring(15, files[i].toString().length() - 4);
                    }

                    Arrays.sort(listOfFiles);

                    try {
                        existingLevelName = (String) JOptionPane.showInputDialog(mainFrame, "Select which level you want to play:", "", JOptionPane.QUESTION_MESSAGE, null, listOfFiles, listOfFiles[0]);
                    } catch (HeadlessException exc) {
                        existingLevelName = "";
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();


                }

                editingNewLevel = false;

                File levelFile = new File("./customLevels/" + existingLevelName + ".txt");
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
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            levelEditor();
        }

        if (e.getSource() == levelEditorResetButton) {
            reset();
        }

        if (e.getSource() == levelEditorSaveButton) {
            int sCounter = 0;
            int fCounter = 0;
            int t1Counter = 0;
            int t2Counter = 0;

            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 20; x++) {
                    if (level[x][y] == 's') {
                        sCounter++;
                    } else if (level[x][y] == 'f') {
                        fCounter++;
                    } else if (level[x][y] == 't') { //suggested by emily making a level with 50 of each block
                        t1Counter++;
                    } else if (level[x][y] == 'T') { //same here
                        t2Counter++;
                    }
                }
            }

            if (sCounter == 1 && fCounter > 0 && !(t1Counter > 1 || t2Counter > 1 || (t1Counter == 1 && t2Counter < 1) || (t2Counter == 1 && t2Counter < 1))) {
                File file;

                if (editingNewLevel) {
                    file = new File("./customLevels/" + newLevel + ".txt");
                } else {
                    file = new File("./customLevels/" + existingLevelName + ".txt");
                }

                String rawLevelData = "";

                for (int y = 0; y < 20; y++) {
                    for (int x = 0; x < 20; x++) {
                        rawLevelData += level[x][y];
                    }
                }

                try {
                    Files.write(file.toPath(), rawLevelData.getBytes(StandardCharsets.UTF_8));

                    if (DEBUG) System.out.println("Level Saved");

                    String[] options = {"Play Level", "Return to Main Menu", "Continue Editing"};

                    int decision = JOptionPane.showOptionDialog(levelEditorFrame, "Level Saved\nWould you like to play your level or return to the main menu?", "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (decision == 0) {

                        if (editingNewLevel) {
                            existingLevelName = newLevel;
                        }

                        reset();
                        loadCustomLevels();

                        playCustomLevels();
                    } else if (decision == 1) {
                        mainMenu();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                    if (DEBUG) System.out.println("Level Not Saved");

                    JOptionPane.showMessageDialog(levelEditorFrame, "Level Not Saved: \nInternal error");
                }

            } else {
                String message = "";

                if (sCounter < 1) {
                    message += "\nNo start block";
                }
                if (sCounter > 1) {
                    message += "\nToo many start blocks";
                }
                if (fCounter < 1) {
                    message += "\nNo finish block";
                }
                if (t1Counter > 1) {
                    message += "\nToo many orange teleport blocks";
                }
                if (t2Counter > 1) {
                    message += "\nToo many blue teleport blocks";
                }
                if (t1Counter == 1 && t2Counter < 1) {
                    message += "\nOne orange teleport block but no blue teleport block";
                }
                if (t2Counter == 1 && t1Counter < 1) {
                    message += "\nOne blue teleport block but no orange teleport block";
                }

                JOptionPane.showMessageDialog(levelEditorFrame, "Level Not Saved:" + message);
            }
        }

        if (e.getSource() == playCustomLevelButton) {
            getCustomLevels();
            playCustomLevels();
        }

        if (e.getSource() == gameBackButton || e.getSource() == levelEditorBackButton || e.getSource() == winningBackButton) {
            movement.stop();
            mainMenu();
        }

        if (e.getSource() == restartLevelButton) {
            restartLevel();
        }

        if (e.getSource() == timer) {
            movement.stop();
            gameTime.stop();

            if (gameFrame.isVisible()) {
                JOptionPane.showMessageDialog(gameFrame, "Game Paused:\nPress okay to resume");
            }

            if (customLevelFrame.isVisible()) {
                JOptionPane.showMessageDialog(customLevelFrame, "Game Paused:\nPress okay to resume");
            }

            movement.start();
            gameTime.start();
        }
    }

    //KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getSource() == gameFrame || e.getSource() == customLevelFrame) && (gameFrame.isVisible() || customLevelFrame.isVisible())) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                restartLevel();
                if (DEBUG) System.out.println();
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                movement.stop();
                mainMenu();
                if (DEBUG) System.out.println();
            }

            if (direction.equals("")) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    direction = "north";
                    if (DEBUG) System.out.println("Up arrow key pressed");

                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    direction = "south";
                    if (DEBUG) System.out.println("Down arrow key pressed");

                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    direction = "east";
                    if (DEBUG) System.out.println("right arrow key pressed");

                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    direction = "west";
                    if (DEBUG) System.out.println("Left arrow key pressed");

                }
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
        if (e.getSource() == levelEditorDrawing && levelEditorFrame.isVisible()) {
            if (DEBUG) System.out.println("LE X: " + e.getX() + " | Y: " + e.getY());

            //sets the brush variable to the corresponding character depending on what block the user clicks on.
            if (e.getX() >= 30 && e.getX() <= 70) {
                if (e.getY() >= 20 && e.getY() <= 60) {
                    brush = '0';
                    levelEditorFrame.setCursor(cursor0);
                }
                if (e.getY() >= 80 && e.getY() <= 120) {
                    brush = 'x';
                    levelEditorFrame.setCursor(cursorX);
                }
                if (e.getY() >= 140 && e.getY() <= 180) {
                    brush = 's';
                    levelEditorFrame.setCursor(cursorS);
                }
                if (e.getY() >= 200 && e.getY() <= 240) {
                    brush = 'f';
                    levelEditorFrame.setCursor(cursorF);
                }
                if (e.getY() >= 260 && e.getY() <= 300) {
                    brush = 't';
                    levelEditorFrame.setCursor(cursorT1);
                }
                if (e.getY() >= 320 && e.getY() <= 360) {
                    brush = 'T';
                    levelEditorFrame.setCursor(cursorT2);
                }
                if (e.getY() >= 380 && e.getY() <= 420) {
                    brush = '^';
                    levelEditorFrame.setCursor(cursorUpBlock);
                }
                if (e.getY() >= 440 && e.getY() <= 480) {
                    brush = 'v';
                    levelEditorFrame.setCursor(cursorDownBlock);
                }
                if (e.getY() >= 500 && e.getY() <= 540) {
                    brush = '>';
                    levelEditorFrame.setCursor(cursorRightBlock);
                }
                if (e.getY() >= 560 && e.getY() <= 600) {
                    brush = '<';
                    levelEditorFrame.setCursor(cursorLeftBlock);
                }
            }

            if (DEBUG) System.out.println("Brush: " + brush);
        }

        if (e.getSource() == canvas && levelEditorFrame.isVisible()) {
            drawStart = true;
            if (DEBUG) System.out.println("GB X: " + e.getX() + " | Y: " + e.getY());

            //sets the tile that the user clicks on to the current brush.
            level[((int) Math.floor(e.getX() / 40))][((int) Math.floor(e.getY() / 40))] = brush;

            canvas.validate();
            canvas.repaint();
        }
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
        if (e.getSource() == winningFrame)
            JOptionPane.showMessageDialog(winningFrame, "Thank you for playing!\nGood Bye!");

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