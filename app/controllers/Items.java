package controllers;

import com.google.gson.Gson;
import models.Item;
import play.Logger;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

public class Items extends Controller {
    public static final Gson gson = new Gson();

    public static void items(int offset, int limit) {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item());
        items.add(new Item());
        String jsonItems = gson.toJson(items);
        render(jsonItems, offset, limit);
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
