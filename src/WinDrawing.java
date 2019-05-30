import java.awt.*;

public class WinDrawing extends Canvas {
    public int counter = 0;

    Color darkYellow = new Color(0xA78C0B);

    WinDrawing() {
        repaint();
    }

    public void paint(Graphics g) {
        counter++;

        if (counter > 6) counter = 0;

        g.setColor(Color.cyan);
        g.fillRect(0, 0, 800, 800);

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {

                //Drawing Y
                g.setColor(darkYellow);

                if (counter == 1) g.setColor(Color.yellow);

                g.fillRect(80, 80, 40, 40);
                g.fillRect(80, 120, 40, 40);
                g.fillRect(120, 160, 40, 40);
                g.fillRect(160, 200, 40, 40);
                g.fillRect(160, 240, 40, 40);
                g.fillRect(160, 280, 40, 40);
                g.fillRect(200, 160, 40, 40);
                g.fillRect(240, 80, 40, 40);
                g.fillRect(240, 120, 40, 40);

                //Drawing O
                g.setColor(darkYellow);

                if (counter == 2) g.setColor(Color.yellow);

                g.fillRect(320, 120, 40, 40);
                g.fillRect(320, 160, 40, 40);
                g.fillRect(320, 200, 40, 40);
                g.fillRect(320, 240, 40, 40);
                g.fillRect(360, 80, 40, 40);
                g.fillRect(360, 280, 40, 40);
                g.fillRect(400, 80, 40, 40);
                g.fillRect(400, 280, 40, 40);
                g.fillRect(440, 120, 40, 40);
                g.fillRect(440, 160, 40, 40);
                g.fillRect(440, 200, 40, 40);
                g.fillRect(440, 240, 40, 40);

                //Drawing U
                g.setColor(darkYellow);

                if (counter == 3) g.setColor(Color.yellow);

                g.fillRect(520, 80, 40, 40);
                g.fillRect(520, 120, 40, 40);
                g.fillRect(520, 160, 40, 40);
                g.fillRect(520, 200, 40, 40);
                g.fillRect(520, 240, 40, 40);
                g.fillRect(560, 280, 40, 40);
                g.fillRect(600, 280, 40, 40);
                g.fillRect(640, 80, 40, 40);
                g.fillRect(640, 120, 40, 40);
                g.fillRect(640, 160, 40, 40);
                g.fillRect(640, 200, 40, 40);
                g.fillRect(640, 240, 40, 40);

                //Drawing W

                g.setColor(darkYellow);

                if (counter == 4) g.setColor(Color.yellow);

                g.fillRect(80, 480, 40, 40);
                g.fillRect(80, 520, 40, 40);
                g.fillRect(80, 560, 40, 40);
                g.fillRect(80, 600, 40, 40);
                g.fillRect(80, 640, 40, 40);
                g.fillRect(80, 680, 40, 40);
                g.fillRect(120, 640, 40, 40);
                g.fillRect(160, 600, 40, 40);
                g.fillRect(200, 640, 40, 40);
                g.fillRect(240, 480, 40, 40);
                g.fillRect(240, 520, 40, 40);
                g.fillRect(240, 560, 40, 40);
                g.fillRect(240, 600, 40, 40);
                g.fillRect(240, 640, 40, 40);
                g.fillRect(240, 680, 40, 40);

                //Drawing I

                g.setColor(darkYellow);

                if (counter == 5) g.setColor(Color.yellow);

                g.fillRect(320, 480, 40, 40);
                g.fillRect(320, 680, 40, 40);
                g.fillRect(360, 480, 40, 40);
                g.fillRect(360, 520, 40, 40);
                g.fillRect(360, 560, 40, 40);
                g.fillRect(360, 600, 40, 40);
                g.fillRect(360, 640, 40, 40);
                g.fillRect(360, 680, 40, 40);
                g.fillRect(400, 480, 40, 40);
                g.fillRect(400, 680, 40, 40);

                //Drawing N
                g.setColor(darkYellow);

                if (counter == 6) g.setColor(Color.yellow);

                g.fillRect(480, 480, 40, 40);
                g.fillRect(480, 520, 40, 40);
                g.fillRect(480, 560, 40, 40);
                g.fillRect(480, 600, 40, 40);
                g.fillRect(480, 640, 40, 40);
                g.fillRect(480, 680, 40, 40);
                g.fillRect(520, 520, 40, 40);
                g.fillRect(560, 560, 40, 40);
                g.fillRect(560, 600, 40, 40);
                g.fillRect(600, 640, 40, 40);
                g.fillRect(640, 480, 40, 40);
                g.fillRect(640, 520, 40, 40);
                g.fillRect(640, 560, 40, 40);
                g.fillRect(640, 600, 40, 40);
                g.fillRect(640, 640, 40, 40);
                g.fillRect(640, 680, 40, 40);

                //Drawing white grid
                g.setColor(Color.white);
                g.drawLine(x * 40, 0, x * 40, 800);
                g.drawLine(0, y * 40, 800, y * 40);
            }
        }
    }
}
