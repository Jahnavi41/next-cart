package dev.ju.nextcart.service.image;

import dev.ju.nextcart.dto.ImageDTO;
import dev.ju.nextcart.exceptions.BadRequestException;
import dev.ju.nextcart.model.Image;
import dev.ju.nextcart.model.Product;
import dev.ju.nextcart.repository.ImageRepository;
import dev.ju.nextcart.service.product.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;

    public ImageService(ImageRepository imageRepository, IProductService productService) {
        this.imageRepository = imageRepository;
        this.productService = productService;
    }

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(
                        () -> new BadRequestException("Image does not exist!"));
    }

    @Override
    public void deleteImageById(Long imageId) {
        imageRepository.findById(imageId)
                .ifPresentOrElse(imageRepository :: delete,
                        () -> {
                    throw new BadRequestException("Image not found!");
                });
    }

    @Override
    public List<ImageDTO> saveImage( Long productId,   List<MultipartFile> files) {
        Product product = productService.getProductById(productId);

        List<ImageDTO> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(file.getBytes());
                image.setProduct(product);

                Image savedImage = imageRepository.save(image);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO imageDto = new ImageDTO();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes());
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
