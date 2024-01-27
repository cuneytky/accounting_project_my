package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAll();

    @Transactional
    void deleteProductById(Long id);

    List<Product> getProductsById(Long companyId);
}