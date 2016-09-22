package proo.sei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proo.sei.mo.Professor;
import proo.sei.mo.Usuario;

public class ProfessorDAO extends UsuarioDAO{
	private DadosPessoaisDAO dadosCRUD = new DadosPessoaisDAO(); 
	
	public void criar (Connection conexao, Professor professor) throws SQLException {
		this.criarUsuario(conexao, (Usuario)professor);
		
		professor.setCodUsuario(this.pegarCodigo(conexao, professor.getLogin()));
		
		String sql = "insert into professor (codProfessor, dataAdmissao) values (?, ?)";
		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, professor.getCodUsuario());
			stmt.setString(2, professor.getDataAdmissao());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro: "+e.getMessage());
		}
		
		dadosCRUD.criar(conexao, professor.getCodUsuario(), professor.getDadosPessoais());
	}
	
	public String procuraNomeProfessor(Connection conexao, int cod) throws SQLException {
		String sql = "select * from professor, usuario where professor.codProfessor='" + cod + "' and usuario.codUsuario='" + cod + "'";
		
		Professor professor = this.selectProfessor(conexao, sql);
		
		if (professor != null) {
			return professor.getNome();
		}
		
		return null;
	}
	
	public Professor procuraProfessor(Connection conexao, int cod) throws SQLException {
		String sql = "select * from professor, usuario where professor.codProfessor='" + cod + "' and usuario.codUsuario='" + cod + "'";
		
		Professor professor = this.selectProfessor(conexao, sql);
		
		if (professor != null) {
			return professor;
		}
		
		return null;
	}
	
	public Professor selectProfessor(Connection conexao, String sql) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			if (rs.first()) {
				Professor professor = new Professor();
				
				professor.setCodUsuario(rs.getInt("codUsuario"));
				professor.setDadosPessoais(dadosCRUD.procuraDadosPessoais(conexao, rs.getInt("codUsuario")));
				professor.setDataAdmissao(rs.getString("dataAdmissao"));
				professor.setLogin(rs.getString("login"));
				professor.setNome(rs.getString("nome"));
				professor.setSenha("senha");
				professor.setTipoUsuario(rs.getString("tipoUsuario").charAt(0));
				
				return professor;
			}
		} catch (SQLException e) {
			throw new SQLException("Erro: "+e.getMessage());
		}
		
		return null;
	}
	
	public void apagar (Connection conexao, int cod) throws SQLException {		
		try (PreparedStatement stmt = conexao.prepareStatement("update disciplina set codProfessor=NULL where codProfessor='" + cod + "'")) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro: "+e.getMessage());
		}
		
		try (PreparedStatement stmt = conexao.prepareStatement("delete from professor where codProfessor='" + cod + "'");) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro: "+e.getMessage());
		}
		
		this.dadosCRUD.apagar(conexao, cod);
		
		this.apagarUsuario(conexao, cod);
	}
}
