package models;


import play.data.validation.Email;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;
import play.libs.Crypto;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User extends Model {

    public static final String AUTH_KEY  = "AUTH_KEY";

    @ManyToMany
    public List<Channel> channels;

    @OneToMany
    public List<UserItem> userItems;

    @Required
    @Unique
    public String username;

    @Required
    public String passwordHash;

    @Email
    @Required
    @Unique
    public String email;

    public User(String password, String username, String email) {
        this.passwordHash = Crypto.passwordHash(password);
        this.username = username;
        this.email = email;
    }

    public static boolean exists (String password, String login) {
        return !User.find("username = ? or email = ? and passwordHash = ?", login, login, Crypto.passwordHash(password)).fetch().isEmpty();
    }

    public static User getByCookie(String cookie) {  //TODO
        List<User> users = User.findAll();
        return users.get(0);
    }

    public Channel getChannel(long channelid) {
        return Channel.getChannel(channelid);
    }
}
