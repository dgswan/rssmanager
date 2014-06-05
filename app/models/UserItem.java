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
        create();
    }

    public static List<UserItem> findByUser(User user, int page, int length) {
        return find("user", user).fetch(page, length);
    }


}
