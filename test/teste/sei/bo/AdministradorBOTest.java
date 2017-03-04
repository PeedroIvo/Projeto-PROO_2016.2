package teste.sei.bo;

import org.junit.Test;
import static org.junit.Assert.*;

import proo.sei.bo.AdministradorBO;

public class AdministradorBOTest {
	AdministradorBO adminBO = new AdministradorBO();
	
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
	public void testeNaoValidaCPFSeConterLetras() {
		assertFalse(adminBO.validaCPF("A1A2A3A4A5A"));
	}
	
	@Test
	public void testeValidaCPFSeTiver11NumerosENaoConterLetras() {
		assertTrue(adminBO.validaCPF("12345678910"));
	}
	
	@Test
	public void testeNaoValidaRGSeNaoTiver8Numeros() {
		assertFalse(adminBO.validaRG("0000"));
		assertFalse(adminBO.validaRG("000000000000"));
	}
	
	@Test
	public void testeNaoValidaRGSeConterLetras() {
		assertFalse(adminBO.validaRG("A1A2A3A4"));
	}
	
	@Test
	public void testeValidaRGSeTiver8NumerosENaoConterLetras() {
		assertTrue(adminBO.validaRG("12345678"));
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
