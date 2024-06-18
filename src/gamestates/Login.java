package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import main.Game;
import utilz.LoadSave;

public class Login extends State implements Statemethods {

    private Game game;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private String message;
    private JButton viewPasswordButton;
    private JButton signupButton;
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
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.login_BACKGROUND);
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        menuWidth = backgroundImg.getWidth();
        menuHeight = backgroundImg.getHeight();
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (40 * Game.SCALE);
    }


    private synchronized void initUI() {
        if (componentsInitialized) return;

        SwingUtilities.invokeLater(() -> {
            int fieldWidth = 250;
            int fieldHeight = 50;
            int buttonWidth = 100;
            int buttonHeight = 30;
            int spacing = 10;

            usernameField = new JTextField();
            usernameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (150 * Game.SCALE), fieldWidth, fieldHeight);
            usernameField.setFocusable(true);
            usernameField.requestFocusInWindow();

            passwordField = new JPasswordField();
            passwordField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (200 * Game.SCALE), fieldWidth, fieldHeight);
            passwordField.setFocusable(true);

            viewPasswordButton = new JButton("View Password");
            viewPasswordButton.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2 + fieldWidth + spacing, (int) (200 * Game.SCALE), buttonWidth / 2, fieldHeight);
            viewPasswordButton.addActionListener(e -> togglePasswordVisibility());

            loginButton = new JButton("Log In");
            loginButton.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2, (int) (250 * Game.SCALE), buttonWidth, buttonHeight);
            loginButton.addActionListener(e -> handleLogin());

            signupButton = new JButton("Sign Up");
            signupButton.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2 + fieldWidth + spacing, (int) (250 * Game.SCALE), buttonWidth, buttonHeight);
            signupButton.addActionListener(e -> handleSignup());

            game.getGamePanel().setLayout(null);
            game.getGamePanel().add(usernameField);
            game.getGamePanel().add(passwordField);
            game.getGamePanel().add(viewPasswordButton);
            game.getGamePanel().add(loginButton);
            game.getGamePanel().add(signupButton);
            game.getGamePanel().revalidate();
            game.getGamePanel().repaint();
            componentsInitialized = true;
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("password")) {
            message = "Login successful!";
            removeLoginComponents();
            Gamestate.state = Gamestate.MENU;
            game.getGamePanel().requestFocusInWindow(); // Ensure focus is set to game panel
        } else {
            message = "Invalid username or password.";
            game.getGamePanel().repaint();
        }
    }

    private void handleSignup() {
        removeLoginComponents();
        Gamestate.state = Gamestate.SIGNIN;
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
            game.getGamePanel().add(signupButton);
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
            game.getGamePanel().remove(signupButton);
        });
    }

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
        g.drawString("Log In", Game.GAME_WIDTH / 2 - 30, (int) (120 * Game.SCALE));
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
}
