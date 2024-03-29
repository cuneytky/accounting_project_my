package com.cydeo.fintracker.dto;

import com.cydeo.fintracker.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVendorDto {

    private Long id;

    @NotBlank(message = "Company Name is required field" )
    @Size(min = 2, max = 100,message = "Company Name should have 2-50 characters long.")
    private String clientVendorName;

    @NotBlank(message = "Phone Number is a required field and may be in any valid phone number format.")
    private String phone;

    @NotBlank(message = "Website is a required field.")
    @Pattern(regexp = "^https?://[a-zA-Z0-9/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*", message = "Website should have a valid format.")
    private String website;

    @NotBlank(message = "Please select type")
    private ClientVendorType clientVendorType;

    @Valid
    private AddressDto address;

    @Valid
    private CompanyDto company;
    private boolean hasInvoice;

}