import javax.swing.*;
import java.awt.*;

/**
 * Maze Display
 */
public class Maze extends JPanel {

    private int xMax;
    private int yMax;
    private MazeLayout mazeLayout;
    private int cellSize;
    
    /**
     * Constructs a maze of the specified dimensions and displays it.
     * @param cellSize
     * @param xMax
     * @param yMax
     */
    public Maze(int cellSize, int xMax, int yMax) {
        this.cellSize = cellSize;
        this.xMax = xMax;
        this.yMax = yMax;
        this.removeAll();
        mazeLayout = new MazeLayout(cellSize);
        mazeLayout.generateMazeLayout(yMax,xMax);
        this.draw();
    }

    /**
     * Draws the maze.
     */
    final public void draw() {

        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        int row = 0;
        Cell b = null;

        // make size*size maze, with solid outer sides and random inner walls.
        // c.gridy is the row number
        // Adds Celles to arraylist of arraylist of Celles to keep track of maze data.

        for( int i = 0; i < yMax+1; i++ ) {
            c.gridy = row;

            for( int j = 0; j < xMax+1; j++ ) {
                b = mazeLayout.get().get(i).get(j);
                this.add(b, c);
            }
            row++;

        }

        this.validate();
        this.repaint();
    }

    public MazeLayout getMazeLayout() {
        return mazeLayout;
    }

    /**
     * Whether a move is legal in the maze.
     * @param x x cell coordinate
     * @param y y cell coordinate
     * @param move MazeLayout
     * @return
     */
    public boolean legalMove( int x, int y, int move ) {
        return mazeLayout.legalMove(x,y,move);
    }

}
