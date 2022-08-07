
import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.lang.Math;

class No {
	public int elemento;
	public No dir;
	public No esq;

	public No() {
		this.elemento = -1;
		this.dir = null;
		this.esq = null;
	}

	public No(int elemento) {
		this.elemento = elemento;
		this.dir = null;
		this.esq = null;
	}
}

/**
 * Algoritmo de ordenacao Quicksort
 */
class Arvore {
	private No raiz;
	private int contador;

	/**
	 * Construtor da classe que cria uma lista sem elementos (somente no cabeca).
	 */
	public Arvore() {
		raiz = null;
		contador = 0;
	}

	public void inserir(int elemento) throws Exception {
		raiz = inserir(elemento, raiz);
	}

	private No inserir(int elemento, No i) throws Exception {
		if (i == null) {
			i = new No(elemento);
		} else {
			if (elemento > i.elemento) {
				++contador;
				i.dir = inserir(elemento, i.dir);
			} else if (elemento < i.elemento) {
				++contador;
				i.esq = inserir(elemento, i.esq);
			} else {
				throw new Exception("Erro ao inserir!");
			}
		}
		return i;
	}

	public void insert(String elementos, int quantidade) throws Exception {
		int []array = new int[quantidade];
		String number = "";
		int j = 0;
		//System.out.println(elementos);
		for (int i = 0; i < elementos.length(); i++) {
			if(elementos.charAt(i) == ' '){
				//System.out.println(elementos.charAt(i));
				array[j] = Integer.parseInt(number);
				number = "";
				j++;
			}else{
				if (elementos.charAt(i) >= 48 && elementos.charAt(i) <= 57) {
					//System.out.println(elementos.charAt(i));
					number += elementos.charAt(i);
				}
			}
		}
		array[quantidade-1] = Integer.parseInt(number);
		for (int i = 0; i < array.length; i++) {
			inserir(array[i]);
		}
	}


	public void caminharCentral() {
		System.out.print("In..: ");
		caminharCentral(raiz);
		System.out.print("\n");
	}

	private void caminharCentral(No i) {
		if (i != null) {
			caminharCentral(i.esq); // Elementos da esquerda.
			System.out.print(i.elemento + " "); // Conteudo do no.
			caminharCentral(i.dir); // Elementos da direita.
		}
	}

	public void caminharPre() {
		System.out.print("Pre.: ");
		caminharPre(raiz);
		System.out.print("\n");
	}

	private void caminharPre(No i) {
		if (i != null) {
			System.out.print(i.elemento + " "); // Conteudo do no.
			caminharPre(i.esq); // Elementos da esquerda.
			caminharPre(i.dir); // Elementos da direita.
		}
	}

	public void caminharPos() {
		System.out.print("Post: ");
		caminharPos(raiz);
		System.out.print("\n");

	}

	private void caminharPos(No i) {
		if (i != null) {
			caminharPos(i.esq); // Elementos da esquerda.
			caminharPos(i.dir); // Elementos da direita.
			System.out.print(i.elemento + " "); // Conteudo do no.
		}
	}

	public int getContador() {
		return contador;
	}

}

public class TP08B {
	static boolean isFim(String palavra) {
		boolean resposta = false;
		if (palavra.equals("FIM")) {
			resposta = true;
		}
		return resposta;
	}

	static String clearTag(String palavra, int i) {
		String newWord = "";
		while (i < palavra.length()) {
			newWord += palavra.charAt(i);
			i++;
		}
		return newWord;
	}

	public static void main(String[] args) throws ParseException, Exception {

		Locale.setDefault(new Locale("en", "US"));
		Scanner sc = new Scanner(System.in);
		int tamanho = 0;
		int quantidade = 0;
		int elemento = -1;
		int contador = 0;
		String size = "";
		long tempoInicial = System.currentTimeMillis();

		// size = sc.nextLine().trim();
		tamanho = Integer.parseInt(sc.nextLine().trim());
		tamanho++;
		for (int i = 1; i < tamanho; i++) {
			Arvore arvore = new Arvore();
			// size = sc.nextLine().trim();
			quantidade = Integer.parseInt(sc.nextLine().trim());
			String elementos = sc.nextLine().trim();
			arvore.insert(elementos, quantidade);
			System.out.println("Case " + i + ":");
			arvore.caminharPre();
			arvore.caminharCentral();
			arvore.caminharPos();
			if (i == 1) {
				System.out.println();
			}
			contador += arvore.getContador();
		}

	}
}
