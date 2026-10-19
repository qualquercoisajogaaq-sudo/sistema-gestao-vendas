package sistemavendas;

public class SistemaVendas {

    public static void main(String[] args) {
        System.out.println("=== PROTÓTIPO DO SISTEMA DE GESTÃO DE VENDAS ===");
        System.out.println("Demonstração da funcionalidade de registro de venda\n");

        // 1. Cadastrando produtos no protótipo
        Produto p1 = new Produto(1, "Notebook Dell", 3500.00, 10);
        Produto p2 = new Produto(2, "Mouse Sem Fio", 120.00, 25);
        Produto p3 = new Produto(3, "Teclado Mecânico", 250.00, 5);

        // 2. Cadastrando cliente
        Cliente c1 = new Cliente(1, "Carlos da Silva", "123.456.789-00", "(11) 98888-7777");

        // 3. Executando o fluxo de uma venda
        System.out.println("Iniciando venda para o cliente: " + c1.getNome());
        Venda venda = new Venda(101, c1);

        venda.adicionarProduto(p1, 1);
        venda.adicionarProduto(p2, 2);
        venda.adicionarProduto(p3, 1);

        // 4. Exibindo o resultado da operação
        venda.exibirResumoVenda();

        System.out.println("\nProtótipo executado com sucesso e estoque atualizado!");
    }
}