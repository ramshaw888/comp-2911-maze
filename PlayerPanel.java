import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Layer which holds the players and manages movements.
 */
public class PlayerPanel extends JPanel {

    private int keyPress = -1;

    public PlayerPanel() {
        // null for pixel layout
        this.setLayout( null );
        setOpaque(false);
    }

    /**
     * Sets bounds (layer positions) of all the players and paints them onto the layer.
     * Manages collisions between player and golds.
     * @param players Array of players to be painted.
     * @return returns flag of collision.
     */
    public int drawNewPositions( ArrayList<Entity> players ) {
        int collision = -1;
        // Collision
        // 0 is win
        // 1 is score increment

        Player pl = null;

        // When collisions, remove the gold.
        ArrayList<Entity> toRemove = new ArrayList<Entity>();

        for( Entity e: players ) {

            // Set positions on pane.
            e.setBounds(e.getXPixel(), e.getYPixel() ,100,100 );
            this.add(e);

            if( e instanceof Player ) {
                pl = (Player) e;
            } else if( e instanceof Gold ) {
                if( e.getXCurrent() == pl.getXCurrent() && e.getYCurrent() == pl.getYCurrent() ) {
                    collision = 1;
                    toRemove.add(e);
                }
            } else if( e instanceof Treasure ) {
                if( e.getXCurrent() == pl.getXCurrent() && e.getYCurrent() == pl.getYCurrent() ) {
                    collision = 0;
                }
            }
            repaint();
        }

        // Remove collided entities
        for( Entity e: toRemove ) {
            e.setVisible(false);
            players.remove(e);
        }

        return collision;
    }

    /**
     * Sets new cell positions, delegates to entity specific methods to check for legal moves etc.
     * @param entities list of entities on panel
     * @param layout MazeLayout
     */
    public void setNewPositions( ArrayList<Entity> entities, MazeLayout layout ) {


        for( Entity e: entities ) {
            if( e instanceof Player ) {
                e.setCellPositions(layout, keyPress);
            } else {
                e.setCellPositions(layout, -1);
            }
            e.setPixelPositions();
        }

        keyPress = -1;

    }

    /**
     * Flags keyPress for moves.
     * @param keyPress 0 - left, 1 - right, 2 - down, 3 - up
     */
    public void keyPress( int keyPress ) {
        this.keyPress = keyPress;
    }

}