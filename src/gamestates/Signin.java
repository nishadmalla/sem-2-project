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

public class Signin extends State implements Statemethods {

    private Game game;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signinButton;
    private String message;
    private JButton viewPasswordButton;
    private JButton backButton;

    public Signin(Game game) {
        super(game);
        this.game = game;
        initUI();
    }

    void initUI() {
        int fieldWidth = 200;
        int fieldHeight = 30;
        int buttonWidth = 200;
        int buttonHeight = 30;
        int spacing = 10;

    
        usernameField = new JTextField();
        usernameField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (150 * Game.SCALE), fieldWidth, fieldHeight);

      
        passwordField = new JPasswordField();
        passwordField.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2, (int) (220 * Game.SCALE), fieldWidth, fieldHeight);

       
        viewPasswordButton = new JButton("View Password");
        viewPasswordButton.setBounds(Game.GAME_WIDTH / 2 - fieldWidth / 2 + fieldWidth + spacing, (int) (220 * Game.SCALE), buttonWidth / 2, fieldHeight);
        viewPasswordButton.addActionListener(e -> togglePasswordVisibility());

        
        signinButton = new JButton("Sign In");
        signinButton.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2, (int) (290 * Game.SCALE), buttonWidth, buttonHeight);
        signinButton.addActionListener(e -> handleSignin());

       
        backButton = new JButton("Back");
        backButton.setBounds(Game.GAME_WIDTH / 2 - buttonWidth / 2, (int) (360 * Game.SCALE), buttonWidth, buttonHeight);
        backButton.addActionListener(e -> handleBack());

        
        game.getGamePanel().setLayout(null);
        game.getGamePanel().add(usernameField);
        game.getGamePanel().add(passwordField);
        game.getGamePanel().add(viewPasswordButton);
        game.getGamePanel().add(signinButton);
        game.getGamePanel().add(backButton);
    }

    private void handleSignin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
    
        if (username.equals("admin") && password.equals("password")) {
            message = "Sign In successful!";
            // Remove signin components from the panel
            game.getGamePanel().removeAll();
            // Repaint the panel to reflect changes
            game.getGamePanel().revalidate();
            game.getGamePanel().repaint();
            // Transition to the menu state
            Gamestate.state = Gamestate.MENU;
        } else {
            message = "Invalid username or password.";
            // Set the state to SIGNIN to display the sign-in screen again
            Gamestate.state = Gamestate.SIGNIN;
            game.getGamePanel().repaint();
        }
    }

    private void handleBack() {
        // Transition back to the login state
        Gamestate.state = Gamestate.LOGIN;
        // Remove signin components from the panel
        game.getGamePanel().removeAll();
        // Repaint the panel to reflect changes
        game.getGamePanel().revalidate();
        game.getGamePanel().repaint();
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
        game.getGamePanel().repaint();
    }

    @Override
    public void draw(Graphics g) {
        // Draw background and signin message
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Sign In", Game.GAME_WIDTH / 2 - 30, (int) (120 * Game.SCALE));
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(message != null ? message : "", Game.GAME_WIDTH / 2 - 60, (int) (320 * Game.SCALE));
    }
  

    @Override
    public void mouseClicked(MouseEvent e) {
        // Mouse click logic for the Signin state
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
