package CLI.menus;

import CLI.menu.Menu;

public class LoginMenu extends Menu {

    private String userName;
    private String password;

    public LoginMenu() {
        super();
        this.userName = null;
        this.password = null;
    }

    protected void run() {
        System.out.println("\n\n------------Type BACK to go back to the menu.-------------");
        System.out.println("------------------Enter your user name--------------------");

        this.userName = scanString();
        if (this.userName.equals(BACK)) {
            setNext("main");
            return;
        }

        System.out.println("-------------------Enter your password--------------------");

        this.password = scanString();
        if (this.password.equals(BACK)) {
            setNext("main");
            return;
        }

        setNext("new");
        // execute();

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        System.out.println("I sent a post request to the tomcat server");
    }

}
