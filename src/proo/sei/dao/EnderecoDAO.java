package proo.sei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proo.sei.vo.EnderecoVO;

public class EnderecoDAO {
	public void criar(Connection conexao, int cod, EnderecoVO endereco) throws SQLException {
		String sql = "insert into endereco values (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, cod);
			stmt.setString(2, endereco.getCep());
			stmt.setString(3, endereco.getRua());
			stmt.setString(4, endereco.getnCasa());
			stmt.setString(5, endereco.getBairro());
			stmt.setString(6, endereco.getComplemento());
			stmt.setString(7, endereco.getCidade());
			stmt.setString(8, endereco.getEstado());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro ao criar o endereço: "+e.getMessage());
		}
	}
	
	public void apagar (Connection conexao, int cod) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement("delete from endereco where codUsuario='" + cod + "'");) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro ao apagar o endereço: "+e.getMessage());
		}
	}
	
	public EnderecoVO procuraEndereco(Connection conexao, int cod) throws SQLException {
		String sql = "select * from endereco where codUsuario='" + cod + "'";
		
		EnderecoVO endereco = this.selectEndereco(conexao, sql);
		
		if (endereco != null) {
			return endereco;
		}
		
		return null;
	}
	
	public EnderecoVO selectEndereco(Connection conexao, String sql) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			if (rs.first()) {
				EnderecoVO endereco = new EnderecoVO();
				
				endereco.setBairro(rs.getString("bairro"));
				endereco.setCep(rs.getString("cep"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setComplemento(rs.getString("complemento"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setnCasa(rs.getString("nCasa"));
				endereco.setRua(rs.getString("rua"));
								
				return endereco;
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao consultar: "+e.getMessage());
		}
		
		return null;
	}
}
