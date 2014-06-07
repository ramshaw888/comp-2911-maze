import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Generates mazelayout
 */
public class MazeLayout {

    private int cellSize;

    /**
     * Create a maze.
     * @param cellSize
     */
    
    public MazeLayout(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * Generate the maze given the dimensions.
     * @param xMax
     * @param yMax
     * @return
     */
    public ArrayList<ArrayList<Cell>> generateMazeLayout(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        layout = new ArrayList<ArrayList<Cell>>();
        int k,l = 0;

        for (k = 0; k < xMax; k ++){
            ArrayList<Cell> temp = new ArrayList<Cell>();
            for (l = 0; l < yMax; l ++){
                temp.add(new Cell(k, l, cellSize));
            }
            Cell c = new Cell(k,l,true,false, cellSize);
            if( k == xMax ) {
                c.setTop(false);
            }
            temp.add(c);

            layout.add(temp);
        }

        ArrayList<Cell> t = new ArrayList<Cell>();
        for( l = 0; l < yMax; l++ ) {
           t.add( new Cell(k,l,false,true, cellSize) );
        }
        t.add(new Cell(k,l,false,false, cellSize) );
        layout.add(t);

        Random r = new Random();

        Stack<Cell> cellStack = new Stack<Cell>();
        Cell currCell = layout.get(r.nextInt(xMax)).get(r.nextInt(yMax));
        int totalCells = xMax*yMax;
        int visitedCells = 1;

        while (visitedCells < totalCells){
            ArrayList<Cell> neighbours = neighbours(currCell);
            if (!neighbours.isEmpty()){
                Cell temp = neighbours.get(r.nextInt(neighbours.size())); // choose a random neighbour
                temp.removeWall(currCell);
                cellStack.push(currCell);
                currCell = temp;
                visitedCells ++;
            } else {
                currCell = cellStack.pop();
            }
            neighbours.clear();
        }


        return layout;
    }

    /**
     * Returns all the neighbors of a specific cell.
     * @param c
     * @return
     */
    private ArrayList<Cell> neighbours(Cell c){
        // Checks if the neighbours are within the bounds and if they are closed.

        ArrayList<Cell> n = new ArrayList<Cell>();
        if (c.y() - 1 >= 0 && layout.get(c.x()).get(c.y() - 1).isClosed())    n.add(layout.get(c.x()).get(c.y() - 1));
        if (c.y() + 1 < yMax && layout.get(c.x()).get(c.y() + 1).isClosed()) n.add(layout.get(c.x()).get(c.y() + 1));
        if (c.x() - 1 >= 0 && layout.get(c.x() - 1).get(c.y()).isClosed())    n.add(layout.get(c.x() - 1).get(c.y()));
        if (c.x() + 1 < xMax && layout.get(c.x() + 1).get(c.y()).isClosed()) n.add(layout.get(c.x() + 1).get(c.y()));

        return n;
    }

    /**
     * Prints maze through standard out. (for diagnostic purposes)
     */
    public void display() {
        System.out.print("_");
        for (int i = -1; i < yMax; i ++){
            for (int j = 0; j < xMax; j ++){
                if (i == -1){
                    System.out.print("___");
                } else {
                    if (j == 0) System.out.print("|");
                    layout.get(j).get(yMax - i-1).printCell();
                }
            }
            System.out.println(" ");
        }
    }

    /**
     * Checks if the move is legal or not.
     * @param x x cell coordinate
     * @param y y cell coordinate
     * @param move
     *       move = 0 is left
     *       move = 1 is right
     *       move = 2 is down
     *       move = 3 is up
     * @return
     */
    public boolean legalMove( int x, int y, int move ) {

        if( move == 0 ) {
            return layout.get(y).get(x).isWall(2);
        } else if( move == 1 ) {
            return layout.get(y).get(x+1).isWall(2);
        } else if( move == 2 ) {
            return layout.get(y+1).get(x).isWall(3);
        } else {
            return layout.get(y).get(x).isWall(3);
        }
    }

    public ArrayList<ArrayList<Cell>> get() {
        return layout;
    }

    public ArrayList<ArrayList<Cell>> layout;
    private int xMax;
    private int yMax;

}
