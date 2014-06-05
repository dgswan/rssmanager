package models;


import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class UserItem extends Model {

    public boolean isRead;

    @ManyToOne
    public User user;

    @ManyToOne
    public Item item;

    public UserItem(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public static List<UserItem> getByChannelAndUser (Channel channel, User user) {
        return UserItem.find ("user = ? and item.channel = ?", user, channel).fetch();
    }


}
