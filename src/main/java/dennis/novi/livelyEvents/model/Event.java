package dennis.novi.livelyEvents.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    Venue venue;

    @OneToMany(mappedBy = "event")
    private List<Review> reviews;

    @Column
    private String type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String date;

    @Column(nullable = false, length = 5)
    private String time;

    @Column
    private boolean ticketRequired;

    @Column
    private String eventDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTicketRequired() {
        return ticketRequired;
    }

    public void setTicketRequired(boolean ticketRequired) {
        this.ticketRequired = ticketRequired;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }




}
