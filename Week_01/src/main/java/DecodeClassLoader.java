import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wenfan
 * @version 1.0
 * @className DecodeClassLoader
 * @packageName PACKAGE_NAME
 * @description 自定义的Classloader
 * @date 2020/10/20 4:37 下午
 **/
public class DecodeClassLoader extends ClassLoader{
    public static void main(String[] args){
        DecodeClassLoader decodeClassLoader = new DecodeClassLoader();
        try {
            Class<?>helloClass = decodeClassLoader.findClass("/Users/yeci/Documents/020学习/026myjava/java训练营/myhomework/JAVA-000/Week_01/src/main/resources/Hello.xlass");
            Object object = helloClass.newInstance();
            Method method = helloClass.getMethod("hello");
            method.invoke(object);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected Class<?> findClass(String name) {
        byte[] helloBytes = DecodeClassLoader.readFileBytes(name);
        for (int i = 0; i < helloBytes.length; i++) {
            helloBytes[i] = (byte) (255 - helloBytes[i]);
        }
        return defineClass(null, helloBytes, 0, helloBytes.length);
    }


    private static byte[] readFileBytes(String fileName) {
        byte[] res = null;
        try (FileInputStream fis = new FileInputStream(fileName);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            int i = 0;
            while ((i = fis.read()) != -1) {
                bos.write(i);
            }
            res = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
