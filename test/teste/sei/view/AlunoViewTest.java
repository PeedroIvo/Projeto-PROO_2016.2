package teste.sei.view;

import org.junit.Test;

import junit.framework.TestCase;
import proo.sei.view.AlunoView;

public class AlunoViewTest extends TestCase {
	AlunoView aluno = new AlunoView();
	
	@Test
	public void testeNaoDeveRetornarUmNumeroMenorQue1OuMaiorQue5() {
		int opcao = aluno.interfaceMenu("Teste");
		
		if ((opcao < 1) || (opcao > 4)) {
			assertFalse(true);
		} else {
			assertFalse(false);
		}
	}
}
