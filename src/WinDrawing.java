import javax.swing.*;
import java.awt.*;

public class WinDrawing extends JPanel {
    public int counter = 0;

    Color darkRed = new Color(0x7B0007);

    WinDrawing() {
        repaint();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        counter++;

        System.out.println(counter);

        //fill background
        g2d.setColor(Color.cyan);
        g2d.fillRect(0, 0, 800, 800);

        if (counter % 2 == 0) {
            g2d.setColor(Color.cyan);
        } else {
            g2d.setColor(darkRed);
        }

        g2d.fillRect(80, 80, 40, 40);
        g2d.fillRect(80, 120, 40, 40);
        g2d.fillRect(120, 160, 40, 40);
        g2d.fillRect(160, 200, 40, 40);
        g2d.fillRect(160, 240, 40, 40);
        g2d.fillRect(160, 280, 40, 40);
        g2d.fillRect(200, 160, 40, 40);
        g2d.fillRect(240, 80, 40, 40);
        g2d.fillRect(240, 120, 40, 40);

        //Drawing O

        g2d.fillRect(320, 120, 40, 40);
        g2d.fillRect(320, 160, 40, 40);
        g2d.fillRect(320, 200, 40, 40);
        g2d.fillRect(320, 240, 40, 40);
        g2d.fillRect(360, 80, 40, 40);
        g2d.fillRect(360, 280, 40, 40);
        g2d.fillRect(400, 80, 40, 40);
        g2d.fillRect(400, 280, 40, 40);
        g2d.fillRect(440, 120, 40, 40);
        g2d.fillRect(440, 160, 40, 40);
        g2d.fillRect(440, 200, 40, 40);
        g2d.fillRect(440, 240, 40, 40);

        //Drawing U

        g2d.fillRect(520, 80, 40, 40);
        g2d.fillRect(520, 120, 40, 40);
        g2d.fillRect(520, 160, 40, 40);
        g2d.fillRect(520, 200, 40, 40);
        g2d.fillRect(520, 240, 40, 40);
        g2d.fillRect(560, 280, 40, 40);
        g2d.fillRect(600, 280, 40, 40);
        g2d.fillRect(640, 80, 40, 40);
        g2d.fillRect(640, 120, 40, 40);
        g2d.fillRect(640, 160, 40, 40);
        g2d.fillRect(640, 200, 40, 40);
        g2d.fillRect(640, 240, 40, 40);

        //Drawing W

        g2d.fillRect(80, 360, 40, 40);
        g2d.fillRect(80, 400, 40, 40);
        g2d.fillRect(80, 440, 40, 40);
        g2d.fillRect(80, 480, 40, 40);
        g2d.fillRect(80, 520, 40, 40);
        g2d.fillRect(80, 560, 40, 40);
        g2d.fillRect(120, 520, 40, 40);
        g2d.fillRect(160, 480, 40, 40);
        g2d.fillRect(200, 520, 40, 40);
        g2d.fillRect(240, 360, 40, 40);
        g2d.fillRect(240, 400, 40, 40);
        g2d.fillRect(240, 440, 40, 40);
        g2d.fillRect(240, 480, 40, 40);
        g2d.fillRect(240, 520, 40, 40);
        g2d.fillRect(240, 560, 40, 40);

        //Drawing I

        g2d.fillRect(320, 360, 40, 40);
        g2d.fillRect(320, 560, 40, 40);
        g2d.fillRect(360, 360, 40, 40);
        g2d.fillRect(360, 400, 40, 40);
        g2d.fillRect(360, 440, 40, 40);
        g2d.fillRect(360, 480, 40, 40);
        g2d.fillRect(360, 520, 40, 40);
        g2d.fillRect(360, 560, 40, 40);
        g2d.fillRect(400, 360, 40, 40);
        g2d.fillRect(400, 560, 40, 40);

        //Drawing N

        g2d.fillRect(480, 360, 40, 40);
        g2d.fillRect(480, 400, 40, 40);
        g2d.fillRect(480, 440, 40, 40);
        g2d.fillRect(480, 480, 40, 40);
        g2d.fillRect(480, 520, 40, 40);
        g2d.fillRect(480, 560, 40, 40);
        g2d.fillRect(520, 400, 40, 40);
        g2d.fillRect(560, 440, 40, 40);
        g2d.fillRect(560, 480, 40, 40);
        g2d.fillRect(600, 520, 40, 40);
        g2d.fillRect(640, 360, 40, 40);
        g2d.fillRect(640, 400, 40, 40);
        g2d.fillRect(640, 440, 40, 40);
        g2d.fillRect(640, 480, 40, 40);
        g2d.fillRect(640, 520, 40, 40);
        g2d.fillRect(640, 560, 40, 40);

        //Drawing !

        g2d.fillRect(720, 360, 40, 40);
        g2d.fillRect(720, 400, 40, 40);
        g2d.fillRect(720, 440, 40, 40);
        g2d.fillRect(720, 480, 40, 40);
        g2d.fillRect(720, 560, 40, 40);

        String time = "Time: " + BallPuzzle.gameTimerMinutesString + ":" + BallPuzzle.gameTimerSecondsString;
        g2d.setFont(new Font("Courier New", Font.BOLD, 80));
        g2d.drawString(time, 150, 700);


        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {

                //TODO fix bug where white grid does not display properly.

                g2d.setColor(Color.white);
                g2d.drawLine(x * 40, 0, x * 40, 800);
                g2d.drawLine(0, y * 40, 800, y * 40);
                //Drawing Y
                g2d.setColor(darkRed);

                //Drawing white grid
                g2d.setColor(Color.white);
                g2d.drawLine(x * 40, 0, x * 40, 800);
                g2d.drawLine(0, y * 40, 800, y * 40);
            }
        }
    }
}
