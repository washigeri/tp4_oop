package filter;

public class Filter1 implements IFilter {

    public Filter1() {
        super();
    }

    public String processData(String input) {
        String res;
        res = (input.contains("P1")) ? "DÉTECTÉ" : "NA";
        return res;
    }
}
