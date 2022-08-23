package api.example.controller;

import api.example.controller.request.admin.ChangeAdminStatusRequest;
import api.example.dao.AdminRepository;
import api.example.domain.Admin;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
@Api(value = "/admin", tags = "Supervisor API")
public class SupervisorController {
    Logger logger = LoggerFactory.getLogger(SupervisorController.class);

    private final AdminRepository adminRepository;

    public SupervisorController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PutMapping("/status")
    Admin setAdminStatus(@RequestBody ChangeAdminStatusRequest request) {
        try {
            Admin loggedAdmin = adminRepository.findById(1L).get();

            Admin.Status status = Admin.Status.fromCode(request.getCode());
            if (status == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            loggedAdmin.setStatus(status);
            return adminRepository.save(loggedAdmin);
        } catch (Exception e) {
            String errorMessage = "Cannot delete user due the internal, error";
            logger.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, e);
        }
    }
}
