package dennis.novi.livelyEvents.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnore
    UserNormal userNormal;

    @ManyToOne
    @JsonIgnore
    Event event;

    @ManyToOne
    @JsonIgnore
    Venue venue;

    @Column
    private String reviewContent;




}
