package com.dbs.exercise.config;

import com.dbs.exercise.model.EmailInfo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfigs {

    /**
     * Used to configure spring batch job
     * @param jobBuilderFactory : Spring job builder factory (will be Autowired)
     * @param stepBuilderFactory : Spring step builder factory (will be Autowired)
     * @param itemReader : Item reader of Spring batch step
     * @param itemProcessor : Item processor of Spring batch step
     * @param itemWriter : Item writer of Spring batch step
     * @return Configured Spring Batch job
     */
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                   ItemReader<EmailInfo> itemReader, ItemProcessor<EmailInfo, EmailInfo> itemProcessor,
                   ItemWriter<EmailInfo> itemWriter) {

        Step step = stepBuilderFactory.get("emailInfo-file-load")
                .<EmailInfo, EmailInfo> chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        Job job = jobBuilderFactory.get("emailInfo-file-load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();

        return  job;
    }

    /**
     * Configures item reader for the batch job
     * @param resource : The file resource to be read
     * @return Configured item reader to read file
     */
    @Bean
    public FlatFileItemReader<EmailInfo> flatFileItemReader(@Value("${emailInfoFile}") Resource resource) {
        FlatFileItemReader<EmailInfo> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setName("emailInfo-file-read");
        flatFileItemReader.setLineMapper(lineMapper());
        return  flatFileItemReader;
    }

    /**
     * Configures line mapper which maps file lines to objects
     * @return The configured line mapper
     */
    @Bean
    public LineMapper<EmailInfo> lineMapper() {
        DefaultLineMapper<EmailInfo> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setStrict(false);
        tokenizer.setNames(new String[] {"accountId", "emailAddress"});

        BeanWrapperFieldSetMapper<EmailInfo> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(EmailInfo.class);

        defaultLineMapper.setLineTokenizer(tokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
