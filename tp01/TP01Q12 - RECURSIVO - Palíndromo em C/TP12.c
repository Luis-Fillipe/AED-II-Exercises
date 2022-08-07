/*
 Aluno: Luis Fillipe Magalhaes Conrado Pereira
 Matricula: 753192
*/
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

size_t string_lengh(const char *s)
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
bool isFim(char *palavra)
{
    bool resposta = false;

    if ((int)palavra[0] == 70 && palavra[1] == 73 && palavra[2] == 77)
    {
        resposta = true;
    }

    return resposta;
}
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
bool isPalindromo(char *palavra, int i)
{
    bool resposta = true;                // resposta do teste
    int tamanho = string_lengh(palavra); // tamanho da palavra
    if (isFim(palavra) == true)
    {
        exit(0);
    }
    else
    {
        if (i < (tamanho / 2))
        {
            if (palavra[i] != palavra[(tamanho - 1) - i])
            {

                // printf("Letra: %c com Letra %c\n",palavra[i],palavra[(tamanho - 1) - i]);
                resposta = false;
                i = tamanho;
            }
            else
            {
                //printf("Letra: %c com Letra %c\n", palavra[i], palavra[(tamanho - 1) - i]);
                resposta = isPalindromo(palavra, i + 1);
            }
        }
    }

    return resposta;
}



int main(void)
{
    char entrada[400];
    bool resposta;
    int tamanho = 0;
    
    do
    {
        fgets(entrada, 400, stdin);
        tamanho = string_lengh(entrada);
        entrada[tamanho - 1] = '\0';
        resposta = isPalindromo(entrada, 0);
        if (resposta == true)
        {
            printf("SIM\n");
        }
        else
        {
            printf("NAO\n");
        }
    } while (isFim(entrada) == false); // verificar FIM
    return 0;
}
