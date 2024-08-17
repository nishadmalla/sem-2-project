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
    if (Gamestate.state == Gamestate.LOGIN) {
        // Show or update the login UI
        game.getGamePanel().revalidate();
        game.getGamePanel().repaint();
