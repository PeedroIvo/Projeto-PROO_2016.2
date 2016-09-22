package proo.sei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proo.sei.mo.Nota;

public class NotaDAO {
	public void criar(Connection conexao, int matricAluno, int codDisciplina, int bimestre, double nota) throws SQLException {
		String sql = "insert into nota values (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, matricAluno);
			stmt.setInt(2, codDisciplina);
			stmt.setInt(3, 2016);
			stmt.setInt(4, bimestre);
			stmt.setDouble(5, nota);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro: "+e.getMessage());
		}
	}
	
	public void apagarNotasAluno (Connection conexao, int cod) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement("delete from nota where matricAluno='" + cod + "'");) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro: "+e.getMessage());
		}
	}
	
	public List<Nota> selectListNota(Connection conexao, String sql) throws SQLException {
		List<Nota> notas = new ArrayList<>();
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			if(rs.next()) {
				do {
					Nota nota = new Nota();
					
					nota.setAno(rs.getInt("ano"));
					nota.setBimestre(rs.getInt("bimestre"));
					nota.setCodDisciplina(rs.getInt("codDisciplina"));
					nota.setMatricAluno(rs.getInt("matricAluno"));
					nota.setNota(rs.getDouble("nota"));
					
					notas.add(nota);
				} while (rs.next());
			}
		} catch (SQLException e) {
			throw new SQLException("Erro: "+e.getMessage());
		}
		
		return notas;
	}
	
	public List<Nota> procuraListNota (Connection conexao, int codDisciplina, int bimestre, int codTurma) throws SQLException {
		String sql = "select * from nota, aluno where codDisciplina='" + codDisciplina + "' and bimestre='" + bimestre + "' and aluno.codTurmaAtual='" + codTurma + "' and aluno.matricAluno=nota.matricAluno";
		
		List<Nota> notas = this.selectListNota(conexao, sql);
		
		return notas;
	}
	
	public List<Nota> procuraListNotasAluno (Connection conexao, int matricAluno) throws SQLException {
		String sql = "select * from nota where matricAluno='" + matricAluno + "'";
		
		List<Nota> notas = this.selectListNota(conexao, sql);
		
		return notas;
	}
}
