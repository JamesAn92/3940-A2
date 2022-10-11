package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import java.util.ArrayList;

import models.Image;
import config.DB;

public class ImageService {

    /**
     * Obtains a list of images that match the user id and either the caption or
     * date.
     * 
     * @author Clayton Hunter
     * 
     * @param userId
     * @param caption
     * @param date
     * @return
     */
    public static List<Image> getImagesWith(int userId, String caption, String date) {
        try {
            List<Image> images = new ArrayList<Image>();
            Connection con = DB.createConnection();

            PreparedStatement getPhotos;
            String queryString = "SELECT * FROM photos WHERE userID = '" + userId + "'";

            // if there is a caption or a date or both, query for them too
            if (!caption.equals(FileService.DEFAULT_CAPTION) && !date.equals(FileService.DEFAULT_DATE)) {
                queryString += " AND uploadDate = '" + date + "' OR caption = '" + caption + "'";
            }
            queryString += ";";
            getPhotos = con.prepareStatement(queryString);

            ResultSet rs = getPhotos.executeQuery();
            while (rs.next()) {
                final int idColumn = 1;
                final int fileNameColumn = 3;
                final int captionColumn = 4;
                final int dateColumn = 5;

                String obtainedFileName = rs.getString(fileNameColumn);
                String obtainedCaption = rs.getString(captionColumn);
                String obtainedDate = rs.getString(dateColumn);
                String obtainedId = rs.getString(1);

                Image currentImage = new Image();
                currentImage.setFileName(obtainedFileName);
                currentImage.setCaption(obtainedCaption);
                currentImage.setDate(obtainedDate);

                if (!writeImageFromDBToFolder(rs, currentImage)) {
                    System.out.println("could not write image to folder!");
                    return null;
                }

                images.add(currentImage);

            }
            rs.close();
            con.close();
            return images;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the images under the specified user id
     * 
     * @throws SQLException
     * 
     */
    public static List<Image> getImages(int userId) {
        List<Image> results = new ArrayList<Image>();
        try {
            // Create SQL Statement
            // Send to DB
            Connection con = DB.createConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from photos WHERE userID = " + userId);

            // Put in list and return;
            while (rs.next()) {
                Image currentImage = new Image();
                currentImage.setFileName(rs.getString("filename"));
                currentImage.setCaption(rs.getString("caption"));
                currentImage.setDate(rs.getString("uploadDate"));

                if (!writeImageFromDBToFolder(rs, currentImage)) {
                    System.out.println("could not write image to folder!");
                    return null;
                }

                System.out.println("added an image");
                results.add(currentImage);
            }
            rs.close();
            con.close();

            // Return list of images
            return results;
        } catch (SQLException e) {
            // deal with errors
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * @author Clayton Hunter
     * 
     * @param rs           used to get the image blob
     * @param currentImage used to obtain the file name of the image
     * @return true if image was added to the folder, false otherwise
     */
    private static boolean writeImageFromDBToFolder(ResultSet rs, Image currentImage) {
        try {
            FileOutputStream output = new FileOutputStream(System.getProperty("catalina.base")
                    + "\\webapps\\COMP3940_Assignment1\\images\\" + currentImage.getFileName());
            InputStream input = rs.getBinaryStream("image");
            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            output.close();
            input.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @author Clayton Hunter
     * @return the amount of entries in the photos table under the specified user id
     */
    public static int getAmountOfImagesUnder(int userId) {
        int count = -1;
        try {
            Connection con = DB.createConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM photos WHERE userID = " + userId);
            if (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return count;
    }

    /**
     * @author Ravinder Shokar
     * @param imageId
     * @return boolean true if successful false otherwise
     */
    public static boolean deleteImage(String imageId) {
        try {
            Connection con = DB.createConnection();
            Statement statement = con.createStatement();
            boolean results = statement.execute("DELETE FROM photos where id = " + imageId + ";");
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

}
