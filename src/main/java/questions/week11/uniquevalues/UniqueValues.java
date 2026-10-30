package questions.week11.uniquevalues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class UniqueValues {

    public static List<String> unique(List<String> values) {
        List<String> result = new ArrayList<>();
        HashSet<String> seen = new HashSet<>();
        for (String value : values) {
            if (seen.add(value)) {
                result.add(value);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> languages = Arrays.asList("CPP", "PYTHON", "JAVA", "JAVA", "CPP");
        System.out.println(unique(languages));
    }
}
