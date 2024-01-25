package com.cydeo;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.entity.Category;
import com.cydeo.fintracker.entity.ClientVendor;

public class TestHelper {


    /*
    - add dto and entity objects here to use on test
     */

    // todo Role

    //todo add Address

    // todo add Company

    // todo add User

    //todo add ClientVendors

    public ClientVendor getClientVendor() {
        return ClientVendor.builder().website("description").company(null).build();
    }

    public ClientVendorDto getClientVendorDto() {
        return ClientVendorDto.builder().website("description").company(null).build();
    }

    //todo add Category

    //todo add Product

    // Todo add Invoice

    // todo add Invoice Products


    public Category getCategory() {
        return Category.builder().description("description").company(null).build();
    }

    public CategoryDto getCategoryDto() {
        return CategoryDto.builder().description("description").company(null).build();
    }
}
