# Android-OttoSample

This example shows how a Bus enhance communications between Activities, Services, UI Objects and other Process objects.

Now our Activity has less responsibility as it no longer needs to be concerned with a list of listeners and is immediately decoupled from the interface.

## Approaches

One of the best approaches is to interact with our ![DummyService](app/src/main/java/com/example/kuassivi/android_ottosample/service/DummyService.java) without the need to bound it into our ![MainActivity](app/src/main/java/com/example/kuassivi/android_ottosample/MainActivity.java), see the ![#interactWithService()](app/src/main/java/com/example/kuassivi/android_ottosample/MainActivity.java#LC108) Producer method.

Another one very interesting is to deal with our ![DummyReceiver](app/src/main/java/com/example/kuassivi/android_ottosample/receiver/DummyReceiver.java) once it has received a broadcast communication from any place.

## About @Producers

The *@Produce* annotation means that as soon as an interested subscriber registers on a bus (anywhere!) it will receive an event or the latest one, depending on what you need.

So we don't need to bind the Service to know when it is connected.

See ![#interactWithService()](app/src/main/java/com/example/kuassivi/android_ottosample/MainActivity.java#LC108) method.

## What next?

Try yourself!
