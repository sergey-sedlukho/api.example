package api.example.dao;

import api.example.domain.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAllByValidIsTrue();
    Optional<Customer> findByIdAndValidIsTrue(Long id);

    @Modifying
    @Query("update Customer c set c.valid=false where c.id = :customerId")
    @Transactional
    void deleteCustomer(@Param("customerId") Long customerId);
}
