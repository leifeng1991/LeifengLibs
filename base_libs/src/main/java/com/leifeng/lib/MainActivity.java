package com.leifeng.lib;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.leifeng.lib.base.PermissionActivity;
import com.leifeng.lib.glide.GlideImageLoader;
import com.leifeng.lib.net.RetrofitFactory;
import com.leifeng.lib.net.RetrofitUtils;
import com.leifeng.lib.net.observer.BaseObserver;
import com.leifeng.lib.recyclerview.BaseAdapter;
import com.leifeng.lib.recyclerview.BaseViewHolder;
import com.leifeng.lib.recyclerview.Divider;
import com.leifeng.lib.recyclerview.GridDividerItemDecoration;
import com.leifeng.lib.recyclerview.HeaderAndFooterWrapper;
import com.leifeng.lib.recyclerview.ItemViewDelegate;
import com.leifeng.lib.recyclerview.MultiItemTypeAdapter;
import com.leifeng.lib.utils.LogUtil;
import com.leifeng.lib.utils.PermissionHelper;
import com.leifeng.lib.weight.RefreshLoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.Locale;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY;

public class MainActivity extends PermissionActivity {
    private View mHeaderView;
    private View mHeaderView1;
    private Banner mBanner;
    private RefreshLoadView mRefreshLoadView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private MyAdapter adapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private GLSurfaceView mGlSurfaceView;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mHeaderView = getLayoutInflater().inflate(R.layout.view_header, null);
        mHeaderView1 = getLayoutInflater().inflate(R.layout.view_header, null);
        mBanner = mHeaderView.findViewById(R.id.banner);
        mRefreshLoadView = findViewById(R.id.id_refresh_recycler_view);
        mRecyclerView = mRefreshLoadView.getRecyclerView();
        mSmartRefreshLayout = mRefreshLoadView.getSmartRefreshLayout();
        mGlSurfaceView = findViewById(R.id.id_gl_surface_view);
    }

    @Override
    protected void initData() {
        super.initData();
        /***************************banner相关************************/
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(Arrays.asList(getResources().getStringArray(R.array.url)));
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(Arrays.asList(getResources().getStringArray(R.array.titles)));
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        /***************************RefreshLoadView*******************************/
//        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
//        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        // 防止在滑动过程中子View自动换位置
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
      /*  mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                staggeredGridLayoutManager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
            }
        });*/
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.HORIZONTAL, false));
        GridDividerItemDecoration gridDividerItemDecoration = new GridDividerItemDecoration(mContext);
        gridDividerItemDecoration.setHeaderCount(1);
        gridDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_divider));
//        mRecyclerView.addItemDecoration(gridDividerItemDecoration);
        mRecyclerView.addItemDecoration(Divider.builder().color(Color.YELLOW).width(20).height(20).headerCount(1).build());
        mRecyclerView.setItemAnimator(new SlideInRightAnimator());
        adapter = new MyAdapter(getApplicationContext(), R.layout.adapter_list_item);
//        mRecyclerView.setAdapter(adapter);
        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        /************************权限申请********************************/
        // 权限申请
        mHelper.requestPermissions("请授予[位置]权限", new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
                        //开始定位
                    }

                    @Override
                    public void doAfterDenied(String... permission) {
                    }
                }, Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION);
        /******************************线程池*****************************/
       /* ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    LogUtil.e("===============" + Thread.currentThread().getName() + "==" + finalI);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }*/

       /* ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    LogUtil.e("===============" + Thread.currentThread().getName() + "==" + finalI);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }*/
        // 设置OpenGL版本(一定要设置)
        mGlSurfaceView.setEGLContextClientVersion(2);
        // 设置渲染器
        mGlSurfaceView.setRenderer(new MyRenderer());
        // 设置渲染模式
        mGlSurfaceView.setRenderMode(RENDERMODE_WHEN_DIRTY);
        /***********************排序相关*****************************/
        int[] values = new int[]{26, 53, 48, 11, 13, 48, 32, 15};
        sort(values);
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<OnLineOrderListBean.DataBean>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, OnLineOrderListBean.DataBean dataBean, int position) {
                /********************************WebView**************************************/
//                startActivity(BaseWebActivity.newIntent(mContext,"详情","https://www.baidu.com/"));
                /*********************************Router***************************************/
                // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
                ARouter.getInstance().build("/news/list").navigation();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, OnLineOrderListBean.DataBean dataBean, int position) {
                return false;
            }
        });
    }

    @Override
    protected void loadData() {
        /********************************adapter添加头部和尾部**************************************/
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
//        mHeaderAndFooterWrapper.addHeaderView(mHeaderView);
        mHeaderAndFooterWrapper.addHeaderView(mHeaderView1);
        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);

        /*************************多种样式adapter**************************************/
        adapter.addItemViewDelegate(new ItemViewDelegate<OnLineOrderListBean.DataBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.adapter_list_item1;
            }

            @Override
            public boolean isForViewType(OnLineOrderListBean.DataBean item, int position) {
                return position % 3 == 1;
            }

            @Override
            public void convert(BaseViewHolder holder, OnLineOrderListBean.DataBean s, int position) {
                TextView textView = holder.itemView.findViewById(R.id.id_text);
                textView.setText(String.format(Locale.CHINA, "%d", position));
            }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<OnLineOrderListBean.DataBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.adapter_list_item2;
            }

            @Override
            public boolean isForViewType(OnLineOrderListBean.DataBean item, int position) {
                return position % 3 == 2;
            }

            @Override
            public void convert(BaseViewHolder holder, OnLineOrderListBean.DataBean s, int position) {
                TextView textView = holder.itemView.findViewById(R.id.id_text);
                textView.setText(position + "");
            }
        });
        // 刷新和加载
       /* mRefreshLoadView.setLoadingListener(new RefreshLoadView.OnLoadingListener() {
            @Override
            public void onRefresh() {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    list.add("");
                }

                mRefreshLoadView.handleSuccess(adapter, list);

            }

            @Override
            public void onLoadMore() {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add("");
                }
                mRefreshLoadView.handleSuccess(adapter, list);
            }
        });*/
        /**********************************网络请求**************************************/
       /* RetrofitUtils.httRequest(RetrofitFactory.getInstance().getAPI().getUser("2222332"), new BaseObserver<UserBean>(mActivity) {
            @Override
            public void onSuccess(UserBean bean) {
                List<UserBean.DataBean> data = bean.getNetData();
                for (int i = 0; i < data.size(); i++) {
                    LogUtil.e("=============" + data.get(i).getName());
                }
            }

            @Override
            public void onFailed(BaseBean bean) {

            }
        });*/
        /******************************刷新和加载***************************************/
        mRefreshLoadView.setLoadingListener(new RefreshLoadView.OnLoadingListener() {
            @Override
            public void onRefresh() {
                getNetData();
            }

            @Override
            public void onLoadMore() {
                getNetData();
            }
        });

//        getNetData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGlSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGlSurfaceView.onPause();
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }

    /**
     * 网络请求
     */
    private void getNetData() {

        RetrofitUtils.httRequest(RetrofitFactory.getInstance().getAPI().getOrderShouyin(
                mRefreshLoadView.getPage() + "", "2222332", "1522722440", "1514946450"), new BaseObserver<OnLineOrderListBean>(mContext) {
            @Override
            public void onSuccess(OnLineOrderListBean onLineOrderListBean) {
                mRefreshLoadView.handleSuccess(adapter, onLineOrderListBean.getData());
                // 有头部或者尾部时必须加上这一行
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }

            @Override
            public void onFailed(OnLineOrderListBean bean) {
                mRefreshLoadView.handleFail();
                // 有头部或者尾部时必须加上这一行
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }
        });


//        VersionUpdateUtils.requestStoragePermission(mActivity, mHelper, "http:\\/\\/imtt.dd.qq.com\\/16891\\/2C61DFB307875A308A12652B9FF9EC78.apk?fsname=com.xxzlkj.zhaolin_1.2.1_9.apk&amp;csr=1bbd", 0);

       /* RetrofitUtils.downLoad(mContext, RetrofitFactory.getInstance().getAPI().downLoadFile("http:\\/\\/imtt.dd.qq.com\\/16891\\/2C61DFB307875A308A12652B9FF9EC78.apk?fsname=com.xxzlkj.zhaolin_1.2.1_9.apk&amp;csr=1bbd")
                , new FileDownLoadObserver<File>() {

                    @Override
                    public void onDownLoadSuccess(File file) {
                        LogUtil.e("===========下载完成");
                    }

                    @Override
                    public void onDownLoadFail(Throwable throwable) {
                        LogUtil.e("===========下载出错");
                    }

                    @Override
                    public void onProgress(int progress, long total) {
                        LogUtil.e("===========大小" + total + "==百分比" + progress);
                    }
                });*/
    }

    private void sort(int[] values) {
        // 直接插入排序
        LogUtil.e("===============1" + System.currentTimeMillis());
        for (int i = 1; i < values.length; i++) {
            if (values[i] < values[i - 1]) {
                int temp = values[i];
                values[i] = values[i - 1];
//                values[i - 1] = temp;
                int j = i - 2;
                for (; j >= 0 && temp < values[j]; j--) {
                    // 记录后移
                    values[j + 1] = values[j];
                }
                values[j + 1] = temp;
            }
//            LogUtil.e("===========sort" + Arrays.toString(values));

        }
        LogUtil.e("===============1" + System.currentTimeMillis());
        // 折半排序
        for (int i = 1; i < values.length; i++) {
            int temp = values[i]; //保存待插入元素
            int hi = i - 1;
            int lo = 0;  //设置初始区间
            while (lo <= hi) {  //折半确定插入位置
                int mid = (lo + hi) / 2;
                if (temp < values[mid])
                    hi = mid - 1;
                else lo = mid + 1;
            }
            for (int j = i - 1; j > hi; j--)
                values[j + 1] = values[j]; //移动元素
            values[hi + 1] = temp;

//            LogUtil.e("===========sort" + Arrays.toString(values));
        }
        LogUtil.e("===============1" + System.currentTimeMillis());

    }

    /*********************************adapter***********************************/
    class MyAdapter extends BaseAdapter<OnLineOrderListBean.DataBean> {

        public MyAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder holder, OnLineOrderListBean.DataBean dataBean, int position) {
            holder.getConvertView().setBackgroundColor(ContextCompat.getColor(mContext, position % 2 == 0 ? R.color.colorAccent : R.color.colorPrimaryDark));
            TextView textView = holder.itemView.findViewById(R.id.id_text);
            textView.setText(position + "");
        }
    }

    public class MyRenderer implements GLSurfaceView.Renderer {
        // 顶点着色器的脚本
       /* private static final String VERTEX_SHADER =
                "attribute vec4 vPosition;\n"
                        + "void main() {\n"
                        + " gl_Position = vPosition;\n"
                        + "}";*/
        // 片元着色器的脚本
        private static final String FRAGMENT_SHADER
                = "precision mediump float;         \n" // 声明float类型的精度为中等(精度越高越耗资源)
                + "uniform vec4 uColor;             \n" // uniform的属性uColor
                + "void main(){                     \n"
                + "   gl_FragColor = uColor;        \n" // 给此片元的填充色
                + "}";
        private static final String VERTEX_SHADER =
                "attribute vec4 vPosition;\n"
                        + "uniform mat4 uMVPMatrix;\n"
                        + "void main() {\n"
                        + " gl_Position = uMVPMatrix * vPosition;\n"
                        + "}";
        private int program;
        private int vPosition;
        private int uColor;
        private int mMatrixHandle;
        private final float[] mMVPMatrix = new float[16];
        private final short[] VERTEX_INDEX = {0, 1, 2, 0, 2, 3};
        private final ShortBuffer mVertexIndexBuffer;

        public MyRenderer() {
            mVertexIndexBuffer = ByteBuffer.allocateDirect(VERTEX_INDEX.length * 2)
                    .order(ByteOrder.nativeOrder())
                    .asShortBuffer()
                    .put(VERTEX_INDEX);
            mVertexIndexBuffer.position(0);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // 设置clear color颜色RGBA(这里仅仅是设置清屏时GLES20.glClear()用的颜色值而不是执行清屏)
            GLES20.glClearColor(1.0f, 0, 0, 0);
            // 初始化着色器
            // 基于顶点着色器与片元着色器创建程序
            program = createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
            // 获取着色器中的属性引用id(传入的字符串就是我们着色器脚本中的属性名)
            vPosition = GLES20.glGetAttribLocation(program, "vPosition");
            uColor = GLES20.glGetUniformLocation(program, "uColor");
            mMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
            GLES20.glEnableVertexAttribArray(vPosition);

            // 获取图形的顶点坐标
            FloatBuffer mVertexBuffer = getVertices();
            // 为画笔指定顶点位置数据(vPosition)
            GLES20.glVertexAttribPointer(vPosition, 3, GLES20.GL_FLOAT, false,
                    12, mVertexBuffer);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            // 设置绘图的窗口(可以理解成在画布上划出一块区域来画图)
            GLES20.glViewport(0, 0, width, height);
            Matrix.perspectiveM(mMVPMatrix, 0, 45, (float) width / height, 0.1f, 100f);
            Matrix.translateM(mMVPMatrix, 0, 0f, 0f, -2.5f);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            // 清屏
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            // 允许顶点位置数据数组
            GLES20.glEnableVertexAttribArray(vPosition);
            // 设置属性uColor(颜色 索引,R,G,B,A)
            GLES20.glUniform4f(uColor, 0.0f, 1.0f, 0.0f, 1.0f);
            // 绘制
//            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 5);
            // 禁用指向三角形的顶点数组
//            GLES20.glDisableVertexAttribArray(vPosition);

            GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mMVPMatrix, 0);

//            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 5);
            // 用 glDrawElements 来绘制，mVertexIndexBuffer 指定了顶点绘制顺序
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, VERTEX_INDEX.length,
                    GLES20.GL_UNSIGNED_SHORT, mVertexIndexBuffer);
        }

        /**
         * 创建shader程序的方法
         */
        private int createProgram(String vertexSource, String fragmentSource) {
            //加载顶点着色器
            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
            if (vertexShader == 0)
                return 0;
            // 加载片元着色器
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
            if (fragmentShader == 0)
                return 0;
            // 创建程序
            int program = GLES20.glCreateProgram();
            LogUtil.e("=========program" + program);
            if (program != 0) {
                // 创建成功.
                // 向程序中加入顶点着色器
                GLES20.glAttachShader(program, vertexShader);
                // 向程序中加入片元着色器
                GLES20.glAttachShader(program, fragmentShader);
                // 链接程序
                GLES20.glLinkProgram(program);
                // 使用某套shader程序
                GLES20.glUseProgram(program);
                // 存放链接成功program数量的数组
                int[] linkStatus = new int[1];
                // 获取program的链接情况
                GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
                if (linkStatus[0] != GLES20.GL_TRUE) {
                    // 链接失败
                    LogUtil.e("ES20_ERROR", "Could not link program: ");
                    LogUtil.e("ES20_ERROR", GLES20.glGetProgramInfoLog(program));
                    GLES20.glDeleteProgram(program);
                    program = 0;
                }
            }
            return program;

        }

        /**
         * 加载制定shader的方法
         *
         * @param shaderType shader的类型  GLES20.GL_VERTEX_SHADER   GLES20.GL_FRAGMENT_SHADER
         * @param sourceCode shader的脚本
         * @return shader索引
         */
        private int loadShader(int shaderType, String sourceCode) {
            // 创建一个新shader
            int shader = GLES20.glCreateShader(shaderType);
            // 若创建成功则加载shader
            if (shader != 0) {
                // 加载shader的源代码
                GLES20.glShaderSource(shader, sourceCode);
                // 编译shader
                GLES20.glCompileShader(shader);
                // 存放编译成功shader数量的数组
                int[] compiled = new int[1];
                // 获取Shader的编译情况
                GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
                LogUtil.e("=========compiled[0]" + compiled[0]);
                if (compiled[0] == 0) {
                    //若编译失败则显示错误日志并删除此shader
                    LogUtil.e("ES20_ERROR", "Could not compile shader " + shaderType + ":");
                    LogUtil.e("ES20_ERROR", GLES20.glGetShaderInfoLog(shader));
                    GLES20.glDeleteShader(shader);
                    shader = 0;
                }
            }
            return shader;
        }

        private FloatBuffer getVertices() {
            float vertices[] = {
                    -0.25f, 0.25f, 0.0f,   // top left
                    -0.25f, -0.25f, 0.0f,   // bottom left
                    0.25f, -0.25f, 0.0f,   // bottom right
                    0.25f, 0.25f, 0.0f
            };

            // 创建顶点坐标数据缓冲
            // vertices.length*4是因为一个float占四个字节
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
            // 设置字节顺序
            byteBuffer.order(ByteOrder.nativeOrder());
            // 转换为Float型缓冲
            FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
            // 向缓冲区中放入顶点坐标数据
            floatBuffer.put(vertices);
            // 设置缓冲区起始位置
            floatBuffer.position(0);
            return floatBuffer;
        }
    }


}
