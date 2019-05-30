import javax.swing.*;
import java.awt.*;

//Paint class that draws the 'brush' selection area of the level editor.
class levelEditorDrawing extends JPanel {

    Color darkGreen = new Color(0x006400);
    Color darkBlue = new Color(0x005994);
    Color portalOrange = new Color(0xED6900);
    Color portalBlue = new Color(0x1C5DFF);
    Color purple = new Color(0xA100F7);

    levelEditorDrawing() {
        repaint();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //0 block
        g2d.setColor(Color.cyan);
        g2d.fillRect(30, 20, 40, 40);

        //x block
        g2d.setColor(darkBlue);
        g2d.fillRect(30, 80, 40, 40);

        //s block
        g2d.setColor(Color.red);
        g2d.fillOval(30, 140, 40, 40);

        //f block
        g2d.setColor(darkGreen);
        g2d.fillRect(30, 200, 40, 40);
        g2d.setColor(Color.green);
        g2d.fillRect(35, 205, 30, 30);
        g2d.setColor(darkGreen);
        g2d.fillRect(40, 210, 20, 20);
        g2d.setColor(Color.green);
        g2d.fillRect(45, 215, 10, 10);

        //t block
        g2d.setColor(portalOrange);
        g2d.fillRect(30, 260, 40, 40);
        g2d.setColor(Color.white);
        g2d.fillRect(35, 265, 30, 30);

        //T block
        g2d.setColor(portalBlue);
        g2d.fillRect(30, 320, 40, 40);
        g2d.setColor(Color.white);
        g2d.fillRect(35, 325, 30, 30);

        //^ block
        g2d.setColor(purple);
        g2d.fillRect(30, 380, 40, 40);
        g2d.setColor(Color.magenta);
        Polygon upTriangle = new Polygon();
        upTriangle.addPoint(30, 420);
        upTriangle.addPoint(50, 380);
        upTriangle.addPoint(70, 420);
        g2d.fillPolygon(upTriangle);

        //v block
        g2d.setColor(purple);
        g2d.fillRect(30, 440, 40, 40);
        g2d.setColor(Color.magenta);
        Polygon downTriangle = new Polygon();
        downTriangle.addPoint(30, 440);
        downTriangle.addPoint(50, 480);
        downTriangle.addPoint(70, 440);
        g2d.fillPolygon(downTriangle);

        //> block
        g2d.setColor(purple);
        g2d.fillRect(30, 500, 40, 40);
        g2d.setColor(Color.magenta);
        Polygon rightTriangle = new Polygon();
        rightTriangle.addPoint(30, 500);
        rightTriangle.addPoint(70, 520);
        rightTriangle.addPoint(30, 540);
        g2d.fillPolygon(rightTriangle);

        //< block
        g2d.setColor(purple);
        g2d.fillRect(30, 560, 40, 40);
        g2d.setColor(Color.magenta);
        Polygon leftTriangle = new Polygon();
        leftTriangle.addPoint(70, 560);
        leftTriangle.addPoint(30, 580);
        leftTriangle.addPoint(70, 600);
        g2d.fillPolygon(leftTriangle);
    }
}