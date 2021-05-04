package dennis.novi.livelyEvents.model;

import javax.persistence.*;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    Venue venue;

    @Column
    private String type;

    @Column
    private String name;

    @Column
    private String date;

    @Column
    private String time;

    @Column
    private boolean ticketRequired;

    @Column
    private String eventDescription;


}
