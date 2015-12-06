package com.deange.uwaterlooapi.sample.ui.modules.parking;

import com.deange.uwaterlooapi.sample.ui.Colors;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ParkingLots {

    private static final float[] C = {
            43.466804f, -80.539699f,
            43.466563f, -80.538899f,
            43.466625f, -80.538583f,
            43.466866f, -80.537880f,
            43.466921f, -80.537735f,
            43.467139f, -80.537472f,
            43.467949f, -80.537006f,
            43.468066f, -80.537403f,
            43.467875f, -80.537515f,
            43.467933f, -80.537633f,
            43.467999f, -80.537869f,
            43.468003f, -80.538191f,
            43.468097f, -80.538411f,
            43.468124f, -80.538433f,
            43.468163f, -80.538454f,
            43.468202f, -80.538470f,
            43.468233f, -80.538470f,
            43.468272f, -80.538465f,
            43.468303f, -80.538449f,
            43.468350f, -80.538422f,
            43.468467f, -80.538824f,
    };

    private static final float[] N = {
            43.474302f, -80.545551f,
            43.475089f, -80.543389f,
            43.475400f, -80.543604f,
            43.474622f, -80.545760f,
    };

    private static final float[] W = {
            43.474886f, -80.548217f,
            43.474840f, -80.548190f,
            43.474820f, -80.548255f,
            43.474450f, -80.548003f,
            43.474470f, -80.547949f,
            43.474423f, -80.547922f,
            43.474769f, -80.546951f,
            43.474832f, -80.547000f,
            43.474855f, -80.546930f,
            43.475061f, -80.547053f,
            43.475042f, -80.547118f,
            43.475081f, -80.547144f,
            43.475007f, -80.547364f,
            43.475147f, -80.547466f,

    };

    private static final float[] X = {
            43.476392f, -80.547030f,
            43.476373f, -80.547000f,
            43.476369f, -80.546954f,
            43.476682f, -80.546109f,
            43.476715f, -80.546082f,
            43.476754f, -80.546080f,
            43.476786f, -80.546093f,
            43.476918f, -80.546250f,
            43.476945f, -80.546249f,
            43.476976f, -80.546223f,
            43.477131f, -80.545853f,
            43.477134f, -80.545810f,
            43.477127f, -80.545771f,
            43.477053f, -80.545677f,
            43.477044f, -80.545640f,
            43.477051f, -80.545590f,
            43.477452f, -80.544497f,
            43.477479f, -80.544465f,
            43.477524f, -80.544460f,
            43.477570f, -80.544468f,
            43.478189f, -80.545058f,
            43.478211f, -80.545093f,
            43.478213f, -80.545125f,
            43.478205f, -80.545162f,
            43.478185f, -80.545213f,
            43.478226f, -80.545245f,
            43.477819f, -80.546369f,
            43.477786f, -80.546337f,
            43.477740f, -80.546388f,
            43.477710f, -80.546404f,
            43.477677f, -80.546404f,
            43.477644f, -80.546388f,
            43.477619f, -80.546359f,
            43.477286f, -80.545962f,
            43.477259f, -80.545948f,
            43.477234f, -80.545956f,
            43.477208f, -80.545972f,
            43.477191f, -80.545999f,
            43.477060f, -80.546348f,
            43.477053f, -80.546377f,
            43.477054f, -80.546407f,
            43.477064f, -80.546428f,
            43.477294f, -80.546731f,
            43.477302f, -80.546747f,
            43.477302f, -80.546774f,
            43.477294f, -80.546806f,
            43.477064f, -80.547431f,
            43.477053f, -80.547452f,
            43.477029f, -80.547460f,
            43.477011f, -80.547454f,
            43.476991f, -80.547443f,
    };

    private static final Map<String, List<LatLng>> CACHE_POINTS = new HashMap<>();
    private static final Map<String, PolygonOptions> CACHE_POLYGON = new HashMap<>();

    private ParkingLots() {
        throw new AssertionError();
    }

    public static PolygonOptions getShape(final String parkingLot) {
        PolygonOptions shape = CACHE_POLYGON.get(parkingLot);

        if (shape == null) {
            shape = new PolygonOptions()
                    .addAll(getPoints(parkingLot))
                    .fillColor(Colors.mask(0x20, Colors.AMBER_100));
            CACHE_POLYGON.put(parkingLot, shape);
        }

        return shape;
    }

    public static List<LatLng> getPoints(final String parkingLot) {
        List<LatLng> points = CACHE_POINTS.get(parkingLot);

        if (points == null) {
            points = getPoints(getFloatArray(parkingLot));
            CACHE_POINTS.put(parkingLot, points);
        }

        return points;
    }

    private static List<LatLng> getPoints(final float[] coordinates) {
        final List<LatLng> points = new ArrayList<>();
        for (int i = 0; i < coordinates.length; i += 2) {
            points.add(new LatLng(coordinates[i], coordinates[i + 1]));
        }
        return points;
    }

    private static float[] getFloatArray(final String parkingLot) {
        switch (parkingLot) {
            case "C": return C;
            case "N": return N;
            case "W": return W;
            case "X": return X;
            default:  return new float[0];
        }
    }

    // yeah idk how this works (http://stackoverflow.com/a/2922778/1403479)
    public static boolean isInPoly(final float px, final float py, List<LatLng> points) {
        boolean inPoly = false;
        final int n = points.size();

        for (int i = 0, j = n - 1; i < n; j = i++) {
            float vertiy = (float) points.get(i).latitude;
            float vertix = (float) points.get(i).longitude;
            float vertjy = (float) points.get(j).latitude;
            float vertjx = (float) points.get(j).longitude;

            if (((vertiy > py) != (vertjy > py))
                    && (px < (vertjx - vertix) * (py - vertiy) / (vertjy - vertiy) + vertix)) {
                inPoly = !inPoly;
            }
        }
        return inPoly;
    }

}
