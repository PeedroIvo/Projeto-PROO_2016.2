package proo.sei.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import proo.sei.exceptions.ViewException;

public class AdministradorView {
	private Scanner input = new Scanner(System.in);
	
	public int interfaceMenu (String nome) {		
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
		
		Boolean erro;
		int opcao = 0;
		
		do {
			erro = false;
			System.out.print("Digite sua opção: ");			
			
			try {
				opcao = this.input.nextInt();
				
				if (opcao <= 0 || opcao > 9) {
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
		
		return opcao;
	}
	
}
