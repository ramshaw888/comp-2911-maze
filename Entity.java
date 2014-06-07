import javax.swing.*;


/**
 * Abstract player class for all moving 'players'
 *
 * xDestination and yDestination and xCurrent, yCurrent are the cell co-ordinates.
 * xPixel and yPixel are the pixel co-ordinates.
 *
 * When xPixel != toPixel( xDestination ) && yPixel != toPixel( yDestination),
 * then setPixelPositions increments the xPixel and yPixel until those
 * conditions are met.
 *
 * The amount they increment by is determined by the getDisplacement which is
 * unique to each type of entity.
 */

public abstract class Entity extends JComponent {

    // These are pixel co-ordinates of current position (may be in between cells).
    private int xPixel;
    private int yPixel;

    // The cell where this player is moving
    private int xDestination;
    private int yDestination;
    private int xCurrent;
    private int yCurrent;

    private int cellSize;

    /**
     * Defines starting location
     * @param x x cell coordinate
     * @param y y cell coordinate
     * @param cellSize cell size
     */
    public Entity( int x, int y, int cellSize ) {
        this.cellSize = cellSize;
        this.xDestination = x;
        this.yDestination = y;
        this.xCurrent = x;
        this.yCurrent = y;
        this.xPixel = toPixel(x);
        this.yPixel = toPixel(y);
    }


    /**
     * Checks if the current pixel coordinates are the same as the toPixel( Destination ) coordinates.
     * If not, set pixel coordinates towards the destination.
     */
    public void setPixelPositions() {

        // Speed of which the entity moves
        int d = getCurrentDisplacement();

        // First if : move towards destination,
        // Second if: check if overshot destination

        // Left
        if( xPixel > toPixel(xDestination) ) {
            setPixels( xPixel - d, yPixel );
            if( xPixel <= toPixel(xDestination) ) {
                setAll( xDestination, yDestination );
            }
        }

        // Right
        else if( xPixel < toPixel(xDestination) ) {
            setPixels( xPixel + d, yPixel );
            if( xPixel >= toPixel(xDestination) ) {
               setAll( xDestination, yDestination );
            }
        }

        // Up
        else if( yPixel > toPixel(yDestination) ) {
            setPixels( xPixel, yPixel - d );
            if( yPixel <= toPixel(yDestination) ) {
                setAll( xDestination, yDestination );
            }
        }

        // Down
        else if( yPixel < toPixel(yDestination) ) {
            setPixels( xPixel, yPixel + d );
            if( yPixel >= toPixel(yDestination) ) {
                setAll( xDestination, yDestination );
            }
        }

    }

    /**
     * Each entity will give a displacement. This is how fast the entity will move each loop.
     * @return
     */
    public abstract int getCurrentDisplacement();

    /**
     * Abstract method, each entity will move in it's own way. This checks legal moves and sets positions on a cell coordinate level.
     * @param maze
     * @param move Direction which to move.
     */
    public abstract void setCellPositions( MazeLayout maze, int move );

    // Gives pixel co-ordinate from cell co-ordinate
    public int toPixel( int i ) {
        if( i <= 1 ) {
            return (cellSize/2)-10;
        } else {
            return i * cellSize - (cellSize / 2) - 10;
        }
    }

    public int getXPixel() {
        return xPixel;
    }

    public int getYPixel() {
        return yPixel;
    }

    public void setPixels( int x, int y ) {
        this.xPixel = x;
        this.yPixel = y;
    }

    public void setAll( int x, int y ) {
        this.xPixel = toPixel(x);
        this.yPixel = toPixel(y);
        this.xCurrent = x;
        this.yCurrent = y;
    }

    public int getXCurrent() {
        return xCurrent;
    }

    public int getYCurrent() {
        return yCurrent;
    }

    public int getXDestination() {
        return xDestination;
    }

    public int getYDestination() {
        return yDestination;
    }

    public void setDestination(int x, int y) {
        this.xDestination = x;
        this.yDestination = y;
    }

}
