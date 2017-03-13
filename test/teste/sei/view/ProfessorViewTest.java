package teste.sei.view;

import org.junit.Test;

import junit.framework.TestCase;
import proo.sei.view.ProfessorView;

public class ProfessorViewTest extends TestCase {
	ProfessorView professor = new ProfessorView();
	
	@Test
	public void testeNaoDeveRetornarUmNumeroMenorQue1OuMaiorQue9() {
		int opcao = professor.interfaceMenu("Teste");
		
		if ((opcao < 1) || (opcao > 5)) {
			assertFalse(true);
		} else {
			assertFalse(false);
		}
	}
}
