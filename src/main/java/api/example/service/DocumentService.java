package api.example.service;

import api.example.config.DocumentStorageProperty;
import api.example.dao.DocumentRepository;
import api.example.domain.Customer;
import api.example.domain.document.Document;
import api.example.domain.document.DocumentHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final FactoryService factoryService;
    private final Path docStorageLocation;

    public DocumentService(DocumentRepository documentRepository, FactoryService factoryService,
                           DocumentStorageProperty documentStorageProperty) throws IOException {
        this.documentRepository = documentRepository;
        this.factoryService = factoryService;
        this.docStorageLocation = Paths.get(documentStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();

        Files.createDirectories(this.docStorageLocation);
    }

    @Transactional
    public void addDocument(Customer customer, MultipartFile multipartFile) throws NoSuchAlgorithmException, IOException {
        Document document = factoryService.createDocument(customer, multipartFile);
        storeDocument(multipartFile, document.getHash());
        documentRepository.save(document);
    }

    public DocumentHolder getDocument(Customer customer) throws IOException {
        Optional<Document> optionalDocument = documentRepository.findDocumentByCustomer(customer);
        if (optionalDocument.isEmpty())
            return null;

        Document document = optionalDocument.get();
        String hash = document.getHash();
        byte[] bytes = loadDocument(hash);

        return new DocumentHolder(document, bytes);
    }

    private void storeDocument(MultipartFile file, String hash) throws IOException {
        Path targetLocation = this.docStorageLocation.resolve(hash);
        Files.copy(file.getInputStream(), targetLocation);
    }

    private byte[] loadDocument(String hash) throws IOException {
        Path targetLocation = this.docStorageLocation.resolve(hash);
        return Files.readAllBytes(targetLocation);
    }
}
