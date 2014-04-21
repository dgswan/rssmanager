package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    public String description;

    @Required
    public String url;

    @Required
    public Date pubDate;

    @Required
    public String image;

    public Item() {
        id = 3l;
        title = "title";
        description = "description";
        url = "url";
        pubDate = new Date();
    }

}
