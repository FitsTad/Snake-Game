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
    private Game game;
    private final SnakePanel panel;
    private final JLabel info;
    
    public final int[][] grid = new int[100][60];
    
    public boolean gameover = false;
    
    public SnakeGUI() {
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
    
    private final class SnakePanel extends JPanel {
        private boolean started = false;
        private final BufferedImage instructions;
        
        public SnakePanel() {
            try {
                instructions = ImageIO.read(new File("instructions.png"));
            } catch (IOException e) {
                throw new RuntimeException("I/O Error: could not read instructions.png", e);
            }
        }
        
        @Override
        public void paintComponent(Graphics graphics) {
            if (started) {
                SnakeGUI.this.info.setText(" Score: " + SnakeGUI.this.game.grid.getScore() + (SnakeGUI.this.game.paused ? " [paused]" : "") + (SnakeGUI.this.gameover ? " [game over, press space to reset]" : ""));
                BufferedImage image = new BufferedImage(1000, 600, BufferedImage.TYPE_INT_ARGB);
                Graphics g = image.getGraphics();
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 1000, 600);
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j < 60; j++) {
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
                        g.fillRect(i * 10, j * 10, 9, 9);
                    }
                }
                graphics.drawImage(image, 0, 0, 1000, 600, null);
            } else {
                graphics.drawImage(this.instructions, 0, 0, 1000, 600, null);
            }
        }
    }
    
    public void init() {
        this.setSize(1005, 675);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.info.setBackground(Color.BLACK);
        this.info.setOpaque(true);
        this.info.setForeground(Color.WHITE);
        this.info.setFont(new Font("Courier New", 0, 12));
        this.info.setSize(1000, 50);
        this.info.setMinimumSize(new Dimension(1000, 50));
        this.info.setText(" Waiting for user...");
        this.add(this.info, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(this.panel, new GridBagConstraints(0, 1, 1, 1, 1, 12, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.panel.setSize(1010, 600);
        this.panel.setMinimumSize(new Dimension(1010, 600));
        this.panel.setDoubleBuffered(true);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        SnakeGUI gui = new SnakeGUI();
        gui.init();
    }
}
