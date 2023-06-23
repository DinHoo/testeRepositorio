package Codigo;

import Codigo.classeDado;
import Conexao.Conexao;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.imageio.ImageIO;

/**
 *
 * @author Bruno Marcolin
 */

public class imagemToBanco {
    
    public static void main(String[] args) throws SQLException {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\Bruno\\Documents\\NetBeansProjects\\projeto_BLOB\\imagem\\lampada.jpeg"));
            System.out.println("Imagem carregada com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpeg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] imageData = baos.toByteArray();
        
        classeDado aux = new classeDado();
        aux.setRegistro(2);
        aux.setCodigoImagem(imageData);
        
        //old
//        try {
//            Connection conexao = Conexao.conecta();
//            //criar uma condição do Java interagir com o SQL
//            Statement query = conexao.createStatement();
//            
//            String sql = "insert into imagens values(null, "+aux.getRegistro()+", '"+aux.getCodigoImagem()+"');";
//            query.execute(sql);
//            System.out.println("rodou o sql");
//            query.close();
//            
//        } catch (Exception e) {
//            throw new SQLException("Erro ao inserir os dados no BD.\nCausa: "+e.getMessage());
//        }

        //new
        try (Connection conexao = Conexao.conecta();
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO imagens (registro, corpo_img) VALUES (?, ?)")) {
            
            statement.setInt(1, aux.getRegistro());
            statement.setBytes(2, aux.getCodigoImagem());
            
            statement.executeUpdate();
            System.out.println("Dados inseridos com sucesso.");
            
        } catch (Exception e) {
            throw new SQLException("Erro ao inserir os dados no BD.\nCausa: " + e.getMessage());
        }
    }
    
    

}
