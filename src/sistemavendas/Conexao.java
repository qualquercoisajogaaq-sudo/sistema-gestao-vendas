package sistemavendas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public Connection getConexao() {
        try {
            // Lembre-se de colocar a SUA senha do MySQL no lugar de 'root' se for diferente
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sistemavendas_bd", 
                "root", 
                "root"
            );
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco: " + e.getMessage());
            return null;
        }
    }
}