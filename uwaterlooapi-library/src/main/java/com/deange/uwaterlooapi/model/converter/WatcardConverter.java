package com.deange.uwaterlooapi.model.converter;

import com.deange.uwaterlooapi.model.watcard.Watcard;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.HttpUrl;

public class WatcardConverter
        extends BaseHtmlConverter<Watcard> {

    public WatcardConverter(final HttpUrl baseUrl) {
        super(baseUrl);
    }

    @Override
    public Watcard parse(final Document document) {
        final Elements rows = document.select("#main .ow-content-wrapper table tr");

        final Watcard.Builder builder = Watcard.builder();

        final List<Watcard.Row> rowList = new ArrayList<>();
        for (final Element element : rows) {
            final Elements cells = element.select("td");
            if (cells.isEmpty()) {
                // Header row
                continue;
            }

            final String account = cells.get(1).text();
            final Number limit = parseAmount(cells.get(2).text());
            final Number amount = parseAmount(cells.get(3).text());

            rowList.add(Watcard.Row.create(account, limit, amount));
        }

        builder.setAccounts(rowList);

        final Element totalElement = document.select("#main .ow-summary-panel span").first();
        String total = (totalElement != null) ? totalElement.text() : "$0.00";
        total = total.substring(total.indexOf('$'));

        builder.setTotal(parseAmount(total));

        return builder.build();
    }

    private static Number parseAmount(final String amount) {
        try {
            return NumberFormat.getCurrencyInstance(Locale.CANADA).parse(amount);
        } catch (final ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

}
