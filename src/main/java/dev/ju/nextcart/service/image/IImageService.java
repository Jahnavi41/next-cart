package dev.ju.nextcart.service.image;

import dev.ju.nextcart.dto.ImageDTO;
import dev.ju.nextcart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long imageId);
    void deleteImageById(Long imageId);
    List<ImageDTO> saveImage(List<MultipartFile> files, Long imageId);
    void updateImage(MultipartFile file, Long productId);
}
