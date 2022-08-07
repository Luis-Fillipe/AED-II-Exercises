
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
/**
 * string_lenght - Função para diminuir 1 no tamanho da String caso encontre um caracter especial
 *
 * @return retorna tamanho da string
 * @param s - palavra a ser testada
 */
size_t string_lenght(const char *s)
{
    size_t count = 0;
    while (*s)
    {
        count += (*s++ & 0xC0) != 0x80;
    }
    return count;
}
/**
 * isFim - Função para averiguar a string recebida é igual a FIM
 *
 * @return retorna true quando a string é FIM e false quando nao é FIM
 * @param palavra - palavra a ser testada
 *                Realizo a verificacao com o objetivo se descobrir se a palavra
 *                é FIM, caso se confirme a execução do programa é paralisada
 */
static bool isFim(char *palavra)
{

    bool resposta = false;

    if (palavra[0] == 'F' && palavra[1] == 'I' && palavra[2] == 'M')
    {
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
static bool isVogal(char *palavra)
{
    int tamanho = string_lenght(palavra); // tamanho da palavra
    bool resposta = false;
    if (isFim(palavra) == true)
    {
        exit(0);
    }
    else
    {
        for (int i = 0; i < tamanho; i++)
        {
            if (palavra[i] == 'a' || palavra[i] == 'A' || palavra[i] == 'e' || palavra[i] == 'E' ||
                palavra[i] == 'i' || palavra[i] == 'I' || palavra[i] == 'o' || palavra[i] == 'O' ||
                palavra[i] == 'u' || palavra[i] == 'U')
            {
                resposta = true;
            }
            else
            {
                resposta = false;
                i = tamanho;
            }
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
static bool isConsoante(char *palavra)
{
    int tamanho = string_lenght(palavra); // tamanho da palavra
    bool resposta = false;
    if (isFim(palavra) == true)
    {
        exit(0);
    }
    else
    {
        for (int i = 0; i < tamanho; i++)
        {
            if (palavra[i] >= 'b' && palavra[i] <= 'd' || palavra[i] >= 'B' && palavra[i] <= 'D' ||
                palavra[i] >= 'f' && palavra[i] <= 'h' || palavra[i] >= 'F' && palavra[i] <= 'H' ||
                palavra[i] >= 'j' && palavra[i] <= 'n' || palavra[i] >= 'J' && palavra[i] <= 'N' ||
                palavra[i] >= 'p' && palavra[i] <= 't' || palavra[i] >= 'P' && palavra[i] <= 'T' ||
                palavra[i] >= 'v' && palavra[i] <= 'z' || palavra[i] >= 'V' && palavra[i] <= 'Z')
            {
                resposta = true;
            }
            else
            {
                resposta = false;
                i = tamanho;
            }
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
static bool isNumber(char *palavra)
{

    bool resposta = false;
    int tamanho = string_lenght(palavra); // tamanho da palavra
    if (isFim(palavra) == true)
    {
        exit(0);
    }
    else
    {
        for (int i = 0; i < tamanho; i++)
        {

            if ((int)palavra[i] >= 48 && (int)palavra[i] <= 57)
            {
                resposta = true;
            }
            else
            {
                resposta = false;
                i = tamanho;
            }
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
bool isNumberDecimal(char *palavra)
{

    bool resposta = false;
    int tamanho = string_lenght(palavra); // tamanho da palavra
    int controle = 0;                     // caso haja a presença de uma ou mais virgula/ponto, considero como falso, por
                                          // isso a variavel controle
    if (isFim(palavra) == true)
    {
        exit(0);
    }
    else
    {
        for (int i = 0; i < tamanho; i++)
        {

            if ((int)palavra[i] >= 48 && (int)palavra[i] <= 57)
            {
                resposta = true;
            }
            else
            {
                if ((int)palavra[i] == 44 || (int)palavra[i] == 46)
                {
                    resposta = true;
                    controle++;
                }
                else
                {
                    resposta = false;
                    i = tamanho;
                }
            }
        }
    }
    if (controle > 1)
    {
        resposta = false;
    }
    return resposta;
}

int main(void)
{

    char palavra[400];
    bool resposta = false;
    int tamanho = 0;
    do
    {

        fgets(palavra, 400, stdin);
        tamanho = string_lenght(palavra);
        palavra[tamanho - 1] = '\0';

        resposta = isVogal(palavra);
        if (resposta == true)
        {
            printf("SIM ");
        }
        else
        {
            printf("NAO ");
        }
        resposta = isConsoante(palavra); // verifica se e soemnte consoante
        if (resposta == true)
        {
            printf("SIM ");
        }
        else
        {
            printf("NAO ");
        }
        resposta = isNumber(palavra); // verifica se e somente numero
        if (resposta == true)
        {
            printf("SIM ");
        }
        else
        {
            printf("NAO ");
        }
        resposta = isNumberDecimal(palavra); // verifica se e somente numerais decimais
        if (resposta == true)
        {
            printf("SIM\n");
        }
        else
        {
            printf("NAO\n");
        }

    } while (isFim(palavra) == false); // verificar FIM
}
