
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JetFighterGame {
    private int borderCrossCount;
    private JFrame frame;
    private GamePanel gamePanel;
    private Timer gameTimer;
    //ARKAPLAN FOTOĞRAFI PATH'I
    private static final String BACKGROUND_IMAGE_PATH = "C:\\Users\\Metehan\\Documents\\Kod\\image\\bb62f8c8-8893-4ffa-ba4c-61b2a8051187.jfif";
    private int jetX, jetY;
    private int enemyX, enemyY;
    private int score;
    private int bulletX, bulletY;
    private int lives; 
    private boolean bulletFired;
    private static final int BULLET_SPEED = 10;
    private BufferedImage backgroundImage;
    private BufferedImage airplaneImage;
    private BufferedImage airplaneImage2;
    
        
    public JetFighterGame() throws IOException {
        frame = new JFrame("Jet Fighter Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
        
        gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        gameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateGame();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        initializeGame();

        frame.setVisible(true);
    }

    private void initializeGame() throws IOException {
        jetX = 100;
        jetY = 300;
        enemyX = 1000;
        enemyY = 315;
        score = 0;
        borderCrossCount = 0;
        lives = 3; 
        
        //BIRINCI UCAK FOTOĞRAFI PATH'I
        airplaneImage = ImageIO.read(new File("C:\\Users\\Metehan\\Documents\\Kod\\image\\alliance1.png"));
        //IKINCI UCAK FOTOĞRAFI PATH'I
        airplaneImage2 = ImageIO.read(new File("C:\\Users\\Metehan\\Documents\\Kod\\image\\enemy1.png"));
        gameTimer.start();
    }

    private void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            shoot();
        } else if (keyCode == KeyEvent.VK_W) {
            moveJetUp();
        } else if (keyCode == KeyEvent.VK_S) {
            moveJetDown();
        }
    }

    private void updateGame() throws IOException {
        moveEnemy();
        moveBullet();
        checkCollision();
        gamePanel.repaint();
    }

    private void moveEnemy() throws IOException  {
         if (enemyX > 0) {
        enemyX -= 8;
    } else {
        enemyX = 1100;
        enemyY = (int) (Math.random() * 700);
        borderCrossCount++;

        // Check if the enemy has crossed the border three times
        if (borderCrossCount >= 3) {
            JOptionPane.showMessageDialog(frame, "Game Over! Your Score: " + score);
            initializeGame();
        }
    }
    }

    private void shoot() {
        if (!bulletFired) {
            bulletX = jetX +30;
            bulletY = jetY + 30;
            bulletFired = true;
        }
    }

    private void moveJetUp() {
        if (jetY > 0) {
            jetY -= 20;
        }
    }

    private void moveJetDown() {
        if (jetY < 700) {
            jetY += 20;
        }
    }

    
    private void moveBullet() {
        if (bulletFired) {
            bulletX += BULLET_SPEED;

            // Check if the bullet went off-screen
            if (bulletX > 1200) {
                bulletFired = false; // Reset the bullet
            }
        }
    }
    

    private void checkCollision() throws IOException {
        int distance = Math.abs(jetX - enemyX);
        if (distance < 20 && Math.abs(jetY - enemyY) < 20) {
            lives--; // Yeni: Can azalt
            if (lives <= 0) {
                JOptionPane.showMessageDialog(frame, "Game Over! Your Score: " + score);
                initializeGame();
            } else {
                // Yeni: Can varsa düşmanı sıfırla
                enemyX = 1100;
                enemyY = (int) (Math.random() * 700);
            }
        }
        checkBulletCollision();
    }
    private void checkBulletCollision() {
        if (bulletFired && bulletX > enemyX && bulletX < enemyX + 100 && bulletY > enemyY && bulletY < enemyY + 100) {
            enemyX = 1100;
            enemyY = (int) (Math.random() * 700);
            score++;
            bulletFired = false; // Reset the bullet
        }
    }
    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0-0, 1200, 800, null);
                
            }
            // Draw jet
            g.setColor(Color.BLUE);
            g.fillRect(jetX, jetY, 130, 60);
            if (airplaneImage != null) {
                g.drawImage(airplaneImage, jetX, jetY, 130, 60, null);
            }
            // Draw enemy
            g.setColor(Color.RED);
            g.fillRect(enemyX, enemyY, 130, 60);
            if (airplaneImage2 != null) {
                g.drawImage(airplaneImage2, enemyX, enemyY, 130, 60, null);
            }
            if (bulletFired) {
                g.setColor(Color.BLACK);
                g.fillRect(bulletX, bulletY, 5, 5);
            }

            // Draw score
            g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new JetFighterGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
