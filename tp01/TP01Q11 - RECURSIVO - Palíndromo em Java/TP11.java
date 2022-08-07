import java.io.NotSerializableException;

public class TP11 {

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
    static boolean isPalindromo(String palavra, int i) {
        boolean resposta = true; // resposta do teste
        int tamanho = palavra.length(); // tamanho da palavra
        if (isFim(palavra) == true) {
            System.exit(0);
        } else {

            if(i < (tamanho / 2)){
                if (palavra.charAt(i) != palavra.charAt((tamanho - 1) - i)) {
                    resposta = false;
                    i = tamanho;
    
                } else {
                    resposta = isPalindromo(palavra, i + 1);
                    
                }
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
     * 
     * @param args
     */
    public static void main(String[] args) {
        String entrada;
        boolean resposta = false;
        MyIO.setCharset("UTF-8");
        do {
            entrada = MyIO.readLine();// ler a linha da string
            resposta = isPalindromo(entrada, 0);
            if (isPalindromo(entrada, 0) == true) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        } while (isFim(entrada) == false);// verificar FIM
    }
}