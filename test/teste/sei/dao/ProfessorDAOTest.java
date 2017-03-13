package teste.sei.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import junit.framework.TestCase;
import proo.sei.dao.ProfessorDAO;
import proo.sei.dao.conexao.ConexaoMySQL;
import proo.sei.dao.conexao.IConexao;
import proo.sei.vo.ProfessorVO;

public class ProfessorDAOTest extends TestCase{
	private IConexao conexaoMySQL = new ConexaoMySQL();
	private Connection conexao;
	
	@Test
	public void testDeveriaInserirProfessor() {
		try {
			this.conexao = conexaoMySQL.getConexao("jdbc:mysql", "localhost:3306", "sei", "root", "");			
			conexao.setAutoCommit(false);
			
			ProfessorVO professor = new ProfessorVO();
			professor.setNome("Professor Teste");
			professor.setLogin("profteste");
			professor.setSenha("****");
			professor.setTipoUsuario('p');
			professor.setDataAdmissao("2017-01-01");;
			
			ProfessorDAO profDAO = new ProfessorDAO();
			profDAO.criar(conexao, professor);
			
			conexao.rollback();
			assertTrue(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}
	
	@Test
	public void testDeveriaApagarProfessor() {
		try {
			this.conexao = conexaoMySQL.getConexao("jdbc:mysql", "localhost:3306", "sei", "root", "");			
			conexao.setAutoCommit(false);
			
			ProfessorDAO profDAO = new ProfessorDAO();
			profDAO.apagar(conexao, 2);
			
			conexao.rollback();
			assertTrue(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}
}
