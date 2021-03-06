package teste.sei.bo;

import org.junit.Test;

import junit.framework.TestCase;
import proo.sei.bo.ProfessorBO;

public class ProfessorBOTest extends TestCase {
	ProfessorBO professorBO = new ProfessorBO();
	
	@Test
	public void testeNaoDeveRetornarNenhumCaractereDiferenteDeSOuN() {
		char resposta = professorBO.confirmar("S ou N? ");
		
		if ((resposta != 'S') && (resposta != 'N')) {
			assertFalse(true);
		} else {
			assertFalse(false);
		}
	}
	
	@Test
	public void testeNaoValidaRespostaSeDiferenteDeSOuN() {
		assertFalse(professorBO.validaRespostaConfirmar('0'));
		assertFalse(professorBO.validaRespostaConfirmar(' '));
	}
	
	@Test
	public void testeValidarespostaSeIgualASOuN() {
		assertTrue(professorBO.validaRespostaConfirmar('S'));
		assertTrue(professorBO.validaRespostaConfirmar('n'));
	}
	
	@Test
	public void testeNaoValidaTurnoSeDiferenteDeMOuV() {
		assertFalse(professorBO.validaTurno('0'));
		assertFalse(professorBO.validaTurno(' '));
	}
	
	@Test
	public void testeValidaTurnoSeIgualAMOuV() {
		assertTrue(professorBO.validaTurno('V'));
		assertTrue(professorBO.validaTurno('m'));
	}
	
	@Test
	public void testeNaoValidaBimestreSeDiferenteDe1ou2ou3ou4() {
		assertFalse(professorBO.validaBimestre(0));
	}
	
	@Test
	public void testeValidaBimestreSeIgualA1ou2ou3ou4() {
		assertTrue(professorBO.validaBimestre(1));
		assertTrue(professorBO.validaBimestre(2));
		assertTrue(professorBO.validaBimestre(3));
		assertTrue(professorBO.validaBimestre(4));
	}
}
