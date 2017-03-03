package proo.sei.view;

import java.util.Scanner;

public class ProfessorView {
	
	private Scanner input = new Scanner(System.in);
	
	public int interfaceMenu (String nome) {
		int opcao;
		
		System.out.println("-------------------------------------");
		System.out.println("Olá, " + nome + "! O que deseja fazer?");
		System.out.println("[1] Visualizar suas disciplinas");
		System.out.println("[2] Adicionar notas");
		System.out.println("[3] Visualizar dados pessoais");
		System.out.println("[4] Mudar a senha");
		System.out.println("[5] Logout");

		do {
			System.out.print("Digite sua opção: ");
			opcao = input.nextInt();

			if (opcao <= 0 || opcao > 5) {
				System.out.println("Opção inválida! Tente novamente!");
			}
		} while (opcao <= 0 || opcao > 5);
		
		System.out.println();
		
		return opcao;
	}
}
