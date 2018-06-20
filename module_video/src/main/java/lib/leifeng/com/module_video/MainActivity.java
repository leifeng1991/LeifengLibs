package lib.leifeng.com.module_video;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnAudioFrameListener;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnPreparedListener;
import com.pili.pldroid.player.PLOnSeekCompleteListener;
import com.pili.pldroid.player.PLOnVideoFrameListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoView;

import java.util.ArrayList;
import java.util.Arrays;

import lib.leifeng.com.module_video.widget.MediaController;
import lib.leifeng.com.module_video.widget.MyMediaController;

/**
 * 七牛播放器使用
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public final static int MEDIA_CODEC_SW_DECODE = 0;
    public final static int MEDIA_CODEC_HW_DECODE = 1;
    public final static int MEDIA_CODEC_AUTO = 2;
    private PLVideoView mVideoView;
    private ProgressBar mProgressBar;
    private boolean mIsLiveStreaming = true;
    private MediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);*/
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.id_pl_video_view);
        mProgressBar = findViewById(R.id.id_pb);
        Button mStartButton = findViewById(R.id.id_start);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.start();
            }
        });
        Button mPauseButton = findViewById(R.id.id_pause);
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.pause();
                mVideoView.start();
                mVideoView.pause();
            }
        });
        Button mStopPlatBackButton = findViewById(R.id.id_stop_play_back);
        mStopPlatBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.stopPlayback();
            }
        });
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        // 设置加载动画
        mVideoView.setBufferingIndicator(mProgressBar);
        // 播放参数配置
        AVOptions options = new AVOptions();
        // DNS 服务器设置
        // 若不设置此项，则默认使用 DNSPod 的 httpdns 服务
        // 若设置为 127.0.0.1，则会使用系统的 DNS 服务器
        // 若设置为其他 DNS 服务器地址，则会使用设置的服务器
//        options.setString(AVOptions.KEY_DNS_SERVER, "222.85.96.105");
        // DNS 缓存设置
        // 若不设置此项，则每次播放未缓存的域名时都会进行 DNS 解析，并将结果缓存
        // 参数为 String[]，包含了要缓存 DNS 结果的域名列表
        // SDK 在初始化时会解析列表中的域名，并将结果缓存
//        options.setStringArray(AVOptions.KEY_DOMAIN_LIST, domainList);
        // 解码方式:
        // codec＝AVOptions.MEDIA_CODEC_HW_DECODE，硬解
        // codec=AVOptions.MEDIA_CODEC_SW_DECODE, 软解
        // codec=AVOptions.MEDIA_CODEC_AUTO, 硬解优先，失败后自动切换到软解
        // 默认值是：MEDIA_CODEC_SW_DECODE
        options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_AUTO);
        // 若设置为 1，则底层会进行一些针对直播流的优化
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        // 快开模式，启用后会加快该播放器实例再次打开相同协议的视频流的速度
        options.setInteger(AVOptions.KEY_FAST_OPEN, 1);
        // 打开重试次数，设置后若打开流地址失败，则会进行重试
        options.setInteger(AVOptions.KEY_OPEN_RETRY_TIMES, 5);
        // 预设置 SDK 的 log 等级， 0-4 分别为 v/d/i/w/e
        options.setInteger(AVOptions.KEY_LOG_LEVEL, 2);
        // 打开视频时单次 http 请求的超时时间，一次打开过程最多尝试五次
        // 单位为 ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        // 默认的缓存大小，单位是 ms
        // 默认值是：500
        options.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION, 500);
        // 最大的缓存大小，单位是 ms
        // 默认值是：2000，若设置值小于 KEY_CACHE_BUFFER_DURATION 则不会生效
        options.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 4000);
        // 是否开启直播优化，1 为开启，0 为关闭。若开启，视频暂停后再次开始播放时会触发追帧机制
        // 默认为 0
        if (mIsLiveStreaming) {
            options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        }
        // 设置拖动模式，1 位精准模式，即会拖动到时间戳的那一秒；0 为普通模式，会拖动到时间戳最近的关键帧。默认为 0
        options.setInteger(AVOptions.KEY_SEEK_MODE, 0);
        // 设置 DRM 密钥
//        byte[] key = {xxx, xxx, xxx, xxx, xxx ……};
//        options.setByteArray(AVOptions.KEY_DRM_KEY, key);
        // 设置偏好的视频格式，设置后会加快对应格式视频流的打开速度，但播放其他格式会出错
        // m3u8 = 1, mp4 = 2, flv = 3
//        options.setInteger(AVOptions.KEY_PREFER_FORMAT, 2);
        // 开启解码后的视频数据回调
        // 默认值为 0，设置为 1 则开启
        options.setInteger(AVOptions.KEY_VIDEO_DATA_CALLBACK, 1);
        // 开启解码后的音频数据回调
        // 默认值为 0，设置为 1 则开启
        options.setInteger(AVOptions.KEY_VIDEO_DATA_CALLBACK, 1);
        // 请在开始播放之前配置
        mVideoView.setAVOptions(options);
        // 设置相应的监听器
        // 用于监听播放器的 prepare 过程，该过程主要包括：创建资源、建立连接、请求码流等等，当 prepare 完成后，SDK 会回调该对象的 onPrepared 接口，下一步则可以调用播放器的 start() 启动播放。
       /* mVideoView.setOnPreparedListener(new PLOnPreparedListener() {
            @Override
            public void onPrepared(int i) {
                mVideoView.start();
            }
        });*/
        // 用于监听播放器的状态消息，在播放器启动后，SDK 会在播放器发生状态变化时调用该对象的 onInfo 方法，同步状态信息。
        mVideoView.setOnInfoListener(mOnInfoListener);
        // 于监听当前播放的视频流的尺寸信息，在 SDK 解析出视频的尺寸信息后，会触发该回调，开发者可以在该回调中调整 UI 的视图尺寸
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        // 用于监听当前播放器已经缓冲的数据量占整个视频时长的百分比，在播放直播流中无效，仅在播放文件和回放时才有效
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        // 用于监听播放结束的消息
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnVideoFrameListener(mOnVideoFrameListener);
        mVideoView.setOnAudioFrameListener(mOnAudioFrameListener);
        // 用于监听 seek 完成的消息，当调用的播放器的 seekTo 方法后，SDK 会在 seek 成功后触发该回调
       /* mVideoView.setOnSeekCompleteListener(new PLOnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {

            }
        });*/

        mVideoView.setVideoPath("rtmp://live.hkstv.hk.lxdns.com/live/hks");
//        mVideoView.setVideoPath("http://playertest.longtailvideo.com/adaptive/bipbop/gear4/prog_index.m3u8");
//        mVideoView.setVideoPath("http://video.zhaolin365.com/1510245568082.mp4");
        mVideoView.setLooping(false);

        mMediaController = new MediaController(this, !mIsLiveStreaming, mIsLiveStreaming);
        mMediaController.setOnClickSpeedAdjustListener(mOnClickSpeedAdjustListener);
        MyMediaController myMediaController = new MyMediaController();
        mVideoView.setMediaController(mMediaController);
        ArrayList<String> list = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    private PLOnInfoListener mOnInfoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:// 加载成功，正在播放
                    break;
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START:
//                    Utils.showToastTips(PLVideoViewActivity.this, "first video render time: " + extra + "ms");
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FRAME_RENDERING:
                    Log.i(TAG, "video frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                    Log.i(TAG, "audio frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_GOP_TIME:
                    Log.i(TAG, "Gop Time: " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_SWITCHING_SW_DECODE:
                    Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                    break;
                case PLOnInfoListener.MEDIA_INFO_METADATA:
                    Log.i(TAG, mVideoView.getMetadata().toString());
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_BITRATE:
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FPS:
                    updateStatInfo();
                    break;
                case PLOnInfoListener.MEDIA_INFO_CONNECTED:
                    Log.i(TAG, "Connected !");
                    break;
                case PLOnInfoListener.MEDIA_INFO_UNKNOWN:
                    Log.i(TAG, "未知消息");
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    Log.i(TAG, "Rotation changed: " + extra);
                default:
                    break;
            }
        }
    };

    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLOnErrorListener.ERROR_CODE_IO_ERROR:// -3 网络异常
                    /**
                     * SDK will do reconnecting automatically
                     */
                    Log.e(TAG, "IO Error!");
                    return false;
                case PLOnErrorListener.MEDIA_ERROR_UNKNOWN:// -1 未知错误
                    break;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:// -2 播放器打开失败
//                    Utils.showToastTips(PLVideoViewActivity.this, "failed to open player !");
                    Log.e(TAG, "failed to open player!");
                    break;
                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:// -4 拖动失败
//                    Utils.showToastTips(PLVideoViewActivity.this, "failed to seek !");
                    Log.e(TAG, "failed to seek!");
                    break;
                case PLOnErrorListener.ERROR_CODE_HW_DECODE_FAILURE:// -2003 硬解失败
                    break;
                case PLOnErrorListener.ERROR_CODE_PLAYER_DESTROYED:// -2008 播放器已被销毁，需要再次 setVideoURL 或 prepareAsync
                    break;
                case PLOnErrorListener.ERROR_CODE_PLAYER_VERSION_NOT_MATCH:// -9527 so 库版本不匹配，需要升级
                    break;
                case PLOnErrorListener.ERROR_CODE_PLAYER_CREATE_AUDIO_FAILED:// -4410	AudioTrack 初始化失败，可能无法播放音频
                    break;
                default:
//                    Utils.showToastTips(PLVideoViewActivity.this, "unknown error !");
                    Log.e(TAG, "unknown error!");
                    break;
            }
            finish();
            return true;
        }
    };

    private PLOnCompletionListener mOnCompletionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {
            Log.i(TAG, "Play Completed !");
//            Utils.showToastTips(PLVideoViewActivity.this, "Play Completed !");
            if (!mIsLiveStreaming) {
                mMediaController.refreshProgress();
            }
            //finish();
        }
    };

    private PLOnBufferingUpdateListener mOnBufferingUpdateListener = new PLOnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(int precent) {
            Log.i(TAG, "onBufferingUpdate: " + precent);
        }
    };

    private PLOnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLOnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(int width, int height) {
            Log.i(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height);
        }
    };

    private PLOnVideoFrameListener mOnVideoFrameListener = new PLOnVideoFrameListener() {
        @Override
        public void onVideoFrameAvailable(byte[] data, int size, int width, int height, int format, long ts) {
            Log.i(TAG, "onVideoFrameAvailable: " + size + ", " + width + " x " + height + ", " + format + ", " + ts);
            if (format == PLOnVideoFrameListener.VIDEO_FORMAT_SEI && bytesToHex(Arrays.copyOfRange(data, 19, 23)).equals("ts64")) {
                // If the RTMP stream is from Qiniu
                // Add &addtssei=true to the end of URL to enable SEI timestamp.
                // Format of the byte array:
                // 0:       SEI TYPE                    This is part of h.264 standard.
                // 1:       unregistered user data      This is part of h.264 standard.
                // 2:       payload length              This is part of h.264 standard.
                // 3-18:    uuid                        This is part of h.264 standard.
                // 19-22:   ts64                        Magic string to mark this stream is from Qiniu
                // 23-30:   timestamp                   The timestamp
                // 31:      0x80                        Magic hex in ffmpeg
                Log.i(TAG, " timestamp: " + Long.valueOf(bytesToHex(Arrays.copyOfRange(data, 23, 31)), 16));
            }
        }
    };

    private PLOnAudioFrameListener mOnAudioFrameListener = new PLOnAudioFrameListener() {
        @Override
        public void onAudioFrameAvailable(byte[] data, int size, int samplerate, int channels, int datawidth, long ts) {
            Log.i(TAG, "onAudioFrameAvailable: " + size + ", " + samplerate + ", " + channels + ", " + datawidth + ", " + ts);
        }
    };

    private MediaController.OnClickSpeedAdjustListener mOnClickSpeedAdjustListener = new MediaController.OnClickSpeedAdjustListener() {
        @Override
        public void onClickNormal() {
            // 0x0001/0x0001 = 2
            mVideoView.setPlaySpeed(0X00010001);
        }

        @Override
        public void onClickFaster() {
            // 0x0002/0x0001 = 2
            mVideoView.setPlaySpeed(0X00020001);
        }

        @Override
        public void onClickSlower() {
            // 0x0001/0x0002 = 0.5
            mVideoView.setPlaySpeed(0X00010002);
        }
    };

    private String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private void updateStatInfo() {
        long bitrate = mVideoView.getVideoBitrate() / 1024;
        final String stat = bitrate + "kbps, " + mVideoView.getVideoFps() + "fps";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mStatInfoTextView.setText(stat);
            }
        });
    }
}
