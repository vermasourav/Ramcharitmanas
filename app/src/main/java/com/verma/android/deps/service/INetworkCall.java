package com.verma.android.deps.service;

import com.verma.android.deps.models.request.SampleRequest;


public interface INetworkCall {
    void requestSample(final ICallBack.CallBack callBack, SampleRequest pRequest);

}