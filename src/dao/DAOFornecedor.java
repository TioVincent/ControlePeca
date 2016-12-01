package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Fornecedor;

public class DAOFornecedor {

    private PreparedStatement comando = null;
    private Connection conexao = null;

    private static final String SQLLista
            = "SELECT * FROM fornecedores";
    private static final String SQLConsultaPorCodigo
            = "SELECT * FROM fornecedores WHERE codigo = ?";
    private static final String SQLConsultaPorNome
            = "SELECT * FROM fornecedores WHERE upper(nome) like upper(?)";
    private static final String SQLAtualiza
            = "UPDATE fornecedores SET nome = ?, cidade = ? WHERE codigo = ?";
    private static final String SQLRemove
            = "DELETE FROM fornecedores WHERE codigo = ?";
    private static final String SQLInsere
            = "INSERT INTO fornecedores (codigo, nome, cidade) VALUES (?, ?, ?)";

    public DAOFornecedor(Connection conexao) {
        this.conexao = conexao;
    }

    public void setConnexao(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Fornecedor> consultaPorNome(String nome) throws SQLException {

        List<Fornecedor> fornecedores = new ArrayList<>();
        comando = conexao.prepareStatement(SQLConsultaPorNome);
        comando.setString(1, "%" + nome + "%");
        ResultSet resultadoConsulta = comando.executeQuery();

        while (resultadoConsulta.next()) {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setCodigo(resultadoConsulta.getInt("codigo"));
            fornecedor.setNome(resultadoConsulta.getString("nome"));
            fornecedor.setCidade(resultadoConsulta.getString("cidade"));
            fornecedores.add(fornecedor);
        }

        return fornecedores;
    }

    public Fornecedor consulta(int codigo) throws SQLException {
        Fornecedor resultado = null;
        comando = conexao.prepareStatement(SQLConsultaPorCodigo);
        comando.setInt(1, codigo);
        ResultSet resultadoConsulta = comando.executeQuery();

        if (resultadoConsulta.next()) {
            resultado = new Fornecedor();
            resultado.setCodigo(codigo);
            resultado.setNome(resultadoConsulta.getString("nome"));
            resultado.setCidade(resultadoConsulta.getString("cidade"));
        }

        return resultado;
    }

    public void insere(Fornecedor fornecedor) throws SQLException {
        comando = conexao.prepareStatement(SQLInsere);
        comando.setInt(1, fornecedor.getCodigo());
        comando.setString(2, fornecedor.getNome());
        comando.setString(3, fornecedor.getCidade());
        int linhas = comando.executeUpdate();
    }

    public void atualiza(Fornecedor fornecedor) throws SQLException {
        comando = conexao.prepareStatement(SQLAtualiza);
        comando.setString(1, fornecedor.getNome());
        comando.setString(2, fornecedor.getCidade());
        comando.setInt(3, fornecedor.getCodigo());
        comando.executeUpdate();
    }

    public void remove(Fornecedor fornecedor) throws SQLException {
        comando = conexao.prepareStatement(SQLRemove);
        comando.setInt(1, fornecedor.getCodigo());
        comando.executeUpdate();
    }

    public List<Fornecedor> lista() throws SQLException {
        ResultSet resultadoConsulta = conexao.createStatement().executeQuery(SQLLista);
        List<Fornecedor> fornecedores = new ArrayList();

        while (resultadoConsulta.next()) {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setCodigo(resultadoConsulta.getInt("codigo"));
            fornecedor.setNome(resultadoConsulta.getString("nome"));
            fornecedor.setCidade(resultadoConsulta.getString("cidade"));

            fornecedores.add(fornecedor);
        }

        return fornecedores;
    }
}
