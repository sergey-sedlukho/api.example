package api.example.controller.request.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateCustomerRequest {

    @NotNull
    private String name;
    @NotNull
    private String surname;
}
