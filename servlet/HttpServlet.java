package servlet;

import router.*;

public interface HttpServlet {
    public void doGet(HttpRequest request, HttpResponse response);

    public void doPost(HttpRequest request, HttpResponse response);
}