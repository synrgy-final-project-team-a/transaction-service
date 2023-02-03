package com.synergy.transaction.controller.impl;

import com.synergy.transaction.controller.impl.TransactionControllerImpl;
import com.synergy.transaction.entity.Transaction;
import com.synergy.transaction.service.TransactionService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TransactionControllerImplTest {
    //TODO: create the data Test generator class TransactionBuilder
    private static final String ENDPOINT_URL = "/transactions";
    @MockBean
    private ReferenceMapper referenceMapper;
    @InjectMocks
    private TransactionControllerImpl transactionController;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private TransactionMapper transactionMapper;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.transactionController).build();
    }

    @Test
    public void getAll() throws Exception {
        Mockito.when(transactionMapper.asDTOList(ArgumentMatchers.any())).thenReturn(TransactionBuilder.getListDTO());

        Mockito.when(transactionService.findAll()).thenReturn(TransactionBuilder.getListEntities());
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(transactionMapper.asDTO(ArgumentMatchers.any())).thenReturn(TransactionBuilder.getDTO());

        Mockito.when(transactionService.findById(ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.of(TransactionBuilder.getEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(transactionService, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(transactionMapper.asEntity(ArgumentMatchers.any())).thenReturn(TransactionBuilder.getEntity());
        Mockito.when(transactionService.save(ArgumentMatchers.any(Transaction.class))).thenReturn(TransactionBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(CustomUtils.asJsonString(TransactionBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(transactionService, Mockito.times(1)).save(ArgumentMatchers.any(Transaction.class));
        Mockito.verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(transactionMapper.asEntity(ArgumentMatchers.any())).thenReturn(TransactionBuilder.getEntity());
        Mockito.when(transactionService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(TransactionBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(CustomUtils.asJsonString(TransactionBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(transactionService, Mockito.times(1)).update(ArgumentMatchers.any(Transaction.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(transactionService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(transactionService, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(transactionService);
    }