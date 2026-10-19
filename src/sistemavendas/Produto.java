package sistemavendas;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private int quantidadeEstoque;

    public Produto(int id, String nome, double preco, int quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }

    public void darBaixaEstoque(int qtd) {
        if (qtd <= this.quantidadeEstoque) {
            this.quantidadeEstoque -= qtd;
        } else {
            System.out.println("Estoque insuficiente para o produto: " + nome);
        }
    }
}