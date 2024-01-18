package com.cydeo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String description;
    private String company;
    private boolean hasProduct;
}
