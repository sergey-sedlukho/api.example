package api.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Table(name = "api_user")
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name = "seq_user", allocationSize = 1)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @JsonIgnore
    @Column(name = "is_valid", nullable = false)
    private boolean valid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public enum Status {

        STATUS_1(1),
        STATUS_2(2);

        private final static Map<Integer, Status> enumMap =
                Arrays.stream(values()).collect(Collectors.toMap(Status::getCode, status -> status,
                        (s1, s2) -> s1, HashMap::new));

        private final int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static Status fromCode(int code) {
            return enumMap.get(code);
        }
    }
}
