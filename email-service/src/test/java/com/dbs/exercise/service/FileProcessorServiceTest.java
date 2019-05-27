package com.dbs.exercise.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(FileProcessorService.class)
public class FileProcessorServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    FileProcessorService fileProcessorService;

    @MockBean
    private JobLauncher jobLauncher;
    @MockBean
    private Job job;
    @MockBean
    private JobExecution jobExecution;

    @Test
    public void processEmailInfoFileTest() throws Exception{
        Mockito.doReturn(jobExecution).when(jobLauncher).run(Mockito.any(), Mockito.any());
        Mockito.doReturn(false).when(jobExecution).isRunning();
        Mockito.doReturn(BatchStatus.COMPLETED).when(jobExecution).getStatus();

        fileProcessorService.processEmailInfoFile();

        verify(jobLauncher,times(1)).run(Mockito.any(), Mockito.any());
    }
}
