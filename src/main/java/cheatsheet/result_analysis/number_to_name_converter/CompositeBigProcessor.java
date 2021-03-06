package cheatsheet.result_analysis.number_to_name_converter;

/**
 * Created by erick on 26.02.17.
 */
public class CompositeBigProcessor extends AbstractProcessor implements ScalesArray {

    private HundredProcessor hundredProcessor = new HundredProcessor();
    private AbstractProcessor lowProcessor;
    private int exponent;

    CompositeBigProcessor(int exponent) {
        if (exponent <= 3) {
            lowProcessor = hundredProcessor;
        } else {
            lowProcessor = new CompositeBigProcessor(exponent - 3);
        }
        this.exponent = exponent;
    }

    String getToken() {
        return NumberToWords.SCALE.getName(getPartDivider());
    }

    protected AbstractProcessor getHighProcessor() {
        return hundredProcessor;
    }

    protected AbstractProcessor getLowProcessor() {
        return lowProcessor;
    }

    int getPartDivider() {
        return exponent;
    }

    @Override
    public String getName(String value) {
        StringBuilder buffer = new StringBuilder();

        String high, low;
        if (value.length() < getPartDivider()) {
            high = "";
            low = value;
        } else {
            int index = value.length() - getPartDivider();
            high = value.substring(0, index);
            low = value.substring(index);
        }

        String highName = getHighProcessor().getName(high);
        String lowName = getLowProcessor().getName(low);

        if (!highName.isEmpty()) {
            buffer.append(highName);
            buffer.append(SEPARATOR);
            buffer.append(getToken());

            if (!lowName.isEmpty()) {
                buffer.append(SEPARATOR);
            }
        }

        if (!lowName.isEmpty()) {
            buffer.append(lowName);
        }

        return buffer.toString();
    }
}
