package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Http;

import java.util.HashMap;
import java.util.Map;

public class UsersController extends Controller {
    private static final String BAD_REQUEST_MESSAGE = "Username or email is already exists";
    private static final String NOT_FOUND_MESSAGE = "Incorrect login or password";
    private static final String OK_MESSAGE = "Succesfull";

    public static void create (String password, String username, String email) {
        int code;
        String message;
        User user = new User(password, username, email);
        if (!user.validateAndCreate()) {
            code = Http.StatusCode.BAD_REQUEST;
            message = BAD_REQUEST_MESSAGE;
        } else {
            code = Http.StatusCode.CREATED;
            message = OK_MESSAGE;
        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("code", code);
        response.put("message", message);
        renderJSON(response);
    }

    public static void login(String password, String login) {
        int code;
        String message;
        User user = User.exists(password, login );
        if(user != null) {
            code = Http.StatusCode.OK;
            message = OK_MESSAGE;
            session.put("username", user.username);
        } else {
            code = Http.StatusCode.NOT_FOUND;
            message = NOT_FOUND_MESSAGE;
        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("code", code);
        response.put("message", message);
        renderJSON(response);
    }

    public static void logout () {
        int code = Http.StatusCode.OK;
        session.remove("username");
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        jsonResponse.put("code", code);
        renderJSON(jsonResponse);

    }
}
