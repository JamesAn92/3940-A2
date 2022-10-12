package CLI.menus;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import CLI.util.HttpPostMultipart;

import CLI.menu.Menu;
import CLI.validator.Validator;

import org.json.simple.*;
import org.json.simple.parser.*;

public class NewMenu extends Menu {

    private String category;
    private String date;
    private String path;
    private String fileName;
    private String userId;
    final String OUR_URL = "http://127.0.0.1:8999/";

    public String getCategory() {
        return this.category;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getDate() {
        return this.date;
    }

    public String getPath() {
        return this.path;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private boolean isExit(String s) throws Exception {
        if (s.equals("exit")) {
            destroy();
            throw new Exception("User has selected to exit program");
        }

        return false;
    }

    private void collectFile() {
        System.out.println("\n===============Please enter the file path.=================");

        boolean token = false;
        while (!token) {
            // If file path not valid through error and
            String path = scanString("\n").trim();
            String fileName;

            try {
                isExit(path);
            } catch (Exception e) {
                break;
            }

            System.out.println(
                    "\n++++++This is the url we recieved: " + path + "++++++++");

            try {
                fileName = getFileNameFromUrl(path);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // Check if valid
            if (Validator.isValidFileType(fileName) && Validator.isValidFilePath(path)) {
                System.out.println("file Name Validated");
                setFileName(fileName);
                setPath(path);
                token = true;
            }
        }
    }

    private void collectCategory() {
        System.out.println(
                "\n\n````````````Next we need a category for the image``````````````");

        boolean token = false;
        while (!token) {
            System.out.println("```````````````````Please enter category`````````````````````");
            String category = scanString();

            try {
                isExit(category);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            if (Validator.isValidCategory(category)) {
                setCategory(category);
                token = true;
            }

        }

    }

    private void collectDate() {

        System.out.println("            Last thing we need is a the date              ");

        boolean token = false;
        while (!token) {
            System.out.println("\n             Please enter the YYYY-MM-DD               ");

            String date = scanString();

            try {
                isExit(date);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            if (Validator.isDateValid(date)) {
                setDate(date);
                break;
            }
        }
    }

    private void collectUserId() {
        System.out.println("            So we needed to cheat a little              ");
        System.out.println("             We need your account number              ");
        System.out.println("            Please enter your account number:             ");
        while (true) {
            String userId = "";

            userId = this.scanString();

            System.out.println("user Id " + userId);

            try {
                isExit(userId);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            if (Validator.isValidID(userId)) {
                setUserId(userId);
                break;
            }
        }
    }

    protected void run() {
        System.out.println("\n\n________________Hello Welcome to file uploader.___________");
        System.out.println("____At any time you can type exit to leave the program____");

        try {
            collectFile();
            collectCategory();
            collectDate();
            collectUserId();
            execute();
            System.out.println("\n\n!!!!!!!!!Thanks bloood i appreciate the patientce!!!!!!!!!!");
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
    }

    private String getFileNameFromUrl(String path) throws Exception {
        for (int i = path.length() - 1; i > 0; i--) {
            if (path.charAt(i) == '\\') {
                return path.substring(i + 1);
            }
        }
        throw new Exception("No file found. Please try again.");
    }

    public void execute() {
        System.out.println("Path: \t\t" + this.getPath());
        System.out.println("fileName: \t" + this.getFileName());
        System.out.println("Categroy: \t" + this.getCategory());
        System.out.println("Date: \t\t" + this.getDate());
        System.out.println("User Id: \t\t" + this.getUserId());

        Map<String, String> headers = new HashMap<>();
        headers.put("isCLI", "true");

        try {
            System.out.println("In try");
            HttpPostMultipart request = new HttpPostMultipart(OUR_URL, "UTF-8", headers);
            request.addFormField("caption", this.getCategory());
            request.addFormField("date", this.getDate());
            request.addFormField("USER_ID", this.getUserId());
            request.addFilePart("fileName", new File(this.getPath()));
            System.out.println("Before finish");
            String result = request.finish();

            // parsing file "JSONExample.json"

            System.out.println("Results: " + result);
            // JSONObject obj = (JSONObject) new JSONParser().parse(result);
            // ArrayList<String> filesReturned = (ArrayList<String>) obj.get("fileNames");
            // filesReturned.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
