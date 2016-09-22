package proo.sei.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import proo.sei.mo.Administrador;
import proo.sei.mo.Aluno;
import proo.sei.mo.Disciplina;
import proo.sei.mo.Nota;
import proo.sei.mo.Professor;
import proo.sei.mo.Turma;

public class AdministradorBO extends UsuarioBO {
	public AdministradorBO() {
	
	}
	
	public AdministradorBO(Administrador usuarioAtual) {
		super(usuarioAtual);
	}

	@Override
	public void menu(Connection conexao) throws SQLException {
		this.setConexao(conexao);
		int opcao;
		
		do {
			System.out.println("-------------------------------------");
			System.out.println("Olá, " + admin.getNome() + "! O que deseja fazer?");
			System.out.println("[1] Matricular aluno");
			System.out.println("[2] Desmatricular aluno");
			System.out.println("[3] Cadastrar professor");
			System.out.println("[4] Descadastrar professor");
			System.out.println("[5] Quadro de professores");
			System.out.println("[6] Visualizar boletim de aluno");
			System.out.println("[7] Visualizar turma");
			System.out.println("[8] Mudar a senha");
			System.out.println("[9] Logout");

			do {
				System.out.print("Digite sua opção: ");
				opcao = input.nextInt();

				if (opcao <= 0 || opcao > 9) {
					System.out.println("Opção inválida! Tente novamente!");
				}
			} while (opcao <= 0 || opcao > 9);
			
			System.out.println();

			if (opcao == 1) {
				this.matricularAluno();
			} else if (opcao == 2) {
				this.desmatricularAluno();
			} else if (opcao == 3) {
				this.cadastrarProfessor();
			} else if (opcao == 4) {
				this.descadastrarProfessor();
			} else if (opcao == 5) {
				this.quadroProfessores();
			} else if (opcao == 6) {
				this.visualizarBoletim();
			} else if (opcao == 7) {
				this.visualizarTurma();
			} else if (opcao == 8) {
				this.mudarSenha(admin.getLogin());
			}
		} while (opcao != 9);
	}
	
	public void matricularAluno() throws SQLException {
		Aluno novoAluno = new Aluno();	
		
		novoAluno.setTipoUsuario('a');
		
		System.out.println("Digite os dados do novo aluno abaixo:");
		
		input.nextLine();
		System.out.print("Nome: ");
		novoAluno.setNome(input.nextLine());
		
		System.out.print("Idade: ");
		novoAluno.setIdade(input.nextInt());
		
		do {
			System.out.print("Sexo (F ou M): ");
			novoAluno.setSexo(input.next().charAt(0));
			
			if (novoAluno.getSexo() != 'M' && novoAluno.getSexo() != 'F'){
				System.out.println("Sexo Inválido! Selecione Feminino ou Masculino");
			}
		} while (novoAluno.getSexo() != 'M' && novoAluno.getSexo() != 'F');
		
		System.out.print("CPF: ");
		novoAluno.getDadosPessoais().setCpf(input.next());
		
		System.out.print("RG: ");
		novoAluno.getDadosPessoais().setRg(input.next());
		
		System.out.print("Email: ");
		novoAluno.getDadosPessoais().setEmail(input.next().toLowerCase());
		
		input.nextLine();
		System.out.print("Celular: ");
		novoAluno.getDadosPessoais().setCelular(input.nextLine());
		
		System.out.print("Telefone: ");
		novoAluno.getDadosPessoais().setTelefone(input.nextLine());
		
		System.out.println("\nAgora digite os dados do endereço do novo aluno:" );
		
		System.out.print("CEP: ");
		novoAluno.getDadosPessoais().getEndereco().setCep(input.next());
		
		input.nextLine();
		System.out.print("Rua: ");
		novoAluno.getDadosPessoais().getEndereco().setRua(input.nextLine());
		
		System.out.print("Número: ");
		novoAluno.getDadosPessoais().getEndereco().setnCasa(input.nextLine());
		
		System.out.print("Bairro: ");
		novoAluno.getDadosPessoais().getEndereco().setBairro(input.nextLine());
		
		System.out.print("Complemento: ");
		novoAluno.getDadosPessoais().getEndereco().setComplemento(input.nextLine());
		
		System.out.print("Cidade: ");
		novoAluno.getDadosPessoais().getEndereco().setCidade(input.nextLine());
		
		System.out.print("Estado: ");
		novoAluno.getDadosPessoais().getEndereco().setEstado(input.nextLine());
		
		System.out.println();
		
		int serie;
		
		do {
			System.out.print("Série (1, 2 ou 3): ");
			serie = input.nextInt();
			
			if (serie != 1 && serie != 2 && serie != 3){
				System.out.println("Série inválida! Selecione 1º Ano, 2º Ano ou 3º Ano");
			}
		} while (serie != 1 && serie != 2 && serie != 3);
		
		char turno;
		
		do {
			System.out.print("Turno (M ou V): ");
			turno = input.next().charAt(0);
			
			if (turno != 'M' && turno != 'V'){
				System.out.println("Turno inválido! Selecione Matutino ou Vespertino");
			}
		} while (turno != 'M' && turno != 'V');
		
		novoAluno.setCodTurmaAtual(turmaDAO.procuraCodTurma(conexao, serie, turno));
		
		System.out.println();
		
		do {
			System.out.print("Novo login do aluno no sistema: ");
			novoAluno.setLogin(input.next().toLowerCase());
			
			if (!usuarioDAO.usuarioExiste(conexao, novoAluno.getLogin())){
				System.out.println("Este login já está sendo usado, tente novamente!");
			}
		} while (!usuarioDAO.usuarioExiste(conexao, novoAluno.getLogin()));
		
		alunoDAO.criar(conexao, novoAluno);
		
		System.out.println("\nAluno matriculado com sucesso!\nLogin: " + novoAluno.getLogin() + "\nSenha Padrão: 123456\n");
	}
	
	public void desmatricularAluno() throws SQLException {
		System.out.println("Atenção! O cancelamento de matrícula não poderá ser desfeito!");
		System.out.print("Digite o código de matrícula do Aluno: ");
		int cod = input.nextInt();
		
		String nomeAluno = alunoDAO.procuraNomeAluno(conexao, cod);

		if (nomeAluno == null)  {
			System.out.println("\nNenhum aluno foi encontrado com esse código de matrícula!");
		} else {
			System.out.println("\nNome do Aluno: " + nomeAluno);
			
			char resposta = this.confirmar("Tem certeza que deseja cancelar esta matricula (S ou N)? ");
			
			if (resposta == 'S') {
				notaDAO.apagarNotasAluno(conexao, cod);
				alunoDAO.apagar(conexao, cod);
				
				System.out.println("\nCancelamento de matrícula efetuado com sucesso!");
			}
		}
		
		System.out.println();
	}
	
	public void cadastrarProfessor() throws SQLException {
		Professor novoProfessor = new Professor();
		
		Calendar cal = Calendar.getInstance(); 
		novoProfessor.setDataAdmissao(cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH));
		
		novoProfessor.setTipoUsuario('p');
		
		System.out.println("Digite os dados do novo professor abaixo:");
		
		input.nextLine();
		System.out.print("Nome: ");
		novoProfessor.setNome(input.nextLine());
		
		System.out.print("CPF: ");
		novoProfessor.getDadosPessoais().setCpf(input.next());
		
		System.out.print("RG: ");
		novoProfessor.getDadosPessoais().setRg(input.next());
		
		System.out.print("Email: ");
		novoProfessor.getDadosPessoais().setEmail(input.next().toLowerCase());
		
		input.nextLine();
		System.out.print("Celular: ");
		novoProfessor.getDadosPessoais().setCelular(input.nextLine());
		
		System.out.print("Telefone: ");
		novoProfessor.getDadosPessoais().setTelefone(input.nextLine());
		
		System.out.println("\nAgora digite os dados do endereço do novo professor:" );
		
		System.out.print("CEP: ");
		novoProfessor.getDadosPessoais().getEndereco().setCep(input.next());
		
		input.nextLine();
		System.out.print("Rua: ");
		novoProfessor.getDadosPessoais().getEndereco().setRua(input.nextLine());
		
		System.out.print("Número: ");
		novoProfessor.getDadosPessoais().getEndereco().setnCasa(input.nextLine());
		
		System.out.print("Bairro: ");
		novoProfessor.getDadosPessoais().getEndereco().setBairro(input.nextLine());
		
		System.out.print("Complemento: ");
		novoProfessor.getDadosPessoais().getEndereco().setComplemento(input.nextLine());
		
		System.out.print("Cidade: ");
		novoProfessor.getDadosPessoais().getEndereco().setCidade(input.nextLine());
		
		System.out.print("Estado: ");
		novoProfessor.getDadosPessoais().getEndereco().setEstado(input.nextLine());
		
		System.out.println();
		
		do {
			System.out.print("Novo login do professor no sistema: ");
			novoProfessor.setLogin(input.next().toLowerCase());
			
			if (!usuarioDAO.usuarioExiste(conexao, novoProfessor.getLogin())){
				System.out.println("Este login já está sendo usado, tente novamente!");
			}
		} while (!usuarioDAO.usuarioExiste(conexao, novoProfessor.getLogin()));
		
		professorDAO.criar(conexao, novoProfessor);
		
		System.out.println("\nProfessor matriculado com sucesso!\nLogin: " + novoProfessor.getLogin() + "\nSenha Padrão: 123456\n");
	}
	
	public void quadroProfessores() throws SQLException {
		int serie;
		
		do {
			System.out.print("Deseja consultar as disciplinas de qual série (1, 2 ou 3)? ");
			serie = input.nextInt();
			
			if (serie != 1 && serie != 2 && serie != 3) {
				System.out.println("Série inválida! Selecione 1º Ano, 2º Ano ou 3º Ano");
			}
		} while (serie != 1 && serie != 2 && serie != 3);
		
		System.out.println();
		
		List<Disciplina> disciplinas = disciplinaDAO.listarPorSerie(conexao, serie);
		
		if(disciplinas.isEmpty()){
			System.out.println("Esta série está sem disciplinas cadastradas!");
		} else {
			for (Disciplina disciplina:disciplinas) {
				System.out.println("[" + disciplina.getCodDisciplina() + "] " + disciplina.getSigla() + " | Professor: " + disciplina.getProfResponsavel().getNome() + " [" + disciplina.getProfResponsavel().getCodUsuario() + "] ");
			}
		}
		
		System.out.println();
		
		char resposta = this.confirmar("Deseja mudar o professor de alguma disciplina (S ou N)? ");
		
		if (resposta == 'S') {
			this.editaProfDisciplina();
		}
		
		System.out.println();
	}
	
	public void editaProfDisciplina() throws SQLException {
		System.out.print("Digite o código da disciplina: ");
		int codDisciplina = input.nextInt();
		
		System.out.print("Digite o código do novo professor dessa disciplina: ");
		int codProfessor = input.nextInt();
		
		String nomeDisciplina = disciplinaDAO.procuraNomeDisciplina(conexao, codDisciplina);
		String nomeProfessor = professorDAO.procuraNomeProfessor(conexao, codProfessor);
		
		if((nomeDisciplina != null) && (nomeProfessor != null)) {
			System.out.println("\nDisciplina: " + nomeDisciplina + "\nNovo Professor: " + nomeProfessor);
			
			char resposta = this.confirmar("Deseja confirmar a alteração (S ou N)? ");
			
			if(resposta == 'S') {
				this.disciplinaDAO.updateCodProfessor(conexao, codDisciplina, codProfessor);
				
				System.out.println("\nQuadro de professores editado com sucesso!");
			}
		}
	}
	
	public void descadastrarProfessor() throws SQLException {
		System.out.println("Atenção! O cancelamento de cadastro não poderá ser desfeito!");
		System.out.print("Digite o código de cadastro do Professor: ");
		int cod = input.nextInt();
		
		String nomeProfessor = professorDAO.procuraNomeProfessor(conexao, cod);

		if (nomeProfessor == null)  {
			System.out.println("\nNenhum professor foi encontrado com esse código de cadastro!");
		} else {
			System.out.println("\nNome do Professor: " + nomeProfessor);
			
			char resposta = this.confirmar("Tem certeza que deseja cancelar este cadastro (S ou N)? ");
			
			if (resposta == 'S') {
				professorDAO.apagar(conexao, cod);
				
				System.out.println("\nCancelamento de cadastro efetuado com sucesso!");
			}
		}
		
		System.out.println();
	}
	
	public void visualizarTurma() throws SQLException {
		System.out.println("Digite as informações da turma que deseja consultar abaixo");
		
		int serie;
		
		do {
			System.out.print("Série (1, 2 ou 3): ");
			serie = input.nextInt();
			
			if (serie != 1 && serie != 2 && serie != 3){
				System.out.println("Série inválida! Selecione 1º Ano, 2º Ano ou 3º Ano");
			}
		} while (serie != 1 && serie != 2 && serie != 3);
		
		char turno;
		
		do {
			System.out.print("Turno (M ou V): ");
			turno = input.next().charAt(0);
			
			if (turno != 'M' && turno != 'V'){
				System.out.println("Turno inválido! Selecione Matutino ou Vespertino");
			}
		} while (turno != 'M' && turno != 'V');
		
		int codTurma = turmaDAO.procuraCodTurma(conexao, serie, turno);
		Turma turma = turmaDAO.procuraTurma(conexao, codTurma);
		
		System.out.println("\nCódigo: " + turma.getCodTurma() + " | Série: " + turma.getSerie() + "º Ano | Turno: " + turma.getTurno() + " | Ano: " + turma.getAno());
		
		List<Aluno> alunos =  turma.getAlunos();
		
		if(alunos.isEmpty()){
			System.out.println("No momento, esta turma não tem nenhum aluno matriculado!");
		} else {
			for (Aluno aluno:alunos) {
				System.out.println("[" + aluno.getCodUsuario() + "] Nome: " + aluno.getNome());
			}
		}
		
		System.out.println();
	}
	
	public void visualizarBoletim() throws SQLException {
		System.out.print("Digite o código de matrícula do Aluno: ");
		int cod = input.nextInt();
		
		Aluno aluno = alunoDAO.procuraAluno(conexao, cod);

		System.out.println();
		
		if (aluno == null)  {
			System.out.println("Nenhum aluno foi encontrado com esse código de matrícula!");
		} else {
			int serieDoAluno = turmaDAO.procuraTurma(conexao, aluno.getCodTurmaAtual()).getSerie();
			
			List<Disciplina> disciplinas = disciplinaDAO.listarPorSerie(conexao, serieDoAluno);
			List<Nota> notas = notaDAO.procuraListNotasAluno(conexao, aluno.getCodUsuario());
			
			System.out.println("[" + aluno.getCodUsuario() + "] Aluno: " + aluno.getNome() + " | " + serieDoAluno + "º Ano");
			
			if(disciplinas.isEmpty()) {
				System.out.println("Este aluno não está matriculado em nenhuma disciplina!");
			} else {
				for(Disciplina disciplina:disciplinas) {
					double notaB1=0, notaB2=0, notaB3=0, notaB4=0;
					
					for(Nota nota:notas){
						if(nota.getMatricAluno() == aluno.getCodUsuario() && nota.getCodDisciplina() == disciplina.getCodDisciplina()) {
							if(nota.getBimestre() == 1) {
								notaB1 = nota.getNota();
							} else if(nota.getBimestre() == 2) {
								notaB2 = nota.getNota();
							} else if(nota.getBimestre() == 3) {
								notaB3 = nota.getNota();
							} else if(nota.getBimestre() == 4) {
								notaB4 = nota.getNota();
							}
						}
					}
					
					double media = (notaB1 + notaB2 + notaB3 + notaB4)/4;
					String mediaF = formataNota(media);
					
					String notaB1F = formataNota(notaB1);
					String notaB2F = formataNota(notaB2);
					String notaB3F = formataNota(notaB3);
					String notaB4F = formataNota(notaB4);
					
					System.out.println("Disciplina: " + disciplina.getSigla() + " (" + disciplina.getNome() + ") | N1: " + notaB1F + " | N2: " + notaB2F + " | N3: " + notaB3F + " | N4: " + notaB4F + " | Média: " + mediaF);
				}
				
				System.out.println();
			}
		}
	}
}
