package com.lucasurbas.samplelist;

import android.app.Application;

import com.hannesdorfmann.mosby.dagger1.Injector;

import dagger.ObjectGraph;

/**
 * Created by M30 on 2015-05-31.
 */
public class App extends Application implements Injector {

    ObjectGraph objectGraph;

    @Override public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new InjectionModule(this));
    }

    @Override public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
