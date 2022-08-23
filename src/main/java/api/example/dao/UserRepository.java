package api.example.dao;

import api.example.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAllByValidIsTrue();

    Optional<User> findByIdAndValidIsTrue(Long id);

    @Modifying
    @Query("update User u set u.valid=false where u.id = :userId")
    @Transactional
    void deleteUser(@Param("userId") Long userId);
}
