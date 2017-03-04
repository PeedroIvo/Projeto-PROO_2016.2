package proo.sei.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import proo.sei.bo.SessaoBO;
import proo.sei.exceptions.ViewException;

public class SessaoView {
	private Scanner input = new Scanner(System.in);
	
	private SessaoBO sessaoBO = new SessaoBO();
	
	public SessaoBO getSessaoBO() {
		return sessaoBO;
	}

	public boolean iniciarSessao() {
		System.out.println("-------------------------------------");
		System.out.println("BEM-VINDO AO SISTEMA ESCOLA INTEGRADA");
		System.out.println("-------------------------------------");
		
		System.out.println("[1] Login");
		System.out.println("[2] Sair");
		
		Boolean erro;
		int opcao = 0;
		
		do {
			erro = false;
			System.out.print("Digite sua opção: ");			
			
			try {
				opcao = this.input.nextInt();
				
				if (opcao <= 0 || opcao > 2) {
					throw new ViewException("Opção inválida! Tente novamente!");
				}
			} catch (InputMismatchException e) {
				System.out.println("Opção inválida! Tente novamente!");
				input.next();
				erro = true;
			} catch (ViewException e) {
				System.out.println(e.getMessage());
				erro = true;
			}
		} while (erro);
		
		System.out.println();
		
		if(opcao == 1) {
			this.login();
			return true;
		} else if(opcao == 2) {
			sessaoBO.fecharConexao();
		}
		
		return false;
	}
	
	public void login() {
		String loginDigitado;
		String senhaDigitada;
		
		do {
			System.out.println("-------------------------------------");
			System.out.print("- Login: ");
			loginDigitado = input.next();
	        System.out.print("- Senha: ");
	        senhaDigitada = input.next();
		} while (!sessaoBO.validarLogin(sessaoBO.getConexao(), loginDigitado.toLowerCase(), senhaDigitada));
		
		System.out.println();
	}
}
