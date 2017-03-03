package proo.sei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proo.sei.vo.DisciplinaVO;

public class DisciplinaDAO {
	private ProfessorDAO professorDAO = new ProfessorDAO();
	
	public List<DisciplinaVO> listarPorSerie(Connection conexao, int serie) throws SQLException {
		String sql = "select * from disciplina where serie='" + serie + "'";
		
		List<DisciplinaVO> disciplinas = this.selectListDisciplina(conexao, sql);
		
		return disciplinas;
	}
	
	public List<DisciplinaVO> listarPorProfessor(Connection conexao, int codProfessor) throws SQLException {
		String sql = "select * from disciplina where codProfessor='" + codProfessor + "'";
		
		List<DisciplinaVO> disciplinas = this.selectListDisciplina(conexao, sql);
		
		return disciplinas;
	}
	
	public List<DisciplinaVO> selectListDisciplina(Connection conexao, String sql) throws SQLException{
		List<DisciplinaVO> disciplinas = new ArrayList<>();
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			if(rs.next()) {
				do {
					DisciplinaVO disciplina = new DisciplinaVO();
					
					disciplina.setCodDisciplina(rs.getInt("codDisciplina"));
					disciplina.setNome(rs.getString("nome"));
					disciplina.setSigla(rs.getString("sigla"));
					disciplina.setSerie(rs.getInt("serie"));
					disciplina.setProfResponsavel(professorDAO.procuraProfessor(conexao, rs.getInt("codProfessor")));
					
					disciplinas.add(disciplina);
				} while (rs.next());
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao consultar: "+e.getMessage());
		}
		
		return disciplinas;
	}
	
	public void updateCodProfessor(Connection conexao, int codDisciplina, int codProfessor) throws SQLException {
		String sql = "update disciplina set codProfessor='" + codProfessor + "' where codDisciplina='" + codDisciplina + "'";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro atualizar a disciplina: "+e.getMessage());
		}
	}
	
	public String procuraNomeDisciplina(Connection conexao, int cod) throws SQLException {
		String sql = "select * from disciplina where codDisciplina='" + cod + "'";
		
		DisciplinaVO disciplina = this.selectDisciplina(conexao, sql);
		
		if (disciplina != null) {
			return disciplina.getSigla() + " (" + disciplina.getNome() + ")";
		}
		
		return null;
	}
	
	public DisciplinaVO procuraDisciplina(Connection conexao, int cod) throws SQLException {
		String sql = "select * from disciplina where codDisciplina='" + cod + "'";
		
		DisciplinaVO disciplina = this.selectDisciplina(conexao, sql);
		
		if (disciplina != null) {
			return disciplina;
		}
		
		return null;
	}
	
	public DisciplinaVO selectDisciplina(Connection conexao, String sql) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			if (rs.first()) {
				DisciplinaVO disciplina = new DisciplinaVO();
				
				disciplina.setCodDisciplina(rs.getInt("codDisciplina"));
				disciplina.setNome(rs.getString("nome"));
				disciplina.setSigla(rs.getString("sigla"));
				disciplina.setSerie(rs.getInt("serie"));
				disciplina.setProfResponsavel(professorDAO.procuraProfessor(conexao, rs.getInt("codProfessor")));
				
				return disciplina;
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao consultar: "+e.getMessage());
		}
		
		return null;
	}
}
