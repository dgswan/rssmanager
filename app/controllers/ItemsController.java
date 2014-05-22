package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Item;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class ItemsController extends Controller {
    public static final Gson gson;

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Item.class, new ItemSerializer());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public static void items(int page, int lenght) {
        List<Item> items = Item.findAll();   // very tmp
        String jsonItems = gson.toJson(items);
        render(jsonItems, page, lenght);
    }

    public static void item(int itemId) {
        Item item = new Item();
        String jsonItem = gson.toJson(item);
        render(jsonItem);
    }

    public static void action (int itemId, String typeOfAction) {
        Logger.info("%d", itemId);
    }
}
