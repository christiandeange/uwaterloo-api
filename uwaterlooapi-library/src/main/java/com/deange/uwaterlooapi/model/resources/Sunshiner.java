package com.deange.uwaterlooapi.model.resources;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Parcel
public class Sunshiner extends BaseModel
        implements
        Comparable<Sunshiner> {

    private static final MathContext CURRENCY_CONTEXT = new MathContext(2, RoundingMode.HALF_EVEN);

    @SerializedName("employer")
    String mEmployer;

    @SerializedName("surname")
    String mSurname;

    @SerializedName("given_name")
    String mGivenName;

    @SerializedName("position")
    String mPosition;

    @SerializedName("salary_paid")
    String mSalaryRaw;

    @SerializedName("taxable_benefits")
    String mTaxableBenefitsRaw;

    BigDecimal mSalary;
    BigDecimal mTaxableBenefits;

    /**
     * Name of Employer (UW)
     */
    public String getEmployer() {
        return mEmployer;
    }

    /**
     * Last name of the employee
     */
    public String getSurname() {
        return mSurname;
    }

    /**
     * Given name of the employee
     */
    public String getGivenName() {
        return mGivenName;
    }

    /**
     * Employee's position at the University
     */
    public String getPosition() {
        return mPosition;
    }

    /**
     * Salary paid per annum in dollars
     */
    public BigDecimal getSalary() {
        if (mSalary == null) {
            mSalary = parseCurrency(mSalaryRaw);
        }
        return mSalary;
    }

    /**
     * Salary paid per annum in dollars as a string
     */
    public String getSalaryRaw() {
        return mSalaryRaw;
    }

    /**
     * Employee's taxable benefits in dollars
     */
    public BigDecimal getTaxableBenefits() {
        if (mTaxableBenefits == null) {
            mTaxableBenefits = parseCurrency(mTaxableBenefitsRaw);
        }
        return mTaxableBenefits;
    }

    /**
     * Employee's taxable benefits in dollars as a string
     */
    public String getTaxableBenefitsRaw() {
        return mTaxableBenefitsRaw;
    }

    @Override
    public int compareTo(final Sunshiner another) {
        return getSalary().compareTo(another.getSalary());
    }

    private static BigDecimal parseCurrency(final String currency) {
        final String cleanValue = currency.replaceAll("[^\\d.]+", "");
        return new BigDecimal(cleanValue, CURRENCY_CONTEXT);
    }
}
