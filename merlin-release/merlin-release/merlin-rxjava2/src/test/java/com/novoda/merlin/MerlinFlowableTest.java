package com.novoda.merlin;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MerlinFlowableTest {

    private final Merlin merlin = mock(Merlin.class);

    private TestSubscriber<NetworkStatus> testSubscriber;

    @Before
    public void setUp() {
        testSubscriber = MerlinFlowable.from(merlin)
                .test();
    }

    @Test
    public void unbindWhenDisposed() {
        Disposable disposable = MerlinFlowable.from(merlin)
                .subscribe();
        disposable.dispose();

        verify(merlin).unbind();
    }

    @Test
    public void receiveOneAvailableNetworkStatusOnConnect() {
        ArgumentCaptor<Connectable> argumentCaptor = ArgumentCaptor.forClass(Connectable.class);

        verify(merlin).registerConnectable(argumentCaptor.capture());
        argumentCaptor.getValue()
                .onConnect();

        testSubscriber.assertValue(NetworkStatus.newAvailableInstance());
    }

    @Test
    public void receiveOneUnavailableNetworkStatusOnDisconnect() {
        ArgumentCaptor<Disconnectable> argumentCaptor = ArgumentCaptor.forClass(Disconnectable.class);

        verify(merlin).registerDisconnectable(argumentCaptor.capture());
        argumentCaptor.getValue()
                .onDisconnect();

        testSubscriber.assertValue(NetworkStatus.newUnavailableInstance());
    }

    @Test
    public void receiveOneAvailableNetworkStatusOnBindWhenConnected() {
        ArgumentCaptor<Bindable> argumentCaptor = ArgumentCaptor.forClass(Bindable.class);

        verify(merlin).registerBindable(argumentCaptor.capture());
        argumentCaptor.getValue()
                .onBind(NetworkStatus.newAvailableInstance());

        testSubscriber.assertValue(NetworkStatus.newAvailableInstance());
    }

    @Test
    public void receiveOneUnavailableNetworkStatusOnBindWhenDisconnected() {
        ArgumentCaptor<Bindable> argumentCaptor = ArgumentCaptor.forClass(Bindable.class);

        verify(merlin).registerBindable(argumentCaptor.capture());
        argumentCaptor.getValue()
                .onBind(NetworkStatus.newUnavailableInstance());

        testSubscriber.assertValue(NetworkStatus.newUnavailableInstance());
    }

    @Test
    public void notCrashWhenCreatingWithAMerlinBuilderWithoutCallbacks() {
        Context context = mock(Context.class);
        Merlin.Builder merlinBuilder = new Merlin.Builder();
        MerlinFlowable.from(context, merlinBuilder).subscribe();
    }
}
