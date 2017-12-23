package filter;

public class Filter2 implements IFilter {

    private String lastValue;

    public Filter2() {
        lastValue = "";
    }

    @Override
    public String processData(String input) {
        if (lastValue.equals("") || !input.equals(lastValue)) {
            lastValue = input;
        }
        return lastValue;
    }
}
