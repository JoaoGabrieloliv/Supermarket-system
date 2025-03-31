package com.supermercado.sistema;

import com.supermercado.sistema.model.Produto;
import com.supermercado.sistema.model.Usuario;
import com.supermercado.sistema.service.ProdutoService;
import com.supermercado.sistema.service.UsuarioService;
import com.supermercado.sistema.util.CsvExporter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class SistemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ProdutoService produtoService, UsuarioService usuarioService) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            Usuario usuarioLogado = null;

            while (usuarioLogado == null) {
                System.out.println("=== SISTEMA DE SUPERMERCADO ===");
                System.out.println("1 - Cadastrar novo usuário");
                System.out.println("2 - Fazer login");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                String opcao = scanner.nextLine();

                switch (opcao) {
                    case "1" -> {
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();

                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        System.out.print("Senha: ");
                        String senha = scanner.nextLine();

                        Usuario novoUsuario = new Usuario();
                        novoUsuario.setNome(nome);
                        novoUsuario.setEmail(email);
                        novoUsuario.setSenha(senha);

                        usuarioService.cadastrar(novoUsuario);
                        System.out.println("✅ Usuário cadastrado com sucesso!");
                    }

                    case "2" -> {
                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        System.out.print("Senha: ");
                        String senha = scanner.nextLine();

                        Optional<Usuario> usuario = usuarioService.autenticar(email, senha);

                        if (usuario.isPresent()) {
                            usuarioLogado = usuario.get();
                            System.out.println("✅ Login realizado com sucesso. Bem-vindo, " + usuarioLogado.getNome() + "!");
                        } else {
                            System.out.println("❌ E-mail ou senha inválidos!");
                        }
                    }

                    case "0" -> {
                        System.out.println("Encerrando o sistema.");
                        return;
                    }

                    default -> System.out.println("❌ Opção inválida!");
                }
            }

            // Menu de produtos
            int opcaoProduto;
            do {
                exibirMenuProdutos();
                opcaoProduto = obterOpcao(scanner);

                switch (opcaoProduto) {
                    case 1 -> adicionarProduto(scanner, produtoService);
                    case 2 -> listarProdutos(produtoService);
                    case 3 -> buscarProdutoPorId(scanner, produtoService);
                    case 4 -> atualizarProduto(scanner, produtoService);
                    case 5 -> deletarProduto(scanner, produtoService);
                    case 6 -> gerarRelatorioCSV(produtoService);
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("❌ Opção inválida!");
                }

            } while (opcaoProduto != 0);

            scanner.close();
        };
    }

    private void exibirMenuProdutos() {
        System.out.println("\n====== MENU DE PRODUTOS ======");
        System.out.println("1 - Adicionar produto");
        System.out.println("2 - Listar produtos");
        System.out.println("3 - Buscar produto por ID");
        System.out.println("4 - Atualizar produto");
        System.out.println("5 - Deletar produto");
        System.out.println("6 - Gerar relatório CSV");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int obterOpcao(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void adicionarProduto(Scanner scanner, ProdutoService produtoService) {
        Produto produto = lerDadosDoProduto(scanner, new Produto());
        produtoService.salvar(produto);
        System.out.println("✅ Produto cadastrado com sucesso!");
    }

    private void listarProdutos(ProdutoService produtoService) {
        List<Produto> produtos = produtoService.listarTodos();
        System.out.println("\n=== Lista de Produtos ===");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            for (Produto p : produtos) {
                exibirProduto(p);
            }
        }
    }

    private void buscarProdutoPorId(Scanner scanner, ProdutoService produtoService) {
        System.out.print("Digite o ID do produto: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<Produto> produtoOpt = produtoService.buscarPorId(id);
        produtoOpt.ifPresentOrElse(
            this::exibirProduto,
            () -> System.out.println("❌ Produto não encontrado.")
        );
    }

    private void atualizarProduto(Scanner scanner, ProdutoService produtoService) {
        System.out.print("Digite o ID do produto a ser atualizado: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<Produto> produtoOpt = produtoService.buscarPorId(id);
        if (produtoOpt.isPresent()) {
            Produto produtoAtualizado = lerDadosDoProduto(scanner, produtoOpt.get());
            produtoService.salvar(produtoAtualizado);
            System.out.println("✅ Produto atualizado com sucesso!");
        } else {
            System.out.println("❌ Produto não encontrado.");
        }
    }

    private void deletarProduto(Scanner scanner, ProdutoService produtoService) {
        System.out.print("Digite o ID do produto a ser deletado: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<Produto> produtoOpt = produtoService.buscarPorId(id);
        if (produtoOpt.isPresent()) {
            produtoService.deletar(id);
            System.out.println("✅ Produto removido com sucesso!");
        } else {
            System.out.println("❌ Produto não encontrado.");
        }
    }

    private void gerarRelatorioCSV(ProdutoService produtoService) {
        List<Produto> produtos = produtoService.listarTodos();
        String caminho = "produtos.csv";
        CsvExporter.exportarProdutos(produtos, caminho);
    }

    private Produto lerDadosDoProduto(Scanner scanner, Produto produto) {
        System.out.print("Nome: ");
        produto.setNome(scanner.nextLine());

        System.out.print("Categoria: ");
        produto.setCategoria(scanner.nextLine());

        System.out.print("Quantidade: ");
        produto.setQuantidade(Integer.parseInt(scanner.nextLine()));

        System.out.print("Preço de Compra (ex: 2.33 ou 2,33): ");
        String precoCompraStr = scanner.nextLine().replace(",", ".");
        produto.setPrecoCompra(Double.parseDouble(precoCompraStr));

        System.out.print("Preço de Venda (ex: 3.99 ou 3,99): ");
        String precoVendaStr = scanner.nextLine().replace(",", ".");
        produto.setPrecoVenda(Double.parseDouble(precoVendaStr));

        return produto;
    }

    private void exibirProduto(Produto p) {
        System.out.printf("ID: %d | Nome: %s | Categoria: %s | Quantidade: %d | Compra: R$ %.2f | Venda: R$ %.2f\n",
                p.getId(), p.getNome(), p.getCategoria(), p.getQuantidade(), p.getPrecoCompra(), p.getPrecoVenda());
    }
}
