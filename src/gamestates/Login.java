package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.awt.event.FocusAdapter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Cursor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import main.Game;
import model.SigninDatabaseUtil;
import utilz.LoadSave;

public class Login extends State implements Statemethods {

    private Game game;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private String message;
    private JButton viewPasswordButton;
    private JLabel signupLink;;
    private BufferedImage backgroundImg, backgroundImgPink;
    private int menuX, menuY, menuWidth, menuHeight;
    private boolean componentsInitialized;

    public Login(Game game) {
        super(game);
        this.game = game;
        loadBackground();
        initUI();
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.Login);
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (25 * Game.SCALE);
    }

    synchronized void initUI() {
        if (componentsInitialized) return;

        SwingUtilities.invokeLater(() -> {
            int fieldWidth = 250;       // Width of the input field
            int fieldHeight = 40;       // Height of the input field
            int buttonWidth = 200;      // Button width slightly smaller than the field width
            int buttonHeight = 40;      // Button height matches the field height
            int spacing = 15;           // Spacing between elements
            Color textColor = new Color(169, 169, 169);

            usernameField = new JTextField("Enter Username");
            usernameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (140 * Game.SCALE), fieldWidth, fieldHeight);
            usernameField.setFocusable(true);
            usernameField.setForeground(textColor);
            usernameField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (usernameField.getText().equals("Enter Username")) {
                        usernameField.setText("");
                    }
                }
    
                public void focusLost(FocusEvent e) {
                    if (usernameField.getText().isEmpty()) {
                        usernameField.setText("Enter Username");
                    }
                }
            });

            passwordField = new JPasswordField("Enter Password");
            passwordField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (180 * Game.SCALE), fieldWidth, fieldHeight);
            passwordField.setFocusable(true);
            passwordField.setForeground(textColor);
            passwordField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (String.valueOf(passwordField.getPassword()).equals("Enter Password")) {
                        passwordField.setText("");
                        passwordField.setEchoChar('\u2022'); // Set the echo character for password hiding
                    }
                }
    
                public void focusLost(FocusEvent e) {
                    if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                        passwordField.setText("Enter Password");
                        passwordField.setEchoChar((char) 0); // Remove the echo character to show the placeholder text
                    }
                }
            });
            game.getGamePanel().add(passwordField);
    
            // View Password Button
            viewPasswordButton = new JButton("Show");
            viewPasswordButton.setBounds(Game.GAME_WIDTH / 2 + fieldWidth / 2 + spacing, (int) (180 * Game.SCALE), buttonWidth / 2, fieldHeight);
            viewPasswordButton.addActionListener(e -> togglePasswordVisibility());
            game.getGamePanel().add(viewPasswordButton);

            loginButton = new JButton("Log In");
            loginButton.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2, (int) (210 * Game.SCALE), buttonWidth, buttonHeight);
            loginButton.addActionListener(e -> handleLogin());

              signupLink = new JLabel("<html><a href=''>Sign Up</a></html>");
            signupLink.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2+spacing, (int) (260 * Game.SCALE), buttonWidth, buttonHeight);
            signupLink.setForeground(Color.black);
            signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
            signupLink.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleSignup();
                }
            });

            game.getGamePanel().setLayout(null);
            game.getGamePanel().add(usernameField);
            game.getGamePanel().add(passwordField);
            game.getGamePanel().add(viewPasswordButton);
            game.getGamePanel().add(loginButton);
            game.getGamePanel().add(signupLink);
            game.getGamePanel().revalidate();
            game.getGamePanel().repaint();
            componentsInitialized = true;
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
    
        // Check if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Check for admin credentials
        if (username.equals("admin") && password.equals("password")) {
            // Admin login successful
            message = "Admin login successful!";
            removeLoginComponents();
            Gamestate.state = Gamestate.MENU; // Change to your admin menu state
            game.getGamePanel().requestFocusInWindow(); // Ensure focus is set to game panel
            JOptionPane.showMessageDialog(null, "Admin login successful", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    
        // Perform database checks for regular users
        try {
            // Check if username exists
            if (!SigninDatabaseUtil.usernameExists(username)) {
                JOptionPane.showMessageDialog(null, "Username does not exist. Please check your username.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } 
            // Check if password is correct
            else if (!SigninDatabaseUtil.isPasswordCorrect(username, password)) {
                JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } 
            // If both checks pass
            else {
                // Perform actions on successful login
                message = "Login successful!";
                removeLoginComponents();
                Gamestate.state = Gamestate.MENU;
                game.getGamePanel().requestFocusInWindow(); // Ensure focus is set to game panel
                JOptionPane.showMessageDialog(null, "Login successful", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        // Update the UI with the message (if any)
        if (message != null) {
            game.getGamePanel().repaint();
        }
    }

    private void handleSignup() {
        Gamestate.state = Gamestate.SIGNIN;
        removeLoginComponents();
        game.getGamePanel().revalidate();
        game.getGamePanel().repaint();
    }

    private void togglePasswordVisibility() {
        if (passwordField.getEchoChar() == '\u2022') {
            passwordField.setEchoChar((char) 0);
            viewPasswordButton.setText("Hide ");
        } else {
            passwordField.setEchoChar('\u2022');
            viewPasswordButton.setText("View ");
        }
    }

    public void showLoginComponents() {
        SwingUtilities.invokeLater(() -> {
            game.getGamePanel().add(usernameField);
            game.getGamePanel().add(passwordField);
            game.getGamePanel().add(viewPasswordButton);
            game.getGamePanel().add(loginButton);
            game.getGamePanel().add(signupLink);
            game.getGamePanel().revalidate();
            game.getGamePanel().repaint();
        });
    }

    public void removeLoginComponents() {
        SwingUtilities.invokeLater(() -> {
            game.getGamePanel().remove(usernameField);
            game.getGamePanel().remove(passwordField);
            game.getGamePanel().remove(viewPasswordButton);
            game.getGamePanel().remove(loginButton);
            game.getGamePanel().remove(signupLink);
        });
    }

    public void update() {
        if (Gamestate.state == Gamestate.LOGIN) {
            // Show or update the login UI
            game.getGamePanel().revalidate();
            game.getGamePanel().repaint();
        }
       if (!componentsInitialized) {
            try {
                Thread.sleep(100); // 100 milliseconds delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            initUI();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
    g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

    // Set font and colors
    g.setFont(new Font("MedievalSharp", Font.BOLD, 36));
    Color shadowColor = new Color(139, 69, 19); // Dark Brown for shadow
    Color textColor = new Color(211, 211, 211); // Light Gray for the main text

    // Draw "Log In" with shadow effect
    g.setColor(shadowColor);
    g.drawString("Log In", Game.GAME_WIDTH / 2 - 42, (int) (112 * Game.SCALE)); // Shadow

    g.setColor(textColor);
    g.drawString("Log In", Game.GAME_WIDTH / 2 - 40, (int) (110 * Game.SCALE)); // Main text

    // Draw additional text "Don't have an account?" for sign-up prompt
    g.setFont(new Font("Arial", Font.PLAIN, 18)); // Smaller font for additional text
    g.setColor(textColor);
    g.drawString("Don't have an account?", Game.GAME_WIDTH / 2 - 90, (int) (260 * Game.SCALE));

    // Draw any message (e.g., error messages) at the bottom
    g.setFont(new Font("Arial", Font.PLAIN, 16)); // Slightly smaller font for messages
    g.setColor(Color.RED);
    g.drawString(message != null ? message : "", Game.GAME_WIDTH / 2 - 90, (int) (240 * Game.SCALE));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Mouse click logic for the Login state
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Mouse press logic for the Login state
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Mouse release logic for the Login state
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Mouse move logic for the Login state
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Key press logic for the Login state
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Key release logic for the Login state
    }

  
    }
