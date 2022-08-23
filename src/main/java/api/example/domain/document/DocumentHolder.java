package api.example.domain.document;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DocumentHolder {

    private Document document;
    private byte[] bytes;
}
