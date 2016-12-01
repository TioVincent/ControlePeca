package util;

import java.sql.*;
import javax.swing.JOptionPane;


public class ConectorBD {
    
    private final static String BDDriver = "org.postgresql.Driver";    
    private static String BDNome = "jdbc:postgresql://localhost:5432/temp";
    private String BDUsuario = "postgres";
    private String BDSenha = "teste";
    private Connection conn = null;
    private Statement stat = null;
    
    {
        try {
            Class.forName(BDDriver);            
        } catch(ClassNotFoundException e){            
            JOptionPane.showMessageDialog(null, "Erro na localização do driver do BD", "Problema com Driver", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    public ConectorBD(){
        try {
            conn = DriverManager.getConnection(BDNome, BDUsuario, BDSenha);
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao tentar conectar ao DB", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Connection getConexao(){
        return this.conn;
    }
    
    public Statement getDBCommand(){
        try {
            stat = conn.createStatement();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao tentar conexão ao BD", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        } finally {
            if(stat == null){
                conn = null;
            }
            
            return stat;
        }
    }
    
    public void encerraConexao(){
        if(stat != null){
            try {
                stat.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        
        if(conn != null){
            try {
                conn.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
