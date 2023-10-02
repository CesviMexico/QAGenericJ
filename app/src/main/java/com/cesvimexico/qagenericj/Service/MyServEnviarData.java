package com.cesvimexico.qagenericj.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyServEnviarData extends Service {
    public MyServEnviarData() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}