## Sample application using the lightweight AppDynamics IoT Java SDK
Goal: Instrument a sample IoT application using the AppDynamics IoT Java SDK to report Device Information, Custom Business Events, Network Request Events(HTTPS), and Error Events to the AppDynamics Controller UI.

## Step 1
If you don't have an AppDynamics Account, create a free account [here](https://www.appdynamics.com/free-trial/)

## Step 2 
Create a **Connected Devices App**

 * Click **User Experience** from the top navigation bar.
 * Select the **Connected Devices** tab.
 * Click **Get Started**.
 * From the **Create Application** dialog, select **Create an Application** using the Getting Started Wizard.
 * Click **OK**.
 * For step one of the Getting Started Wizard - Connected Devices page, enter a name for your IoT app.
 * Click Continue.
 * From step two, select **Java SDK** to report IoT data
 * Copy the values for the **App Key** and the **Collector URL**. You'll be using **both** to report IoT data.

  [See](https://docs.appdynamics.com/display/PRO44/Set+Up+and+Access+IoT+Monitoring#SetUpandAccessIoTMonitoring-iot-app-key) for more details

## Step 3
Download the AppDynamics IoT SDK jar **appd-iot-sdk-4.4.x.jar** file from [download.appdynamics.com](https://download.appdynamics.com/download/)

## Step 4
* Clone this repository

* Import the project as a Gradle Project in your favorite IDE

* Edit the source file SampleIoTJavaTest.java with your APP-KEY and Collector URL.
* Add the jar file downloaded in Step 2 (**appd-iot-sdk-4.4.x.jar**) as a third party library.

Compile and run the sample app.

## Step 5
Instrumentation Data should show up on the AppDynamics Dashboard under the app created in **Step 2** under 
**User Experience->Connected Devices Panel**

## Step 6
For more details check out the [official documentation](https://docs.appdynamics.com/display/PRO44/IoT+Monitoring)
Looking to instrument a C++ IoT app? Checkout the sample app [here](https://github.com/Appdynamics/iot-cpp-sample-app)

