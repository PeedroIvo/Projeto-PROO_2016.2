package proo.sei.vo;

import java.sql.SQLException;

public class Index {

	public static void main(String[] args) throws SQLException { 
		SessaoVO sessaoVO = new SessaoVO();
		
		boolean statusSessao;
		
		do {
			statusSessao = sessaoVO.iniciarSessao();
			
			if (statusSessao) {
				sessaoVO.getSessaoBO().getUsuarioBOAtual().menu(sessaoVO.getSessaoBO().getConexao());
			}
		} while (statusSessao);
	}
}

