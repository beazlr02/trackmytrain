package uk.co.rossbeazley.trackmytrain.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TrainRepo {

    private NetworkClient networkClient;

    public TrainRepo() {
        networkClient = new NetworkClient();
    }

    public interface ServiceSuccess {
        void ok(Train object);
    }

    public void service(final String serviceId, final ServiceSuccess serviceSuccess) {

        String url = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service/" + URLEncoder.encode(serviceId);

        networkClient.stringFromUrl(url, new NetworkClient.Success() {
            @Override
            public void ok(String data) {
                try {
                    JSONObject jtrain = new JSONObject(data);
                    Train train = new Train(serviceId, jtrain.getString("eta"), jtrain.getString("sta"));
                    serviceSuccess.ok(train);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }



    static public interface DeparturesSuccess {
        void ok(List<Train> object);
    }


    public void departures(final DeparturesSuccess result) {

        String url = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/departures/MCO/to/SLD";
        new NetworkClient().stringFromUrl(url, new NetworkClient.Success() {
            @Override
            public void ok(String data) {
                List<Train> trains = createTrainList(data);
                result.ok(trains);
            }
        });
    }

    private List<Train> createTrainList(String urlresult)
    {
        List<Train> trains = new ArrayList<Train>();
        try
        {
            final JSONObject jobj = new JSONObject(urlresult);
            JSONArray jtrains = jobj.getJSONArray("rows");
            for(int i=0 ; i<jtrains.length() ; i++) {
                JSONObject jtrain = jtrains.getJSONObject(i);
                Train train = new Train(jtrain.getString("sid"), jtrain.getString("eta"), jtrain.getString("std"));
                trains.add(train);
            }

        }catch (JSONException e) {}

        return trains;
    }



    /**
     *
     * departures: train and service
     * ID : serviceID
     * scheduled time (choose arrival as primary but departure when null or empty) : sta/std
     * estimated time : scheduledTime/etd
     * platform : platform
     *
     */
}
