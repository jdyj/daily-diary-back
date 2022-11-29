package com.seoultech.dailydiary.image;

import java.io.IOException;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @GetMapping(value = "/{filename}", produces = MediaType.IMAGE_PNG_VALUE)
  public Resource downloadImage(@PathVariable String filename)
      throws MalformedURLException {
    return new UrlResource("file:" + imageService.getFullPath(filename));
  }

  @PostMapping("/profile")
  public ResponseEntity uploadProfileImage(MultipartHttpServletRequest servletRequest)
      throws IOException {
    MultipartFile image = servletRequest.getFile("file");
    Image savedImage = imageService.storeFile(image, Category.PROFILE);
    return new ResponseEntity(HttpStatus.OK);
  }


}

