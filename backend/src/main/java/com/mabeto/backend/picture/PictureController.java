package com.mabeto.backend.picture;

import com.mabeto.backend.io.AmazonStorageHandler;
import com.mabeto.backend.io.DynamoDBHandler;
import com.mabeto.backend.picture.model.PictureInformation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/picture")
public class PictureController {
    private final AmazonStorageHandler amazonStorageHandler;
    private final DynamoDBHandler dynamoDBHandler;

    public PictureController() {
        this.amazonStorageHandler = new AmazonStorageHandler();
        this.dynamoDBHandler = new DynamoDBHandler();
    }

    @PostMapping
    public PictureInformation uploadPicture(@RequestBody MultipartFile file, String description) throws IOException {
        return editPicture(file, description, UUID.randomUUID().toString());
    }

    @GetMapping
    public PictureInformation getInfo(@RequestParam String id) {
        return dynamoDBHandler.getInformation(id);
    }

    @DeleteMapping
    public void deletePicture(@RequestParam String id) {
        dynamoDBHandler.deleteInformation(id);
        amazonStorageHandler.deleteFIle(id);
    }

    @GetMapping(path = "/all")
    public List<PictureInformation> listPictures() {
        return dynamoDBHandler.getAllInformation();
    }

    @PatchMapping
    public PictureInformation editPicture(@RequestBody(required = false) MultipartFile file, String description,
                                          @RequestParam String id) throws IOException {
        final PictureInformation information =
                new PictureInformation(id, description, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        amazonStorageHandler.saveFile(file, id);
        dynamoDBHandler.putInformation(information);
        return information;
    }
}
