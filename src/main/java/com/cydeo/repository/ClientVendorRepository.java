package com.cydeo.repository;

import com.cydeo.entity.ClientVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientVendorRepository extends JpaRepository <ClientVendor,Long>{
    ClientVendor findByClientVendorNameAndIsDeleted(String username, Boolean deleted);
    Optional<ClientVendor> findById(Long id);

}
