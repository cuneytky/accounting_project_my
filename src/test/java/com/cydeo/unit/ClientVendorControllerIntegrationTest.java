package com.cydeo.unit;

import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.entity.*;
import com.cydeo.fintracker.entity.common.UserPrincipal;
import com.cydeo.fintracker.enums.ClientVendorType;
import com.cydeo.fintracker.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientVendorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientVendorRepository clientVendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private ClientVendor clientVendor;
    private User user;
    private Company company;
    private Address address;
    private UserPrincipal principal;
    private Product product;
    private Category category;
    @BeforeEach
    void setUp(){
        Role role = new Role("Admin");
        role = roleRepository.save(role);

        company = new Company();
        company.setTitle("Company A");
        companyRepository.save(company);

        clientVendor = new ClientVendor();
        clientVendor.setClientVendorType(ClientVendorType.CLIENT);
        clientVendor.setClientVendorName("clientName");
        clientVendor.setPhone("12345");
        clientVendor.setWebsite("abc@cydeo.com");
        clientVendor.setAddress(address);
        clientVendorRepository.save(clientVendor);

        category = new Category();
        category.setDescription("Category");
        category.setCompany(company);
        category = categoryRepository.save(category);

        user = new User();
        user.setUsername("username");
        user.setRole(role);
        user.setEnabled(true);
        user.setIsDeleted(false);
        user.setPassword("Abc1");
        user.setCompany(company);
        user = userRepository.save(user);

        principal = new UserPrincipal(user);
    }

    @Test
    public void when_should_list_clientVendor_successfully()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/clientVendors/list")
                        .with(SecurityMockMvcRequestPostProcessors.user(principal)))
                .andExpect(status().isOk())
                .andExpect(view().name("clientVendor/clientVendor-list"))
                .andExpect(model().attributeExists("clientVendors"));
   }

    @Test
    public void when_should_list_v1_clientVendor_successfully()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/clientVendors/list/v1")
                        .with(SecurityMockMvcRequestPostProcessors.user(principal)))
                .andExpect(status().isOk())
                .andExpect(view().name("clientVendor/clientVendor-list"))
                .andExpect(model().attributeExists("clientVendors"));
    }

    //@GetMapping("/create")
    @Test
    public void when_should_show_create_clientVendor_successfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/clientVendors/create")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(principal)))
                .andExpect(status().isOk())
                .andExpect(view().name("clientVendor/clientVendor-create"))
                .andExpect(model().attributeExists("newClientVendor"))
                .andExpect(model().attributeExists("clientVendorTypes"))
                .andExpect(model().attributeExists("countries"))
                .andExpect(model().attribute("newClientVendor", instanceOf(ClientVendorDto.class)));
    }

    //@PostMapping("/create")
    @Test
    public void when_should_edit_create_clientVendor_successfully() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name", "new clientVendor");
        formData.add("phone", "new phone");
        formData.add("website", "new website");
        formData.add("clientVendorType", ClientVendorType.VENDOR.getValue());
        formData.add("company", String.valueOf(company.getId()));
        formData.add("address", "new addressline1");
        formData.add("address", "new addressline2");
        formData.add("city", "new city");
        formData.add("state", "new state");
        formData.add("zipCode", "zipCode");
        formData.add("country", "country");

        mockMvc.perform(MockMvcRequestBuilders.post("/clientVendors/create")
                        .params(formData)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(principal)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clientVendors/list"));

        List<ClientVendor> allClientVendors = clientVendorRepository.findAll();
        List<String> allClientVendorsNames = allClientVendors.stream().map(ClientVendor::getClientVendorName).collect(Collectors.toList());

        assertThat(allClientVendorsNames.contains("new clientVendor")).isTrue();
    }


    // @GetMapping("/update/{id}")
    @Test
    public void when_should_show_update_clientVendor_successfully() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/clientVendors/update/"+clientVendor.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user(principal)))
                .andExpect(status().isOk())
                .andExpect(view().name("clientVendor/clientVendor-update"))
                .andExpect(model().attributeExists("countries"))
                .andExpect(model().attributeExists("clientVendor"))
                .andExpect(model().attributeExists("clientVendorTypes"));
    }

    // @PostMapping("/update/{id}")
    @Test
    public void when_should_update_clientVendor_successfully() throws Exception{
        clientVendor.setClientVendorName("Update ClientVendor");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name", clientVendor.getClientVendorName());
        formData.add("phone", clientVendor.getPhone());
        formData.add("website", clientVendor.getWebsite());
        formData.add("clientVendorType", ClientVendorType.VENDOR.getValue());
        formData.add("company", String.valueOf(company.getId()));
        formData.add("address", clientVendor.getAddress().getAddressLine1());
        formData.add("address", clientVendor.getAddress().getAddressLine2());
        formData.add("city", clientVendor.getAddress().getCity());
        formData.add("state", clientVendor.getAddress().getState());
        formData.add("zipCode", clientVendor.getAddress().getZipCode());
        formData.add("country", clientVendor.getAddress().getCountry());

        mockMvc.perform(MockMvcRequestBuilders.post("/clientVendors/update/" + clientVendor.getId())
                        .params(formData)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(principal)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clientVendors/list"));

        assertThat(clientVendor.getClientVendorName()).isEqualTo("Update ClientVendors");
    }

    @Test
    public void when_should_delete_clientVendor_successfully() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/clientVendors/delete/" + clientVendor.getId())
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(principal)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clientVendors/list"));

        Optional<ClientVendor> deletedClientVendor = clientVendorRepository.findById(clientVendor.getId());

        assertThat(deletedClientVendor).isNotPresent();
    }
}
