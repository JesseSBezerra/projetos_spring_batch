package com.springbatch.arquivomultiplosformatos.reader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;

@Configuration
public class ClienteTransacaoLineMapperConfig {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public PatternMatchingCompositeLineMapper lineMapper() {
		PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
		lineMapper.setTokenizers(toKenizeres());
		lineMapper.setFieldSetMappers(fieldMapers());
		return lineMapper;
	}

	@SuppressWarnings({"rawtypes"})
	private Map fieldMapers() {
		Map<String,FieldSetMapper> mapa = new HashMap<>();
		mapa.put("0*", fieldSetMapper(Cliente.class));
		mapa.put("1*", fieldSetMapper(Transacao.class));
		return mapa;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private FieldSetMapper fieldSetMapper(Class classe) {
		BeanWrapperFieldSetMapper mapper = new BeanWrapperFieldSetMapper<>();
		mapper.setTargetType(classe);
		return mapper;
	}

	private Map<String, LineTokenizer> toKenizeres() {
		Map<String,LineTokenizer> map = new HashMap<String,LineTokenizer>();
		map.put("0*", clienteLineTokenizer());
		map.put("1*", trasacaoLineTokenizer());
		return map;
	}

	private LineTokenizer trasacaoLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("id","descricao","valor");
		lineTokenizer.setIncludedFields(1,2,3);
		return lineTokenizer;
	}

	private LineTokenizer clienteLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("nome","sobrenome","idade","email");
		lineTokenizer.setIncludedFields(1,2,3,4);
		return lineTokenizer;
	}
}
