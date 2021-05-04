package dennis.novi.livelyEvents.repository;
import dennis.novi.livelyEvents.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByCityStartingWith(String city);
    Address findByUserId(Long userId);
}
