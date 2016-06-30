package com.dong.listviewrefershandmvp.Utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/30.
 */
public class RxUtils {
    /**
     * {@link rx.Observable.Transformer} that transforms the source observable to subscribe in the
     * io thread and observe on the Android's UI thread.
     */
    private static Observable.Transformer ioToMainThreadSchedulerTransformer;


    static {
        ioToMainThreadSchedulerTransformer = createIOToMainThreadScheduler();
    }


    /**
     * Get {@link rx.Observable.Transformer} that transforms the source observable to subscribe in
     * the io thread and observe on the Android's UI thread.
     * <p>
     * Because it doesn't interact with the emitted items it's safe ignore the unchecked casts.
     *
     * @return {@link rx.Observable.Transformer}
     */
    @SuppressWarnings("unchecked")
    private static <T> Observable.Transformer<T, T> createIOToMainThreadScheduler() {
       /* <T> Transformer<T, T> applySchedulers() {
            return new Transformer<T, T>() {
                @Override
                public Observable<T> call(Observable<T> observable) {
                    return observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        }*/
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(
                        Schedulers.computation()) // TODO: remove when https://github.com/square/okhttp/issues/1592 is fixed
                .observeOn(AndroidSchedulers.mainThread());
    }


    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applyIOToMainThreadSchedulers() {
        return ioToMainThreadSchedulerTransformer;
    }
}
