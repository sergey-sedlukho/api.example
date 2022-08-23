package api.example.controller.request.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ChangeAdminStatusRequest {

    @NotNull
    private Integer code;
}
