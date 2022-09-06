package com.springbatch.skipexception.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import com.springbatch.skipexception.dominio.Cliente;

@Configuration
public class SkipExceptionReaderConfig {
	@Bean
	public ItemReader<Cliente> skipExceptionReader(@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Cliente>()
				.name("skipExceptionReader")
				.dataSource(dataSource)
				.sql("select * from cliente")
				.rowMapper(rowMapper())
				.build();
	}

	private RowMapper<Cliente> rowMapper() {
		// TODO Auto-generated method stub
		return new RowMapper<Cliente>() {

			@Override
			public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
				if(rs.getRow()==11) {
					throw new SQLException(String.format("Deu muito ruim nesse registro %s", rs.getString("email")));
				}else {
					return clienteRowMapper(rs);
				}
			}

			private Cliente clienteRowMapper(ResultSet rs) throws SQLException {
				Cliente cliente = new Cliente();
				cliente.setEmail(rs.getString("email"));
				cliente.setSobrenome(rs.getString("sobrenome"));
				cliente.setNome(rs.getString("nome"));
				cliente.setIdade(rs.getString("idade"));
				return cliente;
			}
		};
	}
}
