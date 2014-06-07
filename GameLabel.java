import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Custom label
 */
public class GameLabel extends JLabel {

    private int x;
    private int y;
    private int fontSize = 20;
    private boolean left = false;

    /**
     * Custom label
     * @param title Label text
     * @param x Label width
     * @param y Label height
     */
    public GameLabel(String title, int x, int y) {
        super(title);
        setPreferredSize(new Dimension(x, y) );
        this.x = x;
        this.y = y;
    }

    /**
     * Custom label, used in the high score table, boolean left is for left aligned text
     * @param title Label text
     * @param x Label width
     * @param y Label height
     * @param fontSize Specifies font size
     * @param left Specifies left align
     */
    public GameLabel(String title, int x, int y, int fontSize, boolean left ) {
        super(title);
        setPreferredSize(new Dimension(x, y) );
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.left = true;
    }

    /**
     * Paint the GameLabel
     */
    @Override
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor( new Color(105, 1,0, 255));
        Rectangle2D r = new Rectangle2D.Double(0, 0,x-1,y-1);
        g2d.fill(r);

        g2d.setColor(Color.white);
        g2d.setFont( new Font("Helvetica", Font.BOLD, fontSize));
        int stringLen = (int) g2d.getFontMetrics().getStringBounds(getText(), g2d).getWidth();
        int start = -1;

        // Left align used in the High score display
        if( left ) {
            start = 10;
            String[] words = null;
            words = getText().split("\\s+");
            g2d.drawString( String.format("%s   %s", words[0], words[1]), start, (int) (y*0.6));
            g2d.drawString( words[2]+" s", x-60, (int) (y*0.6) );

        }

        // Centered text
        else {
            start = x / 2 - stringLen / 2;
            g2d.drawString( getText(), start, (int) (y*0.6));

        }


    }
}
