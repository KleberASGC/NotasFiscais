package conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class Conexao {

	private String url;
	private String usuario;
	private String senha;
	public Connection con;

	
	public Conexao() {
		url = "jdbc:postgresql://localhost:5432/NFes";
		usuario = "postgres";
		senha = "1q2w3e4r";
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, usuario, senha);
			System.out.println("Conex�o efetuada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
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
