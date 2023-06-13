package Controller;

import Model.*;
import View.JegyView;
import View.KorcsolyaView;
import View.NyitvatartasView;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("application")
public class RequestController {

    @GET
    @Path ("jegyarak")
    public Response jegyarak(){

        JSONArray jegyek = XmlReader.read(System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\jegy.xml");
        return Response.ok(JegyView.showJegyek(jegyek)).build();
    }

    @GET
    @Path ("korcsolyak")
    public Response korcsolyak(){
        JSONArray korcsolyak = XmlReader.read(System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\korcsolyak.xml");

        return Response.ok(KorcsolyaView.showKorcsolyak(korcsolyak)).build();
    }

    @GET
    @Path ("nyitvatartas")
    public Response getNyitvatartas(){
        JSONArray nyitvatartas = XmlReader.read(System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\nyitvatartas.xml");

        return Response.ok(NyitvatartasView.showNyitvatartasok(nyitvatartas)).build();
    }

    @GET
    @Path ("kolcsonzesek")
    public Response getKolcsonzesek(){

        return Response.ok(RequestModel.getKolcsonzesek()).build();
    }

    @GET
    @Path ("foglalasok")
    public Response getFoglalasok(){

        return Response.ok(RequestModel.getFoglalasok()).build();
    }

    @POST
    @Path("korcsolyaKolcsonzes")
    public Response kolcsonzes(String igeny) {
        JSONObject igenyJson = new JSONObject(igeny);

        return Response.ok(RequestModel.kolcsonzes(igenyJson)).build();
    }

    @POST
    @Path("korcsolyaFelvetel")
    public Response felvetel(String json){
        JSONObject korcsolya = new JSONObject(json);

        return Response.ok(RequestModel.addKorcsolya(korcsolya)).build();
    }
    @POST
    @Path("palyaFoglalas")
    public Response foglalas(String igeny) {
        JSONObject igenyJson = new JSONObject(igeny);

        return Response.ok(RequestModel.foglalas(igenyJson)).build();
    }

}