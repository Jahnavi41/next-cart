package dev.ju.nextcart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    private Long imageId;
    private String imageName;
    private String downloadUrl;
}
