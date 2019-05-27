package com.dbs.exercise.config;

import com.dbs.exercise.model.CashTransaction;
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
                   ItemReader<CashTransaction> itemReader, ItemProcessor<CashTransaction, CashTransaction> itemProcessor,
                   ItemWriter<CashTransaction> itemWriter) {

        Step step = stepBuilderFactory.get("transaction-file-load")
                .<CashTransaction, CashTransaction> chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        Job job = jobBuilderFactory.get("transaction-file-load")
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
    public FlatFileItemReader<CashTransaction> flatFileItemReader(@Value("${inputTransactionsFile}")Resource resource) {
        FlatFileItemReader<CashTransaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setName("transaction-file-read");
        flatFileItemReader.setLineMapper(lineMapper());
        return  flatFileItemReader;
    }

    /**
     * Configures line mapper which maps file lines to objects
     * @return The configured line mapper
     */
    @Bean
    public LineMapper<CashTransaction> lineMapper() {
        DefaultLineMapper<CashTransaction> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setStrict(false);
        tokenizer.setNames(new String[] {"accountId", "payOrRecieve", "amount", "currencyCode"});

        BeanWrapperFieldSetMapper<CashTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CashTransaction.class);

        defaultLineMapper.setLineTokenizer(tokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
