package models;


import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
public class Channel extends Model {

    @OneToMany(mappedBy = "channel")
    public List<UserChannel> userChannels;

    @OneToMany
    public List<Item> items;

    @Required
    public String title;

    @Column(length = 4000)
    public String description;

    @Required
    @Unique
    public String url;

    @Required
    public Date pubDate;

    public String image;

    public Channel() {
    }

    public Channel(String title, String description, String url, Date pubDate, String image) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.pubDate = pubDate;
        this.image = image;
    }

    public static List<Channel> getChannels(User user, int page, int length) {

        //return find("user", user).fetch(page, length);
        return findAll();
    }

    public static List<Channel> getChannels(String query, int page, int length) {
        query = "%" + query + "%";
        // return find("select ch from Channel ch join ch.users u where title like ? or description like ? or url like ? group by ch, ch.id order by count(u) desc", query, query, query).fetch(page, length);
        return find("select ch from UserChannel uc right join uc.channel ch where (title like ? or description like ? or url like ?) group by ch order by count(uc.user) desc", query, query, query)
                .fetch(page, length);
        //return find("title like ? or description like ? or url like ? ", query, query, query).fetch(page, length);


    }

    public static Channel getChannel(long channelId) {
        return find("id", channelId).first();
    }

    public List<Item> getItems(int page, int length) {
        return Item.getItems(this, page, length);
    }

    public List<Item> getItems(User user, int page, int length) {
        return Item.getItems(this, page, length);
    }

    private boolean exists(Item item) {
        return Item.count("select count(distinct i) from Item i where title = ? and pubDate = ? and channel = ?", item.title, item.pubDate, this) != 0;
    }

    public void update() throws IOException, FeedException {
        URL url = new URL(this.url);
        HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(httpcon));
        List entries = feed.getEntries();
        //List<User> users = getSubscribedUsers();
        Iterator itEntries = entries.iterator();

        while (itEntries.hasNext()) {
            SyndEntry entry = (SyndEntry) itEntries.next();
            Item item = new Item(this, entry.getTitle(),
                    entry.getDescription().getValue(),
                    entry.getLink(),
                    entry.getPublishedDate()
            );
            if (!exists(item)) {
                item.create();
                refresh();
                List<User> users = User.getByChannel(this);
                for (User user : users) {
                    UserItem useritem = new UserItem(user, item);
                    useritem.create();
                }
            }
        }


    }

    public static Channel addChannel(String urlString) throws IOException, FeedException {

        URL url = new URL(urlString);
        HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
        // Reading the feed
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed channel = input.build(new XmlReader(httpcon));
        String img = channel.getImage() != null ? channel.getImage().getUrl() : "";
        Channel ch = new Channel(channel.getTitle(),
                channel.getDescription(),
                urlString,
                channel.getPublishedDate(),
                img);
        if (ch.validateAndCreate()) {
            ch.update();
            return ch;
        }
        return null;
    }

    public static List<Channel> getByUser(User user, int page, int length) {
        return find("select c from User u, UserChannel uc, Channel c where u.id = uc.user.id and uc.channel.id = c.id  and u = ? order by c.title", user)
                .fetch(page, length);
    }


}
