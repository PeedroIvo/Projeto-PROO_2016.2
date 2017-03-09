package proo.sei.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import proo.sei.bo.ProfessorBO;
import proo.sei.exceptions.ViewException;

public class ProfessorView {
	private Scanner input = new Scanner(System.in);
	public ProfessorBO professorBO = new ProfessorBO();
	
	public int interfaceMenu (String nome) {
		System.out.println("-------------------------------------");
		System.out.println("Olá, " + nome + "! O que deseja fazer?");
		System.out.println("[1] Visualizar suas disciplinas");
		System.out.println("[2] Adicionar notas");
		System.out.println("[3] Visualizar dados pessoais");
		System.out.println("[4] Mudar a senha");
		System.out.println("[5] Logout");

		Boolean erro;
		int opcao = 0;
		
		do {
			erro = false;
			System.out.print("Digite sua opção: ");			
			
			try {
				opcao = this.input.nextInt();
				
				if (opcao <= 0 || opcao > 5) {
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
	
	public int interfaceCod(String pedidoCod) {
		Boolean erro;
		int cod = 0;
		
		do {
			erro = false;
			
			System.out.print(pedidoCod + ": ");		
			
			try {
				cod = input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		return cod;
	}
	
	public int interfaceBimestre() {
		Boolean erro;
		int bimestre = 0;	
		
		do {
			erro = false;
			System.out.print("Digite o bimestre correspondente as notas: (1, 2, 3 ou 4): ");			
			
			try {
				bimestre = input.nextInt();
				erro = !professorBO.validaBimestre(bimestre);
			} catch (InputMismatchException e) {
				System.out.println("Digite apenas números!");
				input.next();
				erro = true;
			}
		} while (erro);
		
		return bimestre;
	}
	
	public char interfaceTurno() {
		char turno;

		do {
			System.out.print("Digite o turno da turma (M ou V): ");
			turno = input.next().charAt(0);
		} while (!professorBO.validaTurno(turno));
		
		return turno;
	}
	
	public double interfaceNota(int codUsuario, String nome) {
		double nota;
		
		do {
			System.out.print("[" + codUsuario + "] Nome do aluno: " + nome + " | Digite a nota: ");
			nota = input.nextDouble();
			
			if (nota < 0 || nota > 10) {
				System.out.println("Nota inválida! A nota deverá ser de 0 à 10");
			}
		} while (nota < 0 || nota > 10);
		
		return nota;
	}
}
