package com.ti2cc;
import dao.MusicasDAO;
import model.Musica;
import java.util.List;
import java.util.Scanner;

public class Principal {
    
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in, "UTF-8");
        MusicasDAO musicasDAO = new MusicasDAO();
        
        String options = "";
        
        String menu = "DIGITE: 1 PARA LISTAR \n 2 PARA INSERIR \n 3 PARA EXCLUIR \n 0 PARA SAIR";
        System.out.println(menu);
        options = sc.nextLine();
        
        switch(options) {
            case "1":
                listaMusicas(musicasDAO);
                break;
            case "2":
                insereMusica(musicasDAO, sc);
                break;
            case "3":
                excluiMusica(musicasDAO, sc);
                break;
            default:
                break;
        }
    }
    
    static void insereMusica(MusicasDAO musicasDAO, Scanner sc) {
        System.out.println("\n\n==== Inserir Musica === ");
        System.out.println("Insira o nome:");
        String nome = sc.nextLine();
        System.out.println("Insira o artista:");
        String artista = sc.nextLine();
        System.out.println("Insira o album:");
        String album = sc.nextLine();
        Musica musica = new Musica(3, nome, artista, album);
        if(musicasDAO.insert(musica)) {
            System.out.println("Inserção com sucesso -> " + musica.toString());
        }
    }
    
    static void listaMusicas(MusicasDAO musicasDAO) {
        System.out.println("\n\n==== Lista músicas === ");
        List<Musica> musicas = musicasDAO.get();
        for (Musica musica : musicas) {
            System.out.println(musica.toString());
        }
    }
    
    static void excluiMusica(MusicasDAO musicasDAO, Scanner sc) {
        System.out.println("\n\n==== Excluir Música ==== ");
        System.out.println("Insira o código da música a ser excluída:");
        String codigo = sc.nextLine();
        musicasDAO.delete(Integer.parseInt(codigo));
    }
}
