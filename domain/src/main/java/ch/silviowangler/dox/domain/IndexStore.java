/*
 * Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        DateTime: 09.07.12 07:54
 *        </div>
 */

@Entity
@Table(name = "DOX_IDX_STORE")
public class IndexStore extends AbstractPersistable<Long> {

    @OneToOne(optional = false)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;
    @Column(length = 255)
    private String S_01;
    @Column(length = 255)
    private String S_02;
    @Column(length = 255)
    private String S_03;
    @Column(length = 255)
    private String S_04;
    @Column(length = 255)
    private String S_05;
    @Column(length = 255)
    private String S_06;
    @Column(length = 255)
    private String S_07;
    @Column(length = 255)
    private String S_08;
    @Column(length = 255)
    private String S_09;
    @Column(length = 255)
    private String S_10;
    @Column(length = 255)
    private String S_11;
    @Column(length = 255)
    private String S_12;
    @Column(length = 255)
    private String S_13;
    @Column(length = 255)
    private String S_14;
    @Column(length = 255)
    private String S_15;
    @Column(length = 255)
    private String S_16;
    @Column(length = 255)
    private String S_17;
    @Column(length = 255)
    private String S_18;
    @Column(length = 255)
    private String S_19;
    @Column(length = 255)
    private String S_20;

    @Column
    @Type(type = "ch.silviowangler.dox.domain.hibernate.PersistentDateTime")
    private DateTime D_01;
    @Column
    @Type(type = "ch.silviowangler.dox.domain.hibernate.PersistentDateTime")
    private DateTime D_02;
    @Column
    @Type(type = "ch.silviowangler.dox.domain.hibernate.PersistentDateTime")
    private DateTime D_03;
    @Column
    @Type(type = "ch.silviowangler.dox.domain.hibernate.PersistentDateTime")
    private DateTime D_04;
    @Column
    @Type(type = "ch.silviowangler.dox.domain.hibernate.PersistentDateTime")
    private DateTime D_05;
    @Column
    @Type(type = "ch.silviowangler.dox.domain.hibernate.PersistentDateTime")
    private DateTime D_06;
    @Column
    @Type(type = "ch.silviowangler.dox.domain.hibernate.PersistentDateTime")
    private DateTime D_07;
    @Column
    @Type(type = "ch.silviowangler.dox.domain.hibernate.PersistentDateTime")
    private DateTime D_08;
    @Column
    @Type(type = "ch.silviowangler.dox.domain.hibernate.PersistentDateTime")
    private DateTime D_09;
    @Column
    @Type(type = "ch.silviowangler.dox.domain.hibernate.PersistentDateTime")
    private DateTime D_10;

    @Column
    private Long L_01;
    @Column
    private Long L_02;
    @Column
    private Long L_03;
    @Column
    private Long L_04;
    @Column
    private Long L_05;
    @Column
    private Long L_06;
    @Column
    private Long L_07;
    @Column
    private Long L_08;
    @Column
    private Long L_09;
    @Column
    private Long L_10;

    @Column
    private BigDecimal F_01;
    @Column
    private BigDecimal F_02;
    @Column
    private BigDecimal F_03;
    @Column
    private BigDecimal F_04;
    @Column
    private BigDecimal F_05;
    @Column
    private BigDecimal F_06;
    @Column
    private BigDecimal F_07;
    @Column
    private BigDecimal F_08;
    @Column
    private BigDecimal F_09;
    @Column
    private BigDecimal F_10;

    public String getS_01() {
        return S_01;
    }

    public void setS_01(String s_01) {
        S_01 = s_01;
    }

    public String getS_02() {
        return S_02;
    }

    public void setS_02(String s_02) {
        S_02 = s_02;
    }

    public String getS_03() {
        return S_03;
    }

    public void setS_03(String s_03) {
        S_03 = s_03;
    }

    public String getS_04() {
        return S_04;
    }

    public void setS_04(String s_04) {
        S_04 = s_04;
    }

    public String getS_05() {
        return S_05;
    }

    public void setS_05(String s_05) {
        S_05 = s_05;
    }

    public String getS_06() {
        return S_06;
    }

    public void setS_06(String s_06) {
        S_06 = s_06;
    }

    public String getS_07() {
        return S_07;
    }

    public void setS_07(String s_07) {
        S_07 = s_07;
    }

    public String getS_08() {
        return S_08;
    }

    public void setS_08(String s_08) {
        S_08 = s_08;
    }

    public String getS_09() {
        return S_09;
    }

    public void setS_09(String s_09) {
        S_09 = s_09;
    }

    public String getS_10() {
        return S_10;
    }

    public void setS_10(String s_10) {
        S_10 = s_10;
    }

    public String getS_11() {
        return S_11;
    }

    public void setS_11(String s_11) {
        S_11 = s_11;
    }

    public String getS_12() {
        return S_12;
    }

    public void setS_12(String s_12) {
        S_12 = s_12;
    }

    public String getS_13() {
        return S_13;
    }

    public void setS_13(String s_13) {
        S_13 = s_13;
    }

    public String getS_14() {
        return S_14;
    }

    public void setS_14(String s_14) {
        S_14 = s_14;
    }

    public String getS_15() {
        return S_15;
    }

    public void setS_15(String s_15) {
        S_15 = s_15;
    }

    public String getS_16() {
        return S_16;
    }

    public void setS_16(String s_16) {
        S_16 = s_16;
    }

    public String getS_17() {
        return S_17;
    }

    public void setS_17(String s_17) {
        S_17 = s_17;
    }

    public String getS_18() {
        return S_18;
    }

    public void setS_18(String s_18) {
        S_18 = s_18;
    }

    public String getS_19() {
        return S_19;
    }

    public void setS_19(String s_19) {
        S_19 = s_19;
    }

    public String getS_20() {
        return S_20;
    }

    public void setS_20(String s_20) {
        S_20 = s_20;
    }

    public DateTime getD_01() {
        return D_01;
    }

    public void setD_01(DateTime d_01) {
        D_01 = d_01;
    }

    public DateTime getD_02() {
        return D_02;
    }

    public void setD_02(DateTime d_02) {
        D_02 = d_02;
    }

    public DateTime getD_03() {
        return D_03;
    }

    public void setD_03(DateTime d_03) {
        D_03 = d_03;
    }

    public DateTime getD_04() {
        return D_04;
    }

    public void setD_04(DateTime d_04) {
        D_04 = d_04;
    }

    public DateTime getD_05() {
        return D_05;
    }

    public void setD_05(DateTime d_05) {
        D_05 = d_05;
    }

    public DateTime getD_06() {
        return D_06;
    }

    public void setD_06(DateTime d_06) {
        D_06 = d_06;
    }

    public DateTime getD_07() {
        return D_07;
    }

    public void setD_07(DateTime d_07) {
        D_07 = d_07;
    }

    public DateTime getD_08() {
        return D_08;
    }

    public void setD_08(DateTime d_08) {
        D_08 = d_08;
    }

    public DateTime getD_09() {
        return D_09;
    }

    public void setD_09(DateTime d_09) {
        D_09 = d_09;
    }

    public DateTime getD_10() {
        return D_10;
    }

    public void setD_10(DateTime d_10) {
        D_10 = d_10;
    }

    public Long getL_01() {
        return L_01;
    }

    public void setL_01(Long l_01) {
        L_01 = l_01;
    }

    public Long getL_02() {
        return L_02;
    }

    public void setL_02(Long l_02) {
        L_02 = l_02;
    }

    public Long getL_03() {
        return L_03;
    }

    public void setL_03(Long l_03) {
        L_03 = l_03;
    }

    public Long getL_04() {
        return L_04;
    }

    public void setL_04(Long l_04) {
        L_04 = l_04;
    }

    public Long getL_05() {
        return L_05;
    }

    public void setL_05(Long l_05) {
        L_05 = l_05;
    }

    public Long getL_06() {
        return L_06;
    }

    public void setL_06(Long l_06) {
        L_06 = l_06;
    }

    public Long getL_07() {
        return L_07;
    }

    public void setL_07(Long l_07) {
        L_07 = l_07;
    }

    public Long getL_08() {
        return L_08;
    }

    public void setL_08(Long l_08) {
        L_08 = l_08;
    }

    public Long getL_09() {
        return L_09;
    }

    public void setL_09(Long l_09) {
        L_09 = l_09;
    }

    public Long getL_10() {
        return L_10;
    }

    public void setL_10(Long l_10) {
        L_10 = l_10;
    }

    public BigDecimal getF_01() {
        return F_01;
    }

    public void setF_01(BigDecimal f_01) {
        F_01 = f_01;
    }

    public BigDecimal getF_02() {
        return F_02;
    }

    public void setF_02(BigDecimal f_02) {
        F_02 = f_02;
    }

    public BigDecimal getF_03() {
        return F_03;
    }

    public void setF_03(BigDecimal f_03) {
        F_03 = f_03;
    }

    public BigDecimal getF_04() {
        return F_04;
    }

    public void setF_04(BigDecimal f_04) {
        F_04 = f_04;
    }

    public BigDecimal getF_05() {
        return F_05;
    }

    public void setF_05(BigDecimal f_05) {
        F_05 = f_05;
    }

    public BigDecimal getF_06() {
        return F_06;
    }

    public void setF_06(BigDecimal f_06) {
        F_06 = f_06;
    }

    public BigDecimal getF_07() {
        return F_07;
    }

    public void setF_07(BigDecimal f_07) {
        F_07 = f_07;
    }

    public BigDecimal getF_08() {
        return F_08;
    }

    public void setF_08(BigDecimal f_08) {
        F_08 = f_08;
    }

    public BigDecimal getF_09() {
        return F_09;
    }

    public void setF_09(BigDecimal f_09) {
        F_09 = f_09;
    }

    public BigDecimal getF_10() {
        return F_10;
    }

    public void setF_10(BigDecimal f_10) {
        F_10 = f_10;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "IndexStore{" +
                "id='" + super.getId() + '\'' +
                "document id='" + getDocument().getId() + '\'' +
                "S_01='" + S_01 + '\'' +
                ", S_02='" + S_02 + '\'' +
                ", S_03='" + S_03 + '\'' +
                ", S_04='" + S_04 + '\'' +
                ", S_05='" + S_05 + '\'' +
                ", S_06='" + S_06 + '\'' +
                ", S_07='" + S_07 + '\'' +
                ", S_08='" + S_08 + '\'' +
                ", S_09='" + S_09 + '\'' +
                ", S_10='" + S_10 + '\'' +
                ", S_11='" + S_11 + '\'' +
                ", S_12='" + S_12 + '\'' +
                ", S_13='" + S_13 + '\'' +
                ", S_14='" + S_14 + '\'' +
                ", S_15='" + S_15 + '\'' +
                ", S_16='" + S_16 + '\'' +
                ", S_17='" + S_17 + '\'' +
                ", S_18='" + S_18 + '\'' +
                ", S_19='" + S_19 + '\'' +
                ", S_20='" + S_20 + '\'' +
                ", D_01=" + D_01 +
                ", D_02=" + D_02 +
                ", D_03=" + D_03 +
                ", D_04=" + D_04 +
                ", D_05=" + D_05 +
                ", D_06=" + D_06 +
                ", D_07=" + D_07 +
                ", D_08=" + D_08 +
                ", D_09=" + D_09 +
                ", D_10=" + D_10 +
                ", L_01=" + L_01 +
                ", L_02=" + L_02 +
                ", L_03=" + L_03 +
                ", L_04=" + L_04 +
                ", L_05=" + L_05 +
                ", L_06=" + L_06 +
                ", L_07=" + L_07 +
                ", L_08=" + L_08 +
                ", L_09=" + L_09 +
                ", L_10=" + L_10 +
                ", F_01=" + F_01 +
                ", F_02=" + F_02 +
                ", F_03=" + F_03 +
                ", F_04=" + F_04 +
                ", F_05=" + F_05 +
                ", F_06=" + F_06 +
                ", F_07=" + F_07 +
                ", F_08=" + F_08 +
                ", F_09=" + F_09 +
                ", F_10=" + F_10 +
                '}';
    }
}
