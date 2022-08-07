
import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.lang.Math;

class No {
	public char elemento;
	public No dir;
	public No esq;

	public No() {
		this.elemento = ' ';
		this.dir = null;
		this.esq = null;
	}

	public No(char elemento) {
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

	public void inserir(char elemento) throws Exception {
		raiz = inserir(elemento, raiz);
	}

	private No inserir(char elemento, No i) throws Exception {
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

	public void pesquisar(char elemento) {
		boolean resp = pesquisar(elemento, raiz);
		if (resp == true) {
			System.out.println(elemento+" existe");
		} else {
			System.out.println("nao existe");
		}
	}

	private boolean pesquisar(char elemento, No i) {
		boolean resp;
		if (i == null) {
			resp = false;

		} else if (elemento == i.elemento) {
			++contador;
			resp = true;

		} else if (elemento < i.elemento) {
			++contador;
			resp = pesquisar(elemento, i.esq);

		} else {
			++contador;
			resp = pesquisar(elemento, i.dir);
		}
		return resp;
	}

	public void remover(char elemento) throws Exception {
		raiz = remover(elemento, raiz);
	}

	private No remover(char elemento, No i) throws Exception {

		if (i == null) {
			throw new Exception("Erro ao remover!");

		} else if (elemento < i.elemento) {
			++contador;
			i.esq = remover(elemento, i.esq);

		} else if (elemento > i.elemento) {
			++contador;
			i.dir = remover(elemento, i.dir);

			// Sem no a direita.
		} else if (i.dir == null) {
			i = i.esq;

			// Sem no a esquerda.
		} else if (i.esq == null) {
			i = i.dir;

			// No a esquerda e no a direita.
		} else {
			i.esq = maiorEsq(i, i.esq);
		}

		return i;
	}

	private No maiorEsq(No i, No j) {

		// Encontrou o maximo da subarvore esquerda.
		if (j.dir == null) {
			i.elemento = j.elemento; // Substitui i por j.
			j = j.esq; // Substitui j por j.ESQ.

			// Existe no a direita.
		} else {
			// Caminha para direita.
			j.dir = maiorEsq(i, j.dir);
		}
		return j;
	}

	public void caminharCentral() {
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

public class TP09 {
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
		Scanner sc = new Scanner(System.in);
		Locale.setDefault(new Locale("en", "US"));
		Arvore arvore = new Arvore();
		int contador = 0;
		String entrada = "";
		long tempoInicial = System.currentTimeMillis();
		while (sc.hasNext()){
			entrada = sc.nextLine();
			if (entrada.length() < 4) {
				if (entrada.charAt(0) == 'I') {
					arvore.inserir(entrada.charAt(2));
				}
				else{
					arvore.pesquisar(entrada.charAt(2));
				}
			}
			else{
				if (entrada.compareTo("INFIXA") == 0) {
					arvore.caminharCentral();
				} else if(entrada.compareTo("PREFIXA") == 0){
					arvore.caminharPre();
				}
				else{
					arvore.caminharPos();
				}
			}
		}
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter("753192_arvoreABP.txt"));
			double tempoFinal = (System.currentTimeMillis() - tempoInicial) / 1000.0;
			bw.append("753192" + "\t" + tempoFinal + "\t" + arvore.getContador());
			bw.close(); // fecho o arquivo de escrita
		} catch (IOException ex) {
			System.out.println("erro abrir arquivo");
		}

	}
}
