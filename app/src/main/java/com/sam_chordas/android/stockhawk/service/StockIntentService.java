package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.TaskParams;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {

    public StockIntentService(){
        super(StockIntentService.class.getName());
    }

    public StockIntentService(String name) {
        super(name);
    }

    @Override protected void onHandleIntent(Intent intent) {
        Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
        StockTaskService stockTaskService = new StockTaskService(this);
        Bundle args = new Bundle();
        if (intent.getStringExtra("tag").equals("add") || intent.getStringExtra("tag").equals("historic")){
            args.putString("symbol", intent.getStringExtra("symbol"));
        }
        // We can call OnRunTask from the intent service to force it to run immediately instead of
        // scheduling a task.
        int taskStatus = stockTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
        if(taskStatus == StockTaskService.RESULT_INVALID_INPUT){
            Log.i("StockServiceIntent","Invalid input");
            Intent i = new Intent(StockTaskService.INVALID_INPUT);
            sendBroadcast(i);
        } else if(taskStatus == StockTaskService.RESULT_SERVER_UNAVAILABLE){
            Log.i("StockServiceIntent","Server unavailable");
            Intent i = new Intent(StockTaskService.SERVER_UNAVAILABLE);
            sendBroadcast(i);
        }
    }

}
