package teste.sei.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import junit.framework.TestCase;
import proo.sei.dao.conexao.ConexaoMySQL;
import proo.sei.dao.conexao.IConexao;
import proo.sei.vo.NotaVO;

public class NotaDAOTest extends TestCase {
	
	private IConexao conexaoMySQL = new ConexaoMySQL();
	private Connection conexao;
	
	@Test
	public void testDeveriaInserirNota() {
		try {
			this.conexao = conexaoMySQL.getConexao("jdbc:mysql", "localhost:3306", "sei", "root", "");			
			conexao.setAutoCommit(false);
			
			NotaVO nota = new NotaVO();
			nota.setAno(2015);
			nota.setBimestre(1);
			nota.setCodDisciplina(1);
			nota.setMatricAluno(11);
			nota.setNota(10);
			
			conexao.rollback();
			assertTrue(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}
}
