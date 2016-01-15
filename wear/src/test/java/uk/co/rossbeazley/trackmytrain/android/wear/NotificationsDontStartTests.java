package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotificationsDontStartTests {


    @Test
    public void
    whenAllUIDetachesWhenNotTracking_NONotificationServiceIsStarted() {


        CapturingNotificationServiceService service = new CapturingNotificationServiceService();

        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman(), service);
        final CapturingServiceView anyView = new CapturingServiceView();
        wearApp.attach(anyView);

        wearApp.detach(anyView);

        assertThat(service.state, is(CapturingNotificationServiceService.UNKNOWN));

    }







}
