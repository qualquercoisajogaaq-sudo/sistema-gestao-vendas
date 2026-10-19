package sistemavendas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TelaProdutos extends JFrame {

    private JTextField txtNome;
    private JTextField txtPreco;
    private JTextField txtEstoque;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnExcluir;
    private JButton btnIrVendas;
    private ProdutoDAO dao = new ProdutoDAO();

    public TelaProdutos() {
        setTitle("Cadastro e Controle de Estoque (MySQL)");
        setSize(650, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Cadastro e Controle de Estoque");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(30, 15, 300, 25);
        add(lblTitulo);

        JLabel lblNome = new JLabel("Nome do Produto:");
        lblNome.setBounds(30, 55, 130, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(160, 55, 430, 28);
        add(txtNome);

        JLabel lblPreco = new JLabel("Preço (R$):");
        lblPreco.setBounds(30, 95, 130, 25);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(160, 95, 150, 28);
        add(txtPreco);

        JLabel lblEstoque = new JLabel("Qtd Estoque:");
        lblEstoque.setBounds(340, 95, 100, 25);
        add(lblEstoque);

        txtEstoque = new JTextField();
        txtEstoque.setBounds(440, 95, 150, 28);
        add(txtEstoque);

        btnSalvar = new JButton("Salvar Produto");
        btnSalvar.setBounds(30, 140, 140, 32);
        add(btnSalvar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(180, 140, 100, 32);
        add(btnLimpar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(290, 140, 100, 32);
        add(btnExcluir);

        btnIrVendas = new JButton("Ir para Tela de Vendas >>");
        btnIrVendas.setBounds(410, 140, 180, 32);
        add(btnIrVendas);

        modelo = new DefaultTableModel(new Object[]{"ID", "Nome do Produto", "Preço", "Estoque"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 190, 560, 220);
        add(scroll);

        atualizarTabela();

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = txtNome.getText().trim();
                    double preco = Double.parseDouble(txtPreco.getText().trim());
                    int estoque = Integer.parseInt(txtEstoque.getText().trim());

                    if (nome.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Digite o nome do produto!");
                        return;
                    }

                    dao.cadastrar(new Produto(0, nome, preco, estoque));
                    atualizarTabela();
                    limparCampos();
                    JOptionPane.showMessageDialog(null, "Produto salvo no banco de dados com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar no banco: " + ex.getMessage());
                }
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    int id = (int) tabela.getValueAt(linha, 0);
                    try {
                        dao.excluir(id);
                        atualizarTabela();
                        JOptionPane.showMessageDialog(null, "Produto excluído do banco com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um produto na tabela para excluir!");
                }
            }
        });

        btnIrVendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaVenda().setVisible(true);
                dispose();
            }
        });
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        try {
            List<Produto> lista = dao.listar();
            for (Produto p : lista) {
                modelo.addRow(new Object[]{p.getId(), p.getNome(), String.format("%.2f", p.getPreco()), p.getQuantidadeEstoque()});
            }
        } catch (Exception ex) {
            System.out.println("Erro ao carregar tabela: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtPreco.setText("");
        txtEstoque.setText("");
        txtNome.requestFocus();
    }
}