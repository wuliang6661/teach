package cn.teach.equip.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mob.MobSDK;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.teach.equip.R;
import cn.teach.equip.bean.pojo.ShareBO;

public class ShareUtils {


    /**
     * 分享内容
     *
     * @param platform
     */
    private void showShare(String platform) {
        final OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(WechatMoments.NAME);
            oks.setPlatform(Wechat.NAME);
        }
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        //启动分享
        oks.show(MobSDK.getContext());
    }


    /**
     * 分享图片
     */
    public static void shareImage(int flag, Bitmap bitmap) {
        Platform.ShareParams oks = new Platform.ShareParams();
        oks.setShareType(Platform.SHARE_IMAGE);
        oks.setImageData(bitmap);
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        Platform pla;
        if (flag == 0) {   //微信好友
            pla = ShareSDK.getPlatform(Wechat.NAME);
        } else {
            pla = ShareSDK.getPlatform(WechatMoments.NAME);
        }
        // 执行图文分享
        pla.share(oks);
    }


    /**
     * 图文分享
     */
    public static void shareTuWen(int flag) {
        Platform.ShareParams oks = new Platform.ShareParams();
        oks.setShareType(Platform.SHARE_WEBPAGE);
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        //启动分享
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        Platform pla;
        if (flag == 0) {   //微信好友
            pla = ShareSDK.getPlatform(Wechat.NAME);
        } else {
            pla = ShareSDK.getPlatform(WechatMoments.NAME);
        }
        // 执行图文分享
        pla.share(oks);
    }


    /**
     * 分享应用
     */
    public static void shareApp(int flag, ShareBO shareBO) {
        Platform.ShareParams oks = new Platform.ShareParams();
        oks.setShareType(Platform.SHARE_WEBPAGE);
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(shareBO.getTitle());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareBO.getContent());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setImageData(BitmapFactory.decodeResource(AppManager.getAppManager().curremtActivity().getResources(),
                R.mipmap.ic_launcher));
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareBO.getUrl());
        //启动分享
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        Platform pla;
        if (flag == 0) {   //微信好友
            pla = ShareSDK.getPlatform(Wechat.NAME);
        } else {
            pla = ShareSDK.getPlatform(WechatMoments.NAME);
        }
        // 执行应用分享
        pla.share(oks);
    }


    /**
     * 分享网页
     */
    public static void shareHtml(String title, String content, String url, int flag) {
        Platform.ShareParams oks = new Platform.ShareParams();
        oks.setShareType(Platform.SHARE_WEBPAGE);
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setImageData(BitmapFactory.decodeResource(AppManager.getAppManager().curremtActivity().getResources(),
                R.mipmap.ic_launcher));
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        //启动分享
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        Platform pla;
        if (flag == 0) {   //微信好友
            pla = ShareSDK.getPlatform(Wechat.NAME);
        } else {
            pla = ShareSDK.getPlatform(WechatMoments.NAME);
        }
        // 执行应用分享
        pla.share(oks);
    }


    /**
     * 分享文件
     */
    public static void shareFile(String title, String content, String filePath, int flag) {
        Platform.ShareParams oks = new Platform.ShareParams();
        oks.setShareType(Platform.SHARE_FILE);
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setImageData(BitmapFactory.decodeResource(AppManager.getAppManager().curremtActivity().getResources(),
                R.mipmap.ic_launcher));
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setFilePath(filePath);
        //启动分享
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        Platform pla;
        if (flag == 0) {   //微信好友
            pla = ShareSDK.getPlatform(Wechat.NAME);
        } else {
            pla = ShareSDK.getPlatform(WechatMoments.NAME);
        }
        // 执行应用分享
        pla.share(oks);
    }


    /**
     * 分享视频
     */
    public static void shareVideo(String title, String content, String videoUrl, int flag) {
        Platform.ShareParams oks = new Platform.ShareParams();
        oks.setShareType(Platform.SHARE_VIDEO);
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setImageData(BitmapFactory.decodeResource(AppManager.getAppManager().curremtActivity().getResources(),
                R.mipmap.ic_launcher));
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(videoUrl);
        //启动分享
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        Platform pla;
        if (flag == 0) {   //微信好友
            pla = ShareSDK.getPlatform(Wechat.NAME);
        } else {
            pla = ShareSDK.getPlatform(WechatMoments.NAME);
        }
        // 执行应用分享
        pla.share(oks);
    }

}
