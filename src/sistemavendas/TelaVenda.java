package sistemavendas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TelaVenda extends JFrame {

    private JTextField txtCliente;
    private JComboBox<String> cbProdutos;
    private JTextField txtQtd;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JLabel lblTotal;
    private JButton btnAdicionar;
    private JButton btnFinalizar;
    private JButton btnVoltar;

    private List<Produto> listaBanco = new ArrayList<>();
    private ProdutoDAO dao = new ProdutoDAO();
    private double totalVenda = 0.0;
    private int itemContador = 1;

    public TelaVenda() {
        setTitle("Nova Venda (Integração MySQL)");
        setSize(700, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Nova Venda");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(30, 15, 200, 25);
        add(lblTitulo);

        JLabel lblCli = new JLabel("Cliente:");
        lblCli.setBounds(30, 55, 80, 25);
        add(lblCli);

        txtCliente = new JTextField("Carlos da Silva - 123.456.789-00");
        txtCliente.setBounds(100, 55, 540, 28);
        add(txtCliente);

        JLabel lblProd = new JLabel("Produto:");
        lblProd.setBounds(30, 95, 80, 25);
        add(lblProd);

        cbProdutos = new JComboBox<>();
        carregarComboProdutos();
        cbProdutos.setBounds(100, 95, 340, 28);
        add(cbProdutos);

        JLabel lblQtd = new JLabel("Qtd:");
        lblQtd.setBounds(460, 95, 40, 25);
        add(lblQtd);

        txtQtd = new JTextField("1");
        txtQtd.setBounds(500, 95, 40, 28);
        add(txtQtd);

        btnAdicionar = new JButton("+ Adicionar");
        btnAdicionar.setBounds(550, 95, 90, 28);
        add(btnAdicionar);

        modelo = new DefaultTableModel(new Object[]{"Item", "Produto", "Qtd", "Unitário", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 140, 610, 190);
        add(scroll);

        lblTotal = new JLabel("Total da Venda: R$ 0,00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setBounds(30, 345, 300, 30);
        add(lblTotal);

        btnFinalizar = new JButton("FINALIZAR VENDA");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 13));
        btnFinalizar.setBounds(30, 390, 180, 35);
        add(btnFinalizar);

        btnVoltar = new JButton("<< Voltar para Produtos");
        btnVoltar.setBounds(230, 390, 190, 35);
        add(btnVoltar);

        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = cbProdutos.getSelectedIndex();
                    int qtd = Integer.parseInt(txtQtd.getText().trim());

                    if (index >= 0 && qtd > 0) {
                        Produto p = listaBanco.get(index);

                        if (p.getQuantidadeEstoque() >= qtd) {
                            p.darBaixaEstoque(qtd);
                            dao.atualizarEstoque(p.getId(), p.getQuantidadeEstoque()); // Baixa real no banco

                            double subtotal = p.getPreco() * qtd;
                            totalVenda += subtotal;

                            modelo.addRow(new Object[]{itemContador++, p.getNome(), qtd, String.format("%.2f", p.getPreco()), String.format("%.2f", subtotal)});
                            lblTotal.setText("Total da Venda: R$ " + String.format("%.2f", totalVenda));
                        } else {
                            JOptionPane.showMessageDialog(null, "Estoque insuficiente no banco! Disponível: " + p.getQuantidadeEstoque());
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Informe uma quantidade válida!");
                }
            }
        });

        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modelo.getRowCount() > 0) {
                    JOptionPane.showMessageDialog(null, "Venda finalizada com sucesso!\nEstoque atualizado no banco de dados.");
                    modelo.setRowCount(0);
                    totalVenda = 0.0;
                    itemContador = 1;
                    lblTotal.setText("Total da Venda: R$ 0,00");
                    carregarComboProdutos();
                } else {
                    JOptionPane.showMessageDialog(null, "Adicione pelo menos um produto à venda!");
                }
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaProdutos().setVisible(true);
                dispose();
            }
        });
    }

    private void carregarComboProdutos() {
        cbProdutos.removeAllItems();
        try {
            listaBanco = dao.listar();
            for (Produto p : listaBanco) {
                cbProdutos.addItem(p.getNome() + " (Estoque: " + p.getQuantidadeEstoque() + " | R$ " + p.getPreco() + ")");
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar combobox: " + e.getMessage());
        }
    }
}