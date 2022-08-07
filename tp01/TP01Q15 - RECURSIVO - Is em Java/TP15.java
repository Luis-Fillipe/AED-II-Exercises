public class TP15 {
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
     * isVogal - Função para verificar se todos os caracteres da string sao vogais
     * Recebo 1 String e verifico caracter por caracter se sao vogais
     * 
     * @return retorna TRUE se todos os caracteres sao vogais, FALSE se houver um ou
     *         mais caracteres diferentes de vogais
     * @param palavra - palavra a ser testada
     *                Realizo a verificacao com o objetivo de descobrir se a
     *                palavra
     *                é FIM, caso se confirme a execução do programa é paralisada.
     *                Se a palavra nao for FIM, eu realizo a encriptação de letra
     *                por letra, utilizando tabela ASCII
     */
    static boolean isVogal(String palavra, int i) {

        boolean resposta = true;
        if (isFim(palavra) == true) {
            System.exit(0);
        } else if (i < palavra.length()) {

            if (palavra.charAt(i) == 'a' || palavra.charAt(i) == 'A' || palavra.charAt(i) == 'e'
                    || palavra.charAt(i) == 'E' ||
                    palavra.charAt(i) == 'i' || palavra.charAt(i) == 'I' || palavra.charAt(i) == 'o'
                    || palavra.charAt(i) == 'O' ||
                    palavra.charAt(i) == 'u' || palavra.charAt(i) == 'U') {
                resposta = isVogal(palavra, i + 1);
            } else {
                resposta = false;

            }

        }

        return resposta;
    }

    /**
     * isConsoante - Função para verificar se todos os caracteres da string sao
     * consoantes
     * Recebo 1 String e verifico caracter por caracter se sao consoantes
     * 
     * @return retorna TRUE se todos os caracteres sao consoantes, FALSE se houver
     *         um ou mais caracteres diferentes de consoante
     * @param palavra - palavra a ser testada
     *                Realizo a verificacao com o objetivo de descobrir se a
     *                palavra
     *                é FIM, caso se confirme a execução do programa é paralisada.
     *                Se a palavra nao for FIM, eu realizo a encriptação de letra
     *                por letra, utilizando tabela ASCII
     */
    static boolean isConsoante(String palavra, int i) {

        boolean resposta = true;
        if (isFim(palavra) == true) {
            System.exit(0);
        } else if (i < palavra.length()) {
            if (palavra.charAt(i) >= 'b' && palavra.charAt(i) <= 'd'
                    || palavra.charAt(i) >= 'B' && palavra.charAt(i) <= 'D' ||
                    palavra.charAt(i) >= 'f' && palavra.charAt(i) <= 'h'
                    || palavra.charAt(i) >= 'F' && palavra.charAt(i) <= 'H' ||
                    palavra.charAt(i) >= 'j' && palavra.charAt(i) <= 'n'
                    || palavra.charAt(i) >= 'J' && palavra.charAt(i) <= 'N' ||
                    palavra.charAt(i) >= 'p' && palavra.charAt(i) <= 't'
                    || palavra.charAt(i) >= 'P' && palavra.charAt(i) <= 'T' ||
                    palavra.charAt(i) >= 'v' && palavra.charAt(i) <= 'z'
                    || palavra.charAt(i) >= 'V' && palavra.charAt(i) <= 'Z') {
                resposta = isConsoante(palavra, i + 1);
            } else {
                resposta = false;

            }

        }

        return resposta;
    }

    /**
     * isNumber - Função para verificar se todos os caracteres da string sao
     * numerais
     * Recebo 1 String e verifico caracter por caracter se sao numerais
     * 
     * @return retorna TRUE se todos os caracteres sao numerais, FALSE se houver um
     *         ou mais caracteres diferentes de numerais
     * @param palavra - palavra a ser testada
     *                Realizo a verificacao com o objetivo de descobrir se a
     *                palavra
     *                é FIM, caso se confirme a execução do programa é paralisada.
     *                Se a palavra nao for FIM, eu realizo a encriptação de letra
     *                por letra, utilizando tabela ASCII
     */
    static boolean isNumber(String palavra, int i) {

        boolean resposta = true;

        if (isFim(palavra) == true) {
            System.exit(0);
        } else if (i < palavra.length()) {

            if (palavra.charAt(i) >= 48 && palavra.charAt(i) <= 57) {
                resposta = isNumber(palavra, i + 1);
            } else {
                resposta = false;

            }

        }

        return resposta;
    }

    /**
     * isNumberDecimal - Função para verificar se todos os caracteres da string sao
     * numeros decimais
     * Recebo 1 String e verifico caracter por caracter se sao numerais e se contem
     * a presença de uma virgula ou ponto para representar um conjunto de numeros
     * decimais
     * 
     * @return retorna TRUE se todos os caracteres sao numeros decimais, FALSE se
     *         houver um
     *         ou mais caracteres diferentes de numeros decimais
     * @param palavra - palavra a ser testada
     *                Realizo a verificacao com o objetivo de descobrir se a
     *                palavra
     *                é FIM, caso se confirme a execução do programa é paralisada.
     *                Se a palavra nao for FIM, eu realizo a encriptação de letra
     *                por letra, utilizando tabela ASCII
     */
    static boolean isNumberDecimal(String palavra, int i, int controle) {

        boolean resposta = true;
        // Int controle: caso haja a presença de uma ou mais virgula/ponto, considero
        // como falso, por isso a variavel controle
        if (isFim(palavra) == true) {
            System.exit(0);
        } else if (i < palavra.length()) {

            if (palavra.charAt(i) >= 48 && palavra.charAt(i) <= 57) {
                resposta = isNumberDecimal(palavra, i + 1, controle);
            } else {
                if (palavra.charAt(i) == 44 || palavra.charAt(i) == 46) {

                    controle++;
                    resposta = isNumberDecimal(palavra, i + 1, controle);

                } else {
                    resposta = false;

                }
            }

        }

        if (controle > 1) {
            resposta = false;
        }
        return resposta;

    }

    /**
     * Método principal
     * 
     * @param args
     */
    public static void main(String[] args) {

        String palavra;
        boolean resposta;

        do {

            palavra = MyIO.readLine();// ler a linha da string
            resposta = isVogal(palavra, 0);// verifica se e somente vogal
            if (resposta == true) {
                System.out.print("SIM ");
            } else {
                System.out.print("NAO ");
            }
            resposta = isConsoante(palavra, 0);// verifica se e soemnte consoante
            if (resposta == true) {
                System.out.print("SIM ");
            } else {
                System.out.print("NAO ");
            }
            resposta = isNumber(palavra, 0);// verifica se e somente numero
            if (resposta == true) {
                System.out.print("SIM ");
            } else {
                System.out.print("NAO ");
            }
            resposta = isNumberDecimal(palavra, 0, 0);// verifica se e somente numerais decimais
            if (resposta == true) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }

        } while (isFim(palavra) == false);// verificar FIM

    }
}