package com.dbs.exercise.service;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FileProcessorService {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    /**
     * Processes email details file using spring batch
     * @return The status of the batch job
     */
    public BatchStatus processEmailInfoFile() {
        Map<String, JobParameter> paramMap = new HashMap<>();
        paramMap.put("Time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(paramMap);

        try {
            JobExecution jobExecution = jobLauncher.run(job, parameters);

            System.out.println("Email info file processing status : " + jobExecution.getStatus());
            System.out.println("Email info file is being processed..");
            while (jobExecution.isRunning()){
                System.out.print(".");
            }
            System.out.println("Email info File processing status : " + jobExecution.getStatus());

            return jobExecution.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return BatchStatus.FAILED;
        }
    }
}
