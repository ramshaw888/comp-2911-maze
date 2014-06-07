import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * Gold class. This is the gold that the player picks up.
 */
public class Gold extends Entity {

    public Gold( int x, int y, int cellSize ) {
        super(x, y, cellSize);
    }

    /**
     * Cell positions don't move because gold.
     * @param maze
     */
    public void setCellPositions( MazeLayout maze, int move ) {

    }

    /**
     * Speed of gold is 0.
     * @return
     */
    public int getCurrentDisplacement() {
        return 0;
    }

    @Override
    public void paint(Graphics g) {

        // Paints circle
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(160,0, 153));
        Ellipse2D.Double circle = new Ellipse2D.Double(0,0,20,20);
        g2d.fill(circle);

    }
}
