package zyforu.data.converter;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

import java.nio.charset.StandardCharsets;

/**
 * @author zy
 * @date 2023/3/4 17:20
 */
public class BytesToMoneyConverter implements Converter<byte[], Money> {


    @Override
    public Money convert(byte[] bytes) {
        String s = new String(bytes, StandardCharsets.UTF_8);
        return Money.ofMajor(CurrencyUnit.of("CNY"), Long.parseLong(s));
    }
}
