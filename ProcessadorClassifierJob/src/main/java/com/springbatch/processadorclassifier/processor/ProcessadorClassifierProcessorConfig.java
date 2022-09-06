package com.springbatch.processadorclassifier.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.processadorclassifier.dominio.Cliente;
import com.springbatch.processadorclassifier.dominio.Transacao;

@Configuration
public class ProcessadorClassifierProcessorConfig {
	@SuppressWarnings("rawtypes")
	@Bean
	public ItemProcessor processadorClassifierProcessor() {
		// TODO: Implementar aqui...
		return new ClassifierCompositeItemProcessorBuilder<>()
				.classifier(classifier())
				.build();
	}

	private Classifier classifier() {
		// TODO Auto-generated method stub
		return new Classifier<Object, ItemProcessor>() {

			@Override
			public ItemProcessor classify(Object object) {
				if(object instanceof Cliente) {
					return new ClienteProcessor();
				}else if (object instanceof Transacao) {
					return new TransacaoProcessor();
				}else {
					return null;
				}
			}

			
		
		};
	}
}
