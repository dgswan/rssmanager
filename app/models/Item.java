package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Item extends Model {

    @OneToMany
    public List<UserItem> userItems;

    @ManyToOne
    public Channel channel;

    @Required
    public String title;

    @Required
    @Column(length = 10000)
    public String description;

    @Required
    public String url;

    @Required
    public Date pubDate;

    @Required
    public String image;

    public Item() {

    }

    private String extractImage() {
        int beginIndex = description.indexOf("<img");
        if(beginIndex == -1) {
            return "";
        }
        int endIndex = description.indexOf(">", beginIndex);
        String img = description.substring(beginIndex, endIndex+1);
        description = description.replace(img, "");
        beginIndex = img.indexOf("http");
        return img.substring(beginIndex, img.indexOf("\"", beginIndex));
    }

    public Item(Channel channel, String title, String description, String url, Date pubDate) {
        this.channel = channel;
        this.title = title;
        this.description = description;
        this.url = url;
        this.pubDate = pubDate;
        this.image = extractImage();

    }

    public static List<Item> getItems(Channel channel, int page, int length) {
        return find("channel = ? order by pubDate desc", channel).fetch(page, length);

    }

    public static List<Item> getItems(User user, int page, int length) {
        List<UserItem> userItems = UserItem.findByUser(user);
        List<Item> items = new ArrayList<Item>();
        for (UserItem useritem : userItems) {
            items.add(useritem.item);
        }
        return items;
    }

    public Item getItem(int itemId) {
        return find("id", itemId).first();
    }

    public void markAsRead(User user) {
        UserItem useritem = find("user = ? and item = ?", user, this).first();
        useritem.isRead = true;
        useritem.refresh();
    }


}
