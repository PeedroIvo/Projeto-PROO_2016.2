package proo.sei.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import proo.sei.exceptions.UsuarioBOException;
import proo.sei.view.AdministradorView;
import proo.sei.vo.AdministradorVO;
import proo.sei.vo.AlunoVO;
import proo.sei.vo.DisciplinaVO;
import proo.sei.vo.NotaVO;
import proo.sei.vo.ProfessorVO;
import proo.sei.vo.TurmaVO;

public class AdministradorBO extends UsuarioBO {	
	public AdministradorBO() {
	
	}
	
	public AdministradorBO(AdministradorVO usuarioAtual) {
		super(usuarioAtual);
	}

	@Override
	public void menu(Connection conexao) {
		AdministradorView adminView = new AdministradorView();
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
		AdministradorView adminView = new AdministradorView();
		
		AlunoVO novoAluno = new AlunoVO();
		
		novoAluno.setTipoUsuario('a');
		novoAluno = adminView.interfaceMatricularAluno(novoAluno);
		
		int serie = adminView.interfaceSerie();
		char turno = adminView.interfaceTurno();
		
		try {
			novoAluno.setCodTurmaAtual(turmaDAO.procuraCodTurma(conexao, serie, turno));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();
		
		novoAluno.setLogin(adminView.interfaceLogin(conexao));
		
		try {
			alunoDAO.criar(conexao, novoAluno);
			System.out.println("\nAluno matriculado com sucesso!\nLogin: " + novoAluno.getLogin() + "\nSenha Padrão: 123456\n");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void desmatricularAluno() {
		AdministradorView adminView = new AdministradorView();
		
		System.out.println("Atenção! O cancelamento de matrícula não poderá ser desfeito!");
		
		int cod = adminView.interfaceCod("Digite o código de matrícula do Aluno");
		
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
		AdministradorView adminView = new AdministradorView();
		ProfessorVO novoProfessor = new ProfessorVO();
		
		Calendar cal = Calendar.getInstance(); 
		novoProfessor.setDataAdmissao(cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH));
		
		novoProfessor.setTipoUsuario('p');
		novoProfessor = adminView.interfaceMatricularProfessor(novoProfessor);
		
		novoProfessor.setLogin(adminView.interfaceLogin(conexao));
		
		try {
			professorDAO.criar(conexao, novoProfessor);
			System.out.println("\nProfessor matriculado com sucesso!\nLogin: " + novoProfessor.getLogin() + "\nSenha Padrão: 123456\n");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void quadroProfessores() {
		AdministradorView adminView = new AdministradorView();
		
		int serie = adminView.interfaceSerie();
		
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
		AdministradorView adminView = new AdministradorView();
		
		int codDisciplina = adminView.interfaceCod("Digite o código da disciplina");
		int codProfessor = adminView.interfaceCod("Digite o código do novo professor dessa disciplina");
		
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
		AdministradorView adminView = new AdministradorView();
		
		System.out.println("Atenção! O cancelamento de cadastro não poderá ser desfeito!");
		
		int cod = adminView.interfaceCod("Digite o código de cadastro do Professor: ");
		
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
		AdministradorView adminView = new AdministradorView();
		
		System.out.println("Digite as informações da turma que deseja consultar abaixo");
		
		int serie = adminView.interfaceSerie();
		char turno = adminView.interfaceTurno();
		
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
		AdministradorView adminView = new AdministradorView();
		
		int cod = adminView.interfaceCod("Digite o código de matrícula do Aluno");
		
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
}
