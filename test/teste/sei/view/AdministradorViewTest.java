package teste.sei.view;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import proo.sei.view.AdministradorView;

public class AdministradorViewTest {
	AdministradorView admin = new AdministradorView();
	
	@Test
	public void testeNaoDeveRetornarUmNumeroMenorQue1OuMaiorQue9() {
		int opcao = admin.interfaceMenu("Teste");
		
		if ((opcao < 1) || (opcao > 9)) {
			assertFalse(true);
		} else {
			assertFalse(false);
		}
	}
}
