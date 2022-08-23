package api.example.service;

import api.example.controller.request.customer.CreateCustomerRequest;
import api.example.controller.request.user.CreateUserRequest;
import api.example.domain.Customer;
import api.example.domain.User;
import api.example.domain.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;

@Service
public class FactoryService {

    public Document createDocument(Customer customer, MultipartFile multipartFile) throws NoSuchAlgorithmException {
        Document document = new Document();
        document.setCustomer(customer);
        document.setName(multipartFile.getOriginalFilename());
        document.setMimeType(multipartFile.getContentType());
        document.setSize(multipartFile.getSize());
        document.setHash();
        return document;
    }

    public Customer createCustomer(CreateCustomerRequest request, User createdBy) {
        Customer customer = new Customer();
        customer.setValid(true);
        customer.setCreatedBy(createdBy);
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        return customer;
    }

    public User createUser(CreateUserRequest request) {
        User user = new User();
        user.setValid(true);
        user.setName(request.getName());
        return user;
    }
}
