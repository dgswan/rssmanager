package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UserChannel extends Model {
    @ManyToOne
    User user;

    @ManyToOne
    Channel channel;

    public UserChannel(User user, Channel channel) {
        this.user = user;
        this.channel = channel;
    }
}
