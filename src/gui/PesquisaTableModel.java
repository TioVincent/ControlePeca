package gui;

import dao.DAOFornecedor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Peca;
import model.Fornecedor;
import util.ConectorBD;

public class PesquisaTableModel extends AbstractTableModel {

    // Atributos ACESSÓRIOS, relacionados ao funcionamento do TableModel
    private static final int CODIGO = 0;
    private static final int NOME = 1;
    private static final int PRECO = 2;
    private static final int FORNECEDOR = 3;
    private static final String[] nomeColunas = {"Código", "Nome", "Preço", "Fornecedor"};
    private static final Class[] classeColunas = {Integer.class, String.class, Double.class, String.class};

    private List<Peca> pecas;
    private DAOFornecedor daoFornecedor;

    public PesquisaTableModel() {
        try {
            ConectorBD conexao = new ConectorBD();
            pecas = new ArrayList<>();
            daoFornecedor = new DAOFornecedor(conexao.getConexao());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Peca> getPecas() {
        return pecas;
    }

    public void setPecas(List<Peca> pecas) {
        this.pecas = pecas;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return nomeColunas[columnIndex];
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return classeColunas[columnIndex];
    }

    @Override
    public int getRowCount() {
        return pecas.size();
    }

    @Override
    public int getColumnCount() {
        return nomeColunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object conteudo = null;
        Peca peca = pecas.get(rowIndex);

        Fornecedor fornecedor = null;
        try {
            int codFornecedor = peca.getFornecedor();
            fornecedor = daoFornecedor.consulta(codFornecedor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        switch (columnIndex) {
            case (CODIGO):
                conteudo = (Integer) peca.getCodigo();
                break;
            case (NOME):
                conteudo = peca.getNome();
                break;
            case (PRECO):
                conteudo = peca.getPreco();
                break;
            case (FORNECEDOR):
                if (fornecedor != null) {
                    conteudo = fornecedor.getNome() + " - " + fornecedor.getCidade();
                }
                break;
        }

        return conteudo;
    }

}
