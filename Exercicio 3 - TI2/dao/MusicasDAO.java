package dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import model.Musica;

public class MusicasDAO extends DAO {
	
	public MusicasDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Musica musica) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO musica (codigo, nome, artista, album) "
				       + "VALUES ("+musica.getCodigo()+ ", '" + musica.getNome() + "', '"  
				       + musica.getArtista() + "', '" + musica.getAlbum() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Musica get(int codigo) {
		Musica musica = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE id=" + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 musica = new Musica(rs.getInt("codigo"), rs.getString("placa"), rs.getString("marca"), rs.getString("motor"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return musica;
	}
	
	
	public List<Musica> get() {
		return get("");
	}

	
	public List<Musica> getOrderByCodigo() {
		return get("codigo");		
	}
	
	
	public List<Musica> getOrderByNome() {
		return get("placa");		
	}
	
	
	public List<Musica> getOrderByArtista() {
		return get("marca");		
	}
	
	
	private List<Musica> get(String orderBy) {	
	
		List<Musica> musicas = new ArrayList<Musica>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM musica" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Musica u = new Musica(rs.getInt("codigo"), rs.getString("nome"), rs.getString("artista"), rs.getString("album"));
	        	musicas.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return musicas;
	}
	
	
	public boolean update(Musica musica) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE usuario SET placa = '" + musica.getNome() + "', marca = '"  
				       + musica.getArtista() + "', motor = '" + musica.getAlbum() + "'"
					   + " WHERE codigo = " + musica.getCodigo();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM veiculo WHERE codigo = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}	
}