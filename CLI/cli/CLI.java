package CLI.cli;

import java.util.*;

import CLI.menu.Menu;

import CLI.menus.LoginMenu;
import CLI.menus.MainMenu;
import CLI.menus.NewMenu;

public class CLI {

    static final Map<String, Menu> MENUS = new HashMap<>();

    private Menu currentMenue;

    public CLI() {
        MENUS.put("new", new NewMenu());
        // MENUS.put("search", new Menu("search"));
        MENUS.put("main", new MainMenu());
        MENUS.put("login", new LoginMenu());
        this.currentMenue = new NewMenu();
        System.out.println("\t\tStarting");
        System.out.println("\t\t-----------------------------");

    }

    public Menu getMenu(String menu) throws Exception {
        if (MENUS.containsKey(menu))
            return MENUS.get(menu);
        throw new Exception("No menu found.");
    }

    public void setCurrentMenue(Menu currentMenue) {
        this.currentMenue = currentMenue;
    }

    public Menu getCurrentMenue() {
        return currentMenue;
    }

    public void run() {
        while (getCurrentMenue().isActive()) {
            getCurrentMenue().start(); // shows the current menu
            if (getCurrentMenue().hasNext()) {
                // go to the next menu
                try {
                    setCurrentMenue(getMenu(getCurrentMenue().getNext()));
                    getCurrentMenue().start();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    getCurrentMenue().destroy();
                }
            }
        }
    }

    public static void main(String[] args) {

        // User runs app
        System.out.println("\tWelcome to our COMP 3940 CLI.");
        new CLI().run();

        // User closes app
        System.out.println("\n\n\t\tGoodbye.\n\n");
    }
}
