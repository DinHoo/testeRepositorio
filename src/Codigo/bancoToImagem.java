
package Codigo;

import Conexao.Conexao;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Bruno Marcolin
 */
public class bancoToImagem {
    public static void main(String[] args) throws SQLException{
    
        classeDado aux = new classeDado();
        try {
            Connection conexao = Conexao.conecta();
            //criar uma condição do Java interagir com o SQL
            Statement query = conexao.createStatement();
            
            String sql = "SELECT * FROM imagens;";
            //pegar os dados da tabela
            ResultSet rs = query.executeQuery(sql);
            
            while (rs.next()) {
                aux.setId(rs.getInt("id"));
                aux.setRegistro(rs.getInt("registro"));
                aux.setCodigoImagem(rs.getBytes("corpo_img"));
            }
                   
            System.out.println("rodou o sql");
            query.close();
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao ler os dados no BD.\nCausa: "+e.getMessage());
        }
    
        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Bruno\\Documents\\NetBeansProjects\\projeto_BLOB\\imagem\\lampada2.jpeg")) {
            fos.write(aux.getCodigoImagem());
            //fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
}
