package com.deange.uwaterlooapi.sample.model.responses;

import com.deange.uwaterlooapi.model.common.SimpleResponse;
import com.deange.uwaterlooapi.model.watcard.Watcard;

public class WatcardResponse
    extends SimpleResponse<Watcard> {

  public WatcardResponse(final Watcard data) {
    super(data);
  }
}
