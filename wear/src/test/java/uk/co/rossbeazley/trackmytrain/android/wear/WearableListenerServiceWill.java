package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WearableListenerServiceWill {

    @Test public void
    registerHostNodeIDWhenHostBroadcastsSelf() {
        Postman.NodeId expectedId = new Postman.NodeId("any id");

        HostNode hostNode  = new HostNode();
        WearApp wearApp = new WearApp(hostNode);

        Postman.BroadcastMessage iAmBaseMessage= new IAmBaseMessage(expectedId);
        wearApp.message(iAmBaseMessage);

        final MyResult result = new MyResult();
        hostNode.id(result);

        assertThat(result.actualId, is(expectedId));
    }

    private static class MyResult implements HostNode.Result {
        private Postman.NodeId actualId;

        @Override
        public void id(Postman.NodeId id) {
            actualId = id;
        }
    }


    public class TrackMyTrainMessageService extends WearableListenerService {

        // just forwards calls to the WearApp instance

        @Override
        public void onMessageReceived(MessageEvent messageEvent) {

            //Postman.Message message = new PostmanMessageFactory().toMessage(messageEvent);
            //new WearApp(null).message(message);

        }
    }

    private class WearApp {
        private final HostNode hostNode;

        public WearApp(HostNode hostNode) {
            this.hostNode = hostNode;
        }


        public void message(Postman.Message message) {

        }

        public void message(Postman.BroadcastMessage message) {
            IAmBaseMessage iAmBaseMessage = (IAmBaseMessage) message;
            hostNode.register(iAmBaseMessage.hostNodeId());
        }
    }


}
