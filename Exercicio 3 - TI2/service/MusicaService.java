package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.MusicasDAO;
import model.Musica;
import spark.Request;
import spark.Response;


public class MusicaService {

	private ProdutoDAO produtoDAO = new ProdutoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_DESCRICAO = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	
	public MusicaService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Produto(), FORM_ORDERBY_DESCRICAO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Produto(), orderBy);
	}

	
	public void makeForm(int tipo, Produto produto, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umaMusica = "";
		if (tipo != FORM_INSERT) {
            umaMusica += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umaMusica += "\t\t<tr>";
            umaMusica += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/produto/list/1\">Nova Música</a></b></font></td>";
            umaMusica += "\t\t</tr>";
            umaMusica += "\t</table>";
            umaMusica += "\t<br>";
        }
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/produto/";
			String name, descricao, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Produto";
				descricao = "leite, pão, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + produto.getID();
				name = "Atualizar Produto (ID " + produto.getID() + ")";
				descricao = produto.getDescricao();
				buttonLabel = "Atualizar";
			}
			umaMusica += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umaMusica += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umaMusica += "\t\t<tr>";
			umaMusica += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umaMusica += "\t\t</tr>";
			umaMusica += "\t\t<tr>";
			umaMusica += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umaMusica += "\t\t</tr>";
			umaMusica += "\t\t<tr>";
			umaMusica += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\"" + descricao + "\"></td>";
			umaMusica += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\"" + produto.getPreco() + "\"></td>";
			umaMusica += "\t\t\t<td>Quantidade: <input class=\"input--register\" type=\"text\" name=\"quantidade\" value=\"" + produto.getQuantidade() + "\"></td>";
			umaMusica += "\t\t</tr>";
			umaMusica += "\t\t<tr>";
			umaMusica += "\t\t\t<td>&nbsp;Data de fabricação: <input class=\"input--register\" type=\"text\" name=\"dataFabricacao\" value=\"" + produto.getDataFabricacao().toString() + "\"></td>";
			umaMusica += "\t\t\t<td>Data de validade: <input class=\"input--register\" type=\"text\" name=\"dataValidade\" value=\"" + produto.getDataValidade().toString() + "\"></td>";
			umaMusica += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td>";
			umaMusica += "\t\t</tr>";
			umaMusica += "\t</table>";
			umaMusica += "\t</form>";
			} else if (tipo == FORM_DETAIL) {
				umaMusica += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
				umaMusica += "\t\t<tr>";
				umaMusica += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Produto (ID " + produto.getID() + ")</b></font></td>";
				umaMusica += "\t\t</tr>";
				umaMusica += "\t\t<tr>";
				umaMusica += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
				umaMusica += "\t\t</tr>";
				umaMusica += "\t\t<tr>";
				umaMusica += "\t\t\t<td>&nbsp;Descrição: " + produto.getDescricao() + "</td>";
				umaMusica += "\t\t\t<td>Preco: " + produto.getPreco() + "</td>";
				umaMusica += "\t\t\t<td>Quantidade: " + produto.getQuantidade() + "</td>";
				umaMusica += "\t\t</tr>";
				umaMusica += "\t\t<tr>";
				umaMusica += "\t\t\t<td>&nbsp;Data de fabricação: " + produto.getDataFabricacao().toString() + "</td>";
				umaMusica += "\t\t\t<td>Data de validade: " + produto.getDataValidade().toString() + "</td>";
				umaMusica += "\t\t\t<td>&nbsp;</td>";
				umaMusica += "\t\t</tr>";
				umaMusica += "\t</table>";
			} else {
				System.out.println("ERRO! Tipo não identificado " + tipo);
			}
			form = form.replaceFirst("<UM-PRODUTO>", umaMusica);

		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Produtos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/musica/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/musica/list/" + FORM_ORDERBY_DESCRICAO + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/musica/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Produto> produtos;
		if (orderBy == FORM_ORDERBY_ID) {                 	produtos = produtoDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_DESCRICAO) {		produtos = produtoDAO.getOrderByDescricao();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			produtos = produtoDAO.getOrderByPreco();
		} else {											produtos = produtoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Musica musica : musicas) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\"" + bgcolor + "\">\n" + 
					"\t<td>" + musica.getID() + "</td>\n" +
					"\t<td>" + musica.getDescricao() + "</td>\n" +
					"\t<td>" + musica.getPreco() + "</td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/musica/" + musica.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/musica/update/" + musica.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteMusica('" + musica.getID() + "', '" + musica.getDescricao() + "', '" + musica.getPreco() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);	
	}	
	
	
	public Object insert(Request request, Response response) {
		String descricao = request.queryParams("descricao");
		float preco = Float.parseFloat(request.queryParams("preco de cds"));
		int quantidade = Integer.parseInt(request.queryParams("quantidade de cds"));
		LocalDateTime dataFabricacao = LocalDateTime.parse(request.queryParams("dataFabricacao"));
		LocalDate dataValidade = LocalDate.parse(request.queryParams("dataValidade"));
		
		String resp = "";
		
		Musica musica = new Musica(-1, descricao, preco, quantidade, dataFabricacao, dataValidade);
		
		if(MusicasDAO.insert(musica) == true) {
            resp = "Produto (" + descricao + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Produto (" + descricao + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Musica musica = (Musica) MusicasDAO.get(id);
		
		if (produto != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, produto, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Produto produto = (Produto) produtoDAO.get(id);
		
		if (produto != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, produto, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Musica musica = musicaDAO.get(id);
		String resp = "";
	
		if (musica != null) {
			musica.setDescricao(request.queryParams("descricao"));
			musica.setPreco(Float.parseFloat(request.queryParams("preco")));
			musica.setQuantidade(Integer.parseInt(request.queryParams("quantidade")));
			musica.setDataFabricacao(LocalDateTime.parse(request.queryParams("dataFabricacao")));
			musica.setDataValidade(LocalDate.parse(request.queryParams("dataValidade")));
			musicaDAO.update(musica);
			response.status(200); // success
			resp = "Musica (ID " + musica.getID() + ") atualizada!";
		} else {
			response.status(404); // 404 Not found
			resp = "Musica (ID " + id + ") não encontrada!";
		}
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}
	
	
	public Object delete(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Musica musica = musicaDAO.get(id);
		String resp = "";
	
		if (musica != null) {
			musicaDAO.delete(id);
			response.status(200); // success
			resp = "Musica (" + id + ") excluída!";
		} else {
			response.status(404); // 404 Not found
			resp = "Musica (" + id + ") não encontrada!";
		}
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}
	
}