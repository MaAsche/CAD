package com.mabeto.backend.picture;

import com.mabeto.backend.fileio.AmazonStorageHandler;
import com.mabeto.backend.fileio.FilesystemPictureHandler;
import com.mabeto.backend.picture.model.PictureInformation;
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
    private final PictureInformationRepository pictureInformationRepository;
    private final FilesystemPictureHandler filesystemHandler;
    private final AmazonStorageHandler amazonStorageHandler;

    @Autowired
    public PictureController(PictureInformationRepository pictureInformationRepository) throws IOException {
        this.pictureInformationRepository = pictureInformationRepository;
        this.filesystemHandler = new FilesystemPictureHandler();
        this.amazonStorageHandler = new AmazonStorageHandler();
    }

    @PostMapping
    public PictureInformation uploadPicture(@RequestBody MultipartFile file, String description) throws IOException {
        final byte[] image = file.getBytes();
        final PictureInformation information = PictureInformation.builder()
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
        pictureInformationRepository.save(information);
        filesystemHandler.putImage(information.getId(), image);
        amazonStorageHandler.saveFile(file);
        return information;
    }

    @GetMapping
    public PictureInformation getInfo(@RequestParam Long pictureId) {
        Optional<PictureInformation> possibleInfo = pictureInformationRepository.findById(pictureId);
        if (possibleInfo.isPresent()) {
            return possibleInfo.get();
        } else {
            throw new EntityNotFoundException("No picture with Id " + pictureId + " found!");
        }

    }

    @DeleteMapping
    public void deletePicture(@RequestParam Long pictureId) throws IOException {
        pictureInformationRepository.deleteById(pictureId);
        filesystemHandler.deleteImage(pictureId);
        // amazonStorageHandler.deleteFIle("2021102600591611708573847.jpg");
    }

    @GetMapping(path = "/all")
    public List<PictureInformation> listPictures() {
        return pictureInformationRepository.findAll();
    }

    @PatchMapping
    public PictureInformation editPicture(@RequestBody(required = false) MultipartFile file, String description, @RequestParam Long pictureId) throws IOException {
        Optional<PictureInformation> possibleInfo = pictureInformationRepository.findById(pictureId);
        if (possibleInfo.isEmpty()) {
            throw new EntityNotFoundException("No picture with id " + pictureId + " found!");
        } else {
            PictureInformation info = possibleInfo.get();
            if (file != null) {
                final byte[] image = file.getBytes();
                filesystemHandler.putImage(pictureId, image);
            }
            if (description != null)
                info.setDescription(description);

            return pictureInformationRepository.save(info);
        }

    }
}
