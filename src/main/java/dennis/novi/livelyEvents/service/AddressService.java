package dennis.novi.livelyEvents.service;
import dennis.novi.livelyEvents.model.Address;
import java.util.List;

public interface AddressService {
    List<Address> getAllAddresses();
    Address getAddress(long id);
    List<Address>getAddressCityStartsWith(String city);
    void save(Address address);
    void deleteById(long id) ;
    void updateUserAddress(Address address, long id, String username);
}
