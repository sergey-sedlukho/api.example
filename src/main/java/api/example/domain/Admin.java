package api.example.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Table(name = "admin")
@Entity
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_admin")
    @SequenceGenerator(name = "seq_admin", allocationSize = 1)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @SuppressWarnings("unused")
    @Access(AccessType.PROPERTY)
    @Column(name = "status", nullable = false)
    private int statusCode;

    @Transient
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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

    public int getStatusCode() {
        return status.code;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        this.status = Status.fromCode(statusCode);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        STATUS_1(1),
        STATUS_2(2);

        private static final Map<Integer, Status> enumMap = Arrays.stream(values()).
                collect(Collectors.toMap(status -> status.code,
                        status -> status,
                        (status, status2) -> status,
                        HashMap::new));

        private final int code;

        Status(int code) {
            this.code = code;
        }

        public static Status fromCode(int code) {
            return enumMap.get(code);
        }
    }
}
