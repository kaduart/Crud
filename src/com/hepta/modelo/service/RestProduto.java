package com.hepta.modelo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.hepta.modelo.entity.Produto;
import com.hepta.modelo.persistence.ProdutoDAO;

@Path("/produto")
public class RestProduto {

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	protected void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listar")
	public Response listarProdutos() throws Exception {

		ProdutoDAO dao = new ProdutoDAO();
		List<Produto> listaProdutos = dao.getProdutos();
		GenericEntity<List<Produto>> entity = new GenericEntity<List<Produto>>(listaProdutos) {

		};
		return Response.ok().entity(entity).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/salvar")
	public Response salvarProduto(Produto produto) {
		if (produto != null) {
			System.out.println(produto.getNome());
			System.out.println(produto.getFabricante());
			System.out.println(produto.getQuantidade());
			System.out.println(produto.getValor());

			ProdutoDAO dao = new ProdutoDAO();
			try {
				dao.save(produto);
			} catch (Exception e) {
				e.printStackTrace();
				return Response.ok().status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		return Response.ok().build();
	}

	@GET
	@Path("/editar/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduto(@PathParam("id") int id) {
		ProdutoDAO dao = new ProdutoDAO();
		Produto teste = null;
		try {
			teste = dao.find(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok().entity(teste).build();
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(Produto produto) {

		ProdutoDAO dao = new ProdutoDAO();
		try {
			dao.update(produto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok().build();
	}

	@DELETE
	@Path("/deletar/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id) {

		ProdutoDAO dao = new ProdutoDAO();
		try {
			dao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
