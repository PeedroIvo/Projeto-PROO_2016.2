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
	
	public Boolean validaSexo (char sexo) {
		try {				
			if (sexo != 'M' && sexo != 'F')
				throw new UsuarioBOException ("Sexo Inválido! Selecione Feminino ou Masculino");				
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public Boolean validaCPF (String CPF) {
		try {
			Long.parseLong(CPF);
			
			if (CPF.length() != 11)
				throw new UsuarioBOException ("O CPF deve conter 11 números!");
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (NumberFormatException e) {
			System.out.println("O CPF deve conter apenas números!");
			return false;
		}
				
		return true;
	}
	
	public boolean validaRG(String RG) {
		try {
			Long.parseLong(RG);
			
			if (RG.length() != 8)
				throw new UsuarioBOException ("O RG deve conter 8 números!");
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (NumberFormatException e) {
			System.out.println("O RG deve conter apenas números!");
			return false;
		}
				
		return true;
	}
	
	public boolean validaTelefone(String Telefone) {
		try {
			Long.parseLong(Telefone);
			
			if (Telefone.length() > 11)
				throw new UsuarioBOException ("O telefone deve conter no máximo 11 números com o DDD!");
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (NumberFormatException e) {
			System.out.println("O telefone deve conter apenas números!");
			return false;
		}
				
		return true;
	}
	
	public boolean validaCEP(String CEP) {
		try {
			Long.parseLong(CEP);
			
			if (CEP.length() != 8)
				throw new UsuarioBOException ("O CEP deve conter 8 números!");
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (NumberFormatException e) {
			System.out.println("O CEP deve conter apenas números!");
			return false;
		}
				
		return true;
	}
	
	public Boolean validaSerie (int serie) {
		try {				
			if (serie != 1 && serie != 2 && serie != 3)
				throw new UsuarioBOException ("Série inválida! Selecione 1º Ano, 2º Ano ou 3º Ano");				
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public Boolean validaLogin (Connection conexao, String login) {
		try {				
			if (!usuarioDAO.usuarioExiste(conexao, login))
				throw new UsuarioBOException ("Este login já está sendo usado, tente novamente!");				
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean validaTamanhoCampo(String conteudo, int tamanhoMaximo) {
		try {			
			if (conteudo.length() > tamanhoMaximo)
				throw new UsuarioBOException ("Informação muito longa! Use no máximo " + tamanhoMaximo + " caracteres");
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		}
				
		return true;
	}
	
	public Boolean validaTurno (char turno) {
		try {				
			if (turno != 'M' && turno != 'V')
				throw new UsuarioBOException ("Turno inválido! Selecione Matutino ou Vespertino");				
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public Boolean validaBimestre (int bimestre) {
		try {				
			if (bimestre != 1 && bimestre != 2 && bimestre != 3 && bimestre != 4)
				throw new UsuarioBOException ("Bimestre inválido! Selecione 1, 2, 3 ou 4");				
			
		} catch (UsuarioBOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public Boolean validaRespostaConfirmar (char resposta) {
		try {				
			if (resposta != 'S' && resposta != 'N')
				throw new UsuarioBOException ("Resposta inválida! Digite S ou N");				
			
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
		} while (!validaRespostaConfirmar(resposta));

		return resposta;
	}
	
	public void mudarSenha(String login) {
		String novaSenha;

		do {
			System.out.print("Digite a sua nova senha: ");
			novaSenha = input.next();

			if (novaSenha.length() < 6) {
				System.out.println("A senha deve ter no mínimo 6 caracteres!");
			}
		} while (novaSenha.length() < 6);

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
		try {
			DadosPessoaisVO dados = dadosDAO.procuraDadosPessoais(conexao, codUsuario);
	
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
			System.out.println("Cidade, Estado: " + dados.getEndereco().getCidade() + ", " + dados.getEndereco().getEstado() + "\n");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
