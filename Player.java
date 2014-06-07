import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Player - this is the player that the user controls.
 */
public class Player extends Entity {

    private int displacement = 10;
    private int facing = -1;
    private int cellSize;
    // facing
    // 0 = left
    // 1 = right
    // 2 = down
    // 3 = up

    public Player(int x, int y, int cellSize ) {
        super(x,y, cellSize);
        this.cellSize = cellSize;
    }

    public void setCellPositions( MazeLayout maze, int move ) {

        // movements
        // 0 - left
        // 1 - right
        // 2 - down
        // 3 - up
        // 4 - fire

        if( getXPixel() == toPixel( getXDestination() ) && getYPixel() == toPixel( getYDestination() ) && move != -1 && move != 4) {
            int[] xMove = {-1, 1, 0, 0};
            int[] yMove = {0, 0, 1, -1};

            if (!maze.legalMove( getXDestination() - 1, getYDestination() - 1, move)) {
                setDestination( getXDestination() + xMove[move], getYDestination() + yMove[move] );
                facing = move;
            }
        }

    }


    public int getFacing() {
        return facing;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getCurrentDisplacement() {
        return displacement;
    }


    @Override
    public void paint(Graphics g) {
        // Paints circle
        // This should be changed to a nice graphic.
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        Ellipse2D.Double circle = new Ellipse2D.Double(0,0,20,20);
        g2d.fill(circle);
    }
}
