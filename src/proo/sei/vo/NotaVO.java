package proo.sei.vo;

public class NotaVO {
	private int matricAluno;
	private int codDisciplina;
	private int ano;
	private int bimestre;
	private double nota;

	public int getMatricAluno() {
		return matricAluno;
	}

	public void setMatricAluno(int matricAluno) {
		this.matricAluno = matricAluno;
	}

	public int getCodDisciplina() {
		return codDisciplina;
	}

	public void setCodDisciplina(int codDisciplina) {
		this.codDisciplina = codDisciplina;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getBimestre() {
		return bimestre;
	}

	public void setBimestre(int bimestre) {
		this.bimestre = bimestre;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}
}
