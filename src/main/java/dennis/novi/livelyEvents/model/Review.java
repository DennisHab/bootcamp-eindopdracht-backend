package dennis.novi.livelyEvents.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnoreProperties({"reviews", "password","repeatedPassword","firstName","lastName","authorities","dateOfBirth","emailAddress","address","enabled","passwordValidation"})
    @JoinColumn(name ="username")
    UserNormal userNormal;

    @ManyToOne
    @JsonIgnoreProperties("reviews")
    Event event;

    @ManyToOne
    @JsonIgnoreProperties({"reviews", "events","image","facebook","instagram","twitter","website"})
    Venue venue;


    @Column
    private String reviewContent;

    @Column
    private double rating;

    @Column
    private double reviewRating = 6.0;

    public Review() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserNormal getUserNormal() {
        return userNormal;
    }

    public void setUserNormal(UserNormal userNormal) {
        this.userNormal = userNormal;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public double getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(double reviewRating) {
        this.reviewRating = reviewRating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
