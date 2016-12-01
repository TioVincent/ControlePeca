package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Peca;

public class DAOPeca {

    private PreparedStatement comando = null;
    private Connection conexao = null;

    private static final String SQLLista
            = "SELECT * FROM pecas";
    private static final String SQLConsultaPorCodigo
            = "SELECT * FROM pecas WHERE codigo = ?";
    private static final String SQLConsultaPorNome
            = "SELECT * FROM pecas WHERE upper(nome) like upper(?) order by nome";
    private static final String SQLConsultaPorNomeOuCodigo
            = "SELECT * FROM pecas WHERE upper(nome) like upper(?) and codigo = ? order by nome";
    private static final String SQLAtualiza
            = "UPDATE pecas SET nome = ?, preco = ?, cod_fornecedor = ? WHERE codigo = ?";
    private static final String SQLRemove
            = "DELETE FROM pecas WHERE codigo = ?";
    private static final String SQLInsere
            = "INSERT INTO pecas (codigo, nome, preco, cod_fornecedor) VALUES (?, ?, ?, ?)";

    public DAOPeca(Connection conexao) {
        this.conexao = conexao;
    }

    public void setConnexao(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Peca> consultaPorNomeOuCodigo(String codigoString, String nome) throws SQLException {

        List<Peca> pecas = new ArrayList<>();
        Integer codigo = null;

        try {
            codigo = Integer.parseInt(codigoString);
            
        } catch (Exception ex) {
            ///ex.printStackTrace();
        }

        if (codigo != null) {
            comando = conexao.prepareStatement(SQLConsultaPorNomeOuCodigo);
            comando.setString(1, "%" + nome + "%");
            comando.setInt(2, codigo);
        } else {
            comando = conexao.prepareStatement(SQLConsultaPorNome);
            comando.setString(1, "%" + nome + "%");

        }
        ResultSet resultadoConsulta = comando.executeQuery();

        while (resultadoConsulta.next()) {
            Peca peca = new Peca();
            peca.setCodigo(resultadoConsulta.getInt("codigo"));
            peca.setNome(resultadoConsulta.getString("nome"));
            peca.setPreco(resultadoConsulta.getDouble("preco"));
            peca.setFornecedor(resultadoConsulta.getInt("cod_fornecedor"));
            pecas.add(peca);
        }

        return pecas;
    }

    public Peca consulta(int codigo) throws SQLException {
        Peca resultado = null;
        comando = conexao.prepareStatement(SQLConsultaPorCodigo);
        comando.setInt(1, codigo);
        ResultSet resultadoConsulta = comando.executeQuery();

        if (resultadoConsulta.next()) {
            resultado = new Peca();
            resultado.setCodigo(codigo);
            resultado.setNome(resultadoConsulta.getString("nome"));
            resultado.setPreco(resultadoConsulta.getDouble("preco"));
            resultado.setFornecedor(resultadoConsulta.getInt("cod_fornecedor"));
        }

        return resultado;
    }

    public void insere(Peca peca) throws SQLException {
        comando = conexao.prepareStatement(SQLInsere);
        comando.setInt(1, peca.getCodigo());
        comando.setString(2, peca.getNome());
        comando.setDouble(3, peca.getPreco());
        comando.setInt(4, peca.getFornecedor());
        int linhas = comando.executeUpdate();
    }

    public void atualiza(Peca peca) throws SQLException {
        comando = conexao.prepareStatement(SQLAtualiza);
        comando.setString(1, peca.getNome());
        comando.setDouble(2, peca.getPreco());
        comando.setInt(3, peca.getFornecedor());
        comando.setInt(4, peca.getCodigo());
        comando.executeUpdate();
    }

    public void remove(Peca peca) throws SQLException {
        comando = conexao.prepareStatement(SQLRemove);
        comando.setInt(1, peca.getCodigo());
        comando.executeUpdate();
    }

    public List<Peca> lista() throws SQLException {
        ResultSet resultadoConsulta = conexao.createStatement().executeQuery(SQLLista);
        List<Peca> pecas = new ArrayList();

        while (resultadoConsulta.next()) {
            Peca peca = new Peca();
            peca.setCodigo(resultadoConsulta.getInt("codigo"));
            peca.setNome(resultadoConsulta.getString("nome"));
            peca.setPreco(resultadoConsulta.getDouble("preco"));
            peca.setFornecedor(resultadoConsulta.getInt("cod_fornecedor"));
            pecas.add(peca);
        }

        return pecas;
    }
}
