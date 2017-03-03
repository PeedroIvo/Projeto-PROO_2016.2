package proo.sei.view;

import java.util.Scanner;

public class AlunoView {
	
	private Scanner input = new Scanner(System.in);
	
	public int interfaceMenu (String nome) {
		int opcao;
		
		System.out.println("-------------------------------------");
		System.out.println("Olá, " + nome + "! O que deseja fazer?");
		System.out.println("[1] Visualizar boletim de notas");
		System.out.println("[2] Visualizar dados pessoais");
		System.out.println("[3] Mudar a senha");
		System.out.println("[4] Logout");

		do {
			System.out.print("Digite sua opção: ");
			opcao = input.nextInt();

			if (opcao <= 0 || opcao > 4) {
				System.out.println("Opção inválida! Tente novamente!");
			}
		} while (opcao <= 0 || opcao > 4);
		
		System.out.println();
		
		return opcao;
	}
}
