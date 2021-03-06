package proo.sei.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import proo.sei.dao.AlunoDAO;
import proo.sei.dao.DadosPessoaisDAO;
import proo.sei.dao.DisciplinaDAO;
import proo.sei.dao.NotaDAO;
import proo.sei.dao.ProfessorDAO;
import proo.sei.dao.TurmaDAO;
import proo.sei.dao.UsuarioDAO;
import proo.sei.exceptions.UsuarioBOException;
import proo.sei.view.UsuarioView;
import proo.sei.vo.AdministradorVO;
import proo.sei.vo.AlunoVO;
import proo.sei.vo.DadosPessoaisVO;
import proo.sei.vo.ProfessorVO;

public abstract class UsuarioBO implements IMenu {
	
	protected UsuarioDAO usuarioDAO = new UsuarioDAO();
	protected AlunoDAO alunoDAO = new AlunoDAO();
	protected ProfessorDAO professorDAO = new ProfessorDAO();
	protected DadosPessoaisDAO dadosDAO = new DadosPessoaisDAO();
	protected DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
	protected TurmaDAO turmaDAO = new TurmaDAO();
	protected NotaDAO notaDAO = new NotaDAO();

	protected AdministradorVO admin;
	protected ProfessorVO professor;
	protected AlunoVO aluno;
	
	UsuarioBO() {
		
	}
	
	UsuarioBO(AdministradorVO usuarioAtual) {
		this.admin = usuarioAtual;
	}
	
	UsuarioBO(ProfessorVO usuarioAtual) {
		this.professor = usuarioAtual;
	}
	
	UsuarioBO(AlunoVO usuarioAtual) {
		this.aluno = usuarioAtual;
	}

	protected Scanner input = new Scanner(System.in);
	protected Connection conexao;

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}
	
	public Boolean validaTurno (char turno) {
		try {				
			if (turno != 'M' && turno != 'V')
				throw new UsuarioBOException ("Turno inv�lido! Selecione Matutino ou Vespertino");				
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public Boolean validaRespostaConfirmar (char resposta) {
		try {				
			if (resposta != 'S' && resposta != 'N')
				throw new UsuarioBOException ("Resposta inv�lida! Digite S ou N");				
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public char confirmar(String pergunta) {
		char resposta;

		do {
			System.out.print(pergunta);
			resposta = input.next().charAt(0);
			resposta = String.valueOf(resposta).toUpperCase().charAt(0);
		} while (!validaRespostaConfirmar(resposta));

		return resposta;
	}
	
	public void mudarSenha(String login) {
		UsuarioView usuarioView = new UsuarioView();
		
		String novaSenha = usuarioView.interfaceNovaSenha();

		try {
			this.usuarioDAO.updateSenha(conexao, login, novaSenha);
			System.out.println("\nSenha alterada com sucesso!\n");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public String formataNota(double nota) {
		String notaF = String.format("%.2f", nota);
		return notaF;
	}

	public void visualizarDadosPessoais(int codUsuario, String nome) {
		UsuarioView usuarioView = new UsuarioView();
		
		try {
			DadosPessoaisVO dados = dadosDAO.procuraDadosPessoais(conexao, codUsuario);
			usuarioView.showDadosPessoais(nome, dados);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
