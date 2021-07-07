package dennis.novi.livelyEvents.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    public UserNormal() {
    }

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
}
