package com.custmngt.customer_management.controller;

import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerResponse;
import com.custmngt.customer_management.dto.CustomerUpdateRequest;
import com.custmngt.customer_management.exception.GlobalExceptionHandler;
import com.custmngt.customer_management.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


public class CustomerControllerTest {

    private MockMvc mockMvc;
    @Mock
    private CustomerService service;
    @InjectMocks
    private CustomerController controller;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }


    @Test
    void createCustomer_returnsOk_andResponseBody() throws Exception {
        CustomerRequest request = CustomerRequest.builder()
                .customerName("Raj Yadav")
                .emailId("raj@abc.com")
                .annualSpend(12.00)
                .build();

        LocalDate today = LocalDate.now();
        CustomerResponse mockedResponse = CustomerResponse.builder()
                .uuid(UUID.fromString("88c312a9-f26f-4ce9-96c3-0250f73b019f"))
                .customerName("Raj Yadav")
                .emailId("raj@abc.com")
                .annualSpend(12.0)
                .lastPurchaseDate(today)
                .build();

        when(service.createCustomer(any(CustomerRequest.class))).thenReturn(mockedResponse);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Customer created successfully"))
                .andExpect(jsonPath("$.data.customerName").value("Raj Yadav"))
                .andExpect(jsonPath("$.data.emailId").value("raj@abc.com"))
                .andExpect(jsonPath("$.data.annualSpend").value(12.0))
                .andExpect(jsonPath("$.data.lastPurchaseDate").value(today.toString()))
                .andExpect(jsonPath("$.data.uuid").value("88c312a9-f26f-4ce9-96c3-0250f73b019f")); // works after @JsonProperty("id")
    }

    @Test
    void createCustomer_validationError_returnsBadRequest() throws Exception {
        CustomerRequest badRequest = CustomerRequest.builder()
                .emailId("raj@abc.com")
                .annualSpend(12.00)
                .build();
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void deleteCustomerReturnsOkResponseBody() throws Exception {
        UUID customerId = UUID.fromString("88c312a9-f26f-4ce9-96c3-0250f73b019f");
        String expectedMessage = "Customer deleted successfully";
        when(service.deleteCustomerById(customerId)).thenReturn(expectedMessage);
        mockMvc.perform(delete("/api/v1/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());
        verify(service, times(1)).deleteCustomerById(customerId);
    }

    @Test
    void updateCustomerReturnsOkResponseBody() throws Exception {
        UUID customerId = UUID.fromString("88c312a9-f26f-4ce9-96c3-0250f73b019f");

        CustomerUpdateRequest request = CustomerUpdateRequest.builder()
                .customerName("Updated Raj")
                .emailId("updated.raj@abc.com")
                .build();

        LocalDate today = LocalDate.now();
        CustomerResponse mockedResponse = CustomerResponse.builder()
                .uuid(customerId)
                .customerName("Updated Raj")
                .emailId("updated.raj@abc.com")
                .annualSpend(50.00)
                .lastPurchaseDate(today)
                .build();
        when(service.updateCustomer(eq(customerId), any(CustomerUpdateRequest.class)))
                .thenReturn(mockedResponse);

        mockMvc.perform(put("/api/v1/customers/{uuid}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Customer updated successfully"))
                .andExpect(jsonPath("$.data.uuid").value(customerId.toString()))
                .andExpect(jsonPath("$.data.customerName").value("Updated Raj"))
                .andExpect(jsonPath("$.data.emailId").value("updated.raj@abc.com"))
                .andExpect(jsonPath("$.data.annualSpend").value(50.0))
                .andExpect(jsonPath("$.data.lastPurchaseDate").value(today.toString()));

        verify(service, times(1)).updateCustomer(eq(customerId), any(CustomerUpdateRequest.class));
    }



    @Test
    void getCustomerAnnualSpendsByEmailReturnsCustomerResponse() throws Exception {
        String emailId = "raj@abc.com";
        UUID customerId = UUID.fromString("88c312a9-f26f-4ce9-96c3-0250f73b019f");
        LocalDate today = LocalDate.now();

        CustomerResponse mockedResponse = CustomerResponse.builder()
                .uuid(customerId)
                .customerName("Raj Yadav")
                .emailId(emailId)
                .annualSpend(99.99)
                .lastPurchaseDate(today)
                .build();

       when(service.getCustomerAnnualSpendsByEmail(emailId))
               .thenReturn(Optional.of(mockedResponse));

        mockMvc.perform(get("/api/v1/getCustomerAnnualSpendsByEmail/{emailId}", emailId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").value(customerId.toString()))
                .andExpect(jsonPath("$.customerName").value("Raj Yadav"))
                .andExpect(jsonPath("$.emailId").value(emailId))
                .andExpect(jsonPath("$.annualSpend").value(99.99))
                .andExpect(jsonPath("$.lastPurchaseDate").value(today.toString()));

        verify(service, times(1)).getCustomerAnnualSpendsByEmail(emailId);
    }



}
