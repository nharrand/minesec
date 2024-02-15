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
        assertFalse(pl.startWithPrefix("dfshdf"));
        assertTrue(pl.startWithPrefix("ABC"));
        assertTrue(pl.startWithPrefix("ABCD"));
        assertTrue(pl.startWithPrefix("abcd"));

        assertEquals("2,1,0", pl.print());
    }
}