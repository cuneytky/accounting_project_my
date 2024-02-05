package com.cydeo.unit;

import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.entity.ClientVendor;
import com.cydeo.fintracker.repository.ClientVendorRepository;
import com.cydeo.fintracker.service.ClientVendorService;
import com.cydeo.fintracker.service.InvoiceService;
import com.cydeo.fintracker.service.SecurityService;
import com.cydeo.fintracker.util.MapperUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClientVendor_ServiceImpl_IntegrationTest {

    @Autowired
    private ClientVendorRepository clientVendorRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ClientVendorService clientVendorService;

    @Test
    public void when_should_find_clientVendor_by_id() {

        Long existingClientId = 1L;

        ClientVendorDto foundClientVendor = clientVendorService.findById(existingClientId);

        assertThat(foundClientVendor).isNotNull();
    }

    @Test
    public void when_should_find_clientVendor_name() {

        String clientVendorName = "sampleName";
        ClientVendor clientVendor = new ClientVendor();
        clientVendor.setClientVendorName(clientVendorName);
        clientVendor.setIsDeleted(false);
        clientVendorRepository.save(clientVendor);

        ClientVendorDto foundClientVendor = clientVendorService.findByClientVendorName(clientVendorName);

        assertNotNull(foundClientVendor);
        assertEquals(clientVendorName, foundClientVendor.getClientVendorName());
    }

    @Test
    public void when_should_check_if_client_has_invoice() {

        Long clientId = 1L;

        boolean hasInvoice = clientVendorService.isClientHasInvoice(clientId);

        assertTrue(hasInvoice);
    }

    @Test
    public void when_should_update_clientVendor() {

        Long clientId = 1L;
        ClientVendorDto clientVendorDTO = new ClientVendorDto();
        clientVendorDTO.setClientVendorName("Updated Client Vendor");

        ClientVendorDto updatedClientVendor = clientVendorService.update(clientId, clientVendorDTO);

        assertThat(updatedClientVendor).isNotNull();

        assertEquals("Updated Client Vendor", updatedClientVendor.getClientVendorName());
    }

    @Test
    public void when_should_delete_clientVendor() {

        Long clientId = 1L;

        clientVendorService.delete(clientId);

        ClientVendor deletedClientVendor = clientVendorRepository.findById(clientId).orElse(null);
        assertFalse(deletedClientVendor != null && deletedClientVendor.getIsDeleted());
    }

    @Test
    public void when_should_save_clientVendor(){
        //.................
    }

    @Test
    public void when_should_getAllClientVendors_company(){
        //..................
    }
}