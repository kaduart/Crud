package com.hepta.modelo.persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hepta.modelo.entity.Produto;

public class ProdutoDAO implements Serializable{
	private static final long serialVersionUID = 1L;

	public ProdutoDAO() {

	}

	public Produto save(Produto produto) throws Exception {	
		Connection db = ConnectionJDBCManager.getDBConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO produtos");
		sql.append("(nome, fabricante, quantidade, valor)");
		sql.append("VALUES (?,?,?,?);");

		try {
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, produto.getNome());
			pstmt.setString(2, produto.getFabricante());
			pstmt.setInt(3, produto.getQuantidade() != null ? produto.getQuantidade() : 0);
			pstmt.setBigDecimal(4, produto.getValor());

			pstmt.executeUpdate();

			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				produto.setId(generatedKeys.getLong(1));
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return produto;
	}

	public Produto find(int id) throws Exception {
		Connection db = ConnectionJDBCManager.getDBConnection();
		Produto produto = new Produto();

		PreparedStatement pstmt = null;
		ResultSet result = null;

		try {
			pstmt = db.prepareStatement("SELECT * FROM produtos WHERE id=? ");
			pstmt.setInt(1, id);
			result = pstmt.executeQuery();

			while (result.next()) {
				produto.setId(result.getLong("id"));
				produto.setNome(result.getString("nome"));
				produto.setFabricante(result.getString("fabricante"));
				produto.setQuantidade(result.getInt("quantidade"));
				produto.setValor(result.getBigDecimal("valor"));
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				;
			pstmt.close();
			db.close();
		}
		return produto;
	}

	public List<Produto> getProdutos() throws Exception {
		Connection db = ConnectionJDBCManager.getDBConnection();
		List<Produto> arrayList = new ArrayList<>();

		PreparedStatement pstmt = null;
		ResultSet result = null;

		try {
			pstmt = db.prepareStatement("SELECT * FROM produtos");
			result = pstmt.executeQuery();

			while (result.next()) {
				Produto produto = new Produto();
				produto.setId(result.getLong("id"));
				produto.setNome(result.getString("nome"));
				produto.setFabricante(result.getString("fabricante"));
				produto.setQuantidade(result.getInt("quantidade"));
				produto.setValor(result.getBigDecimal("valor"));

				arrayList.add(produto);
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null) {
				pstmt.close();
				db.close();
			}
		}
		return arrayList;
	}

	public Produto update(Produto produto) throws Exception {
		Connection db = ConnectionJDBCManager.getDBConnection();
		PreparedStatement pstmt = null;

        String querySql = "update produtos set nome=" + "'" + produto.getNome() + 
        		"', fabricante=" + "'" + produto.getFabricante() + 
        		"', quantidade=" + "'" + produto.getQuantidade() + 
        		"', valor=" + "'" + produto.getValor() + "'" + 
        		" where id=" + produto.getId();
	
		try {
			pstmt = db.prepareStatement("UPDATE FROM produtos WHERE id=?");
			pstmt.executeUpdate(querySql);

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return null;
	}

	public void delete(int id) throws Exception {
		Connection db = ConnectionJDBCManager.getDBConnection();
		PreparedStatement pstmt = null;

		try {
			pstmt = db.prepareStatement("DELETE FROM produtos WHERE id=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		if (pstmt != null)
			pstmt.close();
	}
}
