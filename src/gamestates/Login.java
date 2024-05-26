package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import main.Game;

public class Login extends State implements Statemethods {

    private Game game;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private String message;
    private JButton viewPasswordButton;

    public Login(Game game) {
        super(game);
        this.game = game;
        initUI();
    }

    private void initUI() {
        int fieldWidth = 200;
        int fieldHeight = 30;
        int buttonWidth = 200;
        int buttonHeight = 30;
        int spacing = 10;

        // Initialize the username field
        usernameField = new JTextField();
        usernameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (150 * Game.SCALE), fieldWidth, fieldHeight);

        // Initialize the password field
        passwordField = new JPasswordField();
        passwordField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (220 * Game.SCALE), fieldWidth, fieldHeight);

        // Initialize the view password button
        viewPasswordButton = new JButton("View Password");
        viewPasswordButton.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2 + fieldWidth + spacing, (int) (220 * Game.SCALE), buttonWidth / 2, fieldHeight);
        viewPasswordButton.addActionListener(e -> togglePasswordVisibility());

        // Initialize the login button
        loginButton = new JButton("Login");
        loginButton.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2, (int) (290 * Game.SCALE), buttonWidth, buttonHeight);
        loginButton.addActionListener(e -> handleLogin());

        // Initialize the create account button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2, (int) (360 * Game.SCALE), buttonWidth, buttonHeight);
        createAccountButton.addActionListener(e -> handleCreateAccount());

        // Add components to the game panel
        game.getGamePanel().setLayout(null);
        game.getGamePanel().add(usernameField);
        game.getGamePanel().add(passwordField);
        game.getGamePanel().add(viewPasswordButton);
        game.getGamePanel().add(loginButton);
        game.getGamePanel().add(createAccountButton);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("password")) {
            message = "Login successful!";
            game.getGamePanel().removeAll();
            
            game.getGamePanel().revalidate();
            game.getGamePanel().repaint();
            
            Gamestate.state = Gamestate.MENU;
        } else {
            message = "Invalid username or password.";
            game.getGamePanel().repaint();
        }
    }
    private void handleCreateAccount() {
        Gamestate.state = Gamestate.SIGNIN;
       
        game.getGamePanel().removeAll();
       
        game.getGamePanel().revalidate();
        game.getGamePanel().repaint();
    }
}   
private void togglePasswordVisibility() {
    if (passwordField.getEchoChar() == '\u2022') {
        passwordField.setEchoChar((char) 0); // Set to 0 to show password in plain text
        viewPasswordButton.setText("Hide ");
    } else {
        passwordField.setEchoChar('\u2022'); // Set bullet character for hiding
        viewPasswordButton.setText("View ");
    }
}

@Override
public void update() {
    // Update logic for the Login state
}

@Override
    public void draw(Graphics g) {
        // Draw background and login message
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Login", Game.GAME_WIDTH / 2 - 30, (int) (120 * Game.SCALE));
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(message != null ? message : "", Game.GAME_WIDTH / 2 - 60, (int) (320 * Game.SCALE));
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

