package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import main.Game;
import javax.swing.SwingUtilities;
import utilz.LoadSave;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import gamestates.Login;



public class Signin extends State implements Statemethods {

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
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.signup_BACKGROUND);
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        menuWidth = backgroundImg.getWidth();
        menuHeight = backgroundImg.getHeight();
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (40 * Game.SCALE);
    }

    private synchronized void initUI() {
        if (componentsInitialized) return;

        SwingUtilities.invokeLater(() -> {
            int fieldWidth = 200;
            int fieldHeight = 30;
            int buttonWidth = 200;
            int buttonHeight = 30;
            int spacing = 10;

            // First Name
            firstNameField = new JTextField();
            firstNameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2 - 110, (int) (100 * Game.SCALE), fieldWidth, fieldHeight);
            firstNameField.setFocusable(true);
            game.getGamePanel().add(firstNameField);

            // Last Name
            lastNameField = new JTextField();
            lastNameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2 + 110, (int) (100 * Game.SCALE), fieldWidth, fieldHeight);
            lastNameField.setFocusable(true);
            game.getGamePanel().add(lastNameField);

            // Email
            emailField = new JTextField();
            emailField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (120 * Game.SCALE), fieldWidth, fieldHeight);
            emailField.setFocusable(true);
            game.getGamePanel().add(emailField);

            // Username
            usernameField = new JTextField();
            usernameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (140 * Game.SCALE), fieldWidth, fieldHeight);
            usernameField.setFocusable(true);
            game.getGamePanel().add(usernameField);

            // Password
            passwordField = new JPasswordField();
            passwordField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (160 * Game.SCALE), fieldWidth, fieldHeight);
            passwordField.setFocusable(true);
            game.getGamePanel().add(passwordField);

            // View Password Button
            viewPasswordButton = new JButton("Show");
            viewPasswordButton.setBounds(Game.GAME_WIDTH / 2 + fieldWidth / 2 + spacing, (int) (160 * Game.SCALE), buttonWidth / 2, fieldHeight);
            viewPasswordButton.addActionListener(e -> togglePasswordVisibility());
            game.getGamePanel().add(viewPasswordButton);

            // Confirm Password
            confirmPasswordField = new JPasswordField();
            confirmPasswordField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (180 * Game.SCALE), fieldWidth, fieldHeight);
            confirmPasswordField.setFocusable(true);
            game.getGamePanel().add(confirmPasswordField);

            // View Confirm Password Button
            viewPasswordButton2 = new JButton("Show");
            viewPasswordButton2.setBounds(Game.GAME_WIDTH / 2 + fieldWidth / 2 + spacing, (int) (180 * Game.SCALE), buttonWidth / 2, fieldHeight);
            viewPasswordButton2.addActionListener(e -> toggleConfirmPasswordVisibility());
            game.getGamePanel().add(viewPasswordButton2);

            // Sign In Button
            signinButton = new JButton("Sign In");
            signinButton.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2, (int) (200 * Game.SCALE), buttonWidth, buttonHeight);
            signinButton.setFocusable(true);
            signinButton.addActionListener(e -> handleSignin());
            game.getGamePanel().add(signinButton);

            // Back Button
            backButton = new JButton("Back");
            backButton.setBounds(Game.GAME_WIDTH / 3 - buttonWidth / 2, (int) (200 * Game.SCALE), buttonWidth, buttonHeight);
            backButton.setFocusable(true);
            backButton.addActionListener(e -> handleBack());
            game.getGamePanel().add(backButton);

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
            JOptionPane.showMessageDialog(null, "Please fill all the fields","ERROR",JOptionPane.ERROR_MESSAGE);
             
        } 
        else if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null,"Enter a valid email address","ERROR",JOptionPane.ERROR_MESSAGE);
             
        } 
        else if (username.length() >= 15) {
            JOptionPane.showMessageDialog(null, "Username should be less than 15 characters","ERROR",JOptionPane.ERROR_MESSAGE);
             
        }
        else if (password.length() < 8 || password.length() > 16) {
            JOptionPane.showMessageDialog(null, "Password should be more than 8 characters and less than 16","ERROR",JOptionPane.ERROR_MESSAGE);
             
        } 
        else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Password doesn't match","ERROR",JOptionPane.ERROR_MESSAGE);
             
        }
        else {
            JOptionPane.showMessageDialog(null, "Sign Up successfull","INFOMATION",JOptionPane.INFORMATION_MESSAGE);
            
        }
    
    }     

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void handleBack() {
        removeSigninComponents();
        Gamestate.state = Gamestate.LOGIN;
        game.getGamePanel().revalidate();
        game.getGamePanel().repaint();
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

    public void removeSigninComponents() {
        SwingUtilities.invokeLater(() -> {
            game.getGamePanel().remove(firstNameField);
            game.getGamePanel().remove(lastNameField);
            game.getGamePanel().remove(emailField);
            game.getGamePanel().remove(usernameField);
            game.getGamePanel().remove(passwordField);
            game.getGamePanel().remove(confirmPasswordField);
            game.getGamePanel().remove(viewPasswordButton);
            game.getGamePanel().remove(viewPasswordButton2);
            game.getGamePanel().remove(signinButton);
            game.getGamePanel().remove(backButton);
            game.getGamePanel().revalidate();
            game.getGamePanel().repaint();
            componentsInitialized = false; // Reset the flag
        });
    }

    @Override
    public void update() {
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

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("SIGN UP", Game.GAME_WIDTH / 2 - 40, (int) (90 * Game.SCALE));
        g.setFont(new Font("Arial", Font.PLAIN, 12));
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
}
