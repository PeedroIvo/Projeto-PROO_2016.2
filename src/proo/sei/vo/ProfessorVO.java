package proo.sei.vo;

public class ProfessorVO extends UsuarioVO {
	private String dataAdmissao;
	private DadosPessoaisVO dadosPessoais = new DadosPessoaisVO();
	
	public String getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(String dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public DadosPessoaisVO getDadosPessoais() {
		return dadosPessoais;
	}

	public void setDadosPessoais(DadosPessoaisVO dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}
}
