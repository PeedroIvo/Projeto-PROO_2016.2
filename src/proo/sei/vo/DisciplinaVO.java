package proo.sei.vo;

public class DisciplinaVO {
	private int codDisciplina;
	private String nome;
	private String sigla;
	private int serie;
	private ProfessorVO profResponsavel;

	public int getCodDisciplina() {
		return codDisciplina;
	}

	public void setCodDisciplina(int codDisciplina) {
		this.codDisciplina = codDisciplina;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public ProfessorVO getProfResponsavel() {
		return profResponsavel;
	}

	public void setProfResponsavel(ProfessorVO profResponsavel) {
		this.profResponsavel = profResponsavel;
	}

	public int getSerie() {
		return serie;
	}

	public void setSerie(int serie) {
		this.serie = serie;
	}
}
