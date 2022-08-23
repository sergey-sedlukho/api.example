package api.example.controller.request.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateUserRequest {

    @NotNull
    private String name;
}
