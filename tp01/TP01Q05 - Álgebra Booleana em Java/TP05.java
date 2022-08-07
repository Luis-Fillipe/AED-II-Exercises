
/*
 Aluno: Luis Fillipe Magalhaes Conrado Pereira
 Matricula: 753192
*/

public class TP05 {
    static boolean isFim(String entrada) {
        boolean resposta = false;
        if (entrada.charAt(0) == '0') {
            resposta = true;
        }
        return resposta;
    }

    static String tiraEspaco(String entrada) {
        String saida = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) != 32) {
                saida = saida + entrada.charAt(i);
            }

        }
        return saida;
    }

    static int[] takeNumbers(String entrada) {
        int[] binarios = new int[3];
        int aux = entrada.charAt(0);

        if (aux == 50) {
            binarios[0] = (int) entrada.charAt(1) - 48;
            binarios[1] = (int) entrada.charAt(2) - 48;
        }
        if (aux == 51) {
            binarios[0] = (int) entrada.charAt(1) - 48;
            binarios[1] = (int) entrada.charAt(2) - 48;
            binarios[2] = (int) entrada.charAt(3) - 48;
        }

        return binarios;
    }

    static String changeA(String entrada, int bin) {
        String saida = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) != 'A') {
                saida = saida + entrada.charAt(i);

            } else {
                saida = saida + bin;
            }
        }
        return saida;
    }

    static String changeB(String entrada, int bin) {
        String saida = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) != 'B') {
                saida = saida + entrada.charAt(i);
            } else {
                saida = saida + bin;
            }

        }
        return saida;
    }

    static String changeC(String entrada, int bin) {
        String saida = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) != 'C') {
                saida = saida + entrada.charAt(i);
            } else {
                saida = saida + bin;
            }

        }
        return saida;
    }

    static String tirarNumeros(String entrada) {
        String saida = "";
        int aux = entrada.charAt(0);
        if (aux == 50) {
            for (int i = 3; i < entrada.length(); i++) {
                saida = saida + entrada.charAt(i);
            }
        }
        if (aux == 51) {
            for (int i = 4; i < entrada.length(); i++) {
                saida = saida + entrada.charAt(i);
            }
        }
        return saida;
    }

    static String resolveNot(String entrada) {
        String saida = "";
        String saidaAnd = "";
        String saidaAux = "";
        int posicao = 0;
        int posicaoFinal = 0;
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) == 'n' && entrada.charAt(i + 1) == 'o') {
                posicao = i;
                i = entrada.length();
            } else {
                saida = saida + entrada.charAt(i);

            }
        }
        System.out.println(posicao);
        for (int i = posicao; i < entrada.length(); i++) {
            if (entrada.charAt(i) == ')') {
                posicaoFinal = i;

            }
        }
        System.out.println(posicaoFinal);
        for (int i = posicao; i < posicaoFinal; i++) {
            saidaAux = saidaAux + entrada.charAt(i);
        }
        for (int i = posicao; i < posicaoFinal; i++) {
            if (entrada.charAt(i) == 'a' && entrada.charAt(i+1) == 'n' && entrada.charAt(i+2) == 'd') {
                if (entrada.charAt(i) == 'n' || entrada.charAt(i+1) == 'o' || entrada.charAt(i+2) == 't') {
                    saida = saida + entrada.charAt(i) + entrada.charAt(i+1) + entrada.charAt(i+2);
                }
                saidaAnd = changeAnd(saidaAux, 0);
               System.out.println("antes de resolver o and: "+saida);
                saida = saida + saidaAnd;
               System.out.println("Chamei o and e resolvi: "+saida);
            }
            if (saida.charAt(i) == 48) {
                saida = saida + '1';
               System.out.println("entrei aqui resultado = " + saida);
            } else if (entrada.charAt(i) == 49) {
                saida = saida + '0';
               System.out.println("entrei aqui resultado 2 = " + saida);
            }

            if (entrada.charAt(i) == 'n' || entrada.charAt(i) == 'o' || entrada.charAt(i) == 't'
                    || entrada.charAt(i) == '(' || entrada.charAt(i) == ')' || entrada.charAt(i) == '0'
                    || entrada.charAt(i) == '1') {
                continue;

            } else {
                saida = saida + entrada.charAt(i);

            }
        }
        for (int i = posicaoFinal; i < entrada.length(); i++) {
            saida = saida + entrada.charAt(i);
        }
        return saida;
    }

    static String changeAnd(String entrada, int inicio) {
        String saida = "";
        int posicao = inicio;
        int posicaoFinal = 0;
        for (int i = inicio; i < entrada.length(); i++) {
            if (entrada.charAt(i) == 'a' && entrada.charAt(i + 1) == 'n') {
                posicao = i;
                i = entrada.length();
            } else {
                saida = saida + entrada.charAt(i);
            }
        }
        for (int i = posicao; i < entrada.length(); i++) {
            if (entrada.charAt(i) == ')') {
                posicaoFinal = i;
                i = entrada.length();
            }

        }
        for (int i = posicao; i < posicaoFinal; i++) {
            if (entrada.charAt(i) == 'a' && entrada.charAt(i + 1) == 'n' && entrada.charAt(i + 2) == 'd') {
                if (entrada.charAt(i + 4) == 48 && entrada.charAt(i + 6) == 48) {
                    saida = saida + '0';
                } else if (entrada.charAt(i + 4) == 48 && entrada.charAt(i + 6) == 49) {
                    saida = saida + '0';
                } else if (entrada.charAt(i + 4) == 49 && entrada.charAt(i + 6) == 48) {
                    saida = saida + '0';
                } else if (entrada.charAt(i + 4) == 49 && entrada.charAt(i + 6) == 49) {
                    saida = saida + '1';
                }

            }
            if (entrada.charAt(i) == 'a' || entrada.charAt(i) == 'n' || entrada.charAt(i) == 'd'
                    || entrada.charAt(i) == '(' || entrada.charAt(i) == ')' || entrada.charAt(i) == '1'
                    || entrada.charAt(i) == '0' || entrada.charAt(i) == '1' || entrada.charAt(i) == ',') {
                continue;

            } else {
                saida = saida + entrada.charAt(i);
            }
        }
        for (int i = posicaoFinal + 1; i < entrada.length(); i++) {
            saida = saida + entrada.charAt(i);
        }
        System.out.println("resultado and: "+saida);
        return saida;
    }

    static String  changeOr(String entrada) {
        String saida = "";
        int posicao = 0;
        int posicaoFinal = 0;
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) == 'o' && entrada.charAt(i + 1) == 'r') {
                posicao = i;
                i = entrada.length();
            } else {
                saida = saida + entrada.charAt(i);
            }
        }
        for (int i = posicao; i < entrada.length(); i++) {
            if (entrada.charAt(i) == ')') {
                posicaoFinal = i;
                i = entrada.length();
            }

        }
        for (int i = posicao; i < posicaoFinal; i++) {
            if (entrada.charAt(i) == 'o' && entrada.charAt(i + 1) == 'r') {
                if (entrada.charAt(i + 4) == 48 && entrada.charAt(i + 6) == 48) {
                    saida = saida + '0';
                } else if (entrada.charAt(i + 4) == 48 && entrada.charAt(i + 6) == 49) {
                    saida = saida + '1';
                } else if (entrada.charAt(i + 4) == 49 && entrada.charAt(i + 6) == 48) {
                    saida = saida + '1';
                } else if (entrada.charAt(i + 4) == 49 && entrada.charAt(i + 6) == 49) {
                    saida = saida + '1';
                }

            }
            if (entrada.charAt(i) == 'o' || entrada.charAt(i) == 'r' || entrada.charAt(i) == '('
                    || entrada.charAt(i) == ')' || entrada.charAt(i) == '1'
                    || entrada.charAt(i) == '0' || entrada.charAt(i) == ',') {
                continue;

            } else {
                saida = saida + entrada.charAt(i);
            }
        }
        for (int i = posicaoFinal + 1; i < entrada.length(); i++) {
            saida = saida + entrada.charAt(i);
        }
        return saida;
    }

    static String readFirst(String entrada) {
        String saida = "";
        if (entrada.charAt(0) == 'a' && entrada.charAt(1) == 'n' && entrada.charAt(2) == 'd') {
            saida = resolveNot(entrada);
            saida = changeOr(entrada);
            saida = changeAnd(saida,0);
        }
        if (entrada.charAt(0) == 'n' && entrada.charAt(1) == 'o' && entrada.charAt(2) == 't') {
            saida = changeAnd(entrada,0);
            saida = resolveNot(saida);
        }
        if (entrada.charAt(0) == 'o' && entrada.charAt(1) == 'r') {

        }
        return saida;
    }

    public static void main(String[] args) {
        String entrada = "";
        do {
            entrada = MyIO.readLine();
            String saida = "";
            int[] binarios = new int[3];
            if (entrada.charAt(0) == '0') {
                System.exit(0);
            }

            saida = tiraEspaco(entrada);
            binarios = takeNumbers(saida);
            saida = changeA(saida, binarios[0]);
            saida = changeB(saida, binarios[1]);
            saida = changeC(saida, binarios[2]);

            saida = tirarNumeros(saida);
            System.out.println(saida);
            saida = readFirst(saida);
            // entrada = resolveNot(entrada);
            // System.out.println("Apos NOT: " + entrada);
            // entrada = changeAnd(entrada);
            for (int i = 0; i < saida.length(); i++) {
                if (saida.charAt(i) == 48) {
                    System.out.println('0');
                }
                else if(saida.charAt(i) == 49){
                    System.out.println('1');
                }
            }
        } while (isFim(entrada) == false);
    }
}