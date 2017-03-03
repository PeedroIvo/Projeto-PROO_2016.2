package proo.sei.vo;

public class AlunoVO extends UsuarioVO {
	private int idade;
	private char sexo;
	private int codTurmaAtual;
	private DadosPessoaisVO dadosPessoais = new DadosPessoaisVO();
	
	public DadosPessoaisVO getDadosPessoais() {
		return dadosPessoais;
	}

	public void setDadosPessoais(DadosPessoaisVO dadosPessoais) {
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
