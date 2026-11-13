import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

class Aluno {
    private String nome;
    private String turma;
    private double nota1;
    private double nota2;

    public Aluno(String nome, String turma, double nota1, double nota2) {
        this.nome = nome;
        this.turma = turma;
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public double calcularMedia() {
        return (nota1 + nota2) / 2.0;
    }

    public String verificarSituacao() {
        if (calcularMedia() >= 6.0) {
            return "Aprovado";
        } else {
            return "Recuperação";
        }
    }

    public String formatarResumo() {
        return String.format(Locale.US,
                "Nome: %s | Turma: %s | Nota 1: %.1f | Nota 2: %.1f | Média: %.1f | Situação: %s",
                nome, turma, nota1, nota2, calcularMedia(), verificarSituacao());
    }
}

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        Scanner scanner = new Scanner(System.in);
        ArrayList<Aluno> listaAlunos = new ArrayList<>();
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            opcao = lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarAluno(scanner, listaAlunos);
                    break;
                case 2:
                    listarAlunos(listaAlunos);
                    break;
                case 3:
                    pesquisarAluno(scanner, listaAlunos);
                    break;
                case 4:
                    removerAluno(scanner, listaAlunos);
                    break;
                case 5:
                    consultarSituacao(scanner, listaAlunos);
                    break;
                case 6:
                    salvarRelatorioTxt(listaAlunos);
                    break;
                case 7:
                    alterarNotasAluno(scanner, listaAlunos);
                    break;
                case 8:
                    exibirEstatisticasTurma(listaAlunos);
                    break;
                case 0:
                    System.out.println("\nEncerrando o Sistema Escolar. Até logo!");
                    break;
                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
                    break;
            }
        }

        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("\nSISTEMA ESCOLAR - JAVA");
        System.out.println("================================");
        System.out.println("1 - Cadastrar aluno");
        System.out.println("2 - Listar alunos");
        System.out.println("3 - Pesquisar aluno");
        System.out.println("4 - Remover aluno");
        System.out.println("5 - Consultar situação");
        System.out.println("6 - Salvar relatório em TXT");
        System.out.println("7 - Alterar notas de aluno (Desafio Extra)");
        System.out.println("8 - Estatísticas da turma (Desafio Extra)");
        System.out.println("0 - Sair");
        System.out.println("================================");
    }

    public static void cadastrarAluno(Scanner scanner, ArrayList<Aluno> listaAlunos) {
        System.out.println("\n--- CADASTRAR ALUNO ---");
        System.out.print("Nome do aluno: ");
        String nome = scanner.nextLine().trim();

        while (nome.isEmpty()) {
            System.out.print("O nome não pode ficar vazio. Digite o nome do aluno: ");
            nome = scanner.nextLine().trim();
        }

        for (Aluno existente : listaAlunos) {
            if (existente.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Já existe um aluno cadastrado com o nome '" + existente.getNome() + "'.");
                System.out.println("Use a opção 7 do menu caso deseje alterar as notas.");
                return;
            }
        }

        System.out.print("Turma: ");
        String turma = scanner.nextLine().trim();

        while (turma.isEmpty()) {
            System.out.print("A turma não pode ficar vazia. Digite a turma: ");
            turma = scanner.nextLine().trim();
        }

        double nota1 = lerNotaValida(scanner, "Digite a Nota 1 (0 a 10): ");
        double nota2 = lerNotaValida(scanner, "Digite a Nota 2 (0 a 10): ");

        Aluno novoAluno = new Aluno(nome, turma, nota1, nota2);
        listaAlunos.add(novoAluno);

        System.out.println("Aluno cadastrado com sucesso!");
    }

    public static void listarAlunos(ArrayList<Aluno> listaAlunos) {
        System.out.println("\n--- LISTA DE ALUNOS CADASTRADOS ---");
        if (listaAlunos.size() == 0) {
            System.out.println("Nenhum aluno cadastrado no momento.");
            return;
        }

        for (int i = 0; i < listaAlunos.size(); i++) {
            Aluno aluno = listaAlunos.get(i);
            System.out.printf("%d. %s%n", (i + 1), aluno.formatarResumo());
        }

        System.out.println("-----------------------------------");
        System.out.println("Total de alunos cadastrados: " + listaAlunos.size());
        System.out.printf("Média geral da turma: %.2f%n", calcularMediaGeral(listaAlunos));
    }

    public static void pesquisarAluno(Scanner scanner, ArrayList<Aluno> listaAlunos) {
        System.out.println("\n--- PESQUISAR ALUNO ---");
        if (listaAlunos.size() == 0) {
            System.out.println("Nenhum aluno cadastrado para pesquisar.");
            return;
        }

        System.out.print("Digite o nome do aluno que deseja buscar: ");
        String nomeBusca = scanner.nextLine().trim();

        boolean encontrado = false;
        for (Aluno aluno : listaAlunos) {
            if (aluno.getNome().equalsIgnoreCase(nomeBusca)) {
                System.out.println("Aluno encontrado:");
                System.out.println(aluno.formatarResumo());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Aluno '" + nomeBusca + "' não encontrado na lista.");
        }
    }

    public static void removerAluno(Scanner scanner, ArrayList<Aluno> listaAlunos) {
        System.out.println("\n--- REMOVER ALUNO ---");
        if (listaAlunos.size() == 0) {
            System.out.println("Nenhum aluno cadastrado para remover.");
            return;
        }

        System.out.print("Digite o nome do aluno que deseja remover: ");
        String nomeRemover = scanner.nextLine().trim();

        int indiceRemover = -1;
        for (int i = 0; i < listaAlunos.size(); i++) {
            if (listaAlunos.get(i).getNome().equalsIgnoreCase(nomeRemover)) {
                indiceRemover = i;
                break;
            }
        }

        if (indiceRemover != -1) {
            Aluno removido = listaAlunos.remove(indiceRemover);
            System.out.println("Aluno '" + removido.getNome() + "' removido com sucesso!");
        } else {
            System.out.println("Aluno '" + nomeRemover + "' não encontrado.");
        }
    }

    public static void consultarSituacao(Scanner scanner, ArrayList<Aluno> listaAlunos) {
        System.out.println("\n--- CONSULTAR SITUAÇÃO ESCOLAR ---");
        if (listaAlunos.size() == 0) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.print("Digite o nome do aluno (ou deixe em branco para ver todos): ");
        String nomeConsulta = scanner.nextLine().trim();

        if (nomeConsulta.isEmpty()) {
            for (Aluno aluno : listaAlunos) {
                System.out.printf("Aluno: %s | Turma: %s | Média: %.1f | Situação: %s%n",
                        aluno.getNome(), aluno.getTurma(), aluno.calcularMedia(), aluno.verificarSituacao());
            }
        } else {
            boolean encontrado = false;
            for (Aluno aluno : listaAlunos) {
                if (aluno.getNome().equalsIgnoreCase(nomeConsulta)) {
                    System.out.printf("Aluno: %s | Turma: %s | Nota 1: %.1f | Nota 2: %.1f | Média: %.1f | Situação: %s%n",
                            aluno.getNome(), aluno.getTurma(), aluno.getNota1(), aluno.getNota2(),
                            aluno.calcularMedia(), aluno.verificarSituacao());
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Aluno '" + nomeConsulta + "' não encontrado.");
            }
        }
    }

    public static void salvarRelatorioTxt(ArrayList<Aluno> listaAlunos) {
        System.out.println("\n--- SALVAR RELATÓRIO EM TXT ---");
        if (listaAlunos.size() == 0) {
            System.out.println("A lista está vazia. Cadastre alunos antes de gerar o relatório.");
            return;
        }

        String nomeArquivo = "relatorio.txt";

        Aluno[] arrayAlunos = new Aluno[listaAlunos.size()];
        for (int i = 0; i < listaAlunos.size(); i++) {
            arrayAlunos[i] = listaAlunos.get(i);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write("==========================================================================");
            writer.newLine();
            writer.write("                   RELATÓRIO ESCOLAR - SISTEMA JAVA");
            writer.newLine();
            writer.write("==========================================================================");
            writer.newLine();

            for (int i = 0; i < arrayAlunos.length; i++) {
                Aluno a = arrayAlunos[i];
                writer.write(String.format("%d. %s", (i + 1), a.formatarResumo()));
                writer.newLine();
            }

            writer.write("--------------------------------------------------------------------------");
            writer.newLine();
            writer.write("Total de alunos cadastrados: " + arrayAlunos.length);
            writer.newLine();
            writer.write(String.format("Média geral da turma: %.2f", calcularMediaGeral(listaAlunos)));
            writer.newLine();
            writer.write("==========================================================================");
            writer.newLine();

            System.out.println("Relatório salvo com sucesso no arquivo '" + nomeArquivo + "'!");

            exibirConteudoArquivo(nomeArquivo);

        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo relatório: " + e.getMessage());
        }
    }

    public static void exibirConteudoArquivo(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            return;
        }

        System.out.println("\nPré-visualização do arquivo gerado (" + nomeArquivo + "):");
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public static void alterarNotasAluno(Scanner scanner, ArrayList<Aluno> listaAlunos) {
        System.out.println("\n--- ALTERAR NOTAS DE ALUNO ---");
        if (listaAlunos.size() == 0) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.print("Digite o nome do aluno que terá as notas alteradas: ");
        String nomeBusca = scanner.nextLine().trim();

        for (Aluno aluno : listaAlunos) {
            if (aluno.getNome().equalsIgnoreCase(nomeBusca)) {
                System.out.printf("Notas atuais de %s -> Nota 1: %.1f | Nota 2: %.1f%n",
                        aluno.getNome(), aluno.getNota1(), aluno.getNota2());

                double novaNota1 = lerNotaValida(scanner, "Digite a nova Nota 1 (0 a 10): ");
                double novaNota2 = lerNotaValida(scanner, "Digite a nova Nota 2 (0 a 10): ");

                aluno.setNota1(novaNota1);
                aluno.setNota2(novaNota2);

                System.out.printf("Notas atualizadas! Nova média: %.1f | Situação: %s%n",
                        aluno.calcularMedia(), aluno.verificarSituacao());
                return;
            }
        }

        System.out.println("Aluno '" + nomeBusca + "' não encontrado.");
    }

    public static void exibirEstatisticasTurma(ArrayList<Aluno> listaAlunos) {
        System.out.println("\n--- ESTATÍSTICAS DA TURMA ---");
        int total = listaAlunos.size();
        System.out.println("Quantidade total de alunos cadastrados: " + total);

        if (total > 0) {
            double mediaGeral = calcularMediaGeral(listaAlunos);
            int aprovados = 0;
            int recuperacao = 0;

            for (Aluno a : listaAlunos) {
                if (a.calcularMedia() >= 6.0) {
                    aprovados++;
                } else {
                    recuperacao++;
                }
            }

            System.out.printf("Média geral da turma: %.2f%n", mediaGeral);
            System.out.println("Alunos aprovados: " + aprovados);
            System.out.println("Alunos em recuperação: " + recuperacao);
        }
    }

    public static double calcularMediaGeral(ArrayList<Aluno> listaAlunos) {
        if (listaAlunos.size() == 0) {
            return 0.0;
        }
        double somaMedias = 0.0;
        for (Aluno aluno : listaAlunos) {
            somaMedias += aluno.calcularMedia();
        }
        return somaMedias / listaAlunos.size();
    }

    public static double lerNotaValida(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(",", ".");
            try {
                double nota = Double.parseDouble(entrada);
                if (nota >= 0.0 && nota <= 10.0) {
                    if (nota == 0.0) {
                        return 0.0;
                    } else {
                        return nota;
                    }
                } else {
                    System.out.println("Valor inválido! A nota deve estar entre 0 e 10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número válido (ex: 7.5).");
            }
        }
    }

    public static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número inteiro correspondente ao menu.");
            }
        }
    }
}