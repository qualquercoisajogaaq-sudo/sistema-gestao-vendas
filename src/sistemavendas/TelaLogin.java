package sistemavendas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TelaLogin extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnCancelar;

    public TelaLogin() {
        setTitle("Sistema de Vendas - Autenticação");
        setSize(420, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Sistema de Vendas - Autenticação", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setBounds(20, 15, 360, 25);
        add(lblTitulo);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setFont(new Font("Arial", Font.BOLD, 13));
        lblLogin.setBounds(40, 60, 80, 25);
        add(lblLogin);

        txtLogin = new JTextField("admin");
        txtLogin.setBounds(110, 60, 240, 28);
        add(txtLogin);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Arial", Font.BOLD, 13));
        lblSenha.setBounds(40, 105, 80, 25);
        add(lblSenha);

        txtSenha = new JPasswordField("123");
        txtSenha.setBounds(110, 105, 240, 28);
        add(txtSenha);

        btnEntrar = new JButton("ENTRAR");
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 13));
        btnEntrar.setBounds(70, 165, 120, 35);
        add(btnEntrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setBounds(210, 165, 120, 35);
        add(btnCancelar);

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = txtLogin.getText().trim();
                String senha = new String(txtSenha.getPassword()).trim();

                if (!login.isEmpty() && !senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Bem-vindo ao Sistema de Vendas!");
                    new TelaProdutos().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new TelaLogin().setVisible(true);
    }
}