package app;

import static spark.Spark.*;
import service.MusicaService;


public class Aplicacao {
	
	private static MusicaService musicaService = new MusicaService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/musica/insert", (request, response) -> musicaService.insert(request, response));

        get("/musica/:id", (request, response) -> musicaService.get(request, response));
        
        get("/musica/list/:orderby", (request, response) -> musicaService.getAll(request, response));

        get("/musica/update/:id", (request, response) -> musicaService.getToUpdate(request, response));
        
        post("/musica/update/:id", (request, response) -> musicaService.update(request, response));
           
        get("/musica/delete/:id", (request, response) -> musicaService.delete(request, response));

             
    }
}