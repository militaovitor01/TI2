package somaDoisNumeros;
import java.util.*;

class somaDoisNumeros {
	public static Scanner scanner = new Scanner (System.in);
	public static void main (String[] args) {
		//Variáveis		
		int num1, num2, soma;
			
		//Leituras
		System.out.println ("Digite um número");
		num1 = scanner.nextInt();
			
		System.out.println ("Digite outro número");
		num2 = scanner.nextInt();
			
		//Operação de soma
		soma = num1 + num2;
		
		//Mostrar na tela
		System.out.println (soma);
	}
}


