package dennis.novi.livelyEvents.model;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Owner")
public class UserOwner extends User {

    @OneToMany
    @JoinColumn(name = "venue_id")
    List<Venue> venueList;



    public List<Venue> getVenueList() {
        return venueList;
    }

    public void setVenueList(List<Venue> venueList) {
        this.venueList = venueList;
    }
}
