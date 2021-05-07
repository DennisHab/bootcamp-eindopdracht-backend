package dennis.novi.livelyEvents.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Owner")
public class UserOwner extends User {

    @OneToMany(mappedBy = "userOwner")
    List<Venue> venues = new ArrayList<>();

    @Column(length= 10)
    private int kvkNumber;

    public UserOwner() {

    }

    public UserOwner(List<Venue> venues, int kvkNumber) {
        this.venues = venues;
        this.kvkNumber = kvkNumber;
    }

    public List<Venue> getVenueList() {
        return venues;
    }

    public void setVenueList(List<Venue> venueList) {
        this.venues = venueList;
    }
}
