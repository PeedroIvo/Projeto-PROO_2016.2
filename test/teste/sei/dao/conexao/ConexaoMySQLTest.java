package teste.sei.dao.conexao;

import org.junit.Test;
import junit.framework.TestCase;
import proo.sei.dao.conexao.ConexaoMySQL;

public class ConexaoMySQLTest extends TestCase {
	ConexaoMySQL conexao = new ConexaoMySQL();
	
	@Test
	public void testDeveriaConectarSeDadosValidos() throws Exception {
		assertNotNull(conexao.getConexao("jdbc:mysql","localhost:3306","sei","root",""));
	}
	
	@Test
	public void testNaoDeveriaConectarSeUsuarioVazio() throws Exception {
		assertNull(conexao.getConexao("jdbc:mysql","localhost:3306","sei","",""));
	}
}
