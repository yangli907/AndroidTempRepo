package com.android.takePic;

import android.content.Intent;
import android.speech.RecognitionService;
import android.speech.SpeechRecognizer;
import android.util.Log;

public class SimpleVoiceService extends RecognitionService {
	private SpeechRecognizer m_EngineSR;
	@Override
    public void onCreate() {
        super.onCreate();
        Log.i("SimpleVoiceService", "Service started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SimpleVoiceService", "Service stopped");
    }

	@Override
	protected void onCancel(Callback listener) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onStartListening(Intent recognizerIntent, Callback listener) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onStopListening(Callback listener) {
		// TODO Auto-generated method stub

	}

}
