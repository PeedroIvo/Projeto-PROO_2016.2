package proo.sei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proo.sei.mo.DadosPessoais;

public class DadosPessoaisDAO {
	private EnderecoDAO enderecoCRUD = new EnderecoDAO();
	
	public void criar (Connection conexao, int cod, DadosPessoais dados) throws SQLException {
		String sql = "insert into dadosPessoais values (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, cod);
			stmt.setString(2, dados.getCpf());
			stmt.setString(3, dados.getRg());
			stmt.setString(4, dados.getEmail());
			stmt.setString(5, dados.getTelefone());
			stmt.setString(6, dados.getCelular());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro: "+e.getMessage());
		}
		
		this.enderecoCRUD.criar(conexao, cod, dados.getEndereco());
	}
	
	public void apagar (Connection conexao, int cod) throws SQLException {
		this.enderecoCRUD.apagar(conexao, cod);
		
		try (PreparedStatement stmt = conexao.prepareStatement("delete from dadosPessoais where codUsuario='" + cod + "'");) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro: "+e.getMessage());
		}
	}
	
	public DadosPessoais procuraDadosPessoais(Connection conexao, int cod) throws SQLException {
		String sql = "select * from dadospessoais where codUsuario='" + cod + "'";
		
		DadosPessoais dados = this.selectDadosPessoais(conexao, sql);
		
		if (dados != null) {
			return dados;
		}
		
		return null;
	}
	
	public DadosPessoais selectDadosPessoais(Connection conexao, String sql) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			if (rs.first()) {
				DadosPessoais dados = new DadosPessoais();
				
				dados.setCelular(rs.getString("celular"));
				dados.setCpf(rs.getString("cpf"));
				dados.setEmail(rs.getString("email"));
				dados.setEndereco(enderecoCRUD.procuraEndereco(conexao, rs.getInt("codUsuario")));
				dados.setRg(rs.getString("rg"));
				dados.setTelefone(rs.getString("telefone"));
								
				return dados;
			}
		} catch (SQLException e) {
			throw new SQLException("Erro: "+e.getMessage());
		}
		
		return null;
	}
}
