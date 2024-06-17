package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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

public class Signin extends State implements Statemethods {

    private Game game;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordField2;
    private JButton signinButton;
    private String message;
    private JButton viewPasswordButton;
    private JButton viewPasswordButton2;
    private JButton backButton;
    private BufferedImage backgroundImg, backgroundImgPink;
    private int menuX, menuY, menuWidth, menuHeight;
    private boolean componentsInitialized;

    public  String PLACEHOLDER_FIRST_NAME = "First Name";
    public  String PLACEHOLDER_LAST_NAME = "Last Name";
    public  String PLACEHOLDER_EMAIL = "Email";
    public  String PLACEHOLDER_USERNAME = "Username";
    public  String PLACEHOLDER_PASSWORD = "Password";
    public  String PLACEHOLDER_CONFIRM_PASSWORD = "Confirm Password";

    public Signin(Game game) {
        super(game);
        this.game = game;
        loadBackground();
        
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
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
            

            //FirstName
            firstNameField= new JTextField(PLACEHOLDER_FIRST_NAME);
            firstNameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2 - 110, (int) (100 * Game.SCALE), fieldWidth, fieldHeight);
            firstNameField.setFocusable(true);
            addPlaceholderBehavior(firstNameField, "First Name");
            game.getGamePanel().add(firstNameField);
            
            //LastName
            lastNameField = new JTextField(PLACEHOLDER_LAST_NAME);
            lastNameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2 + 110, (int) (100 * Game.SCALE), fieldWidth, fieldHeight);
            lastNameField.setFocusable(true);
            addPlaceholderBehavior(lastNameField, "Last Name");
            game.getGamePanel().add(lastNameField);

            // Email
            emailField = new JTextField(PLACEHOLDER_EMAIL);
            emailField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (120 * Game.SCALE), fieldWidth, fieldHeight);
            emailField.setFocusable(true);
            addPlaceholderBehavior(emailField, "Email");
            game.getGamePanel().add(emailField);

            // Username
            usernameField = new JTextField(PLACEHOLDER_USERNAME);
            usernameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (140 * Game.SCALE), fieldWidth, fieldHeight);
            usernameField.setFocusable(true);
            addPlaceholderBehavior(usernameField, "Username");
            game.getGamePanel().add(usernameField);

            // Password
            passwordField = new JPasswordField(PLACEHOLDER_PASSWORD);
            passwordField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (160 * Game.SCALE), fieldWidth, fieldHeight);
            passwordField.setFocusable(true);
            setPasswordFieldPlaceholder(passwordField, "Password");
            game.getGamePanel().add(passwordField);

            // View Password Button
            viewPasswordButton = new JButton("Show");
            viewPasswordButton.setBounds(Game.GAME_WIDTH / 2 + fieldWidth / 2 + spacing, (int) (160 * Game.SCALE), buttonWidth / 2, fieldHeight);
            viewPasswordButton.setFocusable(true);
            viewPasswordButton.addActionListener(e -> togglePasswordVisibility(passwordField, viewPasswordButton));
            game.getGamePanel().add(viewPasswordButton);


            // Confirm Password
            passwordField2 = new JPasswordField(PLACEHOLDER_CONFIRM_PASSWORD);
            passwordField2.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (180 * Game.SCALE), fieldWidth, fieldHeight);
            passwordField2.setFocusable(true);
            setPasswordFieldPlaceholder(passwordField2, "Confirm Password");
            game.getGamePanel().add(passwordField2);

            // View Confirm Password Button
            viewPasswordButton2 = new JButton("Show");
            viewPasswordButton2.setBounds(Game.GAME_WIDTH / 2 + fieldWidth / 2 + spacing, (int) (180 * Game.SCALE), buttonWidth / 2, fieldHeight);
            viewPasswordButton2.setFocusable(true);
            viewPasswordButton2.addActionListener(e -> togglePasswordVisibility(passwordField2, viewPasswordButton2));
            game.getGamePanel().add(viewPasswordButton2);

            // Sign In Button
            signinButton = new JButton("Sign In");
            signinButton.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2, (int) (200 * Game.SCALE), buttonWidth, buttonHeight);
            signinButton.setFocusable(true);;
            signinButton.addActionListener(e -> handleSignin());
            game.getGamePanel().add(signinButton);

            // Back Button
            backButton = new JButton("Back");
            backButton.setBounds(Game.GAME_WIDTH / 3 - buttonWidth / 2, (int) (200 * Game.SCALE), buttonWidth, buttonHeight);
            backButton.setFocusable(true);;
            backButton.addActionListener(e -> handleBack());
            game.getGamePanel().add(backButton);

            game.getGamePanel().setLayout(null);
            game.getGamePanel().revalidate();
            game.getGamePanel().repaint();
            componentsInitialized = true;
        });
        
    }

    private void setPasswordFieldPlaceholder(JPasswordField passwordField, String placeholder) {
        passwordField.setText(placeholder); // Set placeholder text initially
        passwordField.setEchoChar((char) 0);
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String password = new String(passwordField.getPassword());
                if (password.equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('\u2022'); // Hide password characters
                }
                clearPassword(passwordField.getPassword());
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                String password = new String(passwordField.getPassword());
                if (password.isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0); // Show placeholder text
                }
                clearPassword(passwordField.getPassword());
            }
        });
    }
    
    private void clearPassword(char[] password) {
        for (int i = 0; i < password.length; i++) {
            password[i] = '\0';
        }
    }

    private void addPlaceholderBehavior(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                }
            }
        });
    }

    private void handleSignin() {
        String firstName = getTextOrEmpty(firstNameField, PLACEHOLDER_FIRST_NAME);
        String lastName = getTextOrEmpty(lastNameField, PLACEHOLDER_LAST_NAME);
        String email = getTextOrEmpty(emailField, PLACEHOLDER_EMAIL);
        String username = getTextOrEmpty(usernameField, PLACEHOLDER_USERNAME);
        String password = getPasswordOrEmpty(passwordField, PLACEHOLDER_PASSWORD);
        String confirmPassword = getPasswordOrEmpty(passwordField2, PLACEHOLDER_CONFIRM_PASSWORD);

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Fill all the fields");
            return;
        }

        if (!isValidEmail(email)) {
            showMessage("Enter a valid email");
            return;
        }

        if (username.length() >= 15) {
            showMessage("Username should be less than 15 characters");
            return;
        }

        if (password.length() < 8 || password.length() > 16) {
            showMessage("Passwords should be more than 8 characters and less than 16");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showMessage("The passwords don't match");
            return;
        }

        showMessage("Sign up successful");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(game.getGamePanel(), message);
    }

    private String getTextOrEmpty(JTextField textField, String placeholder) {
        String text = textField.getText();
        if (text.equals(placeholder)) {
            return "";
        }
        return text;
    }

    private String getPasswordOrEmpty(JPasswordField passwordField, String placeholder) {
        String password = new String(passwordField.getPassword());
        if (password.equals(placeholder)) {
            return "";
        }
        return password;
    }
        

    

    


    private void handleBack() {
        removeSigninComponents();
        Gamestate.state = Gamestate.LOGIN;
        game.getGamePanel().revalidate();
        game.getGamePanel().repaint();
    }

    private void togglePasswordVisibility(JPasswordField passwordField, JButton toggleButton) {
        if (passwordField.getEchoChar() == '\u2022') {
            passwordField.setEchoChar((char) 0); // Show password characters
            toggleButton.setText("Hide");
        } else {
            passwordField.setEchoChar('\u2022'); // Hide password characters
            toggleButton.setText("Show");
        }
    }
    public void showSigninComponents() {
        usernameField.setVisible(true);
        passwordField.setVisible(true);
        viewPasswordButton.setVisible(true);
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
            game.getGamePanel().remove(passwordField2);
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
        } else  {
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
