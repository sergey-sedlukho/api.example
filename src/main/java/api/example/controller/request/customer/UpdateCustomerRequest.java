package api.example.controller.request.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateCustomerRequest {

    @NotNull
    private Long id;
    private String name;
    private String surname;
}
