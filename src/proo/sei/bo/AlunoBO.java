package proo.sei.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import proo.sei.mo.Aluno;
import proo.sei.mo.Disciplina;
import proo.sei.mo.Nota;

public class AlunoBO extends UsuarioBO {
	public AlunoBO() {
		
	}

	public AlunoBO(Aluno usuarioAtual) {
		super(usuarioAtual);
	}

	@Override
	public void menu(Connection conexao) throws SQLException {
		this.setConexao(conexao);
		int opcao;
		
		do {
			System.out.println("-------------------------------------");
			System.out.println("Olá, " + aluno.getNome() + "! O que deseja fazer?");
			System.out.println("[1] Visualizar boletim de notas");
			System.out.println("[2] Visualizar dados pessoais");
			System.out.println("[3] Mudar a senha");
			System.out.println("[4] Logout");

			do {
				System.out.print("Digite sua opção: ");
				opcao = input.nextInt();

				if (opcao <= 0 || opcao > 4) {
					System.out.println("Opção inválida! Tente novamente!");
				}
			} while (opcao <= 0 || opcao > 4);
			
			System.out.println();

			if (opcao == 1) {
				this.visualizarBoletim();
			} else if (opcao == 2) {
				this.visualizarDadosPessoais(aluno.getCodUsuario(), aluno.getNome());
			} else if (opcao == 3) {
				this.mudarSenha(aluno.getLogin());
			}
		} while (opcao != 4);
	}
	
	public void visualizarBoletim () throws SQLException {
		aluno.setCodTurmaAtual(alunoDAO.procuraAluno(conexao, aluno.getCodUsuario()).getCodTurmaAtual());
		int serieDoAluno = turmaDAO.procuraTurma(conexao, aluno.getCodTurmaAtual()).getSerie();
		
		List<Disciplina> disciplinas = disciplinaDAO.listarPorSerie(conexao, serieDoAluno);
		List<Nota> notas = notaDAO.procuraListNotasAluno(conexao, aluno.getCodUsuario());
		
		System.out.println("[" + aluno.getCodUsuario() + "] Aluno: " + aluno.getNome() + " | " + serieDoAluno + "º Ano");
		
		if(disciplinas.isEmpty()) {
			System.out.println("Você não está matriculado em nenhuma disciplina!");
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
		}
		
		System.out.println();
	}
}
