package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.RequestMapNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 17/02/2015.
 */
public class ServiceTest {

    private Train serviceDisplayed;

    @Test
    public void theOneWhereWeSelectAServiceAndTrackingStarts() {

        final String serviceId = "3Olk7M389Qp5JIdkXAQt4g==";
        final String scheduledTime = "20:48";
        final String estimatedTime = "On time";
        final String platform = "2";

        Map<NetworkClient.Request, String> map = new HashMap<NetworkClient.Request, String>(){{
            put(new ServiceDetailsRequest(serviceId), jsonForTrain(serviceId, scheduledTime, estimatedTime, platform));
        }};
        NetworkClient client = new RequestMapNetworkClient(map);
        ServiceView serviceView = new ServiceView() {
            @Override public void present(Train train) {
                serviceDisplayed = train;
            }
        };
        TrackMyTrain tmt = new TMTBuilder()
                .with(client)
                .with(serviceView)
                .build();

        tmt.watch(serviceId);

        assertThat(serviceDisplayed, is(new Train(serviceId, estimatedTime, scheduledTime, platform)));

    }

    private String jsonForTrain(String serviceId, String scheduledTime, String estimatedTime, String platform) {
        return "{\n" +
                "\"id\": \"" + serviceId + "\",\n" +
                "\"scheduledTime\": \"" + scheduledTime + "\",\n" +
                "\"estimatedTime\": \"" + estimatedTime + "\",\n" +
                "\"platform\": \"" + platform + "\"\n" +
                "}";
    }

    @Test
    public void serviceDetailsRequestRendersToString() {

        assertThat(new ServiceDetailsRequest("123456").asUrlString(),is("http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service/123456"));
    }

    @Test
    public void theOneWhereWeAreUpdatedAboutTheSelectedService() {
        final String serviceId = "3Olk7M389Qp5JIdkXAQt4g==";
        final String scheduledTime = "20:48";
        String estimatedTime = "On time";
        final String platform = "2";

        final String initialJson = jsonForTrain(serviceId, scheduledTime, estimatedTime, platform);
        final ServiceDetailsRequest serviceDetailsRequest = new ServiceDetailsRequest(serviceId);

        Map<NetworkClient.Request, String> map = new HashMap<NetworkClient.Request, String>(){{
            put(serviceDetailsRequest, initialJson);
        }};
        NetworkClient client = new RequestMapNetworkClient(map);
        ServiceView serviceView = new ServiceView() {
            @Override public void present(Train train) {
                serviceDisplayed = train;
            }
        };
        TrackMyTrain tmt = new TMTBuilder()
                .with(client)
                .with(serviceView)
                .build();

        tmt.watch(serviceId);

        serviceDisplayed=null;

        String lateTime = "20:52";
        map.put(serviceDetailsRequest,jsonForTrain(serviceId, scheduledTime, lateTime, platform));

        // time passes
        tmt.tick();

        assertThat(serviceDisplayed, is(new Train(serviceId, lateTime, scheduledTime, platform)));

    }

    @Test
    public void theOneWhereWeStopTracking() {
        final String serviceId = "3Olk7M389Qp5JIdkXAQt4g==";
        final String scheduledTime = "20:48";
        String estimatedTime = "On time";
        final String platform = "2";

        final String initialJson = jsonForTrain(serviceId, scheduledTime, estimatedTime, platform);
        final ServiceDetailsRequest serviceDetailsRequest = new ServiceDetailsRequest(serviceId);

        Map<NetworkClient.Request, String> map = new HashMap<NetworkClient.Request, String>(){{
            put(serviceDetailsRequest, initialJson);
        }};
        NetworkClient client = new RequestMapNetworkClient(map);
        ServiceView serviceView = new ServiceView() {
            @Override public void present(Train train) {
                serviceDisplayed = train;
            }
        };
        TrackMyTrain tmt = new TMTBuilder()
                .with(client)
                .with(serviceView)
                .build();

        tmt.watch(serviceId);

        String lateTime = "20:52";
        map.put(serviceDetailsRequest,jsonForTrain(serviceId, scheduledTime, lateTime, platform));

        // time passes
        tmt.tick();

        serviceDisplayed=null;

        tmt.unwatch();
        tmt.tick();

        assertThat(serviceDisplayed, is(nullValue()));

    }

}
