package teste.sei.bo;

import org.junit.Test;

import junit.framework.TestCase;

import proo.sei.bo.AdministradorBO;

public class AdministradorBOTest extends TestCase {
	AdministradorBO adminBO = new AdministradorBO();
	
	@Test
	public void testeNaoValidaIdadeSeNegativo() {
		assertFalse(adminBO.validaIdade(-1));
	}
	
	@Test
	public void testeNaoValidaIdadeSeMaiorQue100() {
		assertFalse(adminBO.validaIdade(101));
	}
	
	@Test
	public void testeValidaIdadeSePositivoEMenorQue100() {
		assertTrue(adminBO.validaIdade(10));
	}
	
	@Test
	public void testeNaoValidaSexoSeDiferenteDeFOuM() {
		assertFalse(adminBO.validaSexo('0'));
		assertFalse(adminBO.validaSexo(' '));
	}
	
	@Test
	public void testeValidaSexoSeIgualAFOuM() {
		assertTrue(adminBO.validaSexo('F'));
		assertTrue(adminBO.validaSexo('M'));
	}
	
	@Test
	public void testeNaoValidaCPFSeNaoTiver11Numeros() {
		assertFalse(adminBO.validaCPF("0000"));
		assertFalse(adminBO.validaCPF("000000000000000000"));
	}
	
	@Test
	public void testeNaoValidaCPFSeNaoConterApenasNumeros() {
		assertFalse(adminBO.validaCPF("A1A2A3A4A5A"));
	}
	
	@Test
	public void testeNaoValidaCPFSeForNegativo() {
		assertFalse(adminBO.validaCPF("-12312312300"));
	}
	
	@Test
	public void testeValidaCPFSeTiver11NumerosEConterApenasNumerosENaoForNegativo() {
		assertTrue(adminBO.validaCPF("12345678910"));
	}
	
	@Test
	public void testeNaoValidaRGSeNaoContemApenasNumeros() {
		assertFalse(adminBO.validaRG("A1A2A3A4"));
	}
	
	@Test
	public void testeNaoValidaRGSeForNegativo() {
		assertFalse(adminBO.validaRG("-12345678"));
	}
	
	@Test
	public void testeValidaRGSeContemApenasNumerosENaoForNegativo() {
		assertTrue(adminBO.validaRG("12345678"));
	}
	
	@Test
	public void testeNaoValidaEmailSeNaoCorresponderAoLayout() {
		assertFalse(adminBO.validaEmail("admin"));
		assertFalse(adminBO.validaEmail("admin@"));
		assertFalse(adminBO.validaEmail("admin@admin"));
		assertFalse(adminBO.validaEmail("admin@admin."));
	}
	
	@Test
	public void testeValidaEmailSeCorresponderAoLayout() {
		assertTrue(adminBO.validaEmail("admin@admin.com"));
	}
	
	@Test
	public void testeNaoValidaTelefoneSeTiverMaisque11Numeros() {
		assertFalse(adminBO.validaTelefone("000000000000000000"));
	}
	
	@Test
	public void testeNaoValidaTelefoneSeConterLetras() {
		assertFalse(adminBO.validaTelefone("A1A2A3A4A5A"));
	}
	
	@Test
	public void testeValidaTelefoneSeTiverNoMáximo11NumerosENaoConterLetras() {
		assertTrue(adminBO.validaTelefone("8230309999"));
	}
	
	@Test
	public void testeNaoValidaSeTiverMaisCaracteresQueOLimiteDefinido() {
		assertFalse(adminBO.validaTamanhoCampo("testeJava", 5));
	}
	
	@Test
	public void testeValidaSeTiverNoLimiteDefinidoDeCaracteres() {
		assertTrue(adminBO.validaTamanhoCampo("testeJava", 9));
	}
	
	@Test
	public void testeNaoValidaSerieSeDiferenteDe1ou2ou3() {
		assertFalse(adminBO.validaSerie(0));
	}
	
	@Test
	public void testeValidaSerieSeIgualA1ou2ou3() {
		assertTrue(adminBO.validaSerie(1));
		assertTrue(adminBO.validaSerie(2));
		assertTrue(adminBO.validaSerie(3));
	}
	
	@Test
	public void testeNaoValidaTurnoSeDiferenteDeMOuV() {
		assertFalse(adminBO.validaTurno('0'));
		assertFalse(adminBO.validaTurno(' '));
	}
	
	@Test
	public void testeValidaTurnoSeIgualAMOuV() {
		assertTrue(adminBO.validaTurno('V'));
		assertTrue(adminBO.validaTurno('M'));
	}
}
