package servlet;

import router.*;
import exceptions.APIError;

public interface HttpServlet {
    public void doGet(HttpRequest request, HttpResponse response) throws Exception;

    public void doPost(HttpRequest request, HttpResponse response) throws Exception;
}