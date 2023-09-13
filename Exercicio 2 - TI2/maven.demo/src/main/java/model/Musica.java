package model;

public class Musica {
    private int codigo;
    private String nome;
    private String artista;
    private String album;

    public Musica() {
        this.codigo = -1;
        this.nome = "";
        this.artista = "";
        this.album = "";
    }

    public Musica(int codigo, String nome, String artista, String album) {
        this.codigo = codigo;
        this.nome = nome;
        this.artista = artista;
        this.album = album;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Musica [codigo=" + codigo + ", nome=" + nome + ", artista=" + artista + ", album=" + album + "]";
    }
}
