package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Http;

public class UsersController extends Controller {
    private static final String BAD_REQUEST_MESSAGE = "Username or email is already exists";
    private static final String OK_MESSAGE = "Succesfull";

    public static void create (String password, String username, String email) {
        int code;
        String message;
        User user = new User(password, username, email);
        if (!user.create()) {
            code = Http.StatusCode.BAD_REQUEST;
            message = BAD_REQUEST_MESSAGE;
            render(code, message);
        } else {
            code = Http.StatusCode.CREATED;
            message = OK_MESSAGE;
            render(code, message);
        }
    }
}
