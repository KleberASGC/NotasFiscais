package conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Conexao {

	private String url;
	private String usuario;
	private String senha;
	public Connection con;

	
	public Conexao(String[] credenciaisBanco) {
		url = "jdbc:postgresql://localhost:" +  credenciaisBanco[0] + "/" + credenciaisBanco[1];
		usuario = credenciaisBanco[2];
		senha = credenciaisBanco[3];
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, usuario, senha);
			System.out.println("Conexão efetuada com sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(),
                    "Erro ao tentar conectar com o Banco de Dados.\n" + e.getMessage());
		}
	}

	
	public int executaSQL(String sql) {
		try {

			Statement stm = con.createStatement();
			int res = stm.executeUpdate(sql);
			con.close();
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

}
