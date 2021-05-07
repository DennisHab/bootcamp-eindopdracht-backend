package dennis.novi.livelyEvents.model;

import org.jetbrains.annotations.Range;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Normal")
public class UserNormal extends User {

    @ElementCollection
    private List<String> favouredEvents;

    @OneToMany
    List<Review> reviews;

    @Column
    @Range(from = 1, to = 10)
    private double rating;


    public List<String> getFavouredEvents() {
        return favouredEvents;
    }

    public void setFavouredEvents(List<String> favouredEvents) {
        this.favouredEvents = favouredEvents;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


}
