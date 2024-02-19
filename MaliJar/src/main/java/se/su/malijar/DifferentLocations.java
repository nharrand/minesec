package se.su.malijar;

import java.util.HashMap;
import java.util.Map;

public class DifferentLocations extends ClassLoader {
    static {
        ClassLoader cl = DifferentLocations.class.getClassLoader();

        System.out.println(cl.getParent().toString());
        cls = cl;
    }
    static ClassLoader cls;
    ClassLoader cl1,cl2, cl3;
    public DifferentLocations() {
        cl1 = DifferentLocations.class.getClassLoader();;
    }

    public void init() {
        cl2 = DifferentLocations.class.getClassLoader();
    }

    public void m1() {
        Map<String, ClassLoader> m = new HashMap<>();

        cl3 =  m.computeIfAbsent("test", (str) -> cls);
    }

    class Inner {
        ClassLoader cl1,cl2, cl3;
        public Inner() {
            cl1 = DifferentLocations.class.getClassLoader();;
        }

        public void init() {
            cl2 = DifferentLocations.class.getClassLoader();
        }

        public void m1() {
            Map<String, ClassLoader> m = new HashMap<>();

            cl3 =  m.computeIfAbsent("test", (str) -> cls);
        }
    }
}
