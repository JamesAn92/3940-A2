package models;

public class Image {

    private String fileName;
    private String caption;
    private String date;
    private String id;

    public Image(String fileName, String caption, String date, String id) {
        this.fileName = fileName;
        this.caption = caption;
        this.date = date;
        this.id = id;
    }

    public Image() {
    }

    public String getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public String getDate() {
        return date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String toJson() {
        return ("{ \"id\":" + this.getId() + "," +
                "\"fileName\": \"" + this.getFileName() + "\"," +
                "\"caption\": \"" + this.getCaption() + "\"," +
                "\"date\": \"" + this.getDate() + "\" }");
    }

    public String toString() {
        return "" + this.getId() + " " + this.getFileName() + " " + this.getCaption();
    }

}
