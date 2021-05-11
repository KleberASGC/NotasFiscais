package Acoes;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import conexoes.Conexao;

public class InsercaoBanco {

    public InsercaoBanco(String assinatura, String sql)  {

        Conexao con = new Conexao();
       
        int resposta = con.executaSQL(assinatura + sql);
        
        if (resposta <= 0) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Erro ao inserir o arquivo: no Banco de Dados.");
        }
    }

}
