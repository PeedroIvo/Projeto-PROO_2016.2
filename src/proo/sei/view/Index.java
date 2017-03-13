package proo.sei.view;

import java.sql.SQLException;

public class Index {

	public static void main(String[] args) throws SQLException { 
		SessaoView sessaoView = new SessaoView();
		
		boolean statusSessao;
		
		do {
			statusSessao = sessaoView.iniciarSessao();
			
			if (statusSessao) {
				sessaoView.getSessaoBO().getUsuarioBOAtual().menu(sessaoView.getSessaoBO().getConexao());
			}
		} while (statusSessao);
	}
}

