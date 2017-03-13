package proo.sei.dao.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public class ConexaoMySQL implements IConexao {
	static Connection conexao;

	@Override
	public synchronized Connection getConexao(String baseDados, String enderecoBanco, String nomeBanco, String usuario,
			String senha) {
		conexao = null;
		try {
			conexao = DriverManager.getConnection(baseDados + "://" + enderecoBanco + "/" + nomeBanco, usuario, senha);
		} catch (CommunicationsException e) {
			System.err.println("Erro: A comunicação com o banco de dados falhou!");
			System.exit(0);
		} catch (SQLException e) {
			System.err.println("Erro ao conectar: " + e.getMessage());
			System.exit(0);
		}
		return conexao;
	}

	public void fecharConexao() {
		try {
			ConexaoMySQL.conexao.close();
		} catch (SQLException e) {
			System.err.println("Erro ao fechar a conexão: " + e.getMessage());
		}
	}

}
