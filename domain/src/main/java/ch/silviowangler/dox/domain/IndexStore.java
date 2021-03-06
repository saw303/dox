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

import static com.google.common.base.MoreObjects.toStringHelper;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime D_01;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime D_02;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime D_03;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime D_04;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime D_05;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime D_06;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime D_07;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime D_08;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime D_09;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime D_10;

    @Column
    @Type(type = "ch.silviowangler.dox.hibernate.AmountOfMoneyType")
    private AmountOfMoney C_01;
    @Column
    @Type(type = "ch.silviowangler.dox.hibernate.AmountOfMoneyType")
    private AmountOfMoney C_02;
    @Column
    @Type(type = "ch.silviowangler.dox.hibernate.AmountOfMoneyType")
    private AmountOfMoney C_03;
    @Column
    @Type(type = "ch.silviowangler.dox.hibernate.AmountOfMoneyType")
    private AmountOfMoney C_04;
    @Column
    @Type(type = "ch.silviowangler.dox.hibernate.AmountOfMoneyType")
    private AmountOfMoney C_05;
    @Column
    @Type(type = "ch.silviowangler.dox.hibernate.AmountOfMoneyType")
    private AmountOfMoney C_06;
    @Column
    @Type(type = "ch.silviowangler.dox.hibernate.AmountOfMoneyType")
    private AmountOfMoney C_07;
    @Column
    @Type(type = "ch.silviowangler.dox.hibernate.AmountOfMoneyType")
    private AmountOfMoney C_08;
    @Column
    @Type(type = "ch.silviowangler.dox.hibernate.AmountOfMoneyType")
    private AmountOfMoney C_09;
    @Column
    @Type(type = "ch.silviowangler.dox.hibernate.AmountOfMoneyType")
    private AmountOfMoney C_10;

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
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate LD_01;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate LD_02;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate LD_03;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate LD_04;
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate LD_05;

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

    public AmountOfMoney getC_01() {
        return C_01;
    }

    public void setC_01(AmountOfMoney c_01) {
        C_01 = c_01;
    }

    public AmountOfMoney getC_02() {
        return C_02;
    }

    public void setC_02(AmountOfMoney c_02) {
        C_02 = c_02;
    }

    public AmountOfMoney getC_03() {
        return C_03;
    }

    public void setC_03(AmountOfMoney c_03) {
        C_03 = c_03;
    }

    public AmountOfMoney getC_04() {
        return C_04;
    }

    public void setC_04(AmountOfMoney c_04) {
        C_04 = c_04;
    }

    public AmountOfMoney getC_05() {
        return C_05;
    }

    public void setC_05(AmountOfMoney c_05) {
        C_05 = c_05;
    }

    public AmountOfMoney getC_06() {
        return C_06;
    }

    public void setC_06(AmountOfMoney c_06) {
        C_06 = c_06;
    }

    public AmountOfMoney getC_07() {
        return C_07;
    }

    public void setC_07(AmountOfMoney c_07) {
        C_07 = c_07;
    }

    public AmountOfMoney getC_08() {
        return C_08;
    }

    public void setC_08(AmountOfMoney c_08) {
        C_08 = c_08;
    }

    public AmountOfMoney getC_09() {
        return C_09;
    }

    public void setC_09(AmountOfMoney c_09) {
        C_09 = c_09;
    }

    public AmountOfMoney getC_10() {
        return C_10;
    }

    public void setC_10(AmountOfMoney c_10) {
        C_10 = c_10;
    }

    public LocalDate getLD_01() {
        return LD_01;
    }

    public void setLD_01(LocalDate ld_01) {
        this.LD_01 = ld_01;
    }

    public LocalDate getLD_02() {
        return LD_02;
    }

    public void setLD_02(LocalDate ld_02) {
        this.LD_02 = ld_02;
    }

    public LocalDate getLD_03() {
        return LD_03;
    }

    public void setLD_03(LocalDate ld_03) {
        this.LD_03 = ld_03;
    }

    public LocalDate getLD_04() {
        return LD_04;
    }

    public void setLD_04(LocalDate ld_04) {
        this.LD_04 = ld_04;
    }

    public LocalDate getLD_05() {
        return LD_05;
    }

    public void setLD_05(LocalDate ld_05) {
        this.LD_05 = ld_05;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("document", document.getId())
                .add("S_01", S_01)
                .add("S_02", S_02)
                .add("S_03", S_03)
                .add("S_04", S_04)
                .add("S_05", S_05)
                .add("S_06", S_06)
                .add("S_07", S_07)
                .add("S_08", S_08)
                .add("S_09", S_09)
                .add("S_10", S_10)
                .add("S_11", S_11)
                .add("S_12", S_12)
                .add("S_13", S_13)
                .add("S_14", S_14)
                .add("S_15", S_15)
                .add("S_16", S_16)
                .add("S_17", S_17)
                .add("S_18", S_18)
                .add("S_19", S_19)
                .add("S_20", S_20)
                .add("D_01", D_01)
                .add("D_02", D_02)
                .add("D_03", D_03)
                .add("D_04", D_04)
                .add("D_05", D_05)
                .add("D_06", D_06)
                .add("D_07", D_07)
                .add("D_08", D_08)
                .add("D_09", D_09)
                .add("D_10", D_10)
                .add("C_01", C_01)
                .add("C_02", C_02)
                .add("C_03", C_03)
                .add("C_04", C_04)
                .add("C_05", C_05)
                .add("C_06", C_06)
                .add("C_07", C_07)
                .add("C_08", C_08)
                .add("C_09", C_09)
                .add("C_10", C_10)
                .add("L_01", L_01)
                .add("L_02", L_02)
                .add("L_03", L_03)
                .add("L_04", L_04)
                .add("L_05", L_05)
                .add("L_06", L_06)
                .add("L_07", L_07)
                .add("L_08", L_08)
                .add("L_09", L_09)
                .add("L_10", L_10)
                .add("F_01", F_01)
                .add("F_02", F_02)
                .add("F_03", F_03)
                .add("F_04", F_04)
                .add("F_05", F_05)
                .add("F_06", F_06)
                .add("F_07", F_07)
                .add("F_08", F_08)
                .add("F_09", F_09)
                .add("F_10", F_10)
                .add("LD_01", LD_01)
                .add("LD_02", LD_02)
                .add("LD_03", LD_03)
                .add("LD_04", LD_04)
                .add("LD_05", LD_05)
                .toString();
    }
}
