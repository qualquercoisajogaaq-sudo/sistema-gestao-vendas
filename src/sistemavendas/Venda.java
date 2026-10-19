package sistemavendas;

import java.util.ArrayList;
import java.util.List;

public class Venda {
    private int id;
    private Cliente cliente;
    private List<Produto> produtos;
    private double valorTotal;

    public Venda(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.produtos = new ArrayList<>();
        this.valorTotal = 0.0;
    }

    public void adicionarProduto(Produto p, int quantidade) {
        if (p.getQuantidadeEstoque() >= quantidade) {
            p.darBaixaEstoque(quantidade);
            produtos.add(p);
            this.valorTotal += p.getPreco() * quantidade;
            System.out.println("-> Adicionado: " + p.getNome() + " (Qtd: " + quantidade + " | Subtotal: R$ " + (p.getPreco() * quantidade) + ")");
        } else {
            System.out.println("-> Não foi possível adicionar " + p.getNome() + " por falta de estoque!");
        }
    }

    public void exibirResumoVenda() {
        System.out.println("\n========== RESUMO DA VENDA #" + id + " ==========");
        System.out.println("Cliente: " + cliente.getNome() + " (CPF: " + cliente.getCpf() + ")");
        System.out.println("Itens Comprados: " + produtos.size());
        System.out.println("VALOR TOTAL: R$ " + String.format("%.2f", valorTotal));
        System.out.println("================================================");
    }
}