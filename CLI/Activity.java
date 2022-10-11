package CLI;

import java.io.*;

public class Activity {
    private String dirName = null;

    public static void main(String[] args) {
        new Activity().onCreate();
    }

    public Activity() {
        System.out.println("Activity constructor");
    }

    public void onCreate() {
        System.out.println(new UploadClient().uploadFile());
    }
}