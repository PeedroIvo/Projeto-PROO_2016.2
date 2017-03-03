package proo.sei.view;

import java.util.Scanner;

public class AdministradorView {
	private Scanner input = new Scanner(System.in);
	
	public int interfaceMenu (String nome) {
		int opcao;
		
		System.out.println("-------------------------------------");
		System.out.println("Olá, " + nome + "! O que deseja fazer?");
		System.out.println("[1] Matricular aluno");
		System.out.println("[2] Desmatricular aluno");
		System.out.println("[3] Cadastrar professor");
		System.out.println("[4] Descadastrar professor");
		System.out.println("[5] Quadro de professores");
		System.out.println("[6] Visualizar boletim de aluno");
		System.out.println("[7] Visualizar turma");
		System.out.println("[8] Mudar a senha");
		System.out.println("[9] Logout");

		do {
			System.out.print("Digite sua opção: ");
			opcao = input.nextInt();

			if (opcao <= 0 || opcao > 9) {
				System.out.println("Opção inválida! Tente novamente!");
			}
		} while (opcao <= 0 || opcao > 9);
		
		System.out.println();
		
		return opcao;
	}
	
}
