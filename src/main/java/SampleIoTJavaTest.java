import com.appdynamics.iot.*;
import com.appdynamics.iot.events.CustomEvent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SampleIoTJavaTest {

    public static void main(String[] args){

        AgentConfiguration.Builder agentConfigBuilder = AgentConfiguration.builder();
        AgentConfiguration agentConfig = agentConfigBuilder
                .withAppKey(<YOUR-APP-KEY>)
                .withCollectorUrl(<YOUR-COLLECTOR-URL>)
                .build();

        DeviceInfo.Builder deviceInfoBuilder = DeviceInfo.builder("Connected Car P3", UUID.randomUUID().toString());
        DeviceInfo deviceInfo = deviceInfoBuilder.withDeviceName("Peregrine Falcon").build();

        VersionInfo.Builder versionInfoBuilder = VersionInfo.builder();
        VersionInfo versionInfo = versionInfoBuilder
                .withFirmwareVersion("2.3.4")
                .withHardwareVersion("1.6.7")
                .withOsVersion("8.9.9")
                .withSoftwareVersion("3.1.1").build();

        Instrumentation.start(agentConfig, deviceInfo, versionInfo);

        // Add a custom Event
        CustomEvent.Builder builder = CustomEvent.builder("FL Pressure Drop", "Front Left Tire Pressure Drop");
        long eventStartTime = System.currentTimeMillis();
        long duration = 6000;
        builder.withTimestamp(eventStartTime).withDuration(duration);
        builder.addLongProperty("PSI Drop", 37);
        CustomEvent customEvent = builder.build();

        Instrumentation.addEvent(customEvent);
        Instrumentation.sendAllEvents();

        // Add a Network Event
        String url = "http://ip.jsontest.com/?callback=showMyIP";
        try {
            URL thisUrl = new URL(url);
            // [AppDynamics Instrumentation] Get a Tracker
            final HttpRequestTracker tracker = Instrumentation.beginHttpRequest(thisUrl);

            HttpURLConnection con = (HttpURLConnection) thisUrl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setDoInput(true);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            // [AppDynamics Instrumentation] Retrieve the headers from the response
            Map<String, List<String>> headerFields = null;

            System.out.println("Sending 'POST' request to URL :" + url);
            System.out.println("Response Code :" + responseCode);

            BufferedReader in;
            String inputLine;

            if (responseCode >= 200 && responseCode < 300) {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(
                        new InputStreamReader(con.getErrorStream()));
            }
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // [AppDynamics Instrumentation] Initiate adding NetworkRequestEvent
            if (responseCode >= 200 && responseCode < 300) {
                if (headerFields != null && headerFields.size() > 0){
                    tracker.withResponseCode(responseCode)
                            .withResponseHeaderFields(headerFields)
                            .reportDone();
                } else {
                    tracker.withResponseCode(responseCode)
                            .reportDone();
                }
            } else {
                tracker.withResponseCode(responseCode)
                        .withError(response.toString()).reportDone();
            }
            // End: Add for AppDynamics Instrumentation - Initiate adding NetworkRequestEvent
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Instrumentation.sendAllEvents();

        //Report an Error or Exception
        try {
            //force creating an exception
            float f = (5 / 0);
        } catch (Throwable t) {
            Instrumentation.addErrorEvent(t, Instrumentation.Severity.ALERT);
        }
        Instrumentation.sendAllEvents();
    }
}
