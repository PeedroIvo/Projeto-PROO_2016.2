package proo.sei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proo.sei.vo.AlunoVO;
import proo.sei.vo.UsuarioVO;

public class AlunoDAO extends UsuarioDAO{
	private DadosPessoaisDAO dadosCRUD = new DadosPessoaisDAO();
	
	public void criar (Connection conexao, AlunoVO aluno) throws SQLException {
		this.criarUsuario(conexao, (UsuarioVO)aluno);
		
		aluno.setCodUsuario(this.pegarCodigo(conexao, aluno.getLogin()));
		
		String sql = "insert into aluno (matricAluno, codTurmaAtual, idade, sexo) values (?, ?, ?, ?)";
		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, aluno.getCodUsuario());
			stmt.setInt(2, aluno.getCodTurmaAtual());
			stmt.setInt(3, aluno.getIdade());
			stmt.setString(4, String.valueOf(aluno.getSexo()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro ao criar o aluno: "+e.getMessage());
		}
		
		dadosCRUD.criar(conexao, aluno.getCodUsuario(), aluno.getDadosPessoais());
	}
	
	public void apagar (Connection conexao, int cod) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement("delete from aluno where matricAluno='" + cod + "'");) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro ao apagar o aluno: "+e.getMessage());
		}
		
		this.dadosCRUD.apagar(conexao, cod);
		
		this.apagarUsuario(conexao, cod);
	}
	
	public String procuraNomeAluno(Connection conexao, int cod) throws SQLException {
		String sql = "select * from aluno, usuario where aluno.matricAluno='" + cod + "' and usuario.codUsuario='" + cod + "'";
		
		AlunoVO aluno = this.selectAluno(conexao, sql);
		
		if (aluno != null) {
			return aluno.getNome();
		}
		
		return null;
	}
	
	public AlunoVO procuraAluno(Connection conexao, int cod) throws SQLException {
		String sql = "select * from aluno, usuario where aluno.matricAluno='" + cod + "' and usuario.codUsuario='" + cod + "'";
		
		AlunoVO aluno = this.selectAluno(conexao, sql);
		
		if (aluno != null) {
			return aluno;
		}
		
		return null;
	}
	
	public AlunoVO selectAluno(Connection conexao, String sql) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			if (rs.first()) {
				AlunoVO aluno = new AlunoVO();
				
				aluno.setCodUsuario(rs.getInt("codUsuario"));
				aluno.setDadosPessoais(dadosCRUD.procuraDadosPessoais(conexao, rs.getInt("codUsuario")));
				aluno.setLogin(rs.getString("login"));
				aluno.setNome(rs.getString("nome"));
				aluno.setSenha("senha");
				aluno.setTipoUsuario(rs.getString("tipoUsuario").charAt(0));
				aluno.setCodTurmaAtual(rs.getInt("codTurmaAtual"));
				aluno.setIdade(rs.getInt("idade"));
				aluno.setSexo(rs.getString("sexo").charAt(0));
				
				return aluno;
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao consultar: "+e.getMessage());
		}
		
		return null;
	}
	
	public List<AlunoVO> listarPorTurma(Connection conexao, int codTurma) throws SQLException {
		String sql = "select * from aluno, usuario where aluno.matricAluno=usuario.codUsuario and aluno.codTurmaAtual='" + codTurma + "'";
		
		List<AlunoVO> alunos = this.selectListAluno(conexao, sql);
		
		return alunos;
	}
	
	public List<AlunoVO> selectListAluno(Connection conexao, String sql) throws SQLException{
		List<AlunoVO> alunos = new ArrayList<>();
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			if(rs.next()) {
				do {
					AlunoVO aluno = new AlunoVO();
					
					aluno.setCodUsuario(rs.getInt("codUsuario"));
					aluno.setDadosPessoais(dadosCRUD.procuraDadosPessoais(conexao, rs.getInt("codUsuario")));
					aluno.setLogin(rs.getString("login"));
					aluno.setNome(rs.getString("nome"));
					aluno.setSenha("senha");
					aluno.setTipoUsuario(rs.getString("tipoUsuario").charAt(0));
					aluno.setCodTurmaAtual(rs.getInt("codTurmaAtual"));
					aluno.setIdade(rs.getInt("idade"));
					aluno.setSexo(rs.getString("sexo").charAt(0));
					
					alunos.add(aluno);
				} while (rs.next());
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao consultar: "+e.getMessage());
		}
		
		return alunos;
	}
}
