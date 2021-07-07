package dennis.novi.livelyEvents.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Owner")
public class UserOwner extends User {

    @OneToMany(mappedBy = "userOwner", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    List<Venue> venues = new ArrayList<>();

    public UserOwner() {

    }

    public UserOwner(List<Venue> venues) {
        this.venues = venues;

    }

    public List<Venue> getVenueList() {
        return venues;
    }

    public void setVenueList(List<Venue> venueList) {
        this.venues = venueList;
    }
}
