import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JogoVelha {
    // Estes caracteres são aceitos como caracteres para representarem
    // os jogadores. Utizado para evitar caracteres que não combinem com
    // o desenho do tabuleiro.
    final static String CARACTERES_IDENTIFICADORES_ACEITOS = "XO0UC";

    // Tamanho do tabuleiro 3x3. Para o primeiro nivel de dificuldade
    // considere que este valor não será alterado.
    final static int TAMANHO_TABULEIRO = 3;

    static boolean limpeATela = true;

    public static void main(String[] args) {
         Scanner teclado = new Scanner(System.in);

         char[][] tabuleiro = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
        
          //TODO: Faça a inicialização do tabuleiro aqui
         inicializarTabuleiro(tabuleiro);

         // Definimos aqui qual é o caractere que cada jogador irá utilizar no jogo.
         //TODO: chame as funções obterCaractereUsuario() e obterCaractereComputador
         char caractereUsuario = obterCaractereUsuario(teclado);
         char caractereComputador = obterCaractereComputador(teclado, caractereUsuario);

         //para definir quais caracteres da lista de caracteres aceitos que o jogador
         //quer configurar para ele e para o computador.
         System.out.println("O caractere do jogador é: " + caractereUsuario);
         System.out.println("O caractere do computador é: " + caractereComputador);

         // Esta variavel é utilizada para definir se o usuário começa a jogar ou não.
         // Valor true, usuario começa jogando, valor false computador começa.
         //TODO: obtenha o valor booleano sorteado
         boolean vezUsuarioJogar = sortearValorBooleano();

         boolean jogoContinua;

         do {
             // controla se o jogo terminou
             jogoContinua = true;
             //TODO: Exiba o tabuleiro aqui
             exibirTabuleiro(tabuleiro);
            
             if (vezUsuarioJogar){
                 //TODO: Execute processar vez do usuario
                 System.out.println("É a vez do jogador!");

                 tabuleiro = processaVezUsuario(teclado, tabuleiro, caractereUsuario);

                 // Verifica se o usuario venceu
                 //TODO: Este if deve executar apenas se teve ganhador 
                 if (teveGanhador(tabuleiro, caractereUsuario)) {
                     //TODO: Exiba que o usuario ganhou
                     exibirVitoriaUsuario();
                     jogoContinua = false;
                 }

                 //TODO: defina qual o vaor a variavel abaixo deve possuir
                 vezUsuarioJogar = false;
             } else {

                 //TODO: Execute processar vez do computador
                 tabuleiro = processarVezComputador(tabuleiro, caractereComputador);

                 // Verifica se o computador venceu
                 //TODO: Este if deve executar apenas se teve ganhador
                 if (teveGanhador(tabuleiro, caractereComputador)) {

                     exibirVitoriaComputador();
                     jogoContinua = false;
                 }

                 //TODO: defina qual o vaor a variavel abaixo deve possuir
                 vezUsuarioJogar = true;
             }
        
             //TODO: Este if deve executar apenas se o jogo continua E 
             //ocorreu tempate. Utilize o metodo teveEmpate()
             if ( jogoContinua && teveEmpate(tabuleiro)) {

                 //TODO: Exiba que ocorreu empate
                 exibirEmpate();
                 jogoContinua = false;
             }
         } while (jogoContinua);

         teclado.close();
     }

    // Converte uma jogada no formato "linha coluna" para um vetor de inteiros
    static int[] converterJogadaStringParaVetorInt(String jogada) {
        int jogadaInt[] = new int[2];
        // Divide a string em partes separadas por espaço
        String jogadaTemp[] = jogada.split(" ");
        for (int i = 0; i < 2; i++) {
            jogadaInt[i] = Integer.parseInt(jogadaTemp[i]);
        }
        return jogadaInt;
    }

    static int[] obterJogadaComputador(String posicoesLivres, Scanner teclado) {
        try {
            // Verificar se a string posicoesLivres é nula ou vazia
            if (posicoesLivres == null || posicoesLivres.isEmpty()) {
                throw new IllegalArgumentException("A string de posições livres não pode ser nula ou vazia.");
            }

            // Passo 1: Separar as posições livres
            String[] vetorDePosicoes = posicoesLivres.split(";"); // Criando a String para receber

            // Verificar se o array de posições está vazio
            if (vetorDePosicoes.length == 0) {
                throw new IllegalArgumentException("A string de posições livres não contém posições válidas.");
            }

            // Passo 2: Sortear um índice aleatório
            Random random = new Random();
            int indiceSorteado = random.nextInt(vetorDePosicoes.length);

            // Passo 3: Pegar a posição sorteada
            String posicaoSorteado = vetorDePosicoes[indiceSorteado];

            // Passo 4: Converter a posição sorteada para vetor de inteiros usando o método
            // existente
            int[] jogada = converterJogadaStringParaVetorInt(posicaoSorteado.replace("", " ").trim());

            return jogada;

        } catch (Exception e) {
            throw new IllegalStateException("Erro ao calcular a jogada do computador: " + e.getMessage(), e);
        }

    }

    static boolean teveGanhador(char[][] tabuleiro, char caractereJogador) {
        if(teveGanhadorLinha(tabuleiro, caractereJogador) ||
           teveGanhadorColuna(tabuleiro, caractereJogador) ||
           teveGanhadorDiagonalPrincipal(tabuleiro, caractereJogador) ||
           teveGanhadorDiagonalSecundaria(tabuleiro, caractereJogador)){
        return true;
        }
        else{
            return false;
        }
}
    static boolean teveGanhadorLinha(char[][] tabuleiro, char caractereJogador) {
        boolean ganhadorLinha = false;
        int count = 0;
        for (int i = 0; i < tabuleiro.length; i++) {
            count = 0;
            for (int j = 0; j < tabuleiro.length; j++) {
                if (tabuleiro[i][j] == caractereJogador) {
                    count++;
                }
            }
        }
        if (count == 3)
            ganhadorLinha = true;
        return ganhadorLinha;
    }

    static boolean teveGanhadorColuna(char[][] tabuleiro, char caractereJogador) {
        boolean ganhadorColuna = false;
        int count = 0;
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro.length; j++) {
                if (tabuleiro[i][j] == caractereJogador) {
                    count++;
                }
            }
        }
        if (count == 3)
            ganhadorColuna = true;
        return ganhadorColuna;
    }

    static boolean teveGanhadorDiagonalPrincipal(char[][] tabuleiro, char caractereJogador) {
        boolean ganhadorDiagonalPrincipal = false;
        int count = 0;
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro.length; j++) {
                if (i == j) {
                    if (tabuleiro[i][j] == caractereJogador) {
                        count++;
                    }
                }
            }
        }
        if (count == 3)
            ganhadorDiagonalPrincipal = true;
        return ganhadorDiagonalPrincipal;
    }

    static boolean teveGanhadorDiagonalSecundaria(char[][] tabuleiro, char caractereJogador) {
        boolean ganhadorDiagonalSecundaria = false;
        int count = 0;
        for (int i = 0; i < tabuleiro.length; i++) {
            if (tabuleiro[i][tabuleiro.length - 1 - i] == caractereJogador) {
                count++;
            }
        }
        if (count == 3)
            ganhadorDiagonalSecundaria = true;
        return ganhadorDiagonalSecundaria;
    }

    static char[][] inicializarTabuleiro(char[][] tabuleiro) {
        for (int linha = 0; linha < tabuleiro.length; linha++) {
            for (int coluna = 0; coluna < tabuleiro.length; coluna++) {
                tabuleiro[linha][coluna] = ' ';
            }
        }
        return tabuleiro;
    }

    static String retornarPosicoesLivres(char[][] tabuleiro) {
        String conjuntoPosicoesLivres = "";
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro.length; j++) {
                if (CARACTERES_IDENTIFICADORES_ACEITOS.indexOf(tabuleiro[i][j]) == -1) {
                    conjuntoPosicoesLivres += String.valueOf(i);
                    conjuntoPosicoesLivres += String.valueOf(j);
                    conjuntoPosicoesLivres += ";";
                }
            }
        }
        return conjuntoPosicoesLivres;
    }

    static void exibirTabuleiro(char[][] tabuleiro) {
        // Chama o método para limpar a tela
        LimparTela();
        
        // Exibe as colunas numeradas no topo
        System.out.print("    ");
        for (int coluna = 0; coluna < tabuleiro[0].length; coluna++) {
            System.out.print(" " + (coluna + 1) + " ");  // Exibe os números das colunas, ajustado para começar de 1
            if (coluna < tabuleiro[0].length - 1) {
                System.out.print("|");  // Separador entre as colunas
            }
        }
        System.out.println();  // Pula linha após exibir os números das colunas
        
        // Exibe as linhas numeradas
        for (int linha = 0; linha < tabuleiro.length; linha++) {
            // Exibe o número da linha no início de cada linha, ajustado para começar de 1
            System.out.print(" " + (linha + 1) + " ");
            
            // Exibe as colunas de cada linha
            for (int coluna = 0; coluna < tabuleiro[linha].length; coluna++) {
                System.out.print(" " + tabuleiro[linha][coluna] + " ");
                if (coluna < tabuleiro[linha].length - 1) {
                    System.out.print("|");  // Separador entre as colunas
                }
            }
            System.out.println();  // Nova linha após cada linha do tabuleiro
            
            // Adiciona o separador horizontal
            if (linha < tabuleiro.length - 1) {
                System.out.println("   ---+---+---");  // Separador horizontal
            }
        }
        System.out.println();
    }

    static char obterCaractereComputador(Scanner sc, char caractereUsuario) {
        char caractere = ' ';
        boolean entradaValida = false;
        do {
            try {
                System.out.println(
                        "Escolha o caracter do computador para jogar " + CARACTERES_IDENTIFICADORES_ACEITOS + ": ");
                caractere = sc.nextLine().toUpperCase().charAt(0);
                if (CARACTERES_IDENTIFICADORES_ACEITOS.indexOf(caractere) == -1) {
                    System.out.println();
                    System.out.println("Este caractere não esta disponivel.");
                    continue;
                }
                if (caractere == caractereUsuario) {
                    System.out.println();
                    System.out.println("O usuário já escolheu este caractere, por favor escolha outro.");
                    continue;
                }
                entradaValida = true;
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println();
                System.out.println("Este caracter não esta disponivel!");
            }
        } while (!entradaValida);
        return caractere;
    }

    static char obterCaractereUsuario(Scanner sc) {
        char caractere = ' ';
        boolean contemCaractere = false;
        do {
            try {
                System.out.print("Escolha o seu caractere para jogar " + CARACTERES_IDENTIFICADORES_ACEITOS + ":");
                caractere = sc.nextLine().toUpperCase().charAt(0);
                if (CARACTERES_IDENTIFICADORES_ACEITOS.indexOf(caractere) != -1) {
                    contemCaractere = true;
                } else {
                    System.out.println();
                    System.out.println("Este caractere não esta disponivel");
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println();
                System.out.println("Este caractere não esta disponivel");
            }
        } while (!contemCaractere);
        return caractere;
    }

    static char[][] retornarTabuleiroAtualizado(char[][] tabuleiro, int[] jogada, char caractereJogador) {
        int posicao1 = jogada[0];
        int posicao2 = jogada[1];

        tabuleiro[posicao1][posicao2] = caractereJogador;
        return tabuleiro;
    }

    static void LimparTela() {
        if (limpeATela) {
            try {
                if (System.getProperty("os.name").contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                }
            } catch (Exception e) {
                System.out.println("Erro ao limpar a tela: " + e.getMessage());
            }
        }
    }

    static boolean jogadaValida(String posicoesLivres, int linha, int coluna) {
        String posicao = linha + "" + coluna;
        boolean validacao = true;
        if (posicoesLivres.contains(posicao)) {
            validacao = false;
        } else {
            System.out.println("Jogada nao disponivel! Tente novamente.");
        }
        return validacao;
    }

    public static boolean teveEmpate(char[][] tabuleiro) {
        for (int linha = 0; linha < tabuleiro.length; linha++) {
            for (int coluna = 0; coluna < tabuleiro[linha].length; coluna++) {
                if (tabuleiro[linha][coluna] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    static char[][] processaVezUsuario(Scanner teclado, char[][] tabuleiro, char caractereUsuario) {
        int linha, coluna;
        boolean jogadaValida;
        do {
            System.out.print("Digite a linha e a coluna separados por espaço (ex: 1 2): ");
            linha = teclado.nextInt() - 1;
            coluna = teclado.nextInt() - 1;
            jogadaValida = (linha >= 0 && linha < TAMANHO_TABULEIRO && coluna >= 0 && coluna < TAMANHO_TABULEIRO
                    && tabuleiro[linha][coluna] == ' ');
            if (!jogadaValida) {
                System.out.println("Jogada inválida! Tente novamente.");
            }
        } while (!jogadaValida);
        tabuleiro[linha][coluna] = caractereUsuario;
        return tabuleiro;
    }

    static char[][] processarVezComputador(char[][] tabuleiro, char caractereComputador) {
        Random random = new Random();
        int linha, coluna;
        do {
            linha = random.nextInt(TAMANHO_TABULEIRO);
            coluna = random.nextInt(TAMANHO_TABULEIRO);
        } while (tabuleiro[linha][coluna] != ' ');
        tabuleiro[linha][coluna] = caractereComputador;
        return tabuleiro;
    }

    static boolean sortearValorBooleano() {

        Random random = new Random();
        return random.nextBoolean();

    }

    static int[] obterJogadaUsuario(String posicoesLivres, Scanner teclado) {
        int jogadaUsuario[] = new int[2];
        boolean validarJogada = true;
        String inputUsuario = "";
        while (validarJogada) {
            System.out.print("Digite a posição da sua jogada, Linha e Coluna separados por um espaço em branco: ");
            inputUsuario = teclado.nextLine();
            validarJogada = jogadaValida(posicoesLivres, jogadaUsuario[0], jogadaUsuario[1]);
        }
        return converterJogadaStringParaVetorInt(inputUsuario);
    }

    public static void exibirVitoriaUsuario() {
        System.out.println("O usuário venceu      ");
        System.out.println("            ///////   ");
        System.out.println("     \\\\|//////////  ");
        System.out.println("      \\|/////////    ");
        System.out.println("      |___  ___ |     ");
        System.out.println("      |  o   o  |     ");
        System.out.println("      |    +    |     ");
        System.out.println("      |   \\_/  |     ");
        System.out.println("      \\_______/      ");
        System.out.println("         \\|//        ");
    }

    public static void exibirVitoriaComputador() {

        System.out.println("O computador venceu");
        System.out.println("+---------------------+");
        System.out.println("|+-------------------+|");
        System.out.println("||                   ||");
        System.out.println("||       0     0     ||");
        System.out.println("||          -        ||");
        System.out.println("||       \\\\_/      ||");
        System.out.println("||                   ||");
        System.out.println("|+-------------------+|");
        System.out.println("+------+-------+------+");
        System.out.println("  ___|/     \\\\|____  ");
        System.out.println("/ ****************** \\\\");
        System.out.println("/ ****************** \\\\");
        System.out.println("+-------------------- +");
    }

    public static void exibirEmpate() {
        System.out.println("Ocorreu empate!");
        System.out.println("+---------+                 +---------+");
        System.out.println("| +-----+ |  **         **  | +-----+ |");
        System.out.println("| |     | |    **     **    | |     | |");
        System.out.println("| |     | |     **  **      | |     | |");
        System.out.println("| |     | |       **        | |     | |");
        System.out.println("| |     | |     **  **      | |     | |");
        System.out.println("| *-----+ |   **      **    | *-----* |");
        System.out.println("+---------+ **          **  +---------+");
    }
}