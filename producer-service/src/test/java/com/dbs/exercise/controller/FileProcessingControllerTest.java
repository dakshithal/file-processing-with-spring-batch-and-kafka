package com.dbs.exercise.controller;

import com.dbs.exercise.service.FileProcessorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FileProcessingController.class)
public class FileProcessingControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileProcessorService fileProcessorService;

    @Test
    public void processInputTransactionsFileTest() throws Exception {

        Mockito.doReturn(BatchStatus.COMPLETED).when(fileProcessorService).processInputTransactionsFile();

        mvc.perform(get("/file")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());

        verify(fileProcessorService,times(1)).processInputTransactionsFile();
    }

}
