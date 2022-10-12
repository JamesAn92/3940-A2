package CLI.menu;

import java.util.*;

public abstract class Menu {

    public static final String BACK = "BACK";
    private boolean on = true;
    private String next = null;

    private Scanner scan = new Scanner(System.in);

    public void setOn(boolean on) {
        this.on = on;
    }

    public boolean getOn() {
        return this.on;
    }

    public boolean isActive() {
        return on;
    }

    public boolean hasNext() {
        return next != null;
    }

    public void toMain() {
        this.next = BACK;
    }

    public String getNext() {
        return next;
    }

    public void start() {
        setOn(true);
        setNext(null);
        run();
    }

    public Scanner getScanner() {
        return this.scan;
    }

    protected String scanString() {
        return scan.next().trim();
    }

    protected String scanString(String delimiter) {
        scan.useDelimiter("\n");
        return scan.next().trim();
    }

    protected int scanInt() {
        return scan.nextInt();
    }

    /**
     * This will shutdown the menu and close the CL
     */
    public void destroy() {
        setOn(false);
        scan.close();
    };

    /*
     * Starts the menu.
     */
    protected abstract void run();

    /**
     * This function is responsible for executing the menus task
     * For example the login menu will login.
     * 
     * In this function the next menu is set aswell
     */
    public abstract void execute();

    /**
     * This will indicate which menu should be shown next.
     */
    public void setNext(String next) {
        this.next = next;
    };
}
