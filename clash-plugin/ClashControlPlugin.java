package com.zepplife.steps;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "ClashControl")
public class ClashControlPlugin extends Plugin {

    @PluginMethod
    public void startClash(PluginCall call) {
        try {
            Intent intent = new Intent();
            intent.setClassName(
                "com.github.metacubex.clash.meta",
                "com.github.kr328.clash.ExternalControlActivity"
            );
            intent.setAction("com.github.metacubex.clash.meta.action.START_CLASH");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
            call.resolve();
        } catch (Exception e) {
            call.reject("START_FAILED", e.getMessage());
        }
    }

    @PluginMethod
    public void stopClash(PluginCall call) {
        try {
            // Step 1: Open CMFA main activity to make app visible (registers broadcasts)
            Intent warmup = getContext().getPackageManager()
                .getLaunchIntentForPackage("com.github.metacubex.clash.meta");
            if (warmup != null) {
                warmup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(warmup);
            }

            // Step 2: Wait for broadcasts to register, then send STOP_CLASH
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                try {
                    Intent stopIntent = new Intent();
                    stopIntent.setClassName(
                        "com.github.metacubex.clash.meta",
                        "com.github.kr328.clash.ExternalControlActivity"
                    );
                    stopIntent.setAction("com.github.metacubex.clash.meta.action.STOP_CLASH");
                    stopIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(stopIntent);
                } catch (Exception e2) {
                    call.reject("STOP_FAILED", e2.getMessage());
                    return;
                }
                call.resolve();
            }, 400);
        } catch (Exception e) {
            call.reject("STOP_FAILED", e.getMessage());
        }
    }
}
