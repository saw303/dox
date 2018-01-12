/**
 * Copyright 2012 - 2018 Silvio Wangler (silvio.wangler@gmail.com)
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

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class AmountOfMoneyTest {

    @Test(expected = IllegalArgumentException.class)
    public void doNotAcceptNullValues() {
        new AmountOfMoney(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNotAcceptBlankValues() {
        new AmountOfMoney("");
    }

    @Test
    public void parseSwissFrancs() {

        AmountOfMoney money = new AmountOfMoney("CHF 12");

        assertThat(money.getAmount().toPlainString(), is("12"));
        assertThat(money.getCurrency().getCurrencyCode(), is("CHF"));
    }

    @Test
    public void parseSwissFrancsWithDecimals() {

        AmountOfMoney money = new AmountOfMoney("CHF 1024.95");

        assertThat(money.getAmount().toPlainString(), is("1024.95"));
        assertThat(money.getCurrency().getCurrencyCode(), is("CHF"));
    }

    @Test
    public void parseEuros() {

        AmountOfMoney money = new AmountOfMoney("EUR 12");

        assertThat(money.getAmount().toPlainString(), is("12"));
        assertThat(money.getCurrency().getCurrencyCode(), is("EUR"));
    }

    @Test
    public void parseUSDollars() {

        AmountOfMoney money = new AmountOfMoney("USD 12");

        assertThat(money.getAmount().toPlainString(), is("12"));
        assertThat(money.getCurrency().getCurrencyCode(), is("USD"));
    }

    @Test
    public void parseChfTrimWhitespaces() {

        AmountOfMoney money = new AmountOfMoney("    CHF 19.85 ");

        assertThat(money.getAmount().toPlainString(), is("19.85"));
        assertThat(money.getCurrency().getCurrencyCode(), is("CHF"));
    }

    @Test
    public void parseChfTrimWhitespaces2() {

        AmountOfMoney money = new AmountOfMoney("    CHF    02.20    ");

        assertThat(money.getAmount().toPlainString(), is("2.20"));
        assertThat(money.getCurrency().getCurrencyCode(), is("CHF"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void stringWithoutCurrencyCodeThrowsIllegalArgumentException() {
        new AmountOfMoney("125.55");
    }

    @Test
    public void moneyEquality() {
        assertThat(new AmountOfMoney("CHF 120").equals(new AmountOfMoney("CHF 120")), is(true));
        assertThat(new AmountOfMoney("CHF 120.05").equals(new AmountOfMoney("CHF 120")), is(false));
        assertThat(new AmountOfMoney("EUR 120").equals(new AmountOfMoney("CHF 120")), is(false));
    }
}
