import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This entity represents the goal of the maze.
 */
public class Treasure extends Entity {

    /**
     * Specifies position and the cellSize (fixed for each maze)
     * @param x x cell co-ordinate
     * @param y y cell co-ordinate
     * @param cellSize cell width/height
     */
    public Treasure( int x, int y, int cellSize  ) {
        super( x, y, cellSize );
    }

    /**
     * How fast this entity moves
     * @return 0 - doesn't move
     */
    public int getCurrentDisplacement() {
        return 0;
    }

    /**
     * Abstact method for entity, treasure doesn't move
     * @param maze
     * @param move
     */
    public void setCellPositions( MazeLayout maze, int move ) {
    	return;
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage srcImg = null;
        try {
            srcImg = ImageIO.read(new File("resources/star2.png"));
        } catch (IOException e) { }

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(srcImg, 0, 0, 20, 20, null);
    }

}
