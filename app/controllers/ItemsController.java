package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Item;
import models.User;
import models.UserItem;
import play.mvc.Controller;
import play.mvc.Http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsController extends Controller {
    public static final Gson gson;

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Item.class, new ItemSerializer());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public static void items(int page, int length) {
        List<Item> items =  Item.getFeed(User.getBySession(session), page, length);
        Map<String, Object> response = new HashMap<String, Object>();
        //create response
        response.put("items", items);
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("offset", page);
        metadata.put("limit", length);
        metadata.put("count", items.size());
        response.put("metadata", metadata);
        String prettyJson = gson.toJson(response);
        renderJSON(prettyJson);
    }

    public static void item(long itemId) {
        Item item = new Item();
        String jsonItem = gson.toJson(item);
        render(jsonItem);
    }

    public static void action (long itemId, String action) {
        Item item = Item.findById(itemId);
        UserItem userItem = UserItem.find("item = ? and user = ?", item, User.getBySession(session)).first();
        if (action.equals("read")) {
            userItem.isRead = true;
        } else if (action.equals("unread")) {
            userItem.isRead = false;
        }
        userItem.save();
        int code = Http.StatusCode.OK;
        render(code);
    }
}
