package dennis.novi.livelyEvents.model;

import dennis.novi.livelyEvents.service.UserNormalService;
import org.jetbrains.annotations.Range;
import org.springframework.beans.factory.annotation.Autowired;
import dennis.novi.livelyEvents.utils.AverageRatingCalculator;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Normal")
public class UserNormal extends User {

    @ElementCollection
    private List<String> favouredEvents;

    @OneToMany(mappedBy = "userNormal")
    List<Review> reviews;

    @Column
    @Range(from = 1, to = 10)
    private double rating;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

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
