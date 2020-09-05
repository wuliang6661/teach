$(document).ready(function() {
    // 进来后写页面用来默认的详情数据（待删除）
    var detail = {
        "code": "0",
        "data": {
            "title": "标题",
            "desc": "描述",
            "addTime": "2020-10-10 12:00:00",
            "url": "https://xx.com",
            "smallImgUrl": "https://719a473d15d5470fad023e853d912e4f.jpg",
            "bigImgUrl": "https://719a473d15d5470fad023e853d912e4f.jpg",
            "bannerList": [
                "https://www.betterman.top/uploads/img-6aaeb4b8gy1fwc78of1mag206o06o0w4.gif",
                "http://www.betterman.top/uploads/map.gif",
                "http://www.betterman.top/uploads/map.gif"
            ],
            "content": "",
            "isCollect": false,
            "code": "23456"
        },
        "msg": "成功"
    }

    //title 标题
    // $('.product-name').html(detail.data.title);
    // $('.product-describe').html(detail.data.desc);

    // content是否为空
    // if (detail.data.content) {
    $('.all-image').show();
    // } else {
    // $('.default-text').show();
    // }


    var dom = document.getElementsByClassName('to-top')[0];
    var share = document.getElementsByClassName('share')[0];

    dom.onclick = function() {
            document.documentElement.scrollTop = 0;
        }
        //点击分享
    share.onclick = function() {
        console.log('点击分享')
        callhandler('share', {}, () => {
            console.log('回调');
        });
    }

    window.onscroll = function() {
        if (document.documentElement.scrollTop > 100) {
            dom.style.display = 'block';
        } else {
            dom.style.display = 'none';
        }
    }

    // 与ios交互
    function setupWebViewJavascriptBridge(callback) {
        if (window.WebViewJavascriptBridge) {
            return callback(window.WebViewJavascriptBridge)
        }
        if (window.WVJBCallbacks) {
            return window.WVJBCallbacks.push(callback)
        }
        window.WVJBCallbacks = [callback]
        let WVJBIframe = document.createElement('iframe')
        WVJBIframe.style.display = 'none'
        WVJBIframe.src = 'https://__bridge_loaded__'
        document.documentElement.appendChild(WVJBIframe)
        setTimeout(() => {
            document.documentElement.removeChild(WVJBIframe)
        }, 0)
    }

    function registerhandler(name, callback) {
        setupWebViewJavascriptBridge(function(bridge) {
            bridge.registerHandler(name, function(data, responseCallback) {
                callback(data, responseCallback)
            })
        })
    }

    function callhandler(name, data, callback) {
        setupWebViewJavascriptBridge(function(bridge) {
            bridge.callHandler(name, data, callback)
        })
    }
})