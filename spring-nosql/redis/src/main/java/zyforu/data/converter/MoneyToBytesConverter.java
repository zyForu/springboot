package zyforu.data.converter;

import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

import java.nio.charset.StandardCharsets;

/**
 * @author zy
 * @date 2023/3/4 17:21
 */
public class MoneyToBytesConverter  implements Converter<Money, byte[]> {
    @Override
    public byte[] convert(Money money) {
        String val = Long.toString(money.getAmountMajorLong());
        return val.getBytes(StandardCharsets.UTF_8);
    }
}
