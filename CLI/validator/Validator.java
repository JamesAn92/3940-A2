package CLI.validator;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Validator {

    private static final int MAX_CAPTION_LENGTH = 20;

    final static String DATE_FORMAT = "dd-mm-yyyy";

    final static String[] VALID_FILE_TYPES = { ".png", ".jpeg", ".jpg" };

    /**
     * Make sure URL is valid and returns a file
     */
    public static boolean isValidFilePath(String url) {
        // Print string if invalid
        try {
            Paths.get(url);
        } catch (InvalidPathException | NullPointerException ex) {
            System.out.println("--Invalid String--");
            System.out.println("--" + url + "--");
            return false;
        }
        return true;
    }

    /**
     * Check to see if file type is valid.
     * 
     * @return boolean
     */

    public static boolean isValidFileType(String fileType) {
        System.out.println("Filetype: " + fileType);
        int index = fileType.indexOf('.');
        fileType = fileType.substring(index);
        for (int i = 0; i < VALID_FILE_TYPES.length; i++) {
            if (VALID_FILE_TYPES[i].equals(fileType.toLowerCase())) {
                return true;
            }
        }

        // Print string if invalid
        System.out.println("---Invalid file type passed in---");
        return false;
    }

    private static void printFileTypeError(String file) {
        System.out.println("--Ivalid File Type--");
        System.out.println(file);
    }

    /**
     * @return true if category is correct, false otherwise
     */
    public static boolean isValidCategory(String category) {
        String trimmedCategory = category.trim();
        if (trimmedCategory.length() > MAX_CAPTION_LENGTH || trimmedCategory.length() <= 0) {
            System.out.println("Category length is invalid. Length has to be between 0 - " + MAX_CAPTION_LENGTH);
            return false;
        }
        System.out.println("The current valid category: " + category);
        return true;
    }

    /**
     * Checks to see if date is valid
     * 
     * @param date "dd-MM-yyyy";
     * @return
     */
    public static boolean isDateValid(String date) {
        return true;
        // try {
        // DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        // df.setLenient(false);
        // df.parse(date);
        // return true;
        // } catch (Exception e) {
        // System.out.println("Invalid date. Date must be in the format dd-MM-yyyy");
        // return false;
        // }
    }

    public static boolean isValidYear(int year) {
        if (!(year > 0 && year <= 3000)) {
            System.out.println("Year must be greater than 0 and less than 3000");
            return false;
        }

        return true;
    }

    public static boolean isValidMonth(int month) {
        if (!(month > 0 && month <= 12)) {
            System.out.println("Month must be greater than 0 and less than 12");
            return false;
        }

        return true;
    }

    public static boolean isValidDate(int date) {
        if (!(date > 0 && date <= 31)) {
            System.out.println("Date must be greater than 0 and less than 31");
            return false;
        }

        return true;
    }

    public static boolean isValidID(String id) {
        return true;
    }
}
