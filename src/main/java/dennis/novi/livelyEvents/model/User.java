package dennis.novi.livelyEvents.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.STRING)
public class User {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Id
    @Column(length = 50, unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(nullable = false)
    private boolean enabled = true;

    @OneToMany(
            targetEntity = dennis.novi.livelyEvents.model.Authority.class,
            mappedBy = "userName",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<dennis.novi.livelyEvents.model.Authority> authorities = new HashSet<>();


    @Column(length= 10)
    private String dateOfBirth;

    @Column(length = 300, nullable = false)
    private String emailAddress;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL )
    @JoinColumn(name = "address_id")
    private Address address;

    public User() {

    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
    public void removeAuthorities(Authority authority) {
        this.authorities.remove(authority);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}