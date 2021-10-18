package com.mabeto.backend.picture;


import com.mabeto.backend.picture.model.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/picture")
public class PictureController {
    private final PictureRepository pictureRepository;

    @Autowired
    public PictureController(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @PostMapping
    public Picture uploadPicture(@RequestBody MultipartFile file, String description) throws IOException {
        return pictureRepository.save(Picture.builder()
                .description(description)
                .createdAt(LocalDateTime.now())
                .image(file.getBytes())
                .build()
        );
    }

    @GetMapping
    public Picture getPicture(@RequestParam Long pictureId) {
        Optional<Picture> possiblePicture = pictureRepository.findById(pictureId);
        if (possiblePicture.isPresent()) {
            return possiblePicture.get();
        } else {
            throw new EntityNotFoundException("No picture with Id " + pictureId + " found!");
        }

    }

    @DeleteMapping
    public void deletePicture(@RequestParam Long pictureId) {
        pictureRepository.deleteById(pictureId);
    }

    @GetMapping(path = "/all")
    public List<Picture> getAllPictures() {
        return pictureRepository.findAll();
    }

    @PatchMapping
    public Picture editPicture(@RequestBody(required = false) MultipartFile file, String description, @RequestParam Long pictureId) throws IOException {
        Optional<Picture> possiblePicture = pictureRepository.findById(pictureId);
        if (possiblePicture.isEmpty()) {
            throw new EntityNotFoundException("No picture with id " + pictureId + " found!");
        } else {
            Picture picture = possiblePicture.get();
            if (file != null)
                picture.setImage(file.getBytes());
            if (description != null)
                picture.setDescription(description);

            return pictureRepository.save(picture);
        }

    }
}
