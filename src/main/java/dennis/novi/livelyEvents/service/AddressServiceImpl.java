package dennis.novi.livelyEvents.service;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.Address;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.repository.AddressRepository;
import dennis.novi.livelyEvents.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }
    @Override
    public Address getAddress(long id){
        if (addressRepository.existsById(id)) {
            return addressRepository.findById(id).orElse(null);
        } else {
            throw new RecordNotFoundException("This address id doesn't exist: " + id);
        }
    }
    @Override
    public List<Address>getAddressCityStartsWith(String city){
        return addressRepository.findAllByCityStartingWith(city);

    }
    @Override
    public void save(Address address){
        addressRepository.save(address);
    }
    @Override
    public void deleteById(long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);}
        else {
            throw new RecordNotFoundException("The following ID can't be deleted because it doesnt exist. ID :" + id);
        }
    }
    @Override
    public void updateUserAddress(Address newAddress, long id, String username) {
        if (!addressRepository.existsById(id)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        Long userAddressId = user.getAddress().getId();
        Address address= addressRepository.findById(userAddressId).get();
        address.setStreetName(newAddress.getStreetName());
        address.setHouseNumber(newAddress.getHouseNumber());
        address.setPostalCode(newAddress.getPostalCode());
        address.setCity(newAddress.getCity());
        address.setCountry(newAddress.getCountry());
        addressRepository.save(address);
    }
}
