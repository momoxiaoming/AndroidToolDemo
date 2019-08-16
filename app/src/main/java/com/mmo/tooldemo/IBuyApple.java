package com.mmo.tooldemo;

import org.qiyi.video.svg.IPCCallback;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/8/16
 *     desc  : new class
 * </pre>
 */
public interface IBuyApple
{
    int buyAppleInShop(int userId);
    void buyAppleOnNet(int userId, IPCCallback callback);
}
