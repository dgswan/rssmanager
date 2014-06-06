package models;


import play.data.validation.Email;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;
import play.libs.Crypto;
import play.mvc.Scope;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User extends Model {

    public static final String USER_NAME = "username";

    @OneToMany (mappedBy = "user")
    public List<UserItem> userItems;

    @OneToMany (mappedBy = "user")
    public List<UserChannel> userChannels;

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

    public static User getBySession(Scope.Session session) {

        return User.find("username = ?", session.get(User.USER_NAME)).first();
    }

    public Channel getChannel(long channelid) {
        return Channel.getChannel(channelid);
    }

    public boolean isSubscribed (long channelId) {
        return !User.find("select u from User u, Channel ch, UserChannel uc where u.id = uc.user.id and ch.id = uc.channel.id and ch.id = ? and u.id = ?", channelId, this.id)
                .fetch().isEmpty();
    }

    public static List<User> getByChannel (Channel channel) {
        return find ("select u from User u, UserChannel uc, Channel c where u = uc.user and c = uc.channel and c = ?", channel).fetch();
    }
}
