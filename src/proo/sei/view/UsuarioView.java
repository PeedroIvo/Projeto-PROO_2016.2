package proo.sei.view;

import java.util.Scanner;

import proo.sei.vo.DadosPessoaisVO;

public class UsuarioView {
	private Scanner input = new Scanner(System.in);
	
	public String interfaceNovaSenha() {
		String novaSenha;

		do {
			System.out.print("Digite a sua nova senha: ");
			novaSenha = input.next();

			if (novaSenha.length() < 6) {
				System.out.println("A senha deve ter no mínimo 6 caracteres!");
			}
		} while (novaSenha.length() < 6);
		
		return novaSenha;
	}
	
	public void showDadosPessoais(String nome, DadosPessoaisVO dados) {
		System.out.println("Nome: " + nome);
		System.out.println("CPF: " + dados.getCpf());
		System.out.println("RG: " + dados.getRg());
		System.out.println("Email: " + dados.getEmail());
		System.out.println("Telefone: " + dados.getTelefone());
		System.out.println("Celular: " + dados.getCelular());
		System.out.println("\nCEP: " + dados.getEndereco().getCep());
		System.out.println("Rua: " + dados.getEndereco().getRua());
		System.out.println("Número: " + dados.getEndereco().getnCasa());
		System.out.println("Bairro: " + dados.getEndereco().getBairro());
		System.out.println("Complemento: " + dados.getEndereco().getComplemento());
		System.out.println("Cidade, Estado: " + dados.getEndereco().getCidade() + ", " + dados.getEndereco().getEstado() + "\n");		
	}
}
