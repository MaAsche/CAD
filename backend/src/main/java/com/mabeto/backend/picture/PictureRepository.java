package com.mabeto.backend.picture;


import com.mabeto.backend.picture.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "picture", path = "picture")
public interface PictureRepository extends JpaRepository<Picture, Long> {
}
