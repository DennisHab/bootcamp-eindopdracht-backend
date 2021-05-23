package dennis.novi.livelyEvents.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @Id
    @Column(name="username", length = 50, unique = true, nullable = false)
    private String username;


    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String repeatedPassword;

    @Column
    private String passwordValidation;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false)
    private boolean enabled = true;

    @OneToMany(
            targetEntity = dennis.novi.livelyEvents.model.Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<dennis.novi.livelyEvents.model.Authority> authorities = new HashSet<>();


    @Column(length= 10)
    private String dateOfBirth;

    @Column(length = 300)
    private String emailAddress;


    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL )
    @JoinColumn(name = "address_id")
    private Address address;

    public User() {

    }

    /*public User(String username, String password, String repeatedPassword, String passwordValidation, String firstName, String lastName, boolean enabled, Set<Authority> authorities, String dateOfBirth, String emailAddress, Address address) {
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.passwordValidation = passwordValidation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.authorities = authorities;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.address = address;
    }*/

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getPasswordValidation() {
        return passwordValidation;
    }
    public void setPasswordValidation(String passwordValidation) {
        this.passwordValidation = passwordValidation;
    }
}