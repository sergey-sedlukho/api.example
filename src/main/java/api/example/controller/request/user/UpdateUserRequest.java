package api.example.controller.request.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateUserRequest {

    @NotNull
    private Long id;
    private String name;
}
