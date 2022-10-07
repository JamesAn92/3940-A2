package servlet;

import java.io.*;

public class ServletResponse {
   private OutputStream outputStream = null;

   public ServletResponse(OutputStream outputStream) {
      this.outputStream = outputStream;
   }

   public OutputStream getOutputStream() {
      return outputStream;
   }

}
