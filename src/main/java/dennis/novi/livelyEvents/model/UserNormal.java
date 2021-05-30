package dennis.novi.livelyEvents.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.Range;
import javax.persistence.*;
import java.util.List;


@Entity
@DiscriminatorValue("Normal")
public class UserNormal extends User {

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "userNormal")
    private List<Event> favouredEvents;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userNormal")
    @JsonIgnoreProperties("userNormal")
    List<Review> reviews;

    @Column
    @Range(from = 1, to = 10)
    private double rating;

    public UserNormal() {
    }

    /*public UserNormal(String username, String password, String repeatedPassword, String passwordValidation, String firstName, String lastName, boolean enabled, Set<Authority> authorities, String dateOfBirth, String emailAddress, Address address, List<String> favouredEvents, List<Review> reviews, @Range(from = 1, to = 10) double rating) {
        super(username, password, repeatedPassword, passwordValidation, firstName, lastName, enabled, authorities, dateOfBirth, emailAddress, address);
        this.favouredEvents = favouredEvents;
        this.reviews = reviews;
        this.rating = rating;
    }*/

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Event> getFavouredEvents() {
        return favouredEvents;
    }

    public void setFavouredEvents(List<Event> favouredEvents) {
        this.favouredEvents = favouredEvents;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
