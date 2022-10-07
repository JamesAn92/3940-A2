package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import javax.servlet.http.Part;

import config.DB;

public class FileService {

    public static final String DEFAULT_DATE = "2000-01-01";
    public static final String DEFAULT_CAPTION = "No caption";

    /**
     * Adds the file name, caption, and date to the photos table under the user logged in
     * 
     * @author Clayton Hunter
     * @author (whoever else worked on file upload)
     * 
     * @param userId
     * @param fileName
     * @param caption
     * @param date
     * @return
     */
    public static boolean uploadFile(int userId, String fileName, String caption, String date, File imageFile) {
        Connection con = DB.createConnection();
        try {

            PreparedStatement upload = con.prepareStatement("INSERT INTO photos(fileName, caption, uploadDate, userId, image) values(?, ?, ?, ?, ?)");

            upload.setString(1, fileName);
            upload.setString(2, caption);
            upload.setString(3, date);
            upload.setInt(4, userId);

            FileInputStream imageFileIS = new FileInputStream(imageFile);
            upload.setBinaryStream(5, imageFileIS);

            upload.executeUpdate();

            upload.close();
            con.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

       /**
       * Removes all files from a directory
       * 
       * @author Clayton Hunter
       * 
       * @param dirName 
       */
      public static void removeFilesFromDirectory(String dirName) {
        File directory = new File(System.getProperty("catalina.base") + dirName);
        if (!directory.exists()) {
            System.out.println("Could not remove files from a directory that doesn't exist!");
            return;
        }
        try {
              for (File file : Objects.requireNonNull(directory.listFiles())) {
                    if (!file.isDirectory()) {
                          file.delete();
                    }
              }
        } catch (Exception ex) {
              ex.printStackTrace();
        }
  }

}
