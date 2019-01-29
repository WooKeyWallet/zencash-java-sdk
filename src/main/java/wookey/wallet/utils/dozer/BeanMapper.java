package wookey.wallet.utils.dozer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.dozer.loader.api.TypeMappingOptions.mapEmptyString;
import static org.dozer.loader.api.TypeMappingOptions.mapNull;

/**
 * javaBean mapper
 * It can handle mapping between complex types flexibly.
 * It can not only perform simple attribute mapping, complex type mapping, bidirectional mapping, recursive mapping and so on, but also flexibly configure through XML configuration files.
 */
public class BeanMapper {
    /**
     * DozerBeanMapper Assignment of the same attribute name between objects
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * Types of Objects Converted Based on Dozer
     *
     * @param source
     * @param destinationClass
     * @return
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return dozer.map(source, destinationClass);
    }

    /**
     * mapList:(Conversion of Object Types in Collection Based on Dozer). <br/>
     *
     * @param sourceList
     * @param destinationClass
     * @return
     */
    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
        if (sourceList == null) {
            return null;
        }
        List<T> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;

    }

    /**
     * mapSet:(Conversion of Object Types in Sets Based on Dozer). <br/>
     *
     * @param sourceList
     * @param destinationClass
     * @return
     */
    public static <T> Set<T> mapSet(Collection<?> sourceList, Class<T> destinationClass) {
        if (sourceList == null) {
            return null;
        }
        Set<T> destinationList = Sets.newHashSet();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);

        }
        return destinationList;
    }

    /**
     * copy:(Copy the value of object source to object destinationObject based on Dozer). <br/>
     *
     * @param source
     * @param destinationObject
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

    /**
     * Convert any VO to map
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> convert2Map(T t) {
        Map<String, Object> result = new HashMap<String, Object>();
        Method[] methods = t.getClass().getMethods();
        try {
            for (Method method : methods) {
                Class<?>[] paramClass = method.getParameterTypes();
                if (paramClass.length > 0) { // 如果方法带参数，则跳过
                    continue;
                }
                String methodName = method.getName();
                if (methodName.startsWith("get")) {
                    Object value = method.invoke(t);
                    String a = methodName.substring(4);
                    String b = methodName.substring(3, 4);
                    result.put(b.toLowerCase().concat(a), value);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Copy attributes that are not empty
     *
     * @param sources
     * @param destination
     */
    public static void copyNotNull(final Object sources, final Object destination) {
        dozer.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(sources.getClass(), destination.getClass(), mapNull(false), mapEmptyString(false));
            }
        });
        dozer.map(sources, destination);
    }

}
