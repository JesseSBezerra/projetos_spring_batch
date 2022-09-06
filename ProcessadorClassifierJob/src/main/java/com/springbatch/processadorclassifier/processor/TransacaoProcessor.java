package com.springbatch.processadorclassifier.processor;

import org.springframework.batch.item.ItemProcessor;

import com.springbatch.processadorclassifier.dominio.Transacao;

public class TransacaoProcessor implements ItemProcessor<Transacao, Transacao> {

	@Override
	public Transacao process(Transacao item) throws Exception {
		System.out.println(String.format("Aplicando regras de negócio da transacao %s", item.getDescricao()));
		return item;
	}

}
