package com.mw.spike.exception;

import com.mw.spike.result.CodeMsg;

/**
 * 全局业务异常
 * @author WangCH
 * @create 2018-02-28 15:51
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}
