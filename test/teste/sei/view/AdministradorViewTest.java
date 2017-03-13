package teste.sei.view;

import org.junit.Test;

import junit.framework.TestCase;
import proo.sei.view.AdministradorView;

public class AdministradorViewTest extends TestCase {
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
