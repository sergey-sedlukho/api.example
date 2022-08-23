package api.example.controller;

import api.example.controller.request.user.CreateUserRequest;
import api.example.controller.request.user.UpdateUserRequest;
import api.example.dao.UserRepository;
import api.example.domain.User;
import api.example.service.FactoryService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Api(value = "/users", tags = "Admin API")
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserRepository userRepository;
    private final FactoryService factoryService;

    public AdminController(UserRepository userRepository, FactoryService factoryService) {
        this.userRepository = userRepository;
        this.factoryService = factoryService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> all() {
        return userRepository.findAllByValidIsTrue();
    }

    @PutMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    User create(@RequestBody CreateUserRequest request) {
        try {
            User user = factoryService.createUser(request);
            return userRepository.save(user);
        } catch (Exception e) {
            String errorMessage = "Cannot create user due the internal, error";
            logger.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, e);
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    User update(@RequestBody UpdateUserRequest request) {
        try {
            Long id = request.getId();
            Optional<User> optionalUser = userRepository.findByIdAndValidIsTrue(id);
            if (optionalUser.isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            User user = optionalUser.get();
            String name = request.getName();
            if (StringUtils.hasLength(name)) {
                user.setName(name);
            }

            return userRepository.save(user);
        } catch (Exception e) {
            String errorMessage = "Cannot update user due the internal, error";
            logger.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, e);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    void delete(@PathVariable Long id) {
        try {
            userRepository.deleteUser(id);
        } catch (Exception e) {
            String errorMessage = "Cannot delete user due the internal, error";
            logger.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, e);
        }
    }

}
