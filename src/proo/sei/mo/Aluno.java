package proo.sei.mo;

public class Aluno extends Usuario {
	private int idade;
	private char sexo;
	private int codTurmaAtual;
	private DadosPessoais dadosPessoais = new DadosPessoais();
	
	public DadosPessoais getDadosPessoais() {
		return dadosPessoais;
	}

	public void setDadosPessoais(DadosPessoais dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public int getCodTurmaAtual() {
		return codTurmaAtual;
	}

	public void setCodTurmaAtual(int codTurmaAtual) {
		this.codTurmaAtual = codTurmaAtual;
	}
}
