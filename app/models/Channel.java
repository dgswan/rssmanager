package models;


import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Channel extends Model {
    @ManyToMany
    public List<User> users;

    @OneToMany
    public List<Channel> channels;

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
        title = "title";
        description = "description";
        url = "url";
        pubDate = new Date();
        image = "jfksjkf";
    }

}
