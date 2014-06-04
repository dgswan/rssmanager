package models;


import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User extends Model {
    @ManyToMany
    public List<Channel> channels;

    @OneToMany
    public List<UserItem> userItems;

    @Required
    public String username;

    @Required
    public String passwordHash;

    @Email
    @Required
    public String email;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

}
