package proo.sei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proo.sei.vo.UsuarioVO;

public class UsuarioDAO {
	public void criarUsuario(Connection conexao, UsuarioVO usuario) throws SQLException {
		String sql = "insert into usuario (login, nome, tipoUsuario) values (?, ?, ?)";
		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getNome());
			stmt.setString(3, String.valueOf(usuario.getTipoUsuario()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro ao criar o usuário: "+e.getMessage());
		}
	}
	
	public void apagarUsuario(Connection conexao, int cod) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement("delete from usuario where codUsuario='" + cod + "'");) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro ao apagar o usuário: "+e.getMessage());
		}
	}
	
	public int pegarCodigo(Connection conexao, String login) throws SQLException {
		String sql = "select codUsuario from usuario where login='" + login + "'";
		try (PreparedStatement stmt = conexao.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao consultar: "+e.getMessage());
		}
		
		return 0;
	}
	
	public boolean usuarioExiste(Connection conexao, String loginDigitado) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement("select * from usuario where login='" + loginDigitado + "'");
				ResultSet rs = stmt.executeQuery();) {
			if (rs.first()) {
				return false;
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao consultar: " + e.getMessage());
		}
		
		return true;
	}
	
	public void updateSenha(Connection conexao, String login, String novaSenha) throws SQLException{
		String sql = "update usuario set senha = '" + novaSenha + "' where login='" + login + "'";
		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Erro ao atualizar a senha: "+e.getMessage());
		}
	}
}
