package models;


import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UserItem extends Model {

    public boolean isRead;

    @ManyToOne
    public User user;

    @ManyToOne
    public Item item;


}
