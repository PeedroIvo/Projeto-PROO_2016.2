package proo.sei.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import proo.sei.view.AlunoView;
import proo.sei.vo.AlunoVO;
import proo.sei.vo.DisciplinaVO;
import proo.sei.vo.NotaVO;

public class AlunoBO extends UsuarioBO {
	AlunoView alunoView = new AlunoView();
	
	public AlunoBO() {
		
	}

	public AlunoBO(AlunoVO usuarioAtual) {
		super(usuarioAtual);
	}

	@Override
	public void menu(Connection conexao) throws SQLException {
		this.setConexao(conexao);
		int opcao;
		
		do {
			opcao = alunoView.interfaceMenu(aluno.getNome());

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
		
		List<DisciplinaVO> disciplinas = disciplinaDAO.listarPorSerie(conexao, serieDoAluno);
		List<NotaVO> notas = notaDAO.procuraListNotasAluno(conexao, aluno.getCodUsuario());
		
		System.out.println("[" + aluno.getCodUsuario() + "] Aluno: " + aluno.getNome() + " | " + serieDoAluno + "º Ano");
		
		if(disciplinas.isEmpty()) {
			System.out.println("Você não está matriculado em nenhuma disciplina!");
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
		}
		
		System.out.println();
	}
}
