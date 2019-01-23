package gui;

import javax.swing.JFrame;
import core.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Fitsum, Alex Liao
 */
public class SnakeGUI extends JFrame {
    /**
     * The game (a new one is created every reset)
     */
    private Game game;
    /**
     * The panel (composition class since rendering in the JFrame is unadvised)
     */
    private final SnakePanel panel;
    /**
     * The information label at the top of the screen
     */
    private final JLabel info;
    
    /**
     * The state of each cell (0 = blank, 1 = snake, 2 = apple)
     */
    public final int[][] grid = new int[100][60];
    
    /**
     * Render system pauses and displays a game over in the info bar if the game is over
     */
    public boolean gameover = false;
    
    /**
     * Construct a new window
     */
    public SnakeGUI() {
        // The game is 100 x 60 and extends the snake by 3 spaces each apple eaten
        this.game = new Game(100, 60, 3);
        this.info = new JLabel();
        this.panel = this.new SnakePanel();
        
        // Wait for the user to press SPACE to start the game
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                // START THE GAME!
                // Tell the graphics renderer to start the game
                SnakeGUI.this.panel.started = true;
                // Transfer keypresses to the game handler
                SnakeGUI.this.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent ke) {
                        if (ke.getKeyCode() == KeyEvent.VK_SPACE && SnakeGUI.this.gameover) {
                            SnakeGUI.this.gameover = false;
                            SnakeGUI.this.game = new Game(100, 60, 3);
                        } else {
                            SnakeGUI.this.game.registerKeypress(ke.getKeyCode());
                        }
                    }
                });

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        SnakeGUI.this.game.advanceState();
                        SnakeGUI.this.game.draw(SnakeGUI.this);
                        SnakeGUI.this.panel.update(SnakeGUI.this.panel.getGraphics());
//                        SnakeGUI.this.update(SnakeGUI.this.getGraphics());
                    }
                }, 0, 50);
                SnakeGUI.this.removeKeyListener(this);
            }
        });
    }
    
    /**
     * Composition class which is a JPanel contained in the GUI to render the game
     */
    private final class SnakePanel extends JPanel {
        /**
         * Whether or not the game has started
         */
        private boolean started = false;
        /**
         * The instructions image (loaded by constructor)
         */
        private final BufferedImage instructions;
        
        /**
         * Create a new empty SnakePanel (must belong to a GUI class) and locate the instructions image
         */
        public SnakePanel() {
            // Read the instructions image for display when the game is open and not started
            try {
                instructions = ImageIO.read(new File("instructions.png"));
            } catch (IOException e) {
                throw new RuntimeException("I/O Error: could not read instructions.png", e);
            }
        }
        
        /**
         * Display the game onto the screen
         * 
         * @param graphics the renderer provided by the JVM
         */
        @Override
        public void paintComponent(Graphics graphics) {
            if (started) {
                // Display the score, pause state, and game over state in the info label
                SnakeGUI.this.info.setText(" Score: " + SnakeGUI.this.game.grid.getScore() + (SnakeGUI.this.game.paused ? " [paused]" : "") + (SnakeGUI.this.gameover ? " [game over, press space to reset]" : ""));
                // Render onto an image to avoid lag
                BufferedImage image = new BufferedImage(1000, 600, BufferedImage.TYPE_INT_ARGB);
                Graphics g = image.getGraphics();
                // Fill background black
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 1000, 600);
                // For each grid space
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j < 60; j++) {
                        // Set the color based on the data point (blank, snake, apple)
                        switch (SnakeGUI.this.grid[i][j]) {
                            case 0:
                                g.setColor(Color.BLACK);
                                break;
                            case 1:
                                g.setColor(Color.GREEN);
                                break;
                            default:
                                g.setColor(Color.RED);
                                break;
                        }
                        // Fill the appropriate position in the JPanel
                        g.fillRect(i * 10, j * 10, 9, 9);
                    }
                }
                // Draw a border
                g.setColor(Color.WHITE);
                g.drawRect(0, 0, 1000, 600);
                // Render the image onto the canvas
                graphics.drawImage(image, 0, 0, 1000, 600, null);
            } else {
                // If the game has not yet started, render the instructions image onto the panel
                graphics.drawImage(this.instructions, 0, 0, 1000, 600, null);
            }
        }
    }
    
    /**
     * Initialize the GUI and its components
     */
    public void init() {
        // Size needs to be a bit larger than expected due to JFrame borders
        this.setSize(1005, 675);
        // Exit program when the window is closed
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Using GridBagLayout to allow scaling of items arranged in a grid-like layout
        this.setLayout(new GridBagLayout());
        // Label background color is black to match the rest of the window
        this.info.setBackground(Color.BLACK);
        this.info.setOpaque(true);
        // Label text white and in monospace size 12
        this.info.setForeground(Color.WHITE);
        this.info.setFont(new Font("Courier New", 0, 18));
        // Info size is 1000 x 50
        this.info.setSize(1000, 50);
        this.info.setMinimumSize(new Dimension(1000, 50));
        this.info.setText(" Waiting for user...");
        // Add the two elements in the desired positions, following the GridBagLayout
        this.add(this.info, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(this.panel, new GridBagConstraints(0, 1, 1, 1, 1, 12, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.panel.setSize(1000, 600);
        this.panel.setMinimumSize(new Dimension(1000, 600));
        // Double-buffering reduces lag (or what looks like lag)
        this.panel.setDoubleBuffered(true);
        // Don't allow the window to be resized (pixel errors may occur if the size isn't right)
        this.setResizable(false);
        // Finally, show the window
        this.setVisible(true);
    }
    
    /**
     * Main function
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Create and initialize the game
        SnakeGUI gui = new SnakeGUI();
        gui.init();
    }
}
