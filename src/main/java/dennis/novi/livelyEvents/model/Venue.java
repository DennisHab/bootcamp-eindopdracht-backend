package dennis.novi.livelyEvents.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.Range;
import javax.persistence.*;
import java.util.List;

@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="venue_id")
    private long id;

    @Column(length = 50, nullable = false, unique = true)
    private String venueName;

    @Column
    private int capacity;

    @Column
    private String image;

    @Column
    @Range(from = 1, to = 10)
    private double rating;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "username")
    private UserOwner userOwner;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Column
    private String facebook;

    @Column
    private String instagram;

    @Column
    private String twitter;

    @Column
    private String website;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "venue")
    @JsonIgnoreProperties("reviews")
    private List<Event> events;

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "address_id")
    Address address;

    public Venue(){

    }
    public Venue(long id, String venueName, int capacity, @Range(from = 1, to = 10) double rating, UserOwner userOwner, List<Review> reviews, Address address, String image) {
        this.id = id;
        this.venueName = venueName;
        this.capacity = capacity;
        this.rating = rating;
        this.userOwner = userOwner;
        this.reviews = reviews;
        this.address = address;
        this.image = image;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Venue(List<Review> reviews) {
        this.reviews = reviews;
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

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public UserOwner getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(UserOwner userOwner) {
        this.userOwner = userOwner;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
