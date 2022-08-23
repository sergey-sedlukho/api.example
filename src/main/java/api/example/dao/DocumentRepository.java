package api.example.dao;

import api.example.domain.Customer;
import api.example.domain.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("select d from Document d where d.customer = :customer")
    Optional<Document> findDocumentByCustomer(@Param("customer") Customer customer);

}
