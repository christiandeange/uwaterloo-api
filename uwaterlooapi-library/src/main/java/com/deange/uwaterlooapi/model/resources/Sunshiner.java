package com.deange.uwaterlooapi.model.resources;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Sunshiner
    extends BaseModel
    implements
    Parcelable,
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

  protected Sunshiner(final Parcel in) {
    super(in);
    mEmployer = in.readString();
    mSurname = in.readString();
    mGivenName = in.readString();
    mPosition = in.readString();
    mSalaryRaw = in.readString();
    mTaxableBenefitsRaw = in.readString();
    mSalary = (BigDecimal) in.readSerializable();
    mTaxableBenefits = (BigDecimal) in.readSerializable();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mEmployer);
    dest.writeString(mSurname);
    dest.writeString(mGivenName);
    dest.writeString(mPosition);
    dest.writeString(mSalaryRaw);
    dest.writeString(mTaxableBenefitsRaw);
    dest.writeSerializable(mSalary);
    dest.writeSerializable(mTaxableBenefits);
  }

  public static final Creator<Sunshiner> CREATOR = new Creator<Sunshiner>() {
    @Override
    public Sunshiner createFromParcel(final Parcel in) {
      return new Sunshiner(in);
    }

    @Override
    public Sunshiner[] newArray(final int size) {
      return new Sunshiner[size];
    }
  };

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
