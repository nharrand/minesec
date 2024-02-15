package se.su.nh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefixList {
    
    List<String> prefixes;
    Map<String, Integer> occurrences = new HashMap<>();

    public PrefixList(List<String> prefixes) {
        this.prefixes = prefixes;

    }

    public boolean startWithPrefix(String candidate) {
        for(String prefix: prefixes) {
            if(candidate.startsWith(prefix)) {
                occurrences.put(prefix, occurrences.computeIfAbsent(prefix,(String s) -> 0) + 1);
                return true;
            }
        }
        return false;
    }

    public boolean containsPrefix(String candidate) {
        for(String prefix: prefixes) {
            if(candidate.contains(prefix)) {
                occurrences.put(prefix, occurrences.computeIfAbsent(prefix,(String s) -> 0) + 1);
                return true;
            }
        }
        return false;
    }

    public String print() {
        String output = "";
        for(String prefix: prefixes) {
            if(output.length() > 0) {
                output += ",";
            }
            output += occurrences.computeIfAbsent(prefix,(String s) -> 0);
        }
        return output;
    }

    public String printHeader() {
        String output = "";
        for(String prefix: prefixes) {
            if(output.length() > 0) {
                output += ",";
            }
            output += prefix;
        }
        return output;
    }
}
