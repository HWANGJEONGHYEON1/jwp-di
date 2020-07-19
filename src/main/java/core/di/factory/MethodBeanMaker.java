package core.di.factory;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created By kjs4395 on 7/20/20
 */
public class MethodBeanMaker implements BeanMaker {
    private BeanFactory beanFactory;

    @Override
    public boolean isSupport(BeanInfo beanInfo) {
        return beanInfo.getBeanInvokeType().equals(BeanInvokeType.METHOD);
    }

    @Override
    public <T> T makeBean(BeanInfo beanInfo) {
        Method method = beanInfo.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] objects = new Object[parameters.length];

        try {
            if (parameters.length == 0) {
                return (T) method.invoke(beanInfo.getDefineClazz(), objects);
            }

            for (int i = 0; i < parameters.length; i++) {
                objects[i] = beanFactory.getBean(parameters[i].getType());
            }
            return (T) method.invoke(beanInfo.getDefineClazz(), objects);
        }  catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("make bean error!");
        }
    }
}
