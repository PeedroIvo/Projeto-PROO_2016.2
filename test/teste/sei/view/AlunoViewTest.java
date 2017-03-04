package teste.sei.view;

import static org.junit.Assert.*;

import org.junit.Test;

import proo.sei.view.AlunoView;

public class AlunoViewTest {
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
