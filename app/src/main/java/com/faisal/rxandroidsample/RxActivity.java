package com.faisal.rxandroidsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxActivity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        rxJust();
    }

    private void rxJust() {
        Observable<String> observable = Observable.just("Faisal", "Roman", "Imran");
        Observer<String> dreampanyObserver = getDreampanyObserver();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("r");
                    }
                })
                .subscribe(dreampanyObserver);
    }

    private Observer<String> getDreampanyObserver() {

        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                RxActivity.this.disposable = disposable;
            }

            @Override
            public void onNext(String s) {
                Log.d(RxActivity.class.getSimpleName(), "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
