package proo.sei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proo.sei.vo.TurmaVO;

public class TurmaDAO {
	private AlunoDAO alunoDAO = new AlunoDAO();
	private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
	
	public int procuraCodTurma(Connection conexao, int serie, char turno) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement("select codTurma from turma where serie='" + serie + "' and turno='" + turno + "'");
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				return rs.getInt("codTurma");
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao consultar: "+e.getMessage());
		}
		
		return 0;
	}
	
	public TurmaVO procuraTurma(Connection conexao, int cod) throws SQLException {
		String sql = "select * from turma where codTurma='" + cod + "'";
		
		TurmaVO turma = this.selectTurma(conexao, sql);
		
		if (turma != null) {
			return turma;
		}
		
		return null;
	}
	
	public TurmaVO selectTurma(Connection conexao, String sql) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			if (rs.first()) {
				TurmaVO turma = new TurmaVO();
				
				turma.setAno(rs.getInt("ano"));
				turma.setCodTurma(rs.getInt("codTurma"));
				turma.setSala(rs.getString("sala"));
				turma.setSerie(rs.getInt("serie"));
				turma.setTurno(rs.getString("turno").charAt(0));
				turma.setAlunos(alunoDAO.listarPorTurma(conexao, rs.getInt("codTurma")));
				turma.setDisciplinas(disciplinaDAO.listarPorSerie(conexao, rs.getInt("serie")));
				
				return turma;
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao consultar: "+e.getMessage());
		}
		
		return null;
	}
}
