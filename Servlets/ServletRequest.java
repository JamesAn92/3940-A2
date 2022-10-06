package servlets;

import java.io.*;
public class ServletRequest {
   private InputStream inputStream = null;
   public ServletRequest(InputStream inputStream) {
      this.inputStream = inputStream;
   }
   public InputStream getInputStream() {return inputStream;}
}