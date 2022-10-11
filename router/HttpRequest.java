package router;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JPopupMenu.Separator;

public class HttpRequest {

    private String HEAD_BODY_DELIM = "\r\n\r\n";
    private String BOUNDARY_PROTOCOL_HYPENS = "--";
    private String method;
    private String URL;
    private String version;
    private InputStream inputStream = null;
    private HashMap<String, String> keyValues = new HashMap<>();
    private HashMap<String, String> image = new HashMap<>();
    private String boundary = null;

    public HttpRequest(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        String wholeStream = "";
        String[] head;
        String body;
        String[] seperatedRequest;
        while (inputStream.available() != 0) {
            wholeStream += (char) inputStream.read();
        }
        String hex = "";

        // Iterating through each byte in the array
        for (byte i : wholeStream.getBytes()) {
            hex += String.format("%02X", i);
        }

        // TODO REMOVE
        // System.out.print(hex);

        seperatedRequest = serperateRequest(wholeStream);
        head = seperatedRequest[0].split("\r\n");

        body = seperatedRequest[1];

        // Splits the stream into lines and stores into array
        String[] arrayStream = wholeStream.split("\n");
        parseMethodAndProtocol(head[0]);
        parseHeaders(head);

        if (keyValues.containsKey("Content-Type") &&
                keyValues.get("Content-Type").equals("multipart/form-data")) {
            // Parse body as form data
            parseFormData(body);
        } else {
            // If not form data
            // parseBody();
        }
    }

    // Sets method, url, version from first line of inputstream
    private void parseMethodAndProtocol(String stream) {
        String[] arrayStream = stream.split(" ");
        setMethod(arrayStream[0]);
        setURL(arrayStream[1]);
        setVersion(arrayStream[2]);
    }

    // Parse header and store into Hashmap
    private void parseHeaders(String[] stream) {
        for (int i = 1; i < stream.length - 1; i++) {
            String[] tempValue = stream[i].split(": ");

            // if we reach content type obtain the boundary
            if (tempValue[0].equals("Content-Type") && tempValue[1].contains("multipart/form-data")) {
                boundary = BOUNDARY_PROTOCOL_HYPENS + tempValue[1].split(";")[1].split("=")[1];
                tempValue[1] = tempValue[1].split(";")[0];
            }

            // store headers in a map
            keyValues.put(tempValue[0].trim(), tempValue[1].trim());
        }
    }

    /**
     * Seperates request head and body
     * 
     * @param stream
     */
    private String[] serperateRequest(String stream) {
        String[] result = new String[2];
        for (int i = 0; i < stream.length() - HEAD_BODY_DELIM.length() + 1; i++) {
            if (stream.substring(i, i + HEAD_BODY_DELIM.length()).equals(HEAD_BODY_DELIM)) {
                result[0] = stream.substring(0, i);
                result[1] = stream.substring(i + HEAD_BODY_DELIM.length());
                return result;
            }
        }
        return result;
    }

    /**
     * This will parse all form data and store it as a key value pair.
     * in the case the type is image, we will save it as a map and accesible
     * using a getImage().
     * 
     * @param stream
     * @param startingIndex
     */
    private void parseFormData(String stream) {
        // TODO REMOVE
        // System.out.println(boundary);
        String[] separatedByBoundary = stream.split(boundary);

        // loop through each form data (start at 1 to discard the item before the first
        // boundary because all it consists of is /r/n)
        for (int i = 1; i < separatedByBoundary.length; i++) {
            String[] separatedFormData = serperateRequest(separatedByBoundary[i]);
            String formDataHead = separatedFormData[0];
            String formDataBody = separatedFormData[1];

            // TODO REMOVE
            System.out.println(formDataBody);
            if (formDataHead == null) {
                // if for some reason there is no head in the form data early, we are done
                return;
            }

            // parse the head of the current form data

            String key = null;
            String[] formDataHeadLines = formDataHead.split("\r\n");
            for (int j = 0; j < formDataHeadLines.length; j++) {
                if (!formDataHeadLines[j].isBlank()) {

                    String[] keyVal = formDataHeadLines[j].split(":");
                    if (keyVal[0].contains("Content-Disposition")) {
                        key = parseContentDisposition(keyVal[1]);
                    } else {
                        // TODO REMOVE
                        // System.out.println("content data parsed:");
                        // System.out.println(keyVal[0].trim());
                        // System.out.println(keyVal[1].trim());
                        keyValues.put(keyVal[0].trim(), keyVal[1].trim());
                    }
                }
            }

            // parse the body of the form data
            // TODO PARSE
            // System.out.println("body parsed: ");
            // System.out.println(formDataBody.trim());
            // System.out.println("end body parsed");

            if (image.get(key) == null) {
                // if there is NO value for the image key, then we don't have an image!
                keyValues.put(key, formDataBody.trim());
            } else {
                // if there is a value for the image key, that is the filepath, map it to the
                // image
                image.put(image.get(key), formDataBody);
            }
        }

    }

    private String parseContentDisposition(String contentDisposition) {
        // split the content disposition
        String[] contentDispositionVals = contentDisposition.split(";");

        // get the key (start at index 1 because we want to ignore 'form-data')
        // TODO REMOVE
        // System.out.println(contentDispositionVals[1]);
        String keyWithQuotes = contentDispositionVals[1].trim().split("=")[1];
        String key = keyWithQuotes.substring(1, keyWithQuotes.length() - 1);

        // if the length is greater than 1, then we have a file path, store it to the
        // value of the key
        if (contentDispositionVals.length > 2) {
            String fileNameWithQuotes = contentDispositionVals[2].trim().split("=")[1];
            String fileName = fileNameWithQuotes.substring(1, fileNameWithQuotes.length() - 1);
            image.put(key, fileName);
        } else {
            image.put(key, null);
        }
        return key;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAttribute(String headerKey) {
        return keyValues.get(headerKey);
    }

    public String getFile(String fileName) {
        return image.get(fileName);
        // return new FileInputStream(image.get(fileName));
    }

    public String getValue(String key) {
        return keyValues.get(key);
    }

    public String getFileName(String imageName) {
        // System.out.println(image.toString());
        return image.get(imageName);
    }

}

// GET / HTTP/1.1
// HOST:localhost:8999
// Connection: close
// User-agent: Mozzilla/4.0
// Accept Language: fr

// public static String parse(String responseBody) {
// JSONArray albums = new JSONArray(responseBody);
// for (int i = 0 ; i < albums.length(); i++) {
// JSONObject requestJSON = albums.getJSONObject(i);

// // int userId = album.getInt("userId");
// // int id = album.getInt("id");
// // String title = album.getString("title");
// System.out.println(id+" "+title+" "+userId);
// }
// return null;
// }
