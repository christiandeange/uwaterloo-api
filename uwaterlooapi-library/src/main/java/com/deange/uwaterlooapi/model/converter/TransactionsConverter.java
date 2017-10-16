package com.deange.uwaterlooapi.model.converter;

import android.util.Pair;

import com.deange.uwaterlooapi.model.watcard.Transaction;
import com.deange.uwaterlooapi.model.watcard.TransactionDate;
import com.deange.uwaterlooapi.model.watcard.Transactions;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;

public class TransactionsConverter
    extends BaseHtmlConverter<Transactions> {

  public TransactionsConverter(final HttpUrl baseUrl) {
    super(baseUrl);
  }

  @Override
  public Transactions parse(final Document document) {
    final Elements rows = document.select("table tbody tr");
    if (rows.isEmpty()) {
      // No Watcard information found
      return null;
    }

    final Transactions.Builder builder = Transactions.builder();

    for (final Element element : rows) {
      final Elements cells = element.select("td");

      final TransactionDate date = TransactionDate.create(cells.get(0).text());
      final BigDecimal amount = parseAmount(cells.get(1).text());
      final Transaction.Type type = parseType(cells.get(4).text());
      final Transaction.Vendor vendor = parseVendor(cells.get(5).text());

      builder.addTransaction(
          Transaction.builder()
                     .setDate(date)
                     .setAmount(amount)
                     .setType(type)
                     .setVendor(vendor)
                     .build()
      );
    }

    return builder.build();
  }

  private Transaction.Vendor parseVendor(final String rawVendor) {
    final Pair<Integer, String> fields = parseFieldPair(rawVendor);
    return Transaction.Vendor.create(fields.first, fields.second);
  }

  private static Transaction.Type parseType(final String rawType) {
    final Pair<Integer, String> fields = parseFieldPair(rawType);
    return Transaction.Type.create(fields.first, fields.second);
  }

  private static Pair<Integer, String> parseFieldPair(final String rawValue) {
    final Pattern regex = Pattern.compile("^(\\d+) : (.*)$");
    final Matcher matcher = regex.matcher(rawValue);
    if (matcher.matches()) {
      final int code = Integer.parseInt(matcher.group(1));
      final String category = matcher.group(2);
      return Pair.create(code, category);

    } else {
      return Pair.create(-1, null);
    }
  }

}
