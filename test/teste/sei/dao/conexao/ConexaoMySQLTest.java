package teste.sei.dao.conexao;

import org.junit.Test;

import junit.framework.TestCase;
import proo.sei.dao.conexao.ConexaoMySQL;

public class ConexaoMySQLTest extends TestCase {

	@Test
	public void testDeveriaConectarSeDadosValidos() throws Exception {
		ConexaoMySQL conexao = new ConexaoMySQL();
		assertNotNull(conexao.getConexao("jdbc:mysql","localhost:3306","sei","root",""));
	}
	
	@Test
	public void testNaoDeveriaConectarSeUsuarioVazio() throws Exception {
		ConexaoMySQL conexao = new ConexaoMySQL();
		assertNull(conexao.getConexao("jdbc:mysql","localhost:3306","sei","",""));
	}
}
