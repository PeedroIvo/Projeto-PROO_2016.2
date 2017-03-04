package proo.sei.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;

import proo.sei.view.AdministradorView;
import proo.sei.vo.AdministradorVO;
import proo.sei.vo.AlunoVO;
import proo.sei.vo.DisciplinaVO;
import proo.sei.vo.NotaVO;
import proo.sei.vo.ProfessorVO;
import proo.sei.vo.TurmaVO;

public class AdministradorBO extends UsuarioBO {
	AdministradorView adminView = new AdministradorView();
	
	public AdministradorBO() {
	
	}
	
	public AdministradorBO(AdministradorVO usuarioAtual) {
		super(usuarioAtual);
	}

	@Override
	public void menu(Connection conexao) {
		this.setConexao(conexao);
		int opcao;
		
		do {
			opcao = adminView.interfaceMenu(admin.getNome());

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
	
	public void matricularAluno() {
		Boolean erro;
		
		AlunoVO novoAluno = new AlunoVO();	
		
		novoAluno.setTipoUsuario('a');
		
		System.out.println("Digite os dados do novo aluno abaixo:");
		
		input.nextLine();
		
		System.out.print("Nome: ");
		novoAluno.setNome(input.nextLine());
		
		do {
			erro = false;
			System.out.print("Idade: ");			
			
			try {
				novoAluno.setIdade(input.nextInt());
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);

		do {
			System.out.print("Sexo (F ou M): ");
			novoAluno.setSexo(input.next().charAt(0));
		} while (!validaSexo(novoAluno.getSexo()));
		
		do {
			System.out.print("CPF: ");
			novoAluno.getDadosPessoais().setCpf(input.next());	
		} while (!validaCPF(novoAluno.getDadosPessoais().getCpf()));
		
		do {
			System.out.print("RG: ");
			novoAluno.getDadosPessoais().setRg(input.next());
		} while (!validaRG(novoAluno.getDadosPessoais().getRg()));
		
		System.out.print("Email: ");
		novoAluno.getDadosPessoais().setEmail(input.next().toLowerCase());
	
		input.nextLine();
		
		do {
			System.out.print("Celular: ");
			novoAluno.getDadosPessoais().setCelular(input.nextLine());
		} while (!validaTelefone(novoAluno.getDadosPessoais().getCelular()));
		
		do {
			System.out.print("Telefone: ");
			novoAluno.getDadosPessoais().setTelefone(input.nextLine());
		} while (!validaTelefone(novoAluno.getDadosPessoais().getTelefone()));
		
		System.out.println("\nAgora digite os dados do endereço do novo aluno:" );
		
		do {
			System.out.print("CEP: ");
			novoAluno.getDadosPessoais().getEndereco().setCep(input.next());
		} while (!validaCEP(novoAluno.getDadosPessoais().getEndereco().getCep()));
		
		input.nextLine();
		System.out.print("Rua: ");
		novoAluno.getDadosPessoais().getEndereco().setRua(input.nextLine());
		
		do {
			System.out.print("Número: ");
			novoAluno.getDadosPessoais().getEndereco().setnCasa(input.nextLine());
		} while (!validaTamanhoCampo(novoAluno.getDadosPessoais().getEndereco().getnCasa(), 10));
		
		System.out.print("Bairro: ");
		novoAluno.getDadosPessoais().getEndereco().setBairro(input.nextLine());
		
		System.out.print("Complemento: ");
		novoAluno.getDadosPessoais().getEndereco().setComplemento(input.nextLine());
		
		System.out.print("Cidade: ");
		novoAluno.getDadosPessoais().getEndereco().setCidade(input.nextLine());
		
		System.out.print("Estado: ");
		novoAluno.getDadosPessoais().getEndereco().setEstado(input.nextLine());
		
		System.out.println();
		
		int serie = 0;
		
		do {
			erro = false;
			System.out.print("Série (1, 2 ou 3): ");			
			
			try {
				serie = input.nextInt();
				erro = !validaSerie(serie);
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		char turno;
		
		do {
			System.out.print("Turno (M ou V): ");
			turno = input.next().charAt(0);
		} while (!validaTurno(turno));
		
		try {
			novoAluno.setCodTurmaAtual(turmaDAO.procuraCodTurma(conexao, serie, turno));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();
		
		do {
			System.out.print("Novo login do aluno no sistema: ");
			novoAluno.setLogin(input.next().toLowerCase());			
		} while (!validaLogin(conexao, novoAluno.getLogin()));
		
		try {
			alunoDAO.criar(conexao, novoAluno);
			System.out.println("\nAluno matriculado com sucesso!\nLogin: " + novoAluno.getLogin() + "\nSenha Padrão: 123456\n");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void desmatricularAluno() {		
		System.out.println("Atenção! O cancelamento de matrícula não poderá ser desfeito!");
		
		Boolean erro;
		int cod = 0;
		
		do {
			erro = false;
			System.out.print("Digite o código de matrícula do Aluno: ");			
			
			try {
				cod = input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		try {
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
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();
	}
	
	public void cadastrarProfessor() {
		ProfessorVO novoProfessor = new ProfessorVO();
		
		Calendar cal = Calendar.getInstance(); 
		novoProfessor.setDataAdmissao(cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH));
		
		novoProfessor.setTipoUsuario('p');
		
		System.out.println("Digite os dados do novo professor abaixo:");
		
		input.nextLine();
		System.out.print("Nome: ");
		novoProfessor.setNome(input.nextLine());
		
		do {
			System.out.print("CPF: ");
			novoProfessor.getDadosPessoais().setCpf(input.next());	
		} while (!validaCPF(novoProfessor.getDadosPessoais().getCpf()));
		
		do {
			System.out.print("RG: ");
			novoProfessor.getDadosPessoais().setRg(input.next());
		} while (!validaRG(novoProfessor.getDadosPessoais().getRg()));
		
		System.out.print("Email: ");
		novoProfessor.getDadosPessoais().setEmail(input.next().toLowerCase());
	
		input.nextLine();
		
		do {
			System.out.print("Celular: ");
			novoProfessor.getDadosPessoais().setCelular(input.nextLine());
		} while (!validaTelefone(novoProfessor.getDadosPessoais().getCelular()));
		
		do {
			System.out.print("Telefone: ");
			novoProfessor.getDadosPessoais().setTelefone(input.nextLine());
		} while (!validaTelefone(novoProfessor.getDadosPessoais().getTelefone()));
		
		System.out.println("\nAgora digite os dados do endereço do novo professor:" );
		
		do {
			System.out.print("CEP: ");
			novoProfessor.getDadosPessoais().getEndereco().setCep(input.next());
		} while (!validaCEP(novoProfessor.getDadosPessoais().getEndereco().getCep()));
		
		input.nextLine();
		System.out.print("Rua: ");
		novoProfessor.getDadosPessoais().getEndereco().setRua(input.nextLine());
		
		do {
			System.out.print("Número: ");
			novoProfessor.getDadosPessoais().getEndereco().setnCasa(input.nextLine());
		} while (!validaTamanhoCampo(novoProfessor.getDadosPessoais().getEndereco().getnCasa(), 10));
		
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
		} while (!validaLogin(conexao, novoProfessor.getLogin()));
		
		try {
			professorDAO.criar(conexao, novoProfessor);
			System.out.println("\nProfessor matriculado com sucesso!\nLogin: " + novoProfessor.getLogin() + "\nSenha Padrão: 123456\n");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void quadroProfessores() {
		Boolean erro;
		int serie = 0;
		
		do {
			erro = false;
			System.out.print("Deseja consultar as disciplinas de qual série (1, 2 ou 3)? ");		
			
			try {
				serie = input.nextInt();
				erro = !validaSerie(serie);
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		System.out.println();
		
		try {
			List<DisciplinaVO> disciplinas = disciplinaDAO.listarPorSerie(conexao, serie);
			
			
			if(disciplinas.isEmpty()){
				System.out.println("Esta série está sem disciplinas cadastradas!");
			} else {
				for (DisciplinaVO disciplina:disciplinas) {
					System.out.println("[" + disciplina.getCodDisciplina() + "] " + disciplina.getSigla() + " | Professor: " + disciplina.getProfResponsavel().getNome() + " [" + disciplina.getProfResponsavel().getCodUsuario() + "] ");
				}
			}
			
			System.out.println();
			
			char resposta = this.confirmar("Deseja mudar o professor de alguma disciplina (S ou N)? ");
			
			if (resposta == 'S') {
				this.editaProfDisciplina();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();
	}
	
	public void editaProfDisciplina() {
		Boolean erro;
		
		int codDisciplina = 0;
		do {
			erro = false;
			System.out.print("Digite o código da disciplina: ");			
			
			try {
				codDisciplina = input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		
		int codProfessor = 0;
		do {
			erro = false;
			System.out.print("Digite o código do novo professor dessa disciplina: ");			
			
			try {
				codProfessor = input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		try {
			String nomeDisciplina = disciplinaDAO.procuraNomeDisciplina(conexao, codDisciplina);
			String nomeProfessor = professorDAO.procuraNomeProfessor(conexao, codProfessor);
			
			if((nomeDisciplina != null) && (nomeProfessor != null)) {
				System.out.println("\nDisciplina: " + nomeDisciplina + "\nNovo Professor: " + nomeProfessor);
				
				char resposta = this.confirmar("Deseja confirmar a alteração (S ou N)? ");
				
				if(resposta == 'S') {
					this.disciplinaDAO.updateCodProfessor(conexao, codDisciplina, codProfessor);
					
					System.out.println("\nQuadro de professores editado com sucesso!");
				}
			} else if (nomeDisciplina == null) {
				System.out.println("\nNenhuma disciplina foi encontrada com este código!");
			} else if (nomeProfessor == null) {
				System.out.println("\nNenhum professor foi encontrado com este código!");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void descadastrarProfessor() {
		System.out.println("Atenção! O cancelamento de cadastro não poderá ser desfeito!");
		
		Boolean erro;
		
		int cod = 0;
		do {
			erro = false;
			System.out.print("Digite o código de cadastro do Professor: ");			
			
			try {
				cod = input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		try {
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
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();
	}
	
	public void visualizarTurma() {
		System.out.println("Digite as informações da turma que deseja consultar abaixo");
		
		Boolean erro;
		
		int serie = 0;
		do {
			erro = false;
			System.out.print("Série (1, 2 ou 3): ");			
			
			try {
				serie = input.nextInt();
				erro = !validaSerie(serie);
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		char turno;
		do {
			System.out.print("Turno (M ou V): ");
			turno = input.next().charAt(0);
		} while (!validaTurno(turno));
		
		try {		
			int codTurma = turmaDAO.procuraCodTurma(conexao, serie, turno);
			TurmaVO turma = turmaDAO.procuraTurma(conexao, codTurma);
		
			System.out.println("\nCódigo: " + turma.getCodTurma() + " | Série: " + turma.getSerie() + "º Ano | Turno: " + turma.getTurno() + " | Ano: " + turma.getAno());
			
			List<AlunoVO> alunos =  turma.getAlunos();
			
			if(alunos.isEmpty()){
				System.out.println("No momento, esta turma não tem nenhum aluno matriculado!");
			} else {
				for (AlunoVO aluno:alunos) {
					System.out.println("[" + aluno.getCodUsuario() + "] Nome: " + aluno.getNome());
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();
	}
	
	public void visualizarBoletim() {
		Boolean erro;
		
		int cod = 0;
		do {
			erro = false;
			System.out.print("Digite o código de matrícula do Aluno: ");			
			
			try {
				cod = input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		try {		
			AlunoVO aluno = alunoDAO.procuraAluno(conexao, cod);
	
			System.out.println();
			
			if (aluno == null)  {
				System.out.println("Nenhum aluno foi encontrado com esse código de matrícula!");
			} else {
				int serieDoAluno = turmaDAO.procuraTurma(conexao, aluno.getCodTurmaAtual()).getSerie();
				
				List<DisciplinaVO> disciplinas = disciplinaDAO.listarPorSerie(conexao, serieDoAluno);
				List<NotaVO> notas = notaDAO.procuraListNotasAluno(conexao, aluno.getCodUsuario());
				
				System.out.println("[" + aluno.getCodUsuario() + "] Aluno: " + aluno.getNome() + " | " + serieDoAluno + "º Ano");
				
				if(disciplinas.isEmpty()) {
					System.out.println("Este aluno não está matriculado em nenhuma disciplina!");
				} else {
					for(DisciplinaVO disciplina:disciplinas) {
						double notaB1=0, notaB2=0, notaB3=0, notaB4=0;
						
						for(NotaVO nota:notas){
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
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
