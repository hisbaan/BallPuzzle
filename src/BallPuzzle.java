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
    JFrame gameFrame = new JFrame("Ball Puzzle");

    JPanel bottomGamePanel = new JPanel();
    JButton gameBackButton = new JButton("Back to main menu");
    JButton restartLevelButton = new JButton("Restart");

    JButton timer = new JButton(); //TODO make this pause the game when clicked then bring up a JOptionPane that when interacted with, resumes the game.
    JButton levelButton = new JButton(); //TODO make level button allow the user to choose the level that they would like to play (but only go backwards, not forwards).

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

    //Variables for custom level window.
    JFrame customLevelFrame = new JFrame();
    gameDrawing canvas = new gameDrawing(); //Instance of class to call paint method
    levelEditorDrawing levelEditorDrawing = new levelEditorDrawing();

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
    }

    public void mainMenu() {
        if (DEBUG) System.out.println("mainMenu ran");

        mainFrame.setSize(800, 800);
        mainFrame.setResizable(false);
        if (mainFrame.getWindowListeners().length < 1) mainFrame.addWindowListener(this);
        mainFrame.setLayout(new BorderLayout());

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


        gameTimer = 0;
    }

    public void levelEditor() {
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

            try {
                existingLevelName = (String) JOptionPane.showInputDialog(mainFrame, "Select which level you want to edit from the drop down menu:", "", JOptionPane.QUESTION_MESSAGE, null, listOfFiles, listOfFiles[1]);
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

        File levelFile = new File("./customLevels" + existingLevelName + ".txt");
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

    public void playCustomLevels() { //TODO finish implementing custom level player
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
    }

    public void restartLevel() {
        direction = "";
        movement.stop();

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                ballPosition[x][y] = '0';
            }
        }

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                if (level[x][y] == 's') ballPosition[x][y] = '1';
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
        if (levelNumber > 0)
            JOptionPane.showMessageDialog(gameFrame, "Level " + levelNumber + " complete!\nPress okay to advance:");
        levelNumber++;

        levelButton.setText("Level: " + levelNumber);

        File levelFile = new File("./levels/level" + levelNumber + ".txt");
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
                    if (gameFrame.isVisible()) {
                        nextLevel();
                    }

                    if (customLevelFrame.isVisible()) {
                        JOptionPane.showMessageDialog(customLevelFrame, "Level Completed.\nPress okay to continue.");
                        getCustomLevels();
                    }
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

    //ActionListener method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGame) {
            levelNumber = 0;
            gameStart();
        }

        if (e.getSource() == levelEditorButton) {
            String[] options = new String[]{"New Level", "Pre-existing Level"};
            int response = JOptionPane.showOptionDialog(mainFrame, "Message", "Title",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

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

                    try {
                        existingLevelName = (String) JOptionPane.showInputDialog(mainFrame, "Select which level you want to edit from the drop down menu:", "", JOptionPane.QUESTION_MESSAGE, null, listOfFiles, listOfFiles[1]);
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

            levelEditor(); //TODO get file reading code from here
        }

        if (e.getSource() == levelEditorResetButton) {
            reset();
        }

        if (e.getSource() == levelEditorSaveButton) {
            int sCounter = 0;
            int fCounter = 0;

            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 20; x++) {
                    if (level[x][y] == 's') {
                        sCounter++;
                    } else if (level[x][y] == 'f') {
                        fCounter++;
                    }
                }
            }

            if (sCounter == 1 && fCounter > 0) {
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

                    int decision = JOptionPane.showConfirmDialog(levelEditorFrame, "Level Saved\nWould you like to return to the main menu?", "Return to Main Menu", JOptionPane.YES_NO_OPTION);

                    if (decision == JOptionPane.YES_OPTION) {
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

                JOptionPane.showMessageDialog(levelEditorFrame, "Level Not Saved:" + message);
            }
        }

        if (e.getSource() == playCustomLevelButton) {
            getCustomLevels();
            playCustomLevels();
        }

        if (e.getSource() == gameBackButton || e.getSource() == levelEditorBackButton) {
            movement.stop();
            mainMenu();
        }

        if (e.getSource() == restartLevelButton) {
            restartLevel();
        }

        if (e.getSource() == timer) {
            //TODO add pause functionality here
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
        if (e.getSource() == gameFrame || e.getSource() == customLevelFrame) {
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
        if (e.getSource() == levelEditorDrawing) {
            if (DEBUG) System.out.println("LE X: " + e.getX() + " | Y: " + e.getY());

            //sets the brush variable to the corresponding character depending on what block the user clicks on.
            if (e.getX() >= 30 && e.getX() <= 70) {
                if (e.getY() >= 20 && e.getY() <= 60) {
                    brush = '0';
                }
                if (e.getY() >= 80 && e.getY() <= 120) {
                    brush = 'x';
                }
                if (e.getY() >= 140 && e.getY() <= 180) {
                    brush = 's';
                }
                if (e.getY() >= 200 && e.getY() <= 240) {
                    brush = 'f';
                }
                if (e.getY() >= 260 && e.getY() <= 300) {
                    brush = 't';
                }
                if (e.getY() >= 320 && e.getY() <= 360) {
                    brush = 'T';
                }
                if (e.getY() >= 380 && e.getY() <= 420) {
                    brush = '^';
                }
                if (e.getY() >= 440 && e.getY() <= 480) {
                    brush = 'v';
                }
                if (e.getY() >= 500 && e.getY() <= 540) {
                    brush = '>';
                }
                if (e.getY() >= 560 && e.getY() <= 600) {
                    brush = '<';
                }
            }

            if (DEBUG) System.out.println("Brush: " + brush);
        }

        if (e.getSource() == canvas) {
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

//Paint class that draws the main board.
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

                //Draws the start block if the level editor is on. If it is not, then the start block will not be drawn.
                if (BallPuzzle.level[x][y] == 's' && BallPuzzle.drawStart) {
                    g.setColor(Color.red);
                    g.fillOval(x * 40, y * 40, 40, 40);
                }

                //Draws a green box where the end point of the level is.
                if (BallPuzzle.level[x][y] == 'f') {
                    if (counter % 4 == 0) {
                        g.setColor(Color.green);
                    } else {
                        g.setColor(darkGreen);
                    }

                    g.fillRect(x * 40, y * 40, 40, 40);

                    if (counter % 4 == 0) {
                        g.setColor(darkGreen);
                    } else {
                        g.setColor(Color.green);
                    }

                    g.fillRect((x * 40) + 5, (y * 40) + 5, 30, 30);

                    if (counter % 4 == 0) {
                        g.setColor(Color.green);
                    } else {
                        g.setColor(darkGreen);
                    }

                    g.fillRect((x * 40) + 10, (y * 40) + 10, 20, 20);

                    if (counter % 4 == 0) {
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

                g.setColor(Color.white);
                g.drawLine(x * 40, 0, x * 40, 800);
                g.drawLine(0, y * 40, 800, y * 40);

                //Draws the ball whenever it moves.
                if (BallPuzzle.ballPosition[x][y] == '1' && !BallPuzzle.drawStart) {
                    g.setColor(Color.red);
                    g.fillOval(x * 40, y * 40, 40, 40);
                }
            }
        }
    }
}

//Paint class that draws the 'brush' selection area of the level editor.
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
        g.fillOval(30, 140, 40, 40);

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