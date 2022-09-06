package com.springbatch.processadorclassifier.processor;

import org.springframework.batch.item.ItemProcessor;

import com.springbatch.processadorclassifier.dominio.Cliente;

public class ClienteProcessor implements ItemProcessor<Cliente, Cliente> {

	@Override
	public Cliente process(Cliente item) throws Exception {
		System.out.println(String.format("Aplicando regras de negócio do cliente %s", item.getEmail()));
		return item;
	}

}
