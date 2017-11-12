/**
 * Copyright 2012 - 2017 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.silviowangler.dox.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

import static org.springframework.util.Assert.*;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class AmountOfMoney implements Serializable {

    private Currency currency;
    private BigDecimal amount;

    private static final Logger logger = LoggerFactory.getLogger(AmountOfMoney.class);

    public AmountOfMoney() {
    }

    public AmountOfMoney(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public AmountOfMoney(String value) {
        notNull(value, "Value must not be null");
        hasText(value, "Value must not be blank");

        logger.trace("Value is '{}'", value);
        final String trimmedValue = value.trim();
        logger.trace("Trimmed value is '{}'", trimmedValue);
        final String clearedWhitespaces = trimmedValue.replaceAll(" +", " ");
        logger.trace("Cleared value is '{}'", trimmedValue);
        String[] args = clearedWhitespaces.split(" ");

        isTrue(args.length == 2, "Please provide currency code and amount. E.g. CHF 1250.50. Your value is " + value);

        logger.trace("Arg currency {}, arg amount {}", args[0], args[1]);

        this.currency = Currency.getInstance(args[0]);
        this.amount = new BigDecimal(args[1]);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AmountOfMoney{");
        sb.append("currency=").append(currency);
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmountOfMoney that = (AmountOfMoney) o;

        return amount != null && amount.equals(that.getAmount()) && currency != null && currency.equals(that.getCurrency());
    }

    @Override
    public int hashCode() {
        int result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
