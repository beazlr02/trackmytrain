package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Activity;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class RemembersDepartureQueryTest {


    @Test
    public void theScreenLoadsWithLastQueriedFromStation() {
        activity().setContentView(R.layout.servicedetails);

        TextView viewById = (TextView) activity().findViewById(R.id.from);
        String fromText = String.valueOf(viewById.getText());
        assertThat(fromText,is("BON"));
    }


    private Testing activity() {
        ActivityController<Testing> actCtl = Robolectric.buildActivity(Testing.class);
        return actCtl.create()
                .start()
                .resume()
                .visible()
                .get();
    }

    public static class Testing extends Activity {

    }

}
