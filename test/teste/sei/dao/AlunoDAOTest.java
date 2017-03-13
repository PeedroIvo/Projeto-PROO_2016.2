package teste.sei.dao;

import org.junit.Test;

import junit.framework.TestCase;
import proo.sei.dao.AlunoDAO;
import proo.sei.dao.conexao.ConexaoMySQL;
import proo.sei.dao.conexao.IConexao;
import proo.sei.vo.AlunoVO;

import java.sql.Connection;
import java.sql.SQLException;

public class AlunoDAOTest extends TestCase {
	
	private IConexao conexaoMySQL = new ConexaoMySQL();
	private Connection conexao;
	
	@Test
	public void testDeveriaInserirAluno() {
		try {
			this.conexao = conexaoMySQL.getConexao("jdbc:mysql", "localhost:3306", "sei", "root", "");			
			conexao.setAutoCommit(false);
			
			AlunoVO aluno = new AlunoVO();
			aluno.setNome("Aluno Teste");
			aluno.setLogin("alunoteste");
			aluno.setSenha("****");
			aluno.setTipoUsuario('a');
			aluno.setIdade(20);
			aluno.setSexo('M');
			aluno.setCodTurmaAtual(1);
			
			AlunoDAO alunoDAO = new AlunoDAO();
			alunoDAO.criar(conexao, aluno);
			
			conexao.rollback();
			assertTrue(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}
	
	@Test
	public void testDeveriaApagarAluno() {
		try {
			this.conexao = conexaoMySQL.getConexao("jdbc:mysql", "localhost:3306", "sei", "root", "");			
			conexao.setAutoCommit(false);
			
			AlunoDAO alunoDAO = new AlunoDAO();
			alunoDAO.apagar(conexao, 11);
			
			conexao.rollback();
			assertTrue(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}
}
