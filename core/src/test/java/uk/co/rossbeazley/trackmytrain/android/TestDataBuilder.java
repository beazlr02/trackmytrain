package uk.co.rossbeazley.trackmytrain.android;

import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

public class TestDataBuilder {
    static public String jsonForTrain(Train trainParam) {
        return "{\n" +
                "\"id\": \"" + trainParam.id + "\",\n" +
                "\"scheduledTime\": \"" + trainParam.scheduledTime + "\",\n" +
                "\"estimatedTime\": \"" + trainParam.estimatedTime + "\",\n" +
                "\"platform\": \"" + trainParam.platform + "\"\n" +
                "}";
    }

    public static String jsonForTrains(Train... trains) {
        String SEP = ",\n";

        String header = "{\n" +
                "\"error\": \"\",\n" +
                "\"trains\": [\n";
        String tail = "]\n" +
                "}";

        String body = "";

        for(Train train : trains) {
            body += TestDataBuilder.jsonForTrain(train) + SEP;
        }

        if(trains.length>1) {
            body = body.substring(0,body.length()-2);
        }

        return header +
                body +
                tail;
    }

    public static TMTBuilder TMTBuilder() {
        return new TMTBuilder()
                .with(new NetworkClient() {
                    @Override
                    public void requestString(Request request, Response response) {

                    }
                })
                .with(new NarrowScheduledExecutorService() {
                    @Override
                    public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
                        return null;
                    }
                });
    }

    public static Station anyStation() {
        return Station.fromString("MAN");
    }

    public static Direction anyDirection() {
        return Direction.to(anyStation());
    }

    public static String anyTrains() {
        final Train train1, train2;
        train1 = new Train("aN5S6pak5nKFawy0sXb65Q==", "On time", "21:39", "2");
        train2 = new Train("EAG/q7qfInIUZyPhCdwQKw==", "On time", "22:38", "2");
        return jsonForTrains(train1, train2);
    }
}
