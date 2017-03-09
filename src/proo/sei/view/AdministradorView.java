package proo.sei.view;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

import proo.sei.bo.AdministradorBO;
import proo.sei.exceptions.ViewException;
import proo.sei.vo.AlunoVO;
import proo.sei.vo.ProfessorVO;

public class AdministradorView {
	private Scanner input = new Scanner(System.in);
	private AdministradorBO adminBO = new AdministradorBO();
	
	public int interfaceMenu (String nome) {		
		System.out.println("-------------------------------------");
		System.out.println("Olá, " + nome + "! O que deseja fazer?");
		System.out.println("[1] Matricular aluno");
		System.out.println("[2] Desmatricular aluno");
		System.out.println("[3] Cadastrar professor");
		System.out.println("[4] Descadastrar professor");
		System.out.println("[5] Quadro de professores");
		System.out.println("[6] Visualizar boletim de aluno");
		System.out.println("[7] Visualizar turma");
		System.out.println("[8] Mudar a senha");
		System.out.println("[9] Logout");
		
		Boolean erro;
		int opcao = 0;
		
		do {
			erro = false;
			System.out.print("Digite sua opção: ");			
			
			try {
				opcao = this.input.nextInt();
				
				if (opcao <= 0 || opcao > 9) {
					throw new ViewException("Opção inválida! Tente novamente!");
				}
			} catch (InputMismatchException e) {
				System.out.println("Opção inválida! Tente novamente!");
				input.next();
				erro = true;
			} catch (ViewException e) {
				System.out.println(e.getMessage());
				erro = true;
			}
		} while (erro);
		
		System.out.println();
		
		return opcao;
	}
	
	public int interfaceCod(String pedidoCod) {
		Boolean erro;
		int cod = 0;
		
		do {
			erro = false;
			
			System.out.print(pedidoCod + ": ");		
			
			try {
				cod = input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		return cod;
	}
	
	public String interfaceString(String campo) {
		System.out.print(campo + ": ");
		return input.nextLine();
	}
	
	public int interfaceIdade() {
		Boolean erro;
		int idade = 0;
		
		do {
			erro = false;
			System.out.print("Idade: ");					
			
			try {
				idade = input.nextInt();
				erro = !adminBO.validaIdade(idade);
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}			
		} while (erro);
		
		return idade;
	}
	
	public char interfaceSexo() {
		char sexo;
		
		do {
			System.out.print("Sexo (F ou M): ");
			sexo = input.next().charAt(0);
			sexo = String.valueOf(sexo).toUpperCase().charAt(0);
		} while (!adminBO.validaSexo(sexo));
		
		return sexo;
	}
	
	public String interfaceCPF() {
		String CPF;
		
		do {
			System.out.print("CPF: ");
			CPF = input.next();	
		} while (!adminBO.validaCPF(CPF));
		
		return CPF;
	}
	
	public String interfaceRG() {
		String RG;
		
		do {
			System.out.print("RG: ");
			RG = input.next();
		} while (!adminBO.validaRG(RG));
		
		return RG;
	}
	
	public String interfaceEmail() {
		String email;
		
		do {
			System.out.print("Email: ");
			email = input.next().toLowerCase();
		} while (!adminBO.validaEmail(email));
		input.nextLine();
		
		return email;
	}
	
	public String interfaceTelefone(String tipoTelefone) {
		String telefone;
		
		do {
			System.out.print(tipoTelefone + ": ");			
			telefone = input.nextLine();
		} while (!adminBO.validaTelefone(telefone));
		
		return telefone;
	}
	
	public String interfaceCEP() {
		String CEP;
		
		do {
			System.out.print("CEP: ");
			CEP = input.next();
		} while (!adminBO.validaCEP(CEP));
		input.nextLine();
		
		return CEP;
	}
	
	public String interfaceNCasa() {
		String nCasa;
		
		do {
			System.out.print("Número: ");
			nCasa = input.nextLine();
		} while (!adminBO.validaTamanhoCampo(nCasa, 10));
		
		return nCasa;
	}
	
	public int interfaceSerie() {
		Boolean erro;
		int serie = 0;
		
		do {
			erro = false;
			System.out.print("Série (1, 2 ou 3): ");			
			
			try {
				serie = input.nextInt();
				erro = !adminBO.validaSerie(serie);
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		return serie;
	}
	
	public char interfaceTurno() {
		char turno;

		do {
			System.out.print("Turno (M ou V): ");
			turno = input.next().charAt(0);
			turno = String.valueOf(turno).toUpperCase().charAt(0);
		} while (!adminBO.validaTurno(turno));
		
		return turno;
	}
	
	public String interfaceLogin(Connection conexao) {
		String login;
		
		do {
			System.out.print("Novo login do aluno no sistema: ");
			login = input.next().toLowerCase();			
		} while (!adminBO.validaLogin(conexao, login));
		
		return login;
	}
	
	public AlunoVO interfaceMatricularAluno(AlunoVO novoAluno) {
		System.out.println("Digite os dados do novo aluno abaixo:");
		input.nextLine();
		
		novoAluno.setNome(this.interfaceString("Nome"));
		novoAluno.setIdade(this.interfaceIdade());
		novoAluno.setSexo(this.interfaceSexo());
		
		novoAluno.getDadosPessoais().setCpf(this.interfaceCPF());
		novoAluno.getDadosPessoais().setRg(this.interfaceRG());
		novoAluno.getDadosPessoais().setEmail(this.interfaceEmail());
		novoAluno.getDadosPessoais().setCelular(this.interfaceTelefone("Celular"));
		novoAluno.getDadosPessoais().setTelefone(this.interfaceTelefone("Telefone"));
		
		System.out.println("\nAgora digite os dados do endereço do novo aluno:" );
		
		novoAluno.getDadosPessoais().getEndereco().setCep(this.interfaceCEP());
		novoAluno.getDadosPessoais().getEndereco().setRua(this.interfaceString("Rua"));
		novoAluno.getDadosPessoais().getEndereco().setnCasa(this.interfaceNCasa());
		novoAluno.getDadosPessoais().getEndereco().setBairro(this.interfaceString("Bairro"));
		novoAluno.getDadosPessoais().getEndereco().setComplemento(this.interfaceString("Complemento"));
		novoAluno.getDadosPessoais().getEndereco().setCidade(this.interfaceString("Cidade"));
		novoAluno.getDadosPessoais().getEndereco().setEstado(this.interfaceString("Estado"));
		
		System.out.println();
		
		return novoAluno;
	}
	
	public ProfessorVO interfaceMatricularProfessor(ProfessorVO novoProfessor) {
		System.out.println("Digite os dados do novo professor abaixo:");
		input.nextLine();
		
		novoProfessor.setNome(this.interfaceString("Nome"));
		novoProfessor.getDadosPessoais().setCpf(this.interfaceCPF());
		novoProfessor.getDadosPessoais().setRg(this.interfaceRG());
		novoProfessor.getDadosPessoais().setEmail(this.interfaceEmail());
		novoProfessor.getDadosPessoais().setCelular(this.interfaceTelefone("Celular"));
		novoProfessor.getDadosPessoais().setTelefone(this.interfaceTelefone("Telefone"));
		
		System.out.println("\nAgora digite os dados do endereço do novo professor:" );
		
		novoProfessor.getDadosPessoais().getEndereco().setCep(this.interfaceCEP());
		novoProfessor.getDadosPessoais().getEndereco().setRua(this.interfaceString("Rua"));
		novoProfessor.getDadosPessoais().getEndereco().setnCasa(this.interfaceNCasa());
		novoProfessor.getDadosPessoais().getEndereco().setBairro(this.interfaceString("Bairro"));
		novoProfessor.getDadosPessoais().getEndereco().setComplemento(this.interfaceString("Complemento"));
		novoProfessor.getDadosPessoais().getEndereco().setCidade(this.interfaceString("Cidade"));
		novoProfessor.getDadosPessoais().getEndereco().setEstado(this.interfaceString("Estado"));
		
		System.out.println();
		
		return novoProfessor;		
	}
}
