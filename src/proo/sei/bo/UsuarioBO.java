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
import proo.sei.mo.Administrador;
import proo.sei.mo.Aluno;
import proo.sei.mo.DadosPessoais;
import proo.sei.mo.Professor;

public abstract class UsuarioBO implements IMenu {
	
	protected UsuarioDAO usuarioDAO = new UsuarioDAO();
	protected AlunoDAO alunoDAO = new AlunoDAO();
	protected ProfessorDAO professorDAO = new ProfessorDAO();
	protected DadosPessoaisDAO dadosDAO = new DadosPessoaisDAO();
	protected DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
	protected TurmaDAO turmaDAO = new TurmaDAO();
	protected NotaDAO notaDAO = new NotaDAO();

	protected Administrador admin;
	protected Professor professor;
	protected Aluno aluno;
	
	UsuarioBO() {
		
	}
	
	UsuarioBO(Administrador usuarioAtual) {
		this.admin = usuarioAtual;
	}
	
	UsuarioBO(Professor usuarioAtual) {
		this.professor = usuarioAtual;
	}
	
	UsuarioBO(Aluno usuarioAtual) {
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

	public void mudarSenha(String login) throws SQLException {
		String novaSenha;

		do {
			System.out.print("Digite a sua nova senha: ");
			novaSenha = input.next();

			if (novaSenha.length() < 6) {
				System.out.println("A senha deve ter no mínimo 6 caracteres!");
			}
		} while (novaSenha.length() < 6);

		this.usuarioDAO.updateSenha(conexao, login, novaSenha);

		System.out.println("\nSenha alterada com sucesso!\n");
	}

	public char confirmar(String pergunta) {
		char resposta;

		do {
			System.out.print(pergunta);
			resposta = input.next().charAt(0);

			if (resposta != 'S' && resposta != 'N') {
				System.out.println("Resposta inválida! Digite S ou N");
			}
		} while (resposta != 'S' && resposta != 'N');

		return resposta;
	}

	public String formataNota(double nota) {
		String notaF = String.format("%.2f", nota);

		return notaF;
	}

	public void visualizarDadosPessoais(int codUsuario, String nome) throws SQLException {
		DadosPessoais dados = dadosDAO.procuraDadosPessoais(conexao, codUsuario);

		System.out.println("Nome: " + nome);
		System.out.println("CPF: " + dados.getCpf());
		System.out.println("RG: " + dados.getRg());
		System.out.println("Email: " + dados.getEmail());
		System.out.println("Telefone: " + dados.getTelefone());
		System.out.println("Celular: " + dados.getCelular());
		System.out.println("\nCEP: " + dados.getEndereco().getCep());
		System.out.println("Rua: " + dados.getEndereco().getRua());
		System.out.println("Número: " + dados.getEndereco().getnCasa());
		System.out.println("Bairro: " + dados.getEndereco().getBairro());
		System.out.println("Complemento: " + dados.getEndereco().getComplemento());
		System.out.println(
				"Cidade, Estado: " + dados.getEndereco().getCidade() + ", " + dados.getEndereco().getEstado() + "\n");
	}
}
