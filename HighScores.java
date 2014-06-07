import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores high scores.
 */
public class HighScores {

    private ArrayList<String> names;
    private HashMap<String, Long> scores;


    public HighScores() {
        names = new ArrayList<String>();
        scores = new HashMap<String, Long>();
    }

    /**
     * In order, adds scores from text.
     * @param name
     * @param score
     */
    public void setScore( String name, Long score ) {
        names.add(name);
        scores.put(name, score);
    }

    public String getName( int i ) {
        return names.get(i);
    }

    public Long getScore( int i ) {
        return scores.get( names.get(i) );
    }

    /**
     * Add a new player to the highscore list
     * @param name
     * @param f
     */
    public void addNew( String name, Long f ) {
        for( int i = 0; i < names.size(); i++  ) {
            if( scores.get( names.get(i) ) > f ) {
                names.add(i, name);
                scores.put(name, f);
                break;
            }
        }
    }

    public boolean exists(int i){
        return names.size() > i;
    }
}
