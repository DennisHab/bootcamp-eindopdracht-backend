package dennis.novi.livelyEvents.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(dennis.novi.livelyEvents.model.AuthorityKey.class)
@Table(name = "authorities")
public class Authority implements Serializable {

    @Id
    @Column(nullable = false)
    private String userName;

    @Id
    @Column(nullable = false)
    private String authority;

    public Authority() {}
    public Authority(String username, String authority) {
        this.userName = username;
        this.authority = authority;
    }

    public String getUsername() {
        return userName;
    }
    public void setUsername(String username) {
        this.userName = username;
    }
    public String getAuthority() {
        return authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
