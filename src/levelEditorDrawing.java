import java.awt.*;

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