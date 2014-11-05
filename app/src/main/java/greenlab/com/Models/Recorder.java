package greenlab.com.Models;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Recorder {

    static {
        System.loadLibrary("mp3lame");
    }

    public enum StatusRecord {RECORD, PAUSE_RECORD, STOP}

    public StatusRecord statusRecord;

    private FileOutputStream output;

    private final String filePath;

    public static final int MSG_REC_STARTED = 0;

    public static final int MSG_REC_STOPPED = 1;

    public static final int MSG_REC_PAUSE = 2;

    public static final int MSG_ERROR_GET_MIN_BUFFERSIZE = 3;

    public static final int MSG_ERROR_CREATE_FILE = 4;

    public static final int MSG_ERROR_REC_START = 5;

    public static final int MSG_ERROR_AUDIO_RECORD = 6;

    public static final int MSG_ERROR_AUDIO_ENCODE = 7;

    public static final int MSG_ERROR_WRITE_FILE = 8;

    public static final int MSG_ERROR_CLOSE_FILE = 9;

    public static final int MSG_UPDATE_TIME = 10;

    private final int sampleRate = 8000;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private Handler handler;

    public Recorder(String _filePath) {
        filePath = _filePath;
        statusRecord = StatusRecord.STOP;
    }

    private void init() {
        try {
            output = new FileOutputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            sendEmptyMessage(Recorder.MSG_ERROR_CREATE_FILE);
        }
    }

    public void record() {
        if (statusRecord == StatusRecord.STOP) {
            statusRecord = StatusRecord.RECORD;
            init();
            new Thread(recordThread).start();
        } else if (statusRecord == StatusRecord.PAUSE_RECORD) {
            statusRecord = StatusRecord.RECORD;
            new Thread(recordThread).start();
        } else if (statusRecord == StatusRecord.RECORD) {
            statusRecord = StatusRecord.PAUSE_RECORD;
        }
    }

    public void stop() {
        statusRecord = StatusRecord.STOP;
        sendEmptyMessage(Recorder.MSG_REC_STOPPED);
    }

    private Runnable recordThread = new Runnable() {
        @Override
        public void run() {
            android.os.Process
                    .setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
            final int minBufferSize = AudioRecord.getMinBufferSize(
                    sampleRate, AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);
            if (minBufferSize < 0) {
                sendEmptyMessage(Recorder.MSG_ERROR_GET_MIN_BUFFERSIZE);
                return;
            }
            AudioRecord audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.MIC, sampleRate,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, minBufferSize * 2);

            short[] buffer = new short[sampleRate * (16 / 8) * 1 * 5];
            byte[] mp3buffer = new byte[(int) (7200 + buffer.length * 2 * 1.25)];

            SimpleLame.init(sampleRate, 1, sampleRate, 32);

            try {
                try {
                    audioRecord.startRecording();
                } catch (IllegalStateException e) {
                    sendEmptyMessage(Recorder.MSG_ERROR_REC_START);
                    return;
                }

                try {
                    sendEmptyMessage(Recorder.MSG_REC_STARTED);

                    int readSize = 0;
                    while (statusRecord == StatusRecord.RECORD) {

                        readSize = audioRecord.read(buffer, 0, minBufferSize);
                        if (readSize < 0) {
                            sendEmptyMessage(Recorder.MSG_ERROR_AUDIO_RECORD);
                            break;
                        } else if (readSize == 0) {
                            ;
                        } else {
                            int encResult = SimpleLame.encode(buffer,
                                    buffer, readSize, mp3buffer);
                            if (encResult < 0) {
                                sendEmptyMessage(Recorder.MSG_ERROR_AUDIO_ENCODE);
                                break;
                            }
                            if (encResult != 0) {
                                try {
                                    output.write(mp3buffer, 0, encResult);
                                } catch (IOException e) {
                                    sendEmptyMessage(Recorder.MSG_ERROR_WRITE_FILE);
                                    break;
                                }
                            }
                        }
                    }

                    if (statusRecord == StatusRecord.PAUSE_RECORD) {
                        sendEmptyMessage(Recorder.MSG_REC_PAUSE);
                        return;
                    }

                    int flushResult = SimpleLame.flush(mp3buffer);
                    if (flushResult < 0) {
                        sendEmptyMessage(Recorder.MSG_ERROR_AUDIO_ENCODE);
                    }

                    if (flushResult != 0) {
                        try {
                            output.write(mp3buffer, 0, flushResult);
                        } catch (IOException e) {
                            sendEmptyMessage(Recorder.MSG_ERROR_WRITE_FILE);
                        }
                    }

                    try {
                        output.close();
                    } catch (IOException e) {
                        sendEmptyMessage(Recorder.MSG_ERROR_CLOSE_FILE);
                    }
                } finally {
                    audioRecord.stop();
                    audioRecord.release();
                }
            } finally {
                SimpleLame.close();
            }


        }
    };

    private void sendEmptyMessage(int code) {
        if (handler != null)
            handler.sendEmptyMessage(code);
    }

}
