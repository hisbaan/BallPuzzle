import javax.swing.*;
import java.awt.*;

//Paint class that draws the main board.
class gameDrawing extends JPanel {

    //Colours

    Color darkGreen = new Color(0x006400);
    Color darkBlue = new Color(0x005994);
    Color portalOrange = new Color(0xED6900);
    Color portalBlue = new Color(0x1C5DFF);
    Color purple = new Color(0xA100F7);

    public boolean currentlyDarkGreen = false;

    private int counter = 0;

    gameDrawing() {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        counter++;

        g2d.setColor(Color.cyan);
        g2d.fillRect(0, 0, 800, 800);

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                //Draws the white grid in the
                g2d.setColor(Color.white);
                g2d.drawLine(x * 40, 0, x * 40, 800);
                g2d.drawLine(0, y * 40, 800, y * 40);

                //Draws blue blocks wherever there is a solid block.
                if (BallPuzzle.level[x][y] == 'x') {
                    g2d.setColor(darkBlue);
                    g2d.fillRect(x * 40, y * 40, 40, 40);
                }

                //Draws the start block if the level editor is on. If it is not, then the start block will not be drawn.
                if (BallPuzzle.level[x][y] == 's' && BallPuzzle.drawStart) {
                    g2d.setColor(Color.red);
                    g2d.fillOval(x * 40, y * 40, 40, 40);
                }

                //Draws a green box where the end point of the level is.
                if (BallPuzzle.level[x][y] == 'f') {
                    if (counter % 4 == 0) {
                        g2d.setColor(Color.green);
                    } else {
                        g2d.setColor(darkGreen);
                    }

                    g2d.fillRect(x * 40, y * 40, 40, 40);

                    if (counter % 4 == 0) {
                        g2d.setColor(darkGreen);
                    } else {
                        g2d.setColor(Color.green);
                    }

                    g2d.fillRect((x * 40) + 5, (y * 40) + 5, 30, 30);

                    if (counter % 4 == 0) {
                        g2d.setColor(Color.green);
                    } else {
                        g2d.setColor(darkGreen);
                    }

                    g2d.fillRect((x * 40) + 10, (y * 40) + 10, 20, 20);

                    if (counter % 4 == 0) {
                        g2d.setColor(darkGreen);
                    } else {
                        g2d.setColor(Color.green);
                    }

                    g2d.fillRect((x * 40) + 15, (y * 40) + 15, 10, 10);
                }

                if (BallPuzzle.level[x][y] == 't') {
                    g2d.setColor(portalOrange);
                    g2d.fillRect(x * 40, y * 40, 40, 40);
                    g2d.setColor(Color.white);
                    g2d.fillRect((x * 40) + 5, (y * 40) + 5, 30, 30);
                }

                if (BallPuzzle.level[x][y] == 'T') {
                    g2d.setColor(portalBlue);
                    g2d.fillRect(x * 40, y * 40, 40, 40);
                    g2d.setColor(Color.white);
                    g2d.fillRect((x * 40) + 5, (y * 40) + 5, 30, 30);
                }

                if (BallPuzzle.level[x][y] == '^') {
                    g2d.setColor(purple);
                    g2d.fillRect(x * 40, y * 40, 40, 40);
                    g2d.setColor(Color.magenta);
                    Polygon upTriangle = new Polygon();
                    upTriangle.addPoint((x * 40), (y * 40) + 40);
                    upTriangle.addPoint((x * 40) + 20, (y * 40));
                    upTriangle.addPoint((x * 40) + 40, (y * 40) + 40);
                    g2d.fillPolygon(upTriangle);
                }
                if (BallPuzzle.level[x][y] == 'v') {
                    g2d.setColor(purple);
                    g2d.fillRect(x * 40, y * 40, 40, 40);
                    g2d.setColor(Color.magenta);
                    Polygon downTriangle = new Polygon();
                    downTriangle.addPoint((x * 40), (y * 40));
                    downTriangle.addPoint((x * 40) + 20, (y * 40) + 40);
                    downTriangle.addPoint((x * 40) + 40, (y * 40));
                    g2d.fillPolygon(downTriangle);
                }
                if (BallPuzzle.level[x][y] == '>') {
                    g2d.setColor(purple);
                    g2d.fillRect(x * 40, y * 40, 40, 40);
                    g2d.setColor(Color.magenta);
                    Polygon rightTriangle = new Polygon();
                    rightTriangle.addPoint((x * 40), (y * 40));
                    rightTriangle.addPoint((x * 40) + 40, (y * 40) + 20);
                    rightTriangle.addPoint((x * 40), (y * 40) + 40);
                    g2d.fillPolygon(rightTriangle);
                }
                if (BallPuzzle.level[x][y] == '<') {
                    g2d.setColor(purple);
                    g2d.fillRect(x * 40, y * 40, 40, 40);
                    g2d.setColor(Color.magenta);
                    Polygon leftTriangle = new Polygon();
                    leftTriangle.addPoint((x * 40) + 40, (y * 40));
                    leftTriangle.addPoint((x * 40), (y * 40) + 20);
                    leftTriangle.addPoint((x * 40) + 40, (y * 40) + 40);
                    g2d.fillPolygon(leftTriangle);
                }

                g2d.setColor(Color.white);
                g2d.drawLine(x * 40, 0, x * 40, 800);
                g2d.drawLine(0, y * 40, 800, y * 40);

                //Draws the ball whenever it moves.
                if (BallPuzzle.ballPosition[x][y] == '1' && !BallPuzzle.drawStart) {
                    g2d.setColor(Color.red);
                    g2d.fillOval(x * 40, y * 40, 40, 40);
                }
            }
        }
    }
}