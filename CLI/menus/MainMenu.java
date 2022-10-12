package CLI.menus;

import CLI.menu.Menu;

public class MainMenu extends Menu {

    @Override
    public void execute() {

    }

    @Override
    protected void run() {
        // TODO Auto-generated method stub
        String string = scanString();

        System.out.println(string);
    }

}
