package models;


import play.data.validation.Email;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;
import play.libs.Crypto;
import play.mvc.Scope;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends Model {

    public static final String USER_NAME = "username";

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "UserChannel",
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    public List<Channel> channels;

    @OneToMany (mappedBy = "user")
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

    public static User exists (String password, String login) {
        return User.find("(username = ? or email = ?) and passwordHash = ?", login, login, Crypto.passwordHash(password)).first();
    }

    public static User getBySession(Scope.Session session) {  //TODO

        return User.find("username = ?", session.get(User.USER_NAME)).first();
    }

    public Channel getChannel(long channelid) {
        return Channel.getChannel(channelid);
    }
}
