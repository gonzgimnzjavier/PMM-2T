package com.example.a070htppcon;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class JSONCommentsParser {

    /*
    Método encargado de coordinar la conversión final de un flujo
    con formato JSON
     */
    public List<String> readJsonStream(InputStream in) throws IOException {

        // Nueva instancia de un lector JSON
        JsonReader reader = new JsonReader(
                new InputStreamReader(in, "UTF-8"));

        try {
            return readCommentsArray(reader);
        } finally {
            reader.close();
        }
    }

    /*
    Este método lee cada elemento al interior de un array JSON
     */
    public List<String> readCommentsArray(JsonReader reader) throws IOException {
        List<String> comments = new ArrayList<>();

        // Se dirige al corchete de apertura del arreglo
        reader.beginArray();
        while (reader.hasNext()) {
            comments.add(readMessage(reader));
        }

        // Se dirige al corchete de cierre
        reader.endArray();
        return comments;
    }

    /*
    Lee los atributos de cada objeto
     */
    public String readMessage(JsonReader reader) throws IOException {

        // Cuerpo del comentario
        String body = null;

        // Se dirige a la llave de apertura del objeto
        reader.beginObject();

        while (reader.hasNext()) {

            // Se obtiene el nombre del atributo
            String name = reader.nextName();

            if (name.equals("body")) {
                body = reader.nextString();
            } else {
                reader.skipValue();
            }
        }

        // Se dirige a la llave de cierre del objeto
        reader.endObject();
        return body;
    }
}

