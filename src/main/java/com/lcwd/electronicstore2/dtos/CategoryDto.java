package com.lcwd.electronicstore2.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;
    @NotBlank(message = "Title is required !!!")
    @Size(min = 4,message = "Title must be required 4 characters !!")
    private String title;
    @NotBlank(message = "Description required !!")
    private String description;
    @NotBlank(message = "Cover image required !!")
    private String coverImage;

}
