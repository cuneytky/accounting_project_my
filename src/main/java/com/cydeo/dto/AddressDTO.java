package com.cydeo.dto;


import com.cydeo.entity.common.BaseEntity;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressDTO extends BaseEntity {
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
