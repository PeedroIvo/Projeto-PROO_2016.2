package proo.sei.view;

import java.sql.Connection;

public class Index {

	public static void main(String[] args) { 
		SessaoView sessaoView = new SessaoView();
		
		Connection conexao = sessaoView.getSessaoBO().getConexao();
		
		if (conexao != null) {
			boolean statusSessao;
			
			do {
				statusSessao = sessaoView.iniciarSessao();
				
				if (statusSessao) {
					sessaoView.getSessaoBO().getUsuarioBOAtual().menu(conexao);
				}
			} while (statusSessao);
		}
	}
}

