package se.su.malijar.payloads;

import java.lang.reflect.Method;

public class BytecodeLoader implements Runnable {

    public BytecodeLoader() {}

    public void run() {
        try {
            // Replace 'YourClass' and 'yourClassBytecode' with your class name and bytecode
            String className = "HelloWorld";
            byte[] classBytecode = getYourClassBytecode();

            Class<?> loadedClass = loadClass(className, classBytecode);

            invokeMethod( loadedClass,"greet");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Class<?> loadClass(String className, byte[] bytecode) throws ClassNotFoundException {
        ByteArrayClassLoader classLoader = new ByteArrayClassLoader();
        return classLoader.defineClass(className, bytecode);
    }

    private static Object instantiateClass(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    private static void invokeMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Method method = clazz.getMethod(methodName);
        method.invoke(null);
    }

    private static byte[] getYourClassBytecode() {
        return new byte[] {-54, -2, -70, -66, 0, 0, 0, 51, 0, 31, 10, 0, 2, 0, 3, 7, 0, 4, 12, 0, 5, 0, 6, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 9, 0, 8, 0, 9, 7, 0, 10, 12, 0, 11, 0, 12, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 121, 115, 116, 101, 109, 1, 0, 3, 111, 117, 116, 1, 0, 21, 76, 106, 97, 118, 97, 47, 105, 111, 47, 80, 114, 105, 110, 116, 83, 116, 114, 101, 97, 109, 59, 8, 0, 14, 1, 0, 12, 72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 33, 10, 0, 16, 0, 17, 7, 0, 18, 12, 0, 19, 0, 20, 1, 0, 19, 106, 97, 118, 97, 47, 105, 111, 47, 80, 114, 105, 110, 116, 83, 116, 114, 101, 97, 109, 1, 0, 7, 112, 114, 105, 110, 116, 108, 110, 1, 0, 21, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 86, 7, 0, 22, 1, 0, 10, 72, 101, 108, 108, 111, 87, 111, 114, 108, 100, 1, 0, 4, 67, 111, 100, 101, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108, 101, 1, 0, 4, 116, 104, 105, 115, 1, 0, 12, 76, 72, 101, 108, 108, 111, 87, 111, 114, 108, 100, 59, 1, 0, 5, 103, 114, 101, 101, 116, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 15, 72, 101, 108, 108, 111, 87, 111, 114, 108, 100, 46, 106, 97, 118, 97, 0, 33, 0, 21, 0, 2, 0, 0, 0, 0, 0, 2, 0, 1, 0, 5, 0, 6, 0, 1, 0, 23, 0, 0, 0, 47, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 1, -79, 0, 0, 0, 2, 0, 24, 0, 0, 0, 6, 0, 1, 0, 0, 0, 1, 0, 25, 0, 0, 0, 12, 0, 1, 0, 0, 0, 5, 0, 26, 0, 27, 0, 0, 0, 9, 0, 28, 0, 6, 0, 1, 0, 23, 0, 0, 0, 37, 0, 2, 0, 0, 0, 0, 0, 9, -78, 0, 7, 18, 13, -74, 0, 15, -79, 0, 0, 0, 1, 0, 24, 0, 0, 0, 10, 0, 2, 0, 0, 0, 4, 0, 8, 0, 5, 0, 1, 0, 29, 0, 0, 0, 2, 0, 30};
    }

    static class ByteArrayClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] bytecode) {
            return defineClass(name, bytecode, 0, bytecode.length);
        }
    }
}
