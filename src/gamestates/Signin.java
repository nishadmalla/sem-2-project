package gamestates;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import main.Game;
import model.SigninDatabaseUtil;

import javax.swing.SwingUtilities;
import utilz.LoadSave;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class Signin extends State implements Statemethods ,ActionListener{


    private Game game;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signinButton;
    private String message;
    private JButton viewPasswordButton;
    private JButton viewPasswordButton2;
    private JButton backButton;
    private BufferedImage backgroundImg, backgroundImgPink;
    private int menuX, menuY, menuWidth, menuHeight;
    private boolean componentsInitialized;
    

    public Signin(Game game) {
        super(game);
        this.game = game;
        loadBackground();
    }


    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave. Login);
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (25 * Game.SCALE);
    }


    
    private synchronized void initSigninUI() {
        if (componentsInitialized) return;
    
        SwingUtilities.invokeLater(() -> {
            int fieldWidth = 250;       // Wider input field for a more substantial look
            int fieldHeight = 40;       // Taller input field for better readability
            int buttonWidth = 200;      // Wider buttons to match the input field
            int buttonHeight = 40;      // Taller buttons for a more prominent appearance
            int spacing = 15;           // Slightly larger spacing for a less cramped layout
            Color textColor = new Color(169, 169, 169); // Set the desired font color here
    
            // First Name
            firstNameField = new JTextField("Enter First Name");
            firstNameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2 - 7, (int) (120 * Game.SCALE), fieldWidth, fieldHeight);
            firstNameField.setFocusable(true);
            firstNameField.setForeground(textColor);
            firstNameField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (firstNameField.getText().equals("Enter First Name")) {
                        firstNameField.setText("");
                    }
                }
    
                public void focusLost(FocusEvent e) {
                    if (firstNameField.getText().isEmpty()) {
                        firstNameField.setText("Enter First Name");
                    }
                }
            });
            game.getGamePanel().add(firstNameField);
    
            // Last Name
            lastNameField = new JTextField("Enter Last Name");
            lastNameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2 - 7, (int) (150 * Game.SCALE), fieldWidth, fieldHeight);
            lastNameField.setFocusable(true);
            lastNameField.setForeground(textColor);
            lastNameField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (lastNameField.getText().equals("Enter Last Name")) {
                        lastNameField.setText("");
                    }
                }
    
                public void focusLost(FocusEvent e) {
                    if (lastNameField.getText().isEmpty()) {
                        lastNameField.setText("Enter Last Name");
                    }
                }
            });
            game.getGamePanel().add(lastNameField);
    
            // Email
            emailField = new JTextField("Enter Email");
            emailField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2-7, (int) (180 * Game.SCALE), fieldWidth, fieldHeight);
            emailField.setFocusable(true);
            emailField.setForeground(textColor);
            emailField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (emailField.getText().equals("Enter Email")) {
                        emailField.setText("");
                    }
                }
    
                public void focusLost(FocusEvent e) {
                    if (emailField.getText().isEmpty()) {
                        emailField.setText("Enter Email");
                    }
                }
            });
            game.getGamePanel().add(emailField);
    
            // Username
            usernameField = new JTextField("Enter Username");
            usernameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2-7, (int) (210 * Game.SCALE), fieldWidth, fieldHeight);
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
            game.getGamePanel().add(usernameField);
    
            // Password
            passwordField = new JPasswordField("Enter Password");
            passwordField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2-7, (int) (240 * Game.SCALE), fieldWidth, fieldHeight);
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
            viewPasswordButton.setBounds(Game.GAME_WIDTH / 2 + fieldWidth / 2 + spacing, (int) (240 * Game.SCALE), buttonWidth / 2, fieldHeight);
            viewPasswordButton.addActionListener(e -> togglePasswordVisibility());
            game.getGamePanel().add(viewPasswordButton);
    
            // Confirm Password
            confirmPasswordField = new JPasswordField("Confirm Password");
            confirmPasswordField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth /2 - 7, (int) (270 * Game.SCALE), fieldWidth, fieldHeight);
            confirmPasswordField.setFocusable(true);
            confirmPasswordField.setForeground(textColor);
            confirmPasswordField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (String.valueOf(confirmPasswordField.getPassword()).equals("Confirm Password")) {
                        confirmPasswordField.setText("");
                        confirmPasswordField.setEchoChar('\u2022'); // Set the echo character for password hiding
                    }
                }
    
                public void focusLost(FocusEvent e) {
                    if (String.valueOf(confirmPasswordField.getPassword()).isEmpty()) {
                        confirmPasswordField.setText("Confirm Password");
                        confirmPasswordField.setEchoChar((char) 0); // Remove the echo character to show the placeholder text
                    }
                }
            });
            
            game.getGamePanel().add(confirmPasswordField);
    
            // View Confirm Password Button
            viewPasswordButton2 = new JButton("Show");
            viewPasswordButton2.setBounds(Game.GAME_WIDTH / 2 + fieldWidth / 2 + spacing, (int) (270 * Game.SCALE), buttonWidth / 2, fieldHeight);
            viewPasswordButton2.addActionListener(e -> toggleConfirmPasswordVisibility());
            game.getGamePanel().add(viewPasswordButton2);
    
            // Sign In Button
            signinButton = new JButton("Sign In");
            signinButton.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2, (int) (300 * Game.SCALE), buttonWidth, buttonHeight);
            signinButton.setFocusable(true);
            signinButton.addActionListener(e -> handleSignin());
            game.getGamePanel().add(signinButton);
    
            
            game.getGamePanel().setLayout(null);
            game.getGamePanel().revalidate();
            game.getGamePanel().repaint();
            componentsInitialized = true;
        });
    }
    
        private void handleSignin() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
 
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields", "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
        else if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Enter a valid email address", "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
        else if (username.length() >= 15) {
            JOptionPane.showMessageDialog(null, "Username should be less than 15 characters", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (password.length() < 8 || password.length() > 16) {
            JOptionPane.showMessageDialog(null, "Password should be more than 8 characters and less than 16", "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
        else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Password doesn't match", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else {
            try {
                if (SigninDatabaseUtil.usernameExists(username)) {
                    JOptionPane.showMessageDialog(null, "Username already exists. Please choose another one.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    SigninDatabaseUtil.insertUser(firstName, lastName, email, username, password, confirmPassword);
                    JOptionPane.showMessageDialog(null, "Sign Up successful", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
           
        }
        gotoLogin();
    }
    
    }     


    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);  
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


   
    private void gotoLogin() {
        removeSigninComponents(); // Remove the sign-up components from the screen
        Gamestate.state = Gamestate.MENU; // Switch the game state to the login state
        game.getGamePanel().revalidate(); // Revalidate the game panel to apply the changes
        game.getGamePanel().repaint(); // Repaint the game panel to reflect the new state
    }

    public void togglePasswordVisibility() {
        if (passwordField.getEchoChar() == '\u2022') {
            passwordField.setEchoChar((char) 0); // Show password characters
            viewPasswordButton.setText("Hide");
        } else {
            passwordField.setEchoChar('\u2022'); // Hide password characters
            viewPasswordButton.setText("Show");
        }
    }




    public void toggleConfirmPasswordVisibility() {
        if (confirmPasswordField.getEchoChar() == '\u2022') {
            confirmPasswordField.setEchoChar((char) 0); // Show password characters
            viewPasswordButton2.setText("Hide");
        } else {
            confirmPasswordField.setEchoChar('\u2022'); // Hide password characters
            viewPasswordButton2.setText("Show");
        }
    }


    public void showSigninComponents() {
        firstNameField.setVisible(true);
        lastNameField.setVisible(true);
        emailField.setVisible(true);
        usernameField.setVisible(true);
        passwordField.setVisible(true);
        viewPasswordButton.setVisible(true);
        confirmPasswordField.setVisible(true);
        viewPasswordButton2.setVisible(true);
        signinButton.setVisible(true);
        backButton.setVisible(true);
    }
  
    private void removeSigninComponents() {
        if (firstNameField != null) {
            game.getGamePanel().remove(firstNameField);
        }
        if (lastNameField != null) {
            game.getGamePanel().remove(lastNameField);
        }
        if (emailField != null) {
            game.getGamePanel().remove(emailField);
        }
        if (usernameField != null) {
            game.getGamePanel().remove(usernameField);
        }
        if (passwordField != null) {
            game.getGamePanel().remove(passwordField);
        }
        if (confirmPasswordField != null) {
            game.getGamePanel().remove(confirmPasswordField);
        }
        if (viewPasswordButton != null) {
            game.getGamePanel().remove(viewPasswordButton);
        }
        if (viewPasswordButton2 != null) {
            game.getGamePanel().remove(viewPasswordButton2);
        }
        if (signinButton != null) {
            game.getGamePanel().remove(signinButton);
        }
        if (backButton != null) {
            game.getGamePanel().remove(backButton);
        }
    
        componentsInitialized = false; // Reset the flag
        game.getGamePanel().revalidate(); // Refresh the layout
        game.getGamePanel().repaint(); // Repaint the panel
    }
    
    
   
    
    @Override
    public void update() {
        if (!componentsInitialized) {
            try {
                Thread.sleep(100); // 100 milliseconds delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            initSigninUI();
        }
    }




    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        g.setFont(new Font("MedievalSharp", Font.BOLD, 36));

        // Set the color to a dark, dungeon-themed color scheme
        g.setColor(new Color(139, 69, 19)); // Example: Dark Brown for shadow (stone/worn look)
        
        // Adding a shadow effect for a more 3D appearance
        g.drawString("SIGN UP", Game.GAME_WIDTH / 2 - 82, (int) (112 * Game.SCALE)); // Shadow (offset slightly)
        g.setColor(new Color(211, 211, 211)); // Light Gray for the main text (like stone)
        g.drawString("SIGN UP", Game.GAME_WIDTH / 2 - 80, (int) (110 * Game.SCALE));
        g.drawString(message != null ? message : "", Game.GAME_WIDTH / 2 - 60, (int) (320 * Game.SCALE));
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (Gamestate.state == Gamestate.SIGNIN) {
            showSigninComponents();
        } else {
            removeSigninComponents();
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        // Mouse press logic for the Signin state
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        // Mouse release logic for the Signin state
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        // Mouse move logic for the Signin state
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // Key press logic for the Signin state
    }


    @Override
    public void keyReleased(KeyEvent e) {
        // Key release logic for the Signin state
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}