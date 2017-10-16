package com.deange.uwaterlooapi.model.converter;

import com.deange.uwaterlooapi.model.watcard.Watcard;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;

public class WatcardConverter
    extends BaseHtmlConverter<Watcard> {

  public WatcardConverter(final HttpUrl baseUrl) {
    super(baseUrl);
  }

  @Override
  public Watcard parse(final Document document) {
    final Elements rows = document.select("#main .ow-content-wrapper table tbody tr");
    if (rows.isEmpty()) {
      // No Watcard information found
      return null;
    }

    final Watcard.Builder builder = Watcard.builder();

    final List<Watcard.Row> rowList = new ArrayList<>();
    for (final Element element : rows) {
      final Elements cells = element.select("td");
      if (cells.isEmpty()) {
        // Header row
        continue;
      }

      final String account = cells.get(1).text();
      final BigDecimal limit = parseAmount(cells.get(2).text());
      final BigDecimal amount = parseAmount(cells.get(3).text());

      rowList.add(Watcard.Row.create(account, limit, amount));
    }

    builder.setAccounts(rowList);

    final Element totalElement = document.select("#main .ow-summary-panel span").first();
    final String total;
    if (totalElement != null) {
      total = totalElement.text().replaceAll("Total: ", "");
    } else {
      total = "$0.00";
    }

    builder.setTotal(parseAmount(total));

    return builder.build();
  }

}
