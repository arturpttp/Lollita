package net.dev.art.lollita.aio;

import java.util.ArrayList;
import java.util.List;

public class AIOList extends AIOObject {

    public final List<Object> self = new ArrayList<>();

    public Object get(int index) {
        return self.get(index);
    }

    @Override
    public AIOObject put(String key, Object value) throws AIOException {
        self.add(value);
        return super.put(key, value);
    }

    public AIOList add(Object value) {
        self.add(value);
        return this;
    }

}
