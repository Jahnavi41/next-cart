package dev.ju.nextcart.service.image;

import dev.ju.nextcart.dto.ImageDTO;
import dev.ju.nextcart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long imageId);
    void deleteImageById(Long imageId);
    List<ImageDTO> saveImage(Long productId, List<MultipartFile> files);
    Image updateImage(MultipartFile file, Long imageId);
}
