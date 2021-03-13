
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义类加载器
 * @author chenkui
 */
public class HelloClassLoader extends ClassLoader{


    public static void main(String[] args) {
        try {
            Class<?> xlass = new HelloClassLoader().findClass("Hello");
            Method hello = xlass.getMethod("hello");
            hello.invoke(xlass.newInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected Class<?> findClass(String name) {
        File file = new File(this.getClass().getResource("").getPath() + "/Hello.xlass");
        try (FileInputStream in = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
