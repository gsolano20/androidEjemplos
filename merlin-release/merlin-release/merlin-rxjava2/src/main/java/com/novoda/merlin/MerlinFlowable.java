package com.novoda.merlin;

import android.content.Context;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class MerlinFlowable {

    public static Flowable<NetworkStatus> from(Context context) {
        return from(context, new Merlin.Builder());
    }

    public static Flowable<NetworkStatus> from(Context context, MerlinBuilder merlinBuilder) {
        return from(merlinBuilder.withAllCallbacks()
                            .build(context));
    }

    public static Flowable<NetworkStatus> from(Merlin merlin) {
        return createFlowable(merlin);
    }

    private static Flowable<NetworkStatus> createFlowable(Merlin merlin) {
        return Flowable.create(new MerlinFlowableOnSubscribe(merlin), BackpressureStrategy.LATEST);
    }
}
