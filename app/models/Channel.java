package models;


import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Entity
public class Channel extends Model {
    @ManyToMany
    public List<User> users;

    @OneToMany
    public List<Item> items;

    @Required
    public String title;

    @Required
    public String description;

    @Required
    public String url;

    @Required
    public Date pubDate;

    public String image;

    public Channel() {
        id = 4l;
        title = "title";
        description = "description";
        url = "url";
        pubDate = new Date();
        image = "jfksjkf";
        //create();
    }

    public Channel(String title, String description, String url, Date pubDate, String image) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.pubDate = pubDate;
        this.image = image;
      //  create();
    }

    public static List<Channel> getChannels(User user, int page, int length) {

        //return find("user", user).fetch(page, length);
        return findAll();
    }

    public static Channel getChannel(long channelId) {
        return find("id", channelId ).first();
    }

    public List<Item> getItems(int page, int length) {
        return Item.getItems(this, page, length);
    }

    public void subscribe(User user) {
        users.add(user);
        user.channels.add(this);
        refresh();
        user.refresh();

    }

    public void unsubscribe(User user) {
        users.remove(user);
        user.channels.remove(this);
        refresh();
        user.refresh();

    }

    public static void addChannel(String urlString) throws IOException, FeedException {

        URL url = new URL(urlString);
        HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
        // Reading the feed
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed channel = input.build(new XmlReader(httpcon));
        String img = channel.getImage() != null ? channel.getImage().getUrl() : "";
        Channel ch = new Channel(channel.getTitle(), channel.getDescription(),
                channel.getUri(),
                channel.getPublishedDate(),
                img);
        if (ch.create()) {
            play.Logger.info("channel created");
            play.Logger.info("%d", ch.id);
            play.Logger.info("%s", ch.description );
            play.Logger.info("%s", channel.getDescription());
        } else {
            play.Logger.info("channel not created");
        }
    }



}
