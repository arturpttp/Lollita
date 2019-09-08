package net.dev.art.lollita;

import net.dev.art.lollita.aio.AIOList;
import net.dev.art.lollita.aio.AIOObject;
import net.dev.art.lollita.aio.AIOWriter;
import net.dev.art.lollita.objects.JSONWriter;

import java.io.File;
import java.io.IOException;

public class Teste {

    public static void main(String[] as) {
        AIOObject obj = new AIOObject();
        AIOObject artur = new AIOObject()
        .put("name", "Artur")
        .put("age", 19)
        .put("sexy", "Male")
        .put("parents", new AIOList().put("Mother","Ery").put("Father","A. Marcos"));
        obj.put("parents", new AIOList().add("Ery").add("A. Marcos").put("Mother","Ery").put("Father","A. Marcos"));
        obj.put("artur", artur);
        try {
            AIOWriter w = new AIOWriter(new File("teste.yml"));
            w.write(obj);
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
