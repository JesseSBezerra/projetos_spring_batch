package com.springbatch.contasbancarias.writer;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Conta;

@Configuration
public class ComposedContaWriterConfig {

	@Bean
	public CompositeItemWriter<Conta> compositeContaWriter(@Qualifier("fileConta")  FlatFileItemWriter<Conta> flatConta,
			                                               JdbcBatchItemWriter<Conta> jdbcConta){
		return new CompositeItemWriterBuilder<Conta>()
				   .delegates(flatConta,jdbcConta)
				   .build();
	}
}
