import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Cell extends JComponent {

    private int cellSize;

    /**
     * Constructs a cell with coordinate (x,y) of a certain size. All walls are
     * closed.
     * @param x
     * @param y
     * @param cellSize
     */
    
    public Cell(int x, int y, int cellSize) {

        // 2 is top, 3 is left
        this.cellSize = cellSize;
        walls = new boolean[] {true ,true,true,true};
        isWall(2);
        isWall(3);
        setVisible(true);
        setPreferredSize(new Dimension(this.cellSize,this.cellSize) );
        this.x = x;
        this.y = y;
        setOpaque(false);

    }

    /***
     * Constructs a cell with coordinate (x,y) of a certain size. Walls 
     * are not closed by default, but the left and top walls can be toggled.
     * @param x
     * @param y
     * @param top
     * @param left
     * @param cellSize
     */
    
    public Cell(int x, int y, boolean top, boolean left, int cellSize) {

        // 2 is top, 3 is left
        this.cellSize = cellSize;
        walls = new boolean[] {false ,false,top,left};
        setVisible(true);
        setPreferredSize(new Dimension(this.cellSize,this.cellSize) );
        this.x = x;
        this.y = y;

    }


    /***
     * For debugging, used to print a cell. Called by printMaze().
     */
    
    public void printCell() {

        if (walls[2]){
            System.out.print("__");
        } else {
            System.out.print("  ");
        }

        if (walls[1]){
            System.out.print("|");
        } else {
            System.out.print("_");
        }

    }

    /***
     * Whether the cell is closed on all sides.
     * @return
     */
    
    public boolean isClosed(){
        return (walls[0] & walls[1] & walls[2] & walls[3]);
    }

    /***
     * Removes the common wall of c and the object on which this is called.
     * Requires the cells to be adjacent.
     * @param c An adjacent cell to the specified cell
     */
    
    public void removeWall(Cell c){
        if (x == c.x){
            if (y == c.y + 1){
                this.dropWall(2);
                c.dropWall(0);
            } else if (y == c.y - 1){
                this.dropWall(0);
                c.dropWall(2);
            }
        } else if (y == c.y()){
            if (x == c.x + 1){
                this.dropWall(3);
                c.dropWall(1);
            } else if (x == c.x - 1){
                this.dropWall(1);
                c.dropWall(3);
            }
        }
    }

    /**
     * Remove the specified wall from the cell.
     * @param i
     */
    
    public void dropWall(int i){
        walls[i] = false;
    }

    /**
     * Whether the specified wall exists for the cell.
     * @param i
     * @return
     */
    
    public boolean isWall(int i){
        return walls[i % 4];
    }

    /**
     * Sets the top and left walls of the cell.
     * @param top
     * @param left
     */
    
    public void setWalls(boolean top, boolean left) {
        walls[2] = top;
        walls[3] = left;
    }

    /**
     * Sets the top wall of the cell.
     * @param top
     */
    public void setTop( boolean top ) {
        walls[2] = top;
    }
    
    /**
     * Sets the left wall of the cell.
     * @param left
     */
    public void setLeft( boolean left ) {
        walls[3] = left;
    }
    
    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    /**
     * Paint the graphic of the cell.
     */
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.RED);
        g2d.setColor(new Color(201,76,4));
        if( walls[2] ) {
            g2d.setStroke(new BasicStroke(5));
            g2d.draw(new Line2D.Float(0, 0, 0, cellSize));
        }
        if( walls[3] ) {
            g2d.setStroke(new BasicStroke(5));
            g2d.draw(new Line2D.Float(0, 0, cellSize, 0));
        }
    }

    private boolean[] walls;
    private int x;
    private int y;

}
