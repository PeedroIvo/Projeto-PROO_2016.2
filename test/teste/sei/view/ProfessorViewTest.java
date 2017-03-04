package teste.sei.view;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import proo.sei.view.ProfessorView;

public class ProfessorViewTest {
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
