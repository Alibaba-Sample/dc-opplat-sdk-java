package com.aliyun.dc.opplat.sdk.api.internal.util.json;

public class StdoutStreamErrorListener extends BufferErrorListener {

    @Override
    public void end() {
        System.out.print(buffer.toString());
    }
}
