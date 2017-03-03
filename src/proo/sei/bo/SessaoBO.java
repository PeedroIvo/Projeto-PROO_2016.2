package proo.sei.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proo.sei.dao.conexao.ConexaoMySQL;
import proo.sei.vo.AdministradorVO;
import proo.sei.vo.AlunoVO;
import proo.sei.vo.ProfessorVO;
import proo.sei.vo.UsuarioVO;

public class SessaoBO {
	
	private ConexaoMySQL mysql = new ConexaoMySQL();
	private Connection conexao = mysql.getConexao("jdbc:mysql", "localhost:3306", "sei", "root", "");

	private UsuarioVO usuarioAtual;
	private UsuarioBO usuarioBOAtual;
	
	public UsuarioVO getUsuarioAtual() {
		return usuarioAtual;
	}

	public void setUsuarioAtual(UsuarioVO usuarioAtual) {
		this.usuarioAtual = usuarioAtual;
	}

	public UsuarioBO getUsuarioBOAtual() {
		return usuarioBOAtual;
	}

	public void setUsuarioBOAtual(UsuarioBO usuarioBOAtual) {
		this.usuarioBOAtual = usuarioBOAtual;
	}

	public Connection getConexao() {
		return conexao;
	}
	
	public void fecharConexao() {
		this.mysql.fecharConexao();
	}
	
	public boolean validarLogin(Connection conexao, String loginDigitado, String senhaDigitada) {
		try (PreparedStatement stmt = conexao.prepareStatement("select * from usuario where login='" + loginDigitado + "'");
				ResultSet rs = stmt.executeQuery();) {
			
			if (rs.first()) {
				if (rs.getString("senha").equals(senhaDigitada)) {					
					if(rs.getString("tipoUsuario").equals("0")) {
						AdministradorVO adminAtual = new AdministradorVO();
						
						this.setUsuarioAtual(adminAtual);
						this.setUsuarioBOAtual(new AdministradorBO(adminAtual));
					} else if(rs.getString("tipoUsuario").equals("a")) {
						AlunoVO alunoAtual = new AlunoVO();
						
						this.setUsuarioAtual(alunoAtual);
						this.setUsuarioBOAtual(new AlunoBO(alunoAtual));
					} else if(rs.getString("tipoUsuario").equals("p")) {
						ProfessorVO professorAtual = new ProfessorVO();
						
						this.setUsuarioAtual(professorAtual);
						this.setUsuarioBOAtual(new ProfessorBO(professorAtual));
					}
					
					this.usuarioAtual.setCodUsuario(rs.getInt("codUsuario"));
					this.usuarioAtual.setLogin(rs.getString("login"));
					this.usuarioAtual.setSenha(rs.getString("senha"));
					this.usuarioAtual.setNome(rs.getString("nome"));
					this.usuarioAtual.setTipoUsuario(rs.getString("tipoUsuario").charAt(0));
					
					return true;
					
				} else {
					System.out.println("-------------------------------------");
					System.out.println("A senha digitada está incorreta! Tente novamente!");
				}
			} else {
				System.out.println("-------------------------------------");
				System.out.println("Este login não está cadastrado! Tente novamente!");
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
