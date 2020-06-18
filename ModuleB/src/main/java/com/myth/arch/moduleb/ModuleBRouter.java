package com.myth.arch.moduleb;

import android.content.Context;
import android.content.Intent;

import com.myth.arch.airrouter.AirService;
import com.myth.arch.imoduleb.IModuleBRouter;

@AirService
public class ModuleBRouter implements IModuleBRouter {
    @Override
    public void startModuleBActivity(Context context) {
        context.startActivity(new Intent(context, ModuleBActivity.class));
    }
}
