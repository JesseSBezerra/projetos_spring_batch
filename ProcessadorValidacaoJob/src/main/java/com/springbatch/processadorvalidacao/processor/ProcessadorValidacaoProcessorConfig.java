package com.springbatch.processadorvalidacao.processor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.processadorvalidacao.dominio.Cliente;

@Configuration
public class ProcessadorValidacaoProcessorConfig {
	
	private Set<String> emails = new HashSet<String>();
	
	@Bean
	public ItemProcessor<Cliente, Cliente> procesadorValidacaoProcessor() throws Exception {
        CompositeItemProcessorBuilder<Cliente, Cliente> composite = new CompositeItemProcessorBuilder<>();
        composite.delegates(beanValidatingItemProcessor(),validatingItemProcessor());
		return composite.build();
	}
	
	private BeanValidatingItemProcessor<Cliente> beanValidatingItemProcessor() throws Exception{
		BeanValidatingItemProcessor<Cliente> processor = new BeanValidatingItemProcessor<Cliente>();
		processor.setFilter(true);
		processor.afterPropertiesSet();
		return processor;
	}
	
	private ValidatingItemProcessor<Cliente> validatingItemProcessor(){
		ValidatingItemProcessor<Cliente> processor = new ValidatingItemProcessor<Cliente>();
		processor.setValidator(validator());
		processor.setFilter(true);
		return processor;
	}

	private Validator<Cliente> validator() {
		return new Validator<Cliente>() {
			
			@Override
			public void validate(Cliente value) throws ValidationException {
				if(!emails.contains(value.getEmail())) {
					emails.add(value.getEmail());
				}else {
					throw new ValidationException(String.format("O cliente %s ja foi", value.getEmail()));
				}
			}
		};
	}
}
