package se.su.malijar.payloads;

import java.lang.reflect.Method;

public class BytecodeLoader implements Runnable {
    byte[] bytecode = new byte[] {-54, -2, -70, -66, 0, 0, 0, 51, 0, 31, 10, 0, 2, 0, 3, 7, 0, 4, 12, 0, 5, 0, 6, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 9, 0, 8, 0, 9, 7, 0, 10, 12, 0, 11, 0, 12, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 121, 115, 116, 101, 109, 1, 0, 3, 111, 117, 116, 1, 0, 21, 76, 106, 97, 118, 97, 47, 105, 111, 47, 80, 114, 105, 110, 116, 83, 116, 114, 101, 97, 109, 59, 8, 0, 14, 1, 0, 12, 72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 33, 10, 0, 16, 0, 17, 7, 0, 18, 12, 0, 19, 0, 20, 1, 0, 19, 106, 97, 118, 97, 47, 105, 111, 47, 80, 114, 105, 110, 116, 83, 116, 114, 101, 97, 109, 1, 0, 7, 112, 114, 105, 110, 116, 108, 110, 1, 0, 21, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 86, 7, 0, 22, 1, 0, 10, 72, 101, 108, 108, 111, 87, 111, 114, 108, 100, 1, 0, 4, 67, 111, 100, 101, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108, 101, 1, 0, 4, 116, 104, 105, 115, 1, 0, 12, 76, 72, 101, 108, 108, 111, 87, 111, 114, 108, 100, 59, 1, 0, 5, 103, 114, 101, 101, 116, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 15, 72, 101, 108, 108, 111, 87, 111, 114, 108, 100, 46, 106, 97, 118, 97, 0, 33, 0, 21, 0, 2, 0, 0, 0, 0, 0, 2, 0, 1, 0, 5, 0, 6, 0, 1, 0, 23, 0, 0, 0, 47, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 1, -79, 0, 0, 0, 2, 0, 24, 0, 0, 0, 6, 0, 1, 0, 0, 0, 1, 0, 25, 0, 0, 0, 12, 0, 1, 0, 0, 0, 5, 0, 26, 0, 27, 0, 0, 0, 9, 0, 28, 0, 6, 0, 1, 0, 23, 0, 0, 0, 37, 0, 2, 0, 0, 0, 0, 0, 9, -78, 0, 7, 18, 13, -74, 0, 15, -79, 0, 0, 0, 1, 0, 24, 0, 0, 0, 10, 0, 2, 0, 0, 0, 4, 0, 8, 0, 5, 0, 1, 0, 29, 0, 0, 0, 2, 0, 30};

    String b64 = "I2luY2x1ZGUgPHN0ZGlvLmg+CiNpbmNsdWRlIDxzdGRsaWIuaD4KI2luY2x1ZGUgPG1hdGguaD4KCmRvdWJsZSBlbnRyb3B5X3N0ZXAodW5zaWduZWQgY2hhciBjLCBkb3VibGUgbG9nbWF4KSB7CglpZihjICE9IDApIHsKCQlyZXR1cm4gKChkb3VibGUpIGMpICogKGxvZzIoYykgLSBsb2dtYXgpOwoJfSBlbHNlIHsKCQlyZXR1cm4gMC4wOwoJfQp9CgppbnQgbWFpbihpbnQgYXJnYywgY2hhciAqYXJndltdKSB7CgkvLyBDaGVjayBpZiBmaWxlbmFtZSBpcyBwcm92aWRlZCBhcyBhcmd1bWVudAoJaWYgKGFyZ2MgIT0gMikgewoJCWZwcmludGYoc3RkZXJyLCAiVXNhZ2U6ICVzIDxmaWxlbmFtZT5cbiIsIGFyZ3ZbMF0pOwoJCXJldHVybiAxOwoJfQoKCS8vIE9wZW4gdGhlIGZpbGUgaW4gYmluYXJ5IG1vZGUKCUZJTEUgKmZpbGUgPSBmb3Blbihhcmd2WzFdLCAicmIiKTsKCWlmICghZmlsZSkgewoJCWZwcmludGYoc3RkZXJyLCAiVW5hYmxlIHRvIG9wZW4gZmlsZSAnJXMnLlxuIiwgYXJndlsxXSk7CgkJcmV0dXJuIDE7Cgl9CgoJLy8gRGV0ZXJtaW5lIHRoZSBsZW5ndGggb2YgdGhlIGZpbGUKCWZzZWVrKGZpbGUsIDAsIFNFRUtfRU5EKTsKCXNpemVfdCBmaWxlTGVuID0gZnRlbGwoZmlsZSk7Cglmc2VlayhmaWxlLCAwLCBTRUVLX1NFVCk7CgoJLy8gQWxsb2NhdGUgbWVtb3J5IGZvciB0aGUgYnVmZmVyCgl1bnNpZ25lZCBjaGFyICpidWZmZXIgPSAodW5zaWduZWQgY2hhciAqKW1hbGxvYyhmaWxlTGVuKTsKCWlmICghYnVmZmVyKSB7CgkJZnByaW50ZihzdGRlcnIsICJNZW1vcnkgYWxsb2NhdGlvbiBlcnJvci5cbiIpOwoJCWZjbG9zZShmaWxlKTsKCQlyZXR1cm4gMTsKCX0KCgkvLyBSZWFkIGZpbGUgY29udGVudHMgaW50byBidWZmZXIKCXNpemVfdCBieXRlc1JlYWQgPSBmcmVhZChidWZmZXIsIDEsIGZpbGVMZW4sIGZpbGUpOwoJZmNsb3NlKGZpbGUpOwoKCWlmIChieXRlc1JlYWQgIT0gZmlsZUxlbikgewoJCWZwcmludGYoc3RkZXJyLCAiRXJyb3IgcmVhZGluZyBmaWxlICclcycuXG4iLCBhcmd2WzFdKTsKCQlmcmVlKGJ1ZmZlcik7CgkJcmV0dXJuIDE7Cgl9CgoJLy8gVXNlIGJ1ZmZlciBhcyBieXRlIGFycmF5CgkvLyBIZXJlIHlvdSBjYW4gcGVyZm9ybSBhbnkgb3BlcmF0aW9ucyB3aXRoIHRoZSBieXRlIGFycmF5CgoJdW5zaWduZWQgY2hhciBieXRlX2NvdW50ZXJbMjU2XTsKCXVfaW50MTZfdCB3aW5kb3dfc2l6ZSA9IDI1NjsKCWRvdWJsZSBlbnRyb3B5ID0gMC4wOwoJZG91YmxlIGxvZ21heCA9IDguMDsKCQoJCgoJaWYgKHdpbmRvd19zaXplID49IGZpbGVMZW4pIHsKCQlmcHJpbnRmKHN0ZGVyciwgIldpbmRvdyBzaXplIGJpZ2dlciB0aGFuIHRoZSBmaWxlLlxuIik7CgkJZnJlZShidWZmZXIpOwoJCXJldHVybiAxOwoJfQoJCgkKCgkvL2luaXQgY2hhciBvY3VudGVyCglmb3IodV9pbnQxNl90IGkgPSAwOyBpIDwgMjU2OyBpKyspIHsKCQlieXRlX2NvdW50ZXJbaV0gPSAwOwoJfQoJZm9yKHVfaW50MTZfdCBpID0gMDsgaSA8IHdpbmRvd19zaXplOyBpKyspIHsKCQlieXRlX2NvdW50ZXJbYnVmZmVyW2ldXSsrOwoJfQoJLy9pbml0IGVudHJvcHkKCWZvcih1X2ludDE2X3QgaSA9IDA7IGkgPCAyNTY7IGkrKykgewoJCWlmKGJ5dGVfY291bnRlcltpXSAhPSAwKSB7CgkJCWVudHJvcHkgLT0gZW50cm9weV9zdGVwKGJ5dGVfY291bnRlcltpXSwgbG9nbWF4KTsKCQl9Cgl9CgkvL2ZwcmludGYoc3Rkb3V0LCAiRW50cm9weTogJWZcbiIsIGVudHJvcHkgLyAyNTYuMCk7CgkKCQoJdV9pbnQxNl90IHRhaWwgPSAwOwoJdV9pbnQxNl90IGhlYWQgPSB3aW5kb3dfc2l6ZTsKCWRvdWJsZSBtYXhfZW50cm9weSA9IGVudHJvcHk7CgkKCXdoaWxlKGhlYWQgPCAoZmlsZUxlbi0xKSkgewoJCS8vZnByaW50ZihzdGRvdXQsICJ0YWlsIC0+ICclYycgOiAlaSwgaGVhZCAtPiAnJWMnIDogJWlcbiIsIGJ1ZmZlclt0YWlsXSwgYnl0ZV9jb3VudGVyW2J1ZmZlclt0YWlsXV0sIGJ1ZmZlcltoZWFkXSwgYnl0ZV9jb3VudGVyW2J1ZmZlcltoZWFkXV0pOwoJCWVudHJvcHkgKz0gZW50cm9weV9zdGVwKGJ5dGVfY291bnRlcltidWZmZXJbdGFpbF1dLCBsb2dtYXgpOwoJCWJ5dGVfY291bnRlcltidWZmZXJbdGFpbF1dLS07CgkJZW50cm9weSAtPSBlbnRyb3B5X3N0ZXAoYnl0ZV9jb3VudGVyW2J1ZmZlclt0YWlsXV0sIGxvZ21heCk7CgkJCgkJCgkJZW50cm9weSArPSBlbnRyb3B5X3N0ZXAoYnl0ZV9jb3VudGVyW2J1ZmZlcltoZWFkXV0sIGxvZ21heCk7CgkJYnl0ZV9jb3VudGVyW2J1ZmZlcltoZWFkXV0rKzsKCQllbnRyb3B5IC09IGVudHJvcHlfc3RlcChieXRlX2NvdW50ZXJbYnVmZmVyW2hlYWRdXSwgbG9nbWF4KTsKCQkKCQl0YWlsKys7CgkJaGVhZCsrOwoJCS8vZnByaW50ZihzdGRvdXQsICJFbnRyb3B5OiAlZlxuIiwgZW50cm9weSAvIDI1Ni4wKTsKCQlpZihlbnRyb3B5ID4gbWF4X2VudHJvcHkpIG1heF9lbnRyb3B5ID0gZW50cm9weTsKCX0KCgoKCglmcHJpbnRmKHN0ZG91dCwgIiAtLS0tLS0tLS0tLS0tLS0tLS0tLSBcbiIpOwoKCS8vZnByaW50ZihzdGRvdXQsICJNYXggRW50cm9weTogJWZcbiIsIG1heF9lbnRyb3B5IC8gMjU2LjApOwoJZnByaW50ZihzdGRvdXQsICIlZlxuIiwgbWF4X2VudHJvcHkgLyAyNTYuMCk7CgoKCgkvLyBGcmVlIGFsbG9jYXRlZCBtZW1vcnkKCWZyZWUoYnVmZmVyKTsKCglyZXR1cm4gMDsKfQo=";

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
