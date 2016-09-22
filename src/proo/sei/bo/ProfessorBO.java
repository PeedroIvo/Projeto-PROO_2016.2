package proo.sei.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import proo.sei.mo.Aluno;
import proo.sei.mo.Disciplina;
import proo.sei.mo.Nota;
import proo.sei.mo.Professor;

public class ProfessorBO extends UsuarioBO {
	public ProfessorBO() {

	}
	
	public ProfessorBO(Professor usuarioAtual) {
		super(usuarioAtual);
	}

	@Override
	public void menu(Connection conexao) throws SQLException {
		this.setConexao(conexao);
		int opcao;
		
		do {
			System.out.println("-------------------------------------");
			System.out.println("Olá, " + professor.getNome() + "! O que deseja fazer?");
			System.out.println("[1] Visualizar suas disciplinas");
			System.out.println("[2] Adicionar notas");
			System.out.println("[3] Visualizar dados pessoais");
			System.out.println("[4] Mudar a senha");
			System.out.println("[5] Logout");

			do {
				System.out.print("Digite sua opção: ");
				opcao = input.nextInt();

				if (opcao <= 0 || opcao > 5) {
					System.out.println("Opção inválida! Tente novamente!");
				}
			} while (opcao <= 0 || opcao > 5);
			
			System.out.println();

			if (opcao == 1) {
				this.visualizarDisciplinas();
			} else if (opcao == 2) {
				this.addNotas();
			} else if (opcao == 3) {
				this.visualizarDadosPessoais(professor.getCodUsuario(), professor.getNome());
			} else if (opcao == 4) {
				this.mudarSenha(professor.getLogin());
			}
		} while (opcao != 5);
	}
	
	public void visualizarDisciplinas() throws SQLException {
		
		List<Disciplina> disciplinas = disciplinaDAO.listarPorProfessor(conexao, professor.getCodUsuario());
		
		if(disciplinas.isEmpty()){
			System.out.println("No momento, você não é responsável por nenhuma disciplina!");
		} else {
			for (Disciplina disciplina:disciplinas) {
				System.out.println("[" + disciplina.getCodDisciplina() + "] " + disciplina.getSigla() + " (" + disciplina.getNome() + ") | " + disciplina.getSerie() + "º Ano");
			}
		}
		
		System.out.println();
		
		char resposta = this.confirmar("Deseja vizualizar as notas de alguma turma (S ou N)? ");
		
		if (resposta == 'S') {
			this.visualizarNotas();
		}
		
		System.out.println();
	}
	
	public void addNotas() throws SQLException {
		int codDisciplina;
		
		System.out.print("Digite o cógido da disciplina: ");
		codDisciplina = this.input.nextInt();
		
		if(this.verificaDisciplinaProfessor(codDisciplina)) {
			Disciplina disciplina = disciplinaDAO.procuraDisciplina(conexao, codDisciplina);
			
			char turno;
			
			do {
				System.out.print("Digite o turno da turma (M ou V): ");
				turno = input.next().charAt(0);
				
				if (turno != 'M' && turno != 'V'){
					System.out.println("Turno inválido! Selecione Matutino ou Vespertino");
				}
			} while (turno != 'M' && turno != 'V');
			
			int bimestre;
			
			do {
				System.out.print("Digite o bimestre correspondente as notas: (1, 2, 3 ou 4): ");
				bimestre = input.nextInt();
				
				if (bimestre != 1 && bimestre != 2 && bimestre != 3 && bimestre != 4){
					System.out.println("Bimestre inválido! Selecione 1, 2, 3 ou 4");
				}
			} while (bimestre != 1 && bimestre != 2 && bimestre != 3 && bimestre != 4);
			
			int codTurma = turmaDAO.procuraCodTurma(conexao, disciplina.getSerie(), turno);
			
			List<Aluno> alunos = alunoDAO.listarPorTurma(conexao, codTurma);
			
			System.out.println();
			
			if(alunos.isEmpty()){
				System.out.println("No momento, esta turma não tem nenhum aluno matriculado!");
			} else {
				List<Nota> notas = notaDAO.procuraListNota(conexao, codDisciplina, bimestre, codTurma);
				
				if(notas.isEmpty()){
					for(Aluno aluno:alunos){
						double nota;
						
						do {
							System.out.print("[" + aluno.getCodUsuario() + "] Nome do aluno: " + aluno.getNome() + " | Digite a nota: ");
							nota = input.nextDouble();
							
							if (nota < 0 || nota > 10) {
								System.out.println("Nota inválida! A nota deverá ser de 0 à 10");
							}
						} while (nota < 0 || nota > 10);
						
						notaDAO.criar(conexao, aluno.getCodUsuario(), codDisciplina, bimestre, nota);
					}
					
					System.out.println("As notas foram adicionadas com sucesso!");
				} else {
					System.out.println("As notas desta turma ja foram adicionadas!");
				}
			}
		} else {
			System.out.println("Você não é responsável por esta disciplina!");
		}
		
		System.out.println();
	}
	
	public boolean verificaDisciplinaProfessor(int codDisciplina) throws SQLException {
		List<Disciplina> disciplinas = disciplinaDAO.listarPorProfessor(conexao, professor.getCodUsuario());
		
		for (Disciplina disciplina:disciplinas) {
			if(disciplina.getCodDisciplina() == codDisciplina) {
				return true;
			}
		}
		
		return false;
	}
	
	public void visualizarNotas() throws SQLException {
		int codDisciplina;
		
		System.out.print("Digite o cógido da disciplina: ");
		codDisciplina = input.nextInt();
		
		if(this.verificaDisciplinaProfessor(codDisciplina)) {
			Disciplina disciplina = disciplinaDAO.procuraDisciplina(conexao, codDisciplina);
			
			char turno;
			
			do {
				System.out.print("Digite o turno da turma (M ou V): ");
				turno = input.next().charAt(0);
				
				if (turno != 'M' && turno != 'V'){
					System.out.println("Turno inválido! Selecione Matutino ou Vespertino");
				}
			} while (turno != 'M' && turno != 'V');
			
			
			int codTurma = turmaDAO.procuraCodTurma(conexao, disciplina.getSerie(), turno);
			
			List<Aluno> alunos = alunoDAO.listarPorTurma(conexao, codTurma);
			
			System.out.println();
			
			if(alunos.isEmpty()){
				System.out.println("No momento, esta turma não tem nenhum aluno matriculado!");
			} else {
				List<Nota> notasB1 = notaDAO.procuraListNota(conexao, codDisciplina, 1, codTurma);
				List<Nota> notasB2 = notaDAO.procuraListNota(conexao, codDisciplina, 2, codTurma);
				List<Nota> notasB3 = notaDAO.procuraListNota(conexao, codDisciplina, 3, codTurma);
				List<Nota> notasB4 = notaDAO.procuraListNota(conexao, codDisciplina, 4, codTurma);
				
				System.out.println("Disciplina: " + disciplina.getSigla() + " (" + disciplina.getNome() + ") | " + disciplina.getSerie() + "º Ano | Turno: " + turno);
				
				for(Aluno aluno:alunos){     
					double notaB1=0, notaB2=0, notaB3=0, notaB4=0;
					
					for(Nota nota:notasB1){
						if(nota.getMatricAluno() == aluno.getCodUsuario()){
							notaB1 = nota.getNota();
						}
					}
					
					for(Nota nota:notasB2){
						if(nota.getMatricAluno() == aluno.getCodUsuario()){
							notaB2 = nota.getNota();
						}
					}
					
					for(Nota nota:notasB3){
						if(nota.getMatricAluno() == aluno.getCodUsuario()){
							notaB3 = nota.getNota();
						}
					}
					
					for(Nota nota:notasB4){
						if(nota.getMatricAluno() == aluno.getCodUsuario()){
							notaB4 = nota.getNota();
						}
					}
					
					double media = (notaB1 + notaB2 + notaB3 + notaB4)/4;
					String mediaF = formataNota(media);
					
					String notaB1F = formataNota(notaB1);
					String notaB2F = formataNota(notaB2);
					String notaB3F = formataNota(notaB3);
					String notaB4F = formataNota(notaB4);
					
					System.out.println("[" + aluno.getCodUsuario() + "] Nome: " + aluno.getNome() + " | Nota B1: " + notaB1F + " | Nota B2: " + notaB2F + " | Nota B3: " + notaB3F + " | Nota B4: " + notaB4F + " | Média: " + mediaF);
				}
			}
		} else {
			System.out.println("Você não é responsável por esta disciplina!");
		}
	}
}
