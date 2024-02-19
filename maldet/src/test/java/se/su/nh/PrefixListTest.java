package se.su.nh;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PrefixListTest {
    List<String> l = Arrays.asList(new String[] {"ABC", "ab", "ABCD"});
    PrefixList pl = new PrefixList(l);

    @Test
    public void startWithPrefix() {
        assertFalse(pl.startWithPrefix("dfshdf", "test"));
        assertTrue(pl.startWithPrefix("ABC", "test"));
        assertTrue(pl.startWithPrefix("ABCD", "test"));
        assertTrue(pl.startWithPrefix("abcd", "test"));

        assertEquals("2,1,0", pl.print());
    }
}