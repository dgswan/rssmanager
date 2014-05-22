package controllers;

import com.sun.syndication.io.FeedException;
import play.*;
import play.mvc.*;

import java.io.IOException;
import java.util.*;

import models.*;

public class Application extends Controller {

    public static void signIn(String email, String password) {
    }

    public static void signUp(String email, String password) {
    }

    public static void index() {
        List<Item> items = Item.findAll();
        List<Channel> channels = Channel.findAll();
        render(channels, items);
    }

    public static void login() {
        render();
    }

    public static void feed() {
        List<Item> items = Item.findAll();
        List<Item> firstThreeItems = items.subList(0, 3);
        List<Item> otherItems = items.subList(3, items.size());
        render(firstThreeItems, otherItems);
    }

    public static void channel() {
        render();
    }

    public static void addChannel(String url) {
        try {
            Channel.addChannel(url);
            List<Channel> channels = Channel.findAll();
            for (Channel channel :channels) {
                channel.update();
            }
        }
        catch (FeedException feedException) {
            feedException.printStackTrace();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}