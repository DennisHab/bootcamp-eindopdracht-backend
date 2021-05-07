package dennis.novi.livelyEvents.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.Range;
import javax.persistence.*;
import java.util.List;

@Entity
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="venue_id")
    private long id;

    @Column(length = 50, nullable = false)
    private String venueName;

    @Column
    private int capacity;

    @ElementCollection
    private List<String> priceList;

    @Column
    @Range(from = 1, to = 10)
    private double rating = 6;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "username")
    private UserOwner userOwner;

    @OneToMany
    private List<Review> reviews;

    @OneToOne(fetch = FetchType.EAGER)
    /*@JsonBackReference*/
    @JoinColumn(name = "address_id")
    Address address;


    public Venue(){

    }
    public Venue(long id, String venueName, int capacity, List<String> priceList, @Range(from = 1, to = 10) double rating, UserOwner userOwner, List<Review> reviews, Address address) {
        this.id = id;
        this.venueName = venueName;
        this.capacity = capacity;
        this.priceList = priceList;
        this.rating = rating;
        this.userOwner = userOwner;
        this.reviews = reviews;
        this.address = address;
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

    public List<String> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<String> priceList) {
        this.priceList = priceList;
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
}
