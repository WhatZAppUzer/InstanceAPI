package de.whatsappuser.instanceapi.handler;

import de.whatsappuser.instanceapi.handler.handlers.IHandler;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HandlerManager {

    private final Map<Class<? extends IHandler>, IHandler> handlerList = new HashMap<>();
    public <T> T getHandler(Class<T> tClass) {
        return (T) this.handlerList.get(tClass);
    }

    public void registerHandler(IHandler handler) {
        if(handler == null) return;

        this.handlerList.put(handler.getClass(), handler);
        handler.onLoad();
    }
}
