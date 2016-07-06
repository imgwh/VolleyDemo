package cn.nanguage.volleydemo;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by imgwh on 2016/6/16.
 */
public class MyApplication extends Application{

    public static final String TAG = MyApplication.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    /**
     * 发起多个请求，最好使用共享的请求队列，
     * 要避免每次布置一个请求都使用Volley.newRequestQueue来创建一个新的队列，
     * 因为我们不想看到内存泄漏或者其他的问题发生。
     * 为了达到这个目的，你首先需要创建一个使用了单例模式的类。
     * 这个类被作为静态的，全局的对象引用，然后由它处理RequestQueue对象。
     * 这样，整个应 用中就只有一个RequestQueue。 接下来，继承Application类，
     * 你需要告诉系统在应用启动的时候创建这个对象，这个过程甚至发生在第一个Activity创建之前。
     * 因为我们是在安卓系统中，所以我们要简单的修改一下常规单例模式的结构。
     * 这个类需要在 Application.onCreate方法中创建一个自己的实例
     * 而不是在常规的getInstance方法中判断它为null的时候产生一个实例。为此
     * 创建一个继承自Application类的 MarsWeather.java类，重写onCreate方法，
     * 初始化这个静态实例的RequestQueue对象，在这个单例类中，我们使用一个公共的synchronized方法
     * getInstance来构造类的对象。 在getInstance方法内部，返回一个mInstance变量。
     * onCreate是在应用启动的时候触发， 因此mInstance变量在getInstance方法被调用的第一时间就已经设置好了。
     * Singleton main method. Provides the global static instance of the helper class.
     * @return The MarsWeather instance.
     */
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    /**好了， 一个Application类的实例就创建好了，它甚至要比MainActivity都要先创建。
     *除了那些标准的操作，比如调用super.onCreate，onCreate还产生了一个RequestQueue的实例。
     *除此之外，我们还需要实现另外三个方法才能完成这个帮助类。
     *第一个方法是getRequestQueue，替代了Volley.newRequestQueue，
     *直接返回在onCreate中实例化了的mRequestQueue。
     *我们还需要一个添加请求到队列的方法add，以及负责取消请求的方法cancel。
     *这三个方法实现如下：
     * Provides the general Volley request queue.
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /**
     * Adds the request to the general queue.
     * @param req The object Request
     * @param <T> The type of the request result.
     */
    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels all the pending requests.
     */

    public void cancel() {
        mRequestQueue.cancelAll(TAG);
    }
}
