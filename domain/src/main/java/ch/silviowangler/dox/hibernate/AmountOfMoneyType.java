/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
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

package ch.silviowangler.dox.hibernate;

import ch.silviowangler.dox.domain.AmountOfMoney;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Currency;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 11.07.12 09:37
 *        </div>
 */
public class AmountOfMoneyType implements EnhancedUserType, Serializable {

    private static final int[] SQL_TYPES = new int[]{Types.VARCHAR,};

    @Override
    public String objectToSQLString(Object value) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public String toXMLString(Object value) {
        // will be removed when Hibernate 5 gets released
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public Object fromXMLString(String xmlValue) {
        // will be removed when Hibernate 5 gets released
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return AmountOfMoney.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        AmountOfMoney dtx = (AmountOfMoney) x;
        AmountOfMoney dty = (AmountOfMoney) y;

        return dtx.equals(dty);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String value = (String) StandardBasicTypes.STRING.nullSafeGet(rs, names, session, owner);

        if (value == null) {
            return null;
        }

        String[] tokens = value.split(" ");
        assert tokens.length == 2 : "Wrong token size";

        DecimalFormat df = new DecimalFormat();
        df.setParseBigDecimal(true);
        final BigDecimal amount;
        try {
            amount = (BigDecimal) df.parse(tokens[1]);
            return new AmountOfMoney(Currency.getInstance(tokens[0]), amount);
        } catch (ParseException e) {
            throw new HibernateException("", e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            StandardBasicTypes.STRING.nullSafeSet(st, null, index, session);
        } else {

            AmountOfMoney amountOfMoney = (AmountOfMoney) value;

            StringBuilder sb = new StringBuilder(amountOfMoney.getCurrency().getCurrencyCode());
            sb.append(" ").append(amountOfMoney.getAmount().toPlainString());
            StandardBasicTypes.STRING.nullSafeSet(st, sb.toString(), index, session);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
