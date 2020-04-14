package org.microdeaf.mapper.web.method;

import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;

import java.util.List;

public class RequestResponseMethod {
    private static RequestResponseMethod ourInstance = new RequestResponseMethod();

    public static RequestResponseMethod getInstance() {
        return ourInstance;
    }

    private RequestResponseMethod() {
    }

    public Class<?> getViewClass(Object o, Environment env) {
        if (o != null) {
            Class<?> viewClass = null;
            if (o.getClass().getSimpleName().equals("ArrayList")) {
                List list = (List) o;
                viewClass = getView(list.get(0).getClass().getSimpleName(), env.getProperty("microdeaf.mapper.mapstruct.view.path"));
            } else {
                viewClass = getView(o.getClass().getSimpleName(), env.getProperty("microdeaf.mapper.mapstruct.view.path"));
            }
            if (viewClass != null) {
                return viewClass;
            }
        }

        return null;

    }

    public Class<?> getViewClass(MethodParameter parameter, Environment env) {
        Class<?> viewClass = getView(parameter.getExecutable().getParameterTypes()[0].getSimpleName(),
                env.getProperty("microdeaf.mapper.mapstruct.view.path"));

        if (viewClass != null) {
            return viewClass;
        }

        return null;
    }

    public String getMapperPath(Object o, Environment env) {
        if (o.getClass().getSimpleName().equals("ArrayList")) {
            List list = (List) o;
            String name = list.get(0).getClass().getSimpleName().contains("View") ?
                    list.get(0).getClass().getSimpleName().split("View")[0] : list.get(0).getClass().getSimpleName();
            return env.getProperty("microdeaf.mapper.mapstruct.path") + "." + name;

        } else {
            String name = o.getClass().getSimpleName().contains("View") ?
                    o.getClass().getSimpleName().split("View")[0] : o.getClass().getSimpleName();
            return env.getProperty("microdeaf.mapper.mapstruct.path") + "." + name;
        }
    }

    private Class<?> getView(String modelName, String path) {
        try {
            return Class.forName(path + ".dto." + modelName + "View");
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(path + ".entity." + modelName + "View");
            } catch (ClassNotFoundException ex) {
                try {
                    return Class.forName(path + "." + modelName + "View");
                } catch (ClassNotFoundException classNotFoundException) {
                    return null;
                }
            }
        }
    }
}
