__kernel void contar_palavra(
    __global const char* texto,
    __global const char* palavra,
    const int tamanhoTexto,
    const int tamanhoPalavra,
    __global int* contador) 
{
    int i = get_global_id(0);

    //verifica se há espaço suficiente no texto a partir da posição i
    if (i + tamanhoPalavra > tamanhoTexto) {
        return;
    }

    //verificar correspondência da palavra ignorando maiúsculas/minúsculas
    int corresponde = 1;
    for (int j = 0; j < tamanhoPalavra; j++) {
        char c1 = texto[i + j];
        char c2 = palavra[j];

        //converter ambos para minúsculo se for maiúsculo
        if (c1 >= 'A' && c1 <= 'Z') {
            c1 = c1 + 32;
        }
        if (c2 >= 'A' && c2 <= 'Z') {
            c2 = c2 + 32;
        }

        if (c1 != c2) {
            corresponde = 0;
            break;
        }
    }

    if (corresponde) {
        //verificar se é uma palavra isolada

        //caractere anterior
        if (i > 0) {
            char anterior = texto[i - 1];
            if ((anterior >= 'a' && anterior <= 'z') || (anterior >= 'A' && anterior <= 'Z')) {
                corresponde = 0;
            }
        }

        //caractere seguinte
        if (i + tamanhoPalavra < tamanhoTexto) {
            char seguinte = texto[i + tamanhoPalavra];
            if ((seguinte >= 'a' && seguinte <= 'z') || (seguinte >= 'A' && seguinte <= 'Z')) {
                corresponde = 0;
            }
        }
    }

    //incrementa contador se encontrou
    if (corresponde) {
        atomic_inc(contador);
    }
}