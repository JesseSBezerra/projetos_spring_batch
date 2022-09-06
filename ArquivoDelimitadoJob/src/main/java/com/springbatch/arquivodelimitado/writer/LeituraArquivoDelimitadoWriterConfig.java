package com.springbatch.arquivodelimitado.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.springbatch.arquivodelimitado.dominio.Cliente;

@Configuration
public class LeituraArquivoDelimitadoWriterConfig {
	@StepScope
	@Bean
	public FlatFileItemWriter<Cliente> escritaArquivoDelimitadoWriter(@Value("#{jobParameters['arquivoClientesSaida']}") Resource arquivoClientes) {
		return new FlatFileItemWriterBuilder<Cliente>()
				.name("escritaArquivoDelimitadoWriter")
				.resource(arquivoClientes)
				.delimited()
				.delimiter("*")
				.names(new String[] {"nome", "sobrenome", "idade", "email"})
				.build();
	}
}
