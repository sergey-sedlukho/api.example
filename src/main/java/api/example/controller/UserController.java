package api.example.controller;


import api.example.controller.request.customer.CreateCustomerRequest;
import api.example.controller.request.customer.UpdateCustomerRequest;
import api.example.dao.CustomerRepository;
import api.example.dao.UserRepository;
import api.example.domain.Customer;
import api.example.domain.User;
import api.example.domain.document.DocumentHolder;
import api.example.service.DocumentService;
import api.example.service.FactoryService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@Api(value = "/customers", tags = "User API")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final DocumentService documentService;
    private final FactoryService factoryService;

    public UserController(CustomerRepository customerRepository, UserRepository userRepository,
                          DocumentService documentService, FactoryService factoryService) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.documentService = documentService;
        this.factoryService = factoryService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Customer> all() {
        return customerRepository.findAllByValidIsTrue();
    }

    @PutMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer create(@RequestBody CreateCustomerRequest request) {
        try {
            User loggedUser = userRepository.findById(1L).get();
            Customer customer = factoryService.createCustomer(request, loggedUser);
            return customerRepository.save(customer);
        } catch (Exception e) {
            String errorMessage = "Cannot create customer due the internal, error";
            logger.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, e);
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer update(@RequestBody UpdateCustomerRequest request) {
        try {
            Long id = request.getId();
            Optional<Customer> optionalCustomer = customerRepository.findByIdAndValidIsTrue(id);
            if (optionalCustomer.isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            User loggedUser = userRepository.findById(1L).get();
            Customer customer = optionalCustomer.get();


            String name = request.getName();
            if (StringUtils.hasLength(name))
                customer.setName(name);

            String surname = request.getSurname();
            if (StringUtils.hasLength(surname))
                customer.setSurname(surname);

            customer.setModifiedBy(loggedUser);

            return customerRepository.save(customer);
        } catch (Exception e) {
            String errorMessage = "Cannot update customer due the internal, error";
            logger.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, e);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    void delete(@PathVariable Long id) {
        try {
            customerRepository.deleteCustomer(id);
        } catch (Exception e) {
            String errorMessage = "Cannot delete customer due the internal, error";
            logger.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, e);
        }
    }

    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void uploadPhoto(@PathVariable Long id, @RequestParam(value = "photo") MultipartFile multipartFile) {
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(id);
            if (optionalCustomer.isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            Customer customer = optionalCustomer.get();
            documentService.addDocument(customer, multipartFile);
        } catch (Exception e) {
            String errorMessage = "Cannot upload customer photo due the internal, error";
            logger.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, e);
        }
    }

    @GetMapping(value = "/{id}/photo")
    @ResponseBody
    ResponseEntity<byte[]> photo(@PathVariable Long id) {
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(id);
            if (optionalCustomer.isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            Customer customer = optionalCustomer.get();
            DocumentHolder holder = documentService.getDocument(customer);
            if (holder == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            byte[] bytes = holder.getBytes();
            String mimeType = holder.getDocument().getMimeType();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).body(bytes);
        } catch (Exception e) {
            String errorMessage = "Cannot find customer photo due the internal, error";
            logger.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, e);
        }
    }
}
