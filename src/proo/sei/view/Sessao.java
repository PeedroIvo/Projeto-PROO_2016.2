package proo.sei.view;

import java.util.Scanner;

import proo.sei.bo.SessaoBO;

public class Sessao {
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
		
		int opcao;
		
		do {
			System.out.print("Digite sua opção: ");
			opcao = input.nextInt();

			if (opcao <= 0 || opcao > 2) {
				System.out.println("Opção inválida! Tente novamente!");
			}
		} while (opcao <= 0 || opcao > 2);
		
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
