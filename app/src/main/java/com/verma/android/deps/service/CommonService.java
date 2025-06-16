package com.verma.android.deps.service;

import static io.reactivex.rxjava3.internal.operators.flowable.FlowableFromIterable.subscribe;

import com.verma.android.deps.models.request.SampleRequest;
import com.verma.android.deps.models.response.BaseResponse;
import com.verma.android.template.App;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class CommonService implements INetworkCall {
    private static final String TAG = "CommonService";
    private final NetworkService networkService;
    private SharedPreferencesService sharedPreferencesService;

    public CommonService(NetworkService networkService, SharedPreferencesService sharedPreferencesService) {
        this.networkService = networkService;
        this.sharedPreferencesService = sharedPreferencesService;
    }

    public void requestSample(final ICallBack.CallBack callback, SampleRequest sampleRequest) {
        Timber.tag(TAG).d("requestAuthenticate: %s", sampleRequest.toString());

        networkService.requestSample(App.getInstance().API_PATH, sampleRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(Observable::error)
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                            //Do Nothing
                    }

                    @Override
                    public void onNext(@NonNull BaseResponse serverResponse) {
                        if (null == serverResponse.getStatus()) {
                            callback.onError(new NetworkError(new Throwable(serverResponse.getMessage())));
                        } else {
                            if (200 == serverResponse.getStatus()) {
                                callback.onSuccess(serverResponse);
                            } else {
                                callback.onError(new NetworkError(new Throwable(serverResponse.getError().getErrorMessage())));

                            }
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onComplete() {
                        //DO Nothing
                    }
                })


                ;
    }
}
