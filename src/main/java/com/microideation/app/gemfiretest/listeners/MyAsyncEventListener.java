package com.microideation.app.gemfiretest.listeners;

import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;

import java.util.List;

/**
 * Created by sandheepgr on 1/7/16.
 */
public class MyAsyncEventListener implements AsyncEventListener {


    @Override
    public boolean processEvents(List<AsyncEvent> asyncEvents) {

        System.out.println(asyncEvents);

        return true;

    }

    @Override
    public void close() {

    }
}
