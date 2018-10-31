package com.company.project.android.mvp.rx;

import com.company.project.android.enitiy.ResponseBean;

import io.reactivex.functions.Function;

/**
 * @Author: Administrator
 * @Time: 2018 2018/10/30 15:38
 * @description: （添加一句描述）
 */
public class MapFunction <F> implements Function<ResponseBean<F>, F> {
    @Override
    public F apply(ResponseBean<F> fResponseBean) throws Exception {
        int code = fResponseBean.getCode();
        if (code == ResponseBean.CODE_SUCCESS || code == ResponseBean.CODE_SUCCESS0) {
            return fResponseBean.getData();
        } else {
            throw new ApiException(fResponseBean.getMsg(), fResponseBean.getCode());
        }
    }

  /*  @Override
    public F apply(ResponseBean<F> fResponseBean) throws Exception {
        int code = fResponseBean.getCode();
        if (code == ResponseBean.CODE_SUCCESS || code == ResponseBean.CODE_SUCCESS0) {
            return fResponseBean.getData();
        } else {
            throw new ApiException(fResponseBean.getMsg(), fResponseBean.getCode());
        }
    }*/


}

