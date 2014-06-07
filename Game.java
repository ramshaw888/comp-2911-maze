import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Game {

    // Layers
    static private JLayeredPane layers;
    static private JFrame window;

    // These are the layers that go into JLayeredPane.
    static private Maze maze;
    static private JPanel menu;
    static private JPanel difficultyMenu;
    static private JPanel highScoreMenu;
    static private JPanel gameControls;
    static private JPanel about;
    static private JPanel scoreboard;
    static private GameLabel scoreText;
    static private PlayerPanel playerPanel;
    static private JPanel background;
    static private BufferedImage img;
    static private BufferedImage srcImg;

    // Players
    static private ArrayList<Entity> players;

    // Size coordinates of the maze (x * y cells)
    static private int xMax;
    static private int yMax;

    // Square cell side length
    static private int cellSize;

    // Timing and scoring
    static private int score;
    static private int scoreMax;
    static private long startTime;
    static private long endTime;
    static private int difficulty;

    // High scores
    static private HighScores easyHighScores;
    static private HighScores mediumHighScores;
    static private HighScores hardHighScores;
    static private String playerName;

    // for game loop
    static private boolean running = false;

    // Theme
    static private boolean day = true;

    // keyboard presses, for the game loop.
    static private int keyPress = -1;

    // JFrame window dimensions
    static private int windowHeight;
    static private int windowWidth;

    // Initialises layers. These methods set the Java UI elements for the menus.

    static private void setMenu() {

        // Menu
        menu = new JPanel();
        menu.setBounds(0,0,windowWidth,windowHeight);
        menu.setLayout( new GridBagLayout() );
        menu.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 30;
        c.ipady = 20;
        c.gridx = 0;
        c.gridy = 0;

        GameButton singlePlayerButton = new GameButton("Start Game", 200,75);
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                layers.moveToFront(difficultyMenu);
                difficultyMenu.setVisible(true);
                menu.setVisible(false);
            }
        });
        menu.add(singlePlayerButton, c);

        c.gridy++;
        GameButton highScoreButton = new GameButton("High scores", 200, 75 );
        highScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                layers.moveToFront(highScoreMenu);
                menu.setVisible(false);
                highScoreMenu.setVisible(true);
                resize();
            }
        });
        menu.add(highScoreButton,c);


        c.gridy++;
        GameButton slider = new GameButton("Lights", 200, 75 );
        slider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                day = !day;
                resize();
            }
        });
        menu.add(slider,c);

        c.gridy++;
        GameButton aboutButton = new GameButton("About", 200, 75 );
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                layers.moveToFront(about);
                menu.setVisible(false);
                about.setVisible(true);
            }
        });
        menu.add(aboutButton,c);

        c.gridy++;
        GameButton exitButton = new GameButton("Quit", 200, 75);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        menu.add(exitButton,c);

        // Add to layers
        layers.add(menu, JLayeredPane.DEFAULT_LAYER);
        layers.moveToFront(menu);

    }

    private void setDifficultyMenu() {
        // Menu
        difficultyMenu = new JPanel();
        difficultyMenu.setBounds(0, 0, windowWidth, windowHeight);
        difficultyMenu.setOpaque(false);
        difficultyMenu.setVisible(false);
        difficultyMenu.setLayout( new GridBagLayout() );

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 30;
        c.ipady = 30;

        GameButton easyButton = new GameButton("Easy", 200, 75);
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                difficulty = 0;
                xMax = 10;
                yMax = 6;
                difficultyMenu.setVisible(false);
                startGame();
            }
        });


        // This does nothing at the moment
        GameButton mediumButton = new GameButton("Medium", 200, 75);
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                difficulty = 1;
                xMax = 14;
                yMax = 10;
                startGame();
                difficultyMenu.setVisible(false);
            }
        });

        GameButton hardButton = new GameButton("Hard", 200, 75);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                difficulty = 2;
                xMax = 20;
                yMax = 12;
                window.setMinimumSize( new Dimension( 1280, 800));
                startGame();
                difficultyMenu.setVisible(false);
            }
        });


        GameButton exitButton = new GameButton("Back", 200, 75);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.setVisible(true);
                layers.moveToFront(menu);
                difficultyMenu.setVisible(false);
            }
        });

        difficultyMenu.add(easyButton,c);
        difficultyMenu.add(mediumButton,c);
        difficultyMenu.add(hardButton,c);
        c.gridy++;
        difficultyMenu.add(exitButton,c);

        // Add to layers
        layers.add(difficultyMenu, JLayeredPane.DEFAULT_LAYER);
        layers.moveToFront(difficultyMenu);

    }

    private void setAbout() {
        about = new JPanel();
        about.setBounds(0,0,windowWidth,windowHeight);
        about.setLayout( new GridBagLayout() );
        about.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();

        c.ipady = 40;
        JLabel t1 = new JLabel();
        t1.setBackground( new Color(105, 1,0, 255) );
        t1.setText(" Maze game ");
        t1.setForeground(Color.WHITE);
        t1.setOpaque(true);

        t1.setFont( new Font("Helvetica", Font.BOLD, 20 ) );

        about.add( t1, c);
        c.gridy++;

        JLabel t2 = new JLabel(" COMP2911 Project - S1 2014 ");
        t2.setFont( new Font("Helvetica", Font.BOLD, 20 ) );
        t2.setBackground( new Color(105, 1,0, 255) );
        t2.setForeground(Color.WHITE);
        t2.setOpaque(true);
        c.gridy++;
        about.add( t2, c );

        JLabel t3 = new JLabel(" Aaron Ramshaw - Harry Gibbs - Aaron Carvalho - Rahul Aswani ");
        t3.setFont( new Font("Helvetica", Font.BOLD, 20 ) );
        t3.setBackground( new Color(105, 1,0, 255) );
        t3.setForeground(Color.WHITE);
        t3.setOpaque(true);
        c.gridy++;
        about.add( t3, c );

        c.gridy++;
        JLabel t4 = new JLabel(" ");
        about.add( t4, c);

        c.gridy++;

        GameButton exitButton = new GameButton("Back", 250, 75);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.setVisible(true);
                layers.moveToFront(menu);
                about.setVisible(false);
            }
        });
        about.add(exitButton,c);
        layers.add( about, JLayeredPane.DEFAULT_LAYER );
    }

    static private void setHighScoreMenu() {
        highScoreMenu = new JPanel();
        highScoreMenu.setBounds(0,0,windowWidth,windowHeight);
        highScoreMenu.setLayout( new GridBagLayout() );
        highScoreMenu.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        Scanner sc = null;
        easyHighScores = new HighScores();
        mediumHighScores = new HighScores();
        hardHighScores = new HighScores();


        for( int i = 0; i < 10; i++ ) {
            easyHighScores.setScore( "Blank", new Long(999) );
            mediumHighScores.setScore( "Blank", new Long(999) );
            hardHighScores.setScore( "Blank", new Long(999) );
        }

        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 40;
        GameLabel easyLabel = new GameLabel("Easy", 250,100);
        highScoreMenu.add(easyLabel,c);
        c.gridx++;
        GameLabel mediumLabel = new GameLabel("Medium", 250, 100);
        highScoreMenu.add(mediumLabel,c);
        c.gridx++;
        GameLabel hardLabel = new GameLabel("Hard", 250, 100);
        highScoreMenu.add(hardLabel,c);

        // Display tables of scores
        c.gridy = 1;
        c.gridx = 0;
        for( int i = 0; i < 10; i++ ) {
            GameLabel e;
            if (!easyHighScores.exists(i)){
                e = new GameLabel( "No score" , 250, 50, 15, true);
            } else {
                e = new GameLabel( (i+1)+". "+easyHighScores.getName(i)+" "+easyHighScores.getScore(i), 250, 50, 15, true);
            }
            highScoreMenu.add(e, c);
            c.gridy++;
        }

        c.gridy = 1;
        c.gridx = 1;
        for( int i = 0; i < 10; i++ ) {
            GameLabel m;
            if (!mediumHighScores.exists(i)){
                m = new GameLabel( "No score" , 250, 50, 15, true);
            } else {
                m = new GameLabel( (i+1)+". "+mediumHighScores.getName(i)+" "+mediumHighScores.getScore(i), 250, 50, 15, true);
            }
            highScoreMenu.add(m, c);
            c.gridy++;
        }

        c.gridy = 1;
        c.gridx = 2;
        for( int i = 0; i < 10; i++ ) {
            GameLabel h;
            if (!hardHighScores.exists(i)){
                h = new GameLabel( "No score" , 250, 50, 15, true);
            } else {
                h = new GameLabel( (i+1)+". "+hardHighScores.getName(i)+" "+hardHighScores.getScore(i), 250, 50, 15, true);
            }
            highScoreMenu.add(h, c);
            c.gridy++;
        }
        c.gridy++;
        c.gridx = 1;

        GameButton exitButton = new GameButton("Back", 250, 75);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.setVisible(true);
                layers.moveToFront(menu);
                difficultyMenu.setVisible(false);
                highScoreMenu.setVisible(false);
            }
        });
        highScoreMenu.add(exitButton,c);

        highScoreMenu.setBounds(0, 0, windowWidth, windowHeight);
        layers.add( highScoreMenu, JLayeredPane.DEFAULT_LAYER );
        highScoreMenu.setVisible(false);
    }

    /**
     * When new high score is set, refresh the high score page.
     */
    static private void refreshHighScore() {
        highScoreMenu = new JPanel();
        highScoreMenu.setBounds(0,0,windowWidth,windowHeight);
        highScoreMenu.setLayout( new GridBagLayout() );
        highScoreMenu.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        Scanner sc = null;

        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 40;
        GameLabel easyLabel = new GameLabel("Easy", 250,100);
        highScoreMenu.add(easyLabel,c);
        c.gridx++;
        GameLabel mediumLabel = new GameLabel("Medium", 250, 100);
        highScoreMenu.add(mediumLabel,c);
        c.gridx++;
        GameLabel hardLabel = new GameLabel("Hard", 250, 100);
        highScoreMenu.add(hardLabel,c);
        c.gridy = 1;
        c.gridx = 0;
        for( int i = 0; i < 10; i++ ) {
            GameLabel e;
            if (!easyHighScores.exists(i)){
                e = new GameLabel( "No score" , 250, 50, 15, true);
            } else {
                e = new GameLabel( (i+1)+". "+easyHighScores.getName(i)+" "+easyHighScores.getScore(i), 250, 50, 15, true);
            }
            highScoreMenu.add(e, c);
            c.gridy++;
        }

        c.gridy = 1;
        c.gridx = 1;
        for( int i = 0; i < 10; i++ ) {
            GameLabel m;
            if (!mediumHighScores.exists(i)){
                m = new GameLabel( "No score" , 250, 50, 15, true);
            } else {
                m = new GameLabel( (i+1)+". "+mediumHighScores.getName(i)+" "+mediumHighScores.getScore(i), 250, 50, 15, true);
            }
            highScoreMenu.add(m, c);
            c.gridy++;
        }

        c.gridy = 1;
        c.gridx = 2;
        for( int i = 0; i < 10; i++ ) {
            GameLabel h;
            if (!hardHighScores.exists(i)){
                h = new GameLabel( "No score" , 250, 50, 15, true);
            } else {
                h = new GameLabel( (i+1)+". "+hardHighScores.getName(i)+" "+hardHighScores.getScore(i), 250, 50, 15, true);
            }
            highScoreMenu.add(h, c);
            c.gridy++;
        }
        c.gridy++;
        c.gridx = 1;

        GameButton exitButton = new GameButton("Back", 250, 75);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                menu.setVisible(true);
                layers.moveToFront(menu);
                difficultyMenu.setVisible(false);
                highScoreMenu.setVisible(false);
            }
        });
        highScoreMenu.add(exitButton,c);

        highScoreMenu.setBounds(0, 0, windowWidth, windowHeight);
        layers.add( highScoreMenu, JLayeredPane.DEFAULT_LAYER );
        highScoreMenu.setVisible(false);

    }

    /**
     * Loads the resizable background image.
     */
    static private void setBackground() {
        windowHeight = window.getHeight();
        windowWidth = window.getWidth();

        srcImg = null;
        try {
            if( day ) {
                srcImg = ImageIO.read(new File("resources/day.png"));
            } else {
                srcImg = ImageIO.read(new File("resources/night.png"));
            }
        } catch (IOException e) {
        }

        // Set img to scaled srcImg and add to background.
        img = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, windowWidth, windowHeight, null);
        g2.dispose();

        JLabel bLabel = new JLabel(new ImageIcon(img));
        bLabel.setBounds(0, 0, windowWidth, windowHeight);

        background = new JPanel();
        background.setLayout(null);
        background.add(bLabel);
        background.setBounds(0, 0, windowWidth, windowHeight);

        layers.add(background, new Integer(0));

        // ComponentListener to enable change size when window is re-sized.
        window.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                windowHeight = window.getHeight();
                windowWidth = window.getWidth();
                resize();
            }
            @Override
            public void componentMoved(ComponentEvent componentEvent) { }

            @Override
            public void componentShown(ComponentEvent componentEvent) { }

            @Override
            public void componentHidden(ComponentEvent componentEvent) { }
        });

    }

    /**
     * Resizes everything in the JFrame according to the JFrame size. Called on resize listener.
     */
    static private void resize() {

        try {
            if( day ) {
                srcImg = ImageIO.read(new File("resources/day.png"));
                window.setBackground( new Color( 253,220,201));
            } else {
                srcImg = ImageIO.read(new File("resources/night.png"));
                window.setBackground( new Color(16,44,90) );
            }
        } catch (IOException e) {
        }

        img = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, windowWidth, windowHeight, null);
        g2.dispose();

        // Use JLabel to display re-sized background image
        JLabel bLabel = new JLabel(new ImageIcon(img));
        bLabel.setBounds(0, 0, windowWidth, windowHeight);
        background.removeAll();
        background.setLayout(null);
        background.add(bLabel);
        background.setBounds(0, 0, windowWidth, windowHeight);

        if( running ) {
            maze.setBounds(0, 0, windowWidth, windowHeight);
            gameControls.setSize(new Dimension(windowWidth, windowHeight));
            scoreboard.setSize(new Dimension(windowWidth, windowHeight));
            scoreboard.setBounds( windowWidth/2 + xMax*cellSize/2 + 50, windowHeight/2-50, 150,50 );
            playerPanel.setBounds(windowWidth/2 - (cellSize * (xMax+1) / 2), windowHeight/2 - (cellSize * (yMax+1) / 2), cellSize * (xMax), cellSize * (yMax));
            maze.revalidate();
            difficultyMenu.revalidate();
        } else {
            menu.setSize(new Dimension(windowWidth, windowHeight));
            difficultyMenu.setSize(new Dimension(windowWidth, windowHeight));
            menu.revalidate();
            difficultyMenu.revalidate();
        }

    }

    /**
     * Buttons and scoreboard which are present during game play. These are on a separate layer to the maze and players.
     * This is due to enable easy resizing.
     */
    static private void setGameControls() {

        GridBagLayout gbl = new GridBagLayout();

        gameControls = new JPanel( gbl );
        GridBagConstraints c = new GridBagConstraints();

        gbl.rowWeights = new double[] {1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};

        c.gridy = 0;
        c.ipadx = 30;
        c.ipady = 50;

        c.anchor = GridBagConstraints.SOUTH;
        GameButton quitButton = new GameButton("Quit", 200, 75);
        GameButton newGameButton = new GameButton("New Game", 200, 75);
        GameButton menuButton = new GameButton("Menu", 200, 75);

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearGame();
                menu.setVisible(true);
                layers.moveToFront(menu);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearGame();
                startGame();
            }
        });

        gameControls.add(menuButton, c);
        gameControls.add(newGameButton, c);
        gameControls.add(quitButton, c);

        scoreboard = new JPanel( );
        scoreText = new GameLabel("Scoreboard", 150, 50);
        scoreboard.add(scoreText);

        // add to layer
        gameControls.setBounds(0, 0, windowWidth, windowHeight);
        gameControls.setVisible(false);
        gameControls.setOpaque(false);
        scoreboard.setBounds( windowWidth/2 + xMax*cellSize/2 + 50, windowHeight/2-50, 150,50 );
        scoreboard.setVisible(false);
        scoreboard.setOpaque(false);
        layers.add(gameControls, JLayeredPane.DEFAULT_LAYER);
        layers.add(scoreboard, JLayeredPane.DEFAULT_LAYER);

    }

    /**
     * Generates and sets a new random maze, using MazeLayout.
     */
    static private void setMaze() {

        maze = new Maze(cellSize, xMax, yMax);
        maze.setBounds(0, 0, windowWidth, windowHeight);
        maze.setVisible(false);
        layers.add(maze, new Integer(11));

    }

    /**
     * Sets the player layer. The player layer is separate so that only the entities are refreshed.
     */
    static private void setPlayers() {

        // This is the user player.
        players = new ArrayList<Entity>();
        Player p = new Player(1,1, cellSize);
        players.add( p );

        Random r = new Random();
        scoreMax = xMax/2;

        // Adds gold to the maze in random locations.
        // player wins when all gold are collected (scoreMax)
        for( int i = 0; i < scoreMax+1; i++ ) {
            int ra = r.nextInt();
            if (ra < 0) ra = ra * -1;

            Gold g = new Gold(ra % (xMax - 1) + 1, ra % (yMax - 1) + 2, cellSize);
            players.add(g);
        }

        playerPanel = new PlayerPanel();

        // Treasure to finish the game
        Treasure win = new Treasure( xMax, yMax, cellSize );
        players.add(win);

        // Set playerPanel to be exactly the size of the maze assuming 50px square cells.
        // Player panel aligns perfectly over the maze, but is transparent.
        playerPanel.setBounds(windowWidth/2 - (cellSize * (xMax+1) / 2), windowHeight/2 - (cellSize * (yMax+1) / 2), cellSize * (xMax), cellSize * (yMax));
        layers.add(playerPanel, new Integer(0));

    }

    /**
     * keyboard listener for key presses.
     * This flags the keyPress which is given to the entities through the playerPanel.
     */
    private void initialiseKeyboard() {

        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();


                // flags the booleans, for game loop to act upon, then un-flag.
                switch (key) {
                    case KeyEvent.VK_UP:
                        keyPress = 3;
                        break;

                    case KeyEvent.VK_DOWN:
                        keyPress = 2;
                        break;

                    case KeyEvent.VK_LEFT:
                        keyPress = 0;
                        break;

                    case KeyEvent.VK_RIGHT:
                        keyPress = 1;
                        break;

                    case KeyEvent.VK_SPACE:
                        keyPress = 4;
                        break;
                }

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }

        });

    }

    /**
     * Resets the game
     */
    static private void clearGame() {
        menu.setVisible(true);
        maze.setVisible(false);
        playerPanel.setVisible(false);
        gameControls.setVisible(false);
        scoreboard.setVisible(false);
        running = false;
        resize();
        score = 0;

    }

    /**
     * Begins the game
     */
    static private void startGame(){

        score = 0;
        menu.setVisible(false);
        setPlayers();
        setMaze();
        setGameControls();
        maze.setVisible(true);
        playerPanel.setVisible(true);
        layers.moveToFront(playerPanel);
        gameControls.setVisible(true);
        scoreboard.setVisible(true);
        layers.moveToFront(gameControls);
        layers.moveToFront(scoreboard);
        running = true;
        resize();
        startTime = System.currentTimeMillis();

    }

    /**
     * When the player wins, display a blue winning screen.
     * If high score, then wait for name.
     * Then goes back to the main menu.
     * @param time Length of the game, used in highscore.
     */
    static private void playerWins( Long time ) {

        JPanel redDead = new JPanel( new GridBagLayout() );
        redDead.setBackground(Color.BLUE);
        redDead.setOpaque(true);
        redDead.setBounds(0,0,windowWidth, 1);
        window.add(redDead, new Integer(20));

        // Slides blue screen down.
        for( int i = 1; i < windowHeight; i++ ) {
            redDead.setBounds(0, 0, windowWidth, i);
            redDead.revalidate();
            try {
                Thread.sleep(2);
            } catch (Exception e) {
            }
        }

        JLabel gameOver = new JLabel("You win");
        gameOver.setForeground(Color.WHITE);
        gameOver.setFont( new Font("Courier New", Font.BOLD, 100));
        redDead.add(gameOver, new GridBagConstraints());
        redDead.revalidate();

        // Listener to check for enter press on name entry.
        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if( key == KeyEvent.VK_ENTER ) {
                    JTextField f = (JTextField) e.getSource();
                    playerName = f.getText();
                }
            }
        };


        HighScores hs = null;
        if( difficulty == 0 ) {
            hs = easyHighScores;
        } else if( difficulty == 1 ) {
            hs = mediumHighScores;
        } else {
            hs = hardHighScores;
        }

        // Checks if score has made it to the high score board
        if( new Long(time/1000) < hs.getScore(9) ) {
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = 1;
            c.ipady = 50;
            JLabel h = new JLabel("New High Score");
            h.setForeground(Color.WHITE);
            h.setFont( new Font("Courier New", Font.BOLD, 100));
            redDead.add(h, c);

            final JTextField field = new JTextField("Enter name");
            field.setPreferredSize( new Dimension( 300, 75 ));
            field.setVisible(true);
            field.setEditable(true);
            field.setBackground(Color.WHITE);
            field.setForeground(Color.BLACK);
            field.setOpaque(true);
            field.setFont(new Font("Helvetica", Font.BOLD, 30));

            playerName = "";

            field.addKeyListener(listener);

            c.gridy++;
            redDead.add(field, c);
            redDead.revalidate();
            field.requestFocusInWindow();

            // Loop until name has been entered
            while( playerName.equals("") ) { field.requestFocusInWindow(); }

            // Maxiumum one word
            String[] temp = playerName.split("\\s+");
            playerName = temp[0];

            // Add new high score
            hs.addNew( playerName, new Long(time/1000) );
            refreshHighScore();
            redDead.revalidate();
        } else { // No high score set, just show this screen for a little bit.
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }

        // Go back to main menu
        window.remove(redDead);
        clearGame();
    }

    /**
     * This loops constantly checking for changes, and delegating information to classes.
     * Mainly refreshes the player layer with new positions and information.
     */
    static private void gameLoop() {

        // Score timer.
        long start = -1;
        long end = -1;

        while( true ) {
            if (running) {

                endTime = System.currentTimeMillis();

                // update score timer
                scoreText.setText( new String( new Long(TimeUnit.MILLISECONDS.toSeconds(endTime-startTime)).toString()+":"+ String.format("%03d", ((endTime-startTime)%1000))) );
                start = System.currentTimeMillis();

                // make sure in focus for keyboard inputs.
                window.requestFocusInWindow();

                // Tells players about key press
                playerPanel.keyPress(keyPress);
                keyPress = -1;

                // Set new positions
                playerPanel.setNewPositions(players, maze.getMazeLayout());

                // Draw new positions
                int collision = playerPanel.drawNewPositions(players);

                // Check for collisions
                if( collision == 1 ) {
                    score++;
                    //playerDies();
                } else if ( collision == 0 ) {
                    if( score >= scoreMax-1 ) {
                        playerWins( new Long( endTime-startTime ) );
                    }
                }

            }

            // controls frame rate / re-draw/re-paint rate
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }

    }

    /**
     * Initialises game
     */
    private Game() {

        // set maze size.
        this.xMax = 15;
        this.yMax = 8;

        this.cellSize = 40;

        // initialise frame
        window = new JFrame("Hektik maze game");
        window.setMinimumSize( new Dimension(1280,800));
        window.setBackground( new Color( 253,220,201));

        // initialise layers
        layers = new JLayeredPane();
        window.setContentPane(layers);

        // initialise background
        setBackground();

        // initialise menu
        setMenu();

        // initialise difficulty layer
        setDifficultyMenu();

        // initialises about page
        setAbout();

        // initialise high score menu
        setHighScoreMenu();

        // initialise keyboard input
        initialiseKeyboard();


        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

    }

    public static void main(String args[]) {
        // initialises game, then starts loop.
        Game g = new Game();
        g.gameLoop();
    }

}