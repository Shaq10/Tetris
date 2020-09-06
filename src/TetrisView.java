import javax.swing.*;
import java.awt.*;
// import all the Colors
import static java.awt.Color.*;

public class TetrisView extends JComponent {
    public static Color[] colors = { white,green,blue,red,yellow,magenta,pink,cyan };
    public int[][] dim1;
    public int[][] dim2;// dimensions for block
    public int w, h; //length and width of the playing grid
    public static int size = 20;

    public TetrisView(int[][] dim1, int[][] dim2) {
        this.dim1 = dim1;
        this.dim2 = dim2;
        w = dim1.length;
        h = dim1[0].length;

    }

    public void paintComponent(Graphics g) {
        // a[6][10] = 3;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                g.setColor(colors[dim1[i][j]]);
                g.fill3DRect(i * size, (h - j - 1) * size, size, size, true);
            }
        }
    }
//blocks filled with colour

    public Dimension getPreferredSize() {
        return new Dimension(320,400);
    }
//makes sure grid dimensions are set
}

