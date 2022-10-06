package cli;

import java.io.*;
import cli.UploadClient;

public class Activity {
    private String dirName = null;

    public static void main(String[] args) throws IOException {
        new Activity().onCreate();
    }

    public Activity() {
        System.out.println("Activity constructor");
    }

    public void onCreate() {
        System.out.println(new UploadClient().uploadFile());
    }
}