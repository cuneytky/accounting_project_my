package com.cydeo.unit;

import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.ClientVendor;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.exception.ClientVendorNotFoundException;
import com.cydeo.fintracker.repository.ClientVendorRepository;
import com.cydeo.fintracker.service.InvoiceService;
import com.cydeo.fintracker.service.SecurityService;
import com.cydeo.fintracker.service.impl.ClientVendorServiceImpl;
import com.cydeo.fintracker.util.MapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientVendor_ServiceImpl_UnitTest {

    @Mock
    private ClientVendorRepository clientVendorRepository;

    @Mock
    private MapperUtil mapperUtil;

    @Mock
    private SecurityService securityService;

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private ClientVendorServiceImpl clientVendorServiceImpl;

    @Test
    public void testGetAllClientVendors() {

        ClientVendor clientVendor1 = new ClientVendor();
        ClientVendor clientVendor2 = new ClientVendor();

        List<ClientVendor> mockClientVendorList = Arrays.asList(clientVendor1, clientVendor2);

        when(clientVendorRepository.findAllByIsDeleted(false)).thenReturn(Optional.of(mockClientVendorList));

        ClientVendorDto clientVendorDto1 = new ClientVendorDto();
        ClientVendorDto clientVendorDto2 = new ClientVendorDto();

        when(mapperUtil.convert(any(ClientVendor.class), any(ClientVendorDto.class)))
                .thenReturn(clientVendorDto1, clientVendorDto2);

        List<ClientVendorDto> result = clientVendorServiceImpl.getAll();

        verify(clientVendorRepository, times(1)).findAllByIsDeleted(false);
        verify(mapperUtil, times(2)).convert(any(ClientVendor.class), any(ClientVendorDto.class));

        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllClientVendorsEmptyList() {

        when(clientVendorRepository.findAllByIsDeleted(false)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ClientVendorNotFoundException.class,
                () -> clientVendorServiceImpl.getAll());

        verify(clientVendorRepository, times(1)).findAllByIsDeleted(false);

        assertEquals("There are no ClientVendor found", throwable.getMessage());
    }

    @Test
    public void should_throw_exception_when_client_vendor_not_found_by_id() {

        when(clientVendorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> clientVendorServiceImpl.findById(1L));
    }

    @Test
    public void should_be_deleted_when_category_has_not_product() throws ClientVendorNotFoundException {

        ClientVendor clientVendor = new ClientVendor();

        when(clientVendorRepository.findById(1L)).thenReturn(Optional.of(clientVendor));

        clientVendorServiceImpl.delete(1L);

        assertTrue(clientVendor.getIsDeleted());

        verify(clientVendorRepository,times(1)).save(clientVendor);
    }
    @Test
    public void when_clientVendors_not_found_should_return_empty_list() {
        // given
        UserDto loggedInUser = new UserDto();
        CompanyDto companyDTO = new CompanyDto();
        companyDTO.setId(1L);
        loggedInUser.setCompany(companyDTO);

        String username = "testUsername";
        ClientVendor clientVendor = new ClientVendor();
        clientVendor.setIsDeleted(true);

        when(securityService.getLoggedInUser()).thenReturn(loggedInUser);
        when(clientVendorRepository.findByClientVendorNameAndIsDeleted(eq(username), eq(false)))
                .thenReturn(clientVendor);

        // when
        List<ClientVendorDto> clientVendors = (List<ClientVendorDto>) clientVendorServiceImpl.findByClientVendorName(username);

        // then
        assertTrue(clientVendors.isEmpty());

    }
    @Test
    public void when_should_save_clientVendor(){

        UserDto loggedInUser = new UserDto();
        CompanyDto companyDTO = new CompanyDto();
        loggedInUser.setCompany(companyDTO);

        ClientVendorDto clientVendorDto = new ClientVendorDto();

        when(securityService.getLoggedInUser()).thenReturn(loggedInUser);

        Company company = new Company();
        when(mapperUtil.convert(eq(companyDTO), any())).thenReturn(company);

        ClientVendor clientVendorToSave = new ClientVendor();
        when(mapperUtil.convert(eq(clientVendorDto), any())).thenReturn(clientVendorToSave);

        ClientVendor savedClientVendor = new ClientVendor();
        when(clientVendorRepository.save(any())).thenReturn(savedClientVendor);

        ClientVendorDto result = clientVendorServiceImpl.saveClientVendor(clientVendorDto);

        //assertNotNull(result);
        assertEquals(savedClientVendor.getId(), result.getId());

        verify(securityService, times(1)).getLoggedInUser();
        verify(mapperUtil, times(1)).convert(eq(companyDTO), any());
        verify(mapperUtil, times(1)).convert(eq(clientVendorDto), any());
        verify(clientVendorRepository, times(1)).save(any());
        verify(mapperUtil, times(1)).convert(eq(savedClientVendor), any());
    }

    @Test
    public void when_should_delete_clientVendor_and_set_is_true(){

        Long id = 1L;
        ClientVendor clientVendorToDelete = new ClientVendor();
        clientVendorToDelete.setId(id);
        clientVendorToDelete.setIsDeleted(Boolean.FALSE);

        Optional<ClientVendor> optionalClientVendor = Optional.of(clientVendorToDelete);

        when(clientVendorRepository.findById(id)).thenReturn(optionalClientVendor);
        when(invoiceService.existsByClientVendorId(id)).thenReturn(false);

        clientVendorServiceImpl.delete(id);

        verify(clientVendorRepository).findById(id);
        verify(invoiceService).existsByClientVendorId(id);
        verify(clientVendorRepository).save(clientVendorToDelete);

        assertTrue(clientVendorToDelete.getIsDeleted());
    }

    @Test
    public void when_should_clientVendor_has_invoice() {

        Long clientId = 1L;

        when(invoiceService.existsByClientVendorId(clientId)).thenReturn(true);
        boolean result = clientVendorServiceImpl.isClientHasInvoice(clientId);

        assertTrue(result);
        verify(invoiceService).existsByClientVendorId(clientId);
    }

    @Test
    public void when_should_not_delete_clientVendor_invoices() {
        // given
        Long id = 1L;
        ClientVendor clientVendorToDelete = new ClientVendor();
        clientVendorToDelete.setId(id);
        clientVendorToDelete.setIsDeleted(Boolean.FALSE);

        Optional<ClientVendor> optionalClientVendor = Optional.of(clientVendorToDelete);

        when(clientVendorRepository.findById(id)).thenReturn(optionalClientVendor);
        when(invoiceService.existsByClientVendorId(id)).thenReturn(true);

        // when
        clientVendorToDelete.setIsDeleted(true);
        //clientVendorServiceImpl.delete(id);

        // then
        verify(clientVendorRepository).findById(id);
        verify(invoiceService).existsByClientVendorId(id);
        verify(clientVendorRepository, never()).save(clientVendorToDelete);

        assertFalse(clientVendorToDelete.getIsDeleted());
    }

}
