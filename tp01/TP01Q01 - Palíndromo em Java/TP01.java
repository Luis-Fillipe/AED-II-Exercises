
public class TP01 {

    /**
     * isPalindromo - Função para averiguar se uma string é palindromo
     * 
     * @return retorna SIM quando a string e um palindromo e NAO quando nao e
     *         palindromo
     * @param palavra - palavra a ser testada
     *                Realizo a verificacao da primeira letra com a ultima, se forem
     *                iguais eu verifico a 2 letra com a penultima, e assim por
     *                diante
     *                Caso o teste de falso, eu caio no meu else que para a minha
     *                execução do for e reafirmo que minha resposta e falsa
     */
    static boolean isPalindromo(String palavra) {
        boolean resposta = false; // resposta do teste
        int tamanho = palavra.length(); // tamanho da palavra

        for (int i = 0; i < tamanho; i++) {
            if ((int) palavra.charAt(i) == (int) palavra.charAt((tamanho - 1) - i)) {
                resposta = true;
            } else {
                i = tamanho;
                resposta = false;
            }
        }

        return resposta;
    }

    /**
     * isFim - Função para averiguar a string recebida é igual a FIM
     * 
     * @return retorna true quando a string é FIM e false quando nao é FIM
     * @param palavra - palavra a ser testada
     *                Realizo a verificacao com o objetivo se descobrir se a palavra
     *                é FIM, caso se confirme a execução do programa é paralisada
     */
    static boolean isFim(String palavra) {
        boolean resposta = false;

        if (palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I' && palavra.charAt(2) == 'M') {
            resposta = true;

        }

        return resposta;
    }

    /**
     * Método principal
     * @param args
     */
    public static void main(String[] args) {
        String entrada = MyIO.readLine();
        if (isFim(entrada) == true) { //se palavra = fim paralisa execução
            System.exit(0);
        } else {
            if (isPalindromo(entrada) == true) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }
        while (isFim(entrada) == false) {// enquanto a palavra nao e FIM eu continuo lendo
            entrada = MyIO.readLine();
            if (isFim(entrada) == true) {
                System.exit(0);
            } else {
                if (isPalindromo(entrada) == true) {
                    System.out.println("SIM");
                } else {
                    System.out.println("NAO");
                }
            }
        }
    }
}