package sistemavendas;

import java.util.ArrayList;
import java.util.List;

public class Dados {
    public static List<Produto> produtos = new ArrayList<>();

    // Inicializa com alguns produtos de exemplo para teste
    static {
        produtos.add(new Produto(1, "Notebook Dell", 3500.00, 10));
        produtos.add(new Produto(2, "Mouse Sem Fio", 120.00, 25));
        produtos.add(new Produto(3, "Teclado Mecanico", 250.00, 5));
    }
}