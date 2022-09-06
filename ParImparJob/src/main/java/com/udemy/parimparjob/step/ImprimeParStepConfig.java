package com.udemy.parimparjob.step;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImprimeParStepConfig {
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step imprimeParImparStep() {
		return stepBuilderFactory
				.get("imprimeParImparStep")
				.<Integer,String>chunk(10)
				.reader(contaUmAteDez())
				.processor(parOuImparProcessor())
				.writer(imprimeWriter())
				.build();
	}
	
	private ItemWriter<String> imprimeWriter() {
		return xuxas -> xuxas.forEach(System.out::println);
	}

	private ItemProcessor<Integer,String> parOuImparProcessor() {
		return new FunctionItemProcessor<Integer,String>(xuxa -> xuxa % 2 == 0 ? String.format("O item %s é par ", xuxa) : String.format("O item %s é impar ", xuxa));
	}

	private IteratorItemReader<Integer> contaUmAteDez() {
		List<Integer> numeros = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		return new IteratorItemReader<Integer>(numeros);
		
	}

}
