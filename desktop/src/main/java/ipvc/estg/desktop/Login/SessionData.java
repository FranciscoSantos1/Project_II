package ipvc.estg.desktop.Login;

import entity.Funcionario;

public class SessionData {
    private static SessionData instance;
    private Funcionario currentUser;

    private SessionData() {}

    public static SessionData getInstance() {
        if (instance == null) {
            instance = new SessionData();
        }
        return instance;
    }

    public Funcionario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Funcionario currentUser) {
        this.currentUser = currentUser;
    }
}