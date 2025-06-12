package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection recuperaConexao() {
        try {
            String sgbd = "mysql";
            String endereco = "localhost";
            String porta = "3306";
            String bd = "alertacidadao_db";
            String usuario = "root";
            String senha = "senha";

            // URL de conexão com a porta incluída
            String url = "jdbc:" + sgbd + "://" + endereco + ":" + porta + "/" + bd;

            Connection connection = DriverManager.getConnection(url, usuario, senha);

            return connection;
        } catch (SQLException e) {
            // É uma boa prática imprimir o erro para facilitar a depuração
            System.err.println("Falha na conexão com o banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
