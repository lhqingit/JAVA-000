package work1;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Base64;

public class HelloClassloader extends ClassLoader {
    public static void main(String[] args) throws IOException {
        try {
            Class<?> clazz = new HelloClassloader().findClass("Hello");
            Object object = clazz.newInstance();
            Method method = clazz.getMethod("hello");
            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream("H:\\Qing_java_software\\workspaces_idea\\JAVA-000\\Week_01\\work2\\Hello.xlass"));
            byte[] byteArr = new byte[dis.available()];
            dis.read(byteArr);
            for (int i = 0; i < byteArr.length; i++) {
                byteArr[i] = (byte) (255 - byteArr[i]);
            }
            return defineClass(name, byteArr, 0, byteArr.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.findClass(name);
    }

    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }


}
