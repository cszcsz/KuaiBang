package com.example.kuaibang.communication;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

public class DemoMessageHandler extends BmobIMMessageHandler {

    @Override
    public void onMessageReceive(final MessageEvent event) {
        super.onMessageReceive(event);
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        super.onOfflineReceive(event);
    }
}
