package com.springbatch.demonstrativoorcamentario.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.dominio.Lancamento;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {
	
	@StepScope
	@Bean
	public MultiResourceItemWriter<GrupoLancamento> demostrativoMultiplo(
			@Value("#{jobParameters['demostrativosOracamentarios']}") Resource demostrativosOracamentarios,
			FlatFileItemWriter<GrupoLancamento> demostrativoLancamentoWriter){
		return new MultiResourceItemWriterBuilder<GrupoLancamento>()
				.resource(demostrativosOracamentarios)
				.name("demostrativoMultiplo")
				.resourceSuffixCreator(criadorSufixo())
				.delegate(demostrativoLancamentoWriter)
				.itemCountLimitPerResource(1)
				.build();
	}
	
	private ResourceSuffixCreator criadorSufixo() {
		// TODO Auto-generated method stub
		return new ResourceSuffixCreator() {
			
			@Override
			public String getSuffix(int index) {
				// TODO Auto-generated method stub
				return index+".txt";
			}
		};
	}

	@StepScope
	@Bean
	public FlatFileItemWriter<GrupoLancamento> demostrativoLancamentoWriter(@Value("#{jobParameters['demostrativoOracamentario']}") Resource demostrativoOracamentario, DemonstrativoRodape demonstrativoRodape){
		return new FlatFileItemWriterBuilder<GrupoLancamento>()
				   .name("demostrativoLancamentoWriter")
				   .resource(demostrativoOracamentario)
				   .headerCallback(escreveCabecalho())
				   .lineAggregator(lineAggregator())
				   .footerCallback(demonstrativoRodape)
				   .build();
	}

	

	private FlatFileHeaderCallback escreveCabecalho() {
		// TODO Auto-generated method stub
		return new FlatFileHeaderCallback() {
			
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.append("\n"); 
				writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
				writer.append(String.format("\nMÓDULO: ORÇAMENTO \t\t\t\t\t HORA: %s", new SimpleDateFormat("HH:MM").format(new Date())));
				writer.append(String.format("\n\t\t\tDEMONSTRATIVO ORCAMENTARIO"));
				writer.append(String.format("\n----------------------------------------------------------------------------"));
				writer.append(String.format("\nCODIGO NOME VALOR"));
				writer.append(String.format("\n\t Data Descricao Valor"));
				writer.append(String.format("\n----------------------------------------------------------------------------"));
				
			}
		};
	}

	private LineAggregator<GrupoLancamento> lineAggregator() {
		// TODO Auto-generated method stub
		return new LineAggregator<GrupoLancamento>() {

			@Override
			public String aggregate(GrupoLancamento grupo) {
			  String grupoFormatado= String.format("[%d] %s - %s\n", grupo.getCodigoNaturezaDespesa(),
						grupo.getDescricaoNaturezaDespesa(),
						NumberFormat.getCurrencyInstance().format(grupo.getTotal()));
			  
			  StringBuilder builder = new StringBuilder();
			  for (Lancamento lancamento : grupo.getLancamentos()) {
				  builder.append(String.format("\t [%s] %s - %s\n", new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
							NumberFormat.getCurrencyInstance().format(lancamento.getValor())));
				}
			    String formatoLancamento = builder.toString();
				return grupoFormatado + formatoLancamento;
			}
		};
	}
}
