import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class interfaceGame {
    private JFrame frame;
    private Map<String, String> users;
    private int score;
    private boolean isLogin=false;

    public interfaceGame() {
        frame = new JFrame("Jet Fighter Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        users = new HashMap<>();
        score = 0;
        

        createMenu();
        frame.setVisible(true);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem registerItem = new JMenuItem("Register");
        JMenuItem loginItem = new JMenuItem("Login");
        JMenuItem playGameItem = new JMenuItem("Play Game");
        JMenuItem scoreTableItem = new JMenuItem("Scoretable");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(registerItem);
        fileMenu.add(loginItem);
        fileMenu.add(playGameItem);
        fileMenu.add(scoreTableItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        registerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        loginItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        playGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playGame();
            }
        });

        scoreTableItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScore();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout();
            }
        });
    }

    private void register() {
        String username = JOptionPane.showInputDialog(frame, "Enter username:");
        String password = JOptionPane.showInputDialog(frame, "Enter password:");
        if(username!=null && password!=null){
            users.put(username, password);
            JOptionPane.showMessageDialog(frame, "Registration Successful!");
        }else{
            JOptionPane.showMessageDialog(frame, "Username or Password can not be null");
        }
    }

    private void login() {
        String username = JOptionPane.showInputDialog(frame, "Enter username:");
        String password = JOptionPane.showInputDialog(frame, "Enter password:");

        if (users.containsKey(username) && users.get(username).equals(password)) {
            JOptionPane.showMessageDialog(frame, "Login Successful!");
            isLogin=true;
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void playGame() {
        // Implement game logic here
        if(isLogin==true){
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
        }else{
            JOptionPane.showMessageDialog(frame, "You must be login");
        }
    }

    private void showScore() {
        JOptionPane.showMessageDialog(frame, "Your Score: " + score);
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(frame, "Student Name: [Your Name]\nStudent Number: [Your Number]");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new interfaceGame();
            }
        });
    }
}
