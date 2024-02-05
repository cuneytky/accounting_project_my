package com.cydeo.unit;

import com.cydeo.fintracker.controller.ClientVendorController;
import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.enums.ClientVendorType;
import com.cydeo.fintracker.service.ClientVendorService;
import com.cydeo.fintracker.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ClientVendorControllerUnitTest {

    @Mock
    ClientVendorService clientVendorService;

    @Mock
    CompanyService companyService;

    @InjectMocks
    ClientVendorController clientVendorController;

    @Test
    public void list_shouldClientVendorsSuccessfully(){

        List<ClientVendorDto> clientVendors = Arrays.asList(new ClientVendorDto(), new ClientVendorDto());
        Model model = mock(Model.class);

        when(clientVendorService.getAll()).thenReturn(clientVendors);

        String result = clientVendorController.listClientVendors(model);

        assertEquals("clientVendor/clientVendor-list", result);

        verify(clientVendorService, times(1)).getAll();

        verify(model, times(1)).addAttribute("clientVendors", clientVendors);
    }

    @Test
    public void list_shouldClientVendorsCompanySuccessfully(){

        List<ClientVendorDto> clientVendors = Arrays.asList(new ClientVendorDto(), new ClientVendorDto());
        Model model = mock(Model.class);

        when(clientVendorService.getAllClientVendorsCompany()).thenReturn(clientVendors);

        String result = clientVendorController.listClientVendorsCompany(model);

        assertEquals("clientVendor/clientVendor-list", result);

        verify(clientVendorService, times(1)).getAllClientVendorsCompany();

        verify(model, times(1)).addAttribute("clientVendors", clientVendors);
    }
    @Test
    public void show_shouldReturnCorrectView() {

        Model model = new ConcurrentModel();

        String result = clientVendorController.showCreateVendor(model);

        assertEquals("clientVendor/clientVendor-create", result);

        assertNotNull(model.getAttribute("newClientVendor"));
        assertNotNull(model.getAttribute("clientVendorTypes"));
        assertNotNull(model.getAttribute("countries"));

        assertTrue(model.getAttribute("newClientVendor") instanceof ClientVendorDto);
        assertTrue(model.getAttribute("clientVendorTypes") instanceof List);
        assertTrue(model.getAttribute("countries") instanceof List);
    }

    @Test
    public void create_shouldCreateSuccessfully(){

        ClientVendorDto clientVendorDto = new ClientVendorDto();
        Model model = new ConcurrentModel();

        String result = clientVendorController.editCreateVendor(clientVendorDto, new BeanPropertyBindingResult(clientVendorDto, "clientVendorDto"), model);

        verify(clientVendorService, times(1)).saveClientVendor(clientVendorDto);
        assertEquals("redirect:/clientVendors/list", result);
    }

    @Test
    public void showUpdate_shouldClientVendorSuccessfully(){

        Long id = 1L;
        ClientVendorDto clientVendor = new ClientVendorDto();
        Model model = mock(Model.class);

        when(clientVendorService.findById(id)).thenReturn(clientVendor);

        String result = clientVendorController.showUpdateClientVendor(id, model);

        assertEquals("clientVendor/clientVendor-update", result);

        verify(model, times(1)).addAttribute("countries", companyService.getAllCountries());
        verify(model, times(1)).addAttribute("clientVendor", clientVendor);
        verify(model, times(1)).addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

        verify(clientVendorService, times(1)).findById(id);
    }
    @Test
    public void update_shouldClientVendorsSuccessfully(){

        Long id = 1L;
        ClientVendorDto clientVendor = new ClientVendorDto();
        BindingResult bindingResult = mock(BindingResult.class);
        Model model = mock(Model.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = clientVendorController.updateClientVendor(id, clientVendor, bindingResult, model);

        assertEquals("redirect:/clientVendors/list", result);

        verify(clientVendorService, times(1)).update(id, clientVendor);

        verify(model, never()).addAttribute(eq("clientVendorTypes"), any());
        verify(model, never()).addAttribute(eq("countries"), any());
    }

    @Test
    public void update_shouldReturnUpdatePageOnBindingErrors() {

        Long id = 1L;
        ClientVendorDto clientVendor = new ClientVendorDto();
        BindingResult bindingResult = mock(BindingResult.class);
        Model model = mock(Model.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = clientVendorController.updateClientVendor(id, clientVendor, bindingResult, model);

        assertEquals("clientVendor/clientVendor-update", result);

        verify(model, times(1)).addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        verify(model, times(1)).addAttribute("countries", companyService.getAllCountries());

        verify(clientVendorService, never()).update(id, clientVendor);
    }
    @Test
    public void delete_shouldClientVendorsSuccessfully(){

        Long clientId = 1L;

        String viewName = clientVendorController.deleteClientVendor(clientId);

        assertEquals("redirect:/clientVendors/list", viewName);

        verify(clientVendorService, times(1)).delete(clientId);
    }
}
