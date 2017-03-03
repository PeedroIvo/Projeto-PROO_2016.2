package proo.sei.vo;

import java.util.ArrayList;
import java.util.List;

import proo.sei.vo.AlunoVO;

public class TurmaVO {
	private int codTurma;
	private int serie;
	private char turno;
	private int ano;
	private String sala;
	private List<DisciplinaVO> disciplinas = new ArrayList<>();
	private List<AlunoVO> alunos = new ArrayList<>();

	public int getCodTurma() {
		return codTurma;
	}

	public void setCodTurma(int codTurma) {
		this.codTurma = codTurma;
	}

	public int getSerie() {
		return serie;
	}

	public void setSerie(int serie) {
		this.serie = serie;
	}

	public char getTurno() {
		return turno;
	}

	public void setTurno(char turno) {
		this.turno = turno;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}
	
	public void addAluno(AlunoVO aluno) {
		alunos.add(aluno);
	}

	public List<AlunoVO> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<AlunoVO> alunos) {
		this.alunos = alunos;
	}

	public void addDisciplina(DisciplinaVO disciplina) {
		disciplinas.add(disciplina);
	}

	public List<DisciplinaVO> getDisciplinas() {
		return disciplinas;
	}
	
	public void setDisciplinas(List<DisciplinaVO> disciplinas) {
		this.disciplinas = disciplinas;
	}
}
