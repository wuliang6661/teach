var subjectClass = query('.subject-class');
var activeRoom = query('.active-room');
var outSport = query('.out-sport');
var youShouldKnow = query('.you-should-know');
var showSubjectRoomOptions = query('.show-subject-room-options');
var productList = query('.product-list');
var qrShow = query('.to-vr-list');
var showWords = query('.show-words');
var healthSafe = query('.health-safe');

setTimeout(function() {
    let a = document.getElementsByTagName('body')[0];
    let b = document.getElementsByClassName('you-should-know')[0];

    console.log(b.offsetLeft)
    if (a.scrollLeft === 0) {
        a.scrollTo(b.offsetLeft, 400)
    }
}, 0)


//绑定点击事件
addEvent(subjectClass);
addEvent(activeRoom);
addEvent(outSport);
addEvent(youShouldKnow);
addEvent(showSubjectRoomOptions);
addEvent(productList);
addEvent(qrShow);
addEvent(showWords);
addEvent(healthSafe);

var image = new Image();

image.src = './images/map.gif'
image.onload = function() {
    console.log('图片加载完成')
}

registerhandler('goThemeClass', function(res) {
    goThemeClass(res)
});
registerhandler('goVR', function(res) {
    goThemeClass(res)
});
// 按钮跳转
function addEvent(dom) {
    var name = dom.className;
    var yourOs = queryDevice();
    dom.addEventListener('click', function() {
        // 1主题教室，2户外，3活动，4健康安全
        switch (name) {
            case 'health-safe': // 点击健康与安全
                if (yourOs === 'iphone') {
                    callhandler('goThemeClass', { val: 4, title: '健康与安全' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goThemeClass(4, '健康与安全');
                }
                break;
            case 'subject-class': // 点击主题教室
                // showSubjectRoomOptions.style.display = 'flex';
                if (yourOs === 'iphone') {
                    callhandler('goThemeClass', { val: 1, title: '主题教室' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goThemeClass(1, '主题教室');
                }
                break;
            case 'active-room': // 点击活动室
                if (yourOs === 'iphone') {
                    callhandler('goThemeClass', { val: 3, title: '活动室' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goThemeClass(3, '活动室');
                }
                break;
            case 'out-sport': //点击户外运动
                if (yourOs === 'iphone') {
                    callhandler('goThemeClass', { val: 2, title: '户外运动' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goThemeClass(2, '户外运动');
                }
                break;
            case 'you-should-know': // 点击前言
                showWords.style.display = 'flex';
                break;
            case 'show-subject-room-options': // 取消主题教室
                showSubjectRoomOptions.style.display = 'none';
                break;
            case 'product-list': // 点击产品列表
                if (yourOs === 'iphone') {
                    callhandler('goThemeClass', { val: 1, title: '主题教室' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goThemeClass(1, '主题教室');
                }
                break;
            case 'show-words': // 取消前言
                showWords.style.display = 'none';
                break;
            case 'to-vr-list': // 点击vr
                if (yourOs === 'iphone') {
                    callhandler('goVR', { val: 'VR全景教室', title: 'VR全景教室', url: 'https://shjz.yingjin.pro/api/upload/map/vrList.html' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goVR('VR全景教室', 'https://shjz.yingjin.pro/api/upload/map/vrList.html');
                }
                break;
        }
    })
}

function query(dom) {
    return document.querySelector(dom);
}

function queryDevice() {
    var os = navigator.userAgent.toLocaleLowerCase();
    var yourOs;
    if (/(iphone)/i.test(os)) {
        yourOs = 'iphone';
    }
    if (/(android)/i.test(os)) {
        yourOs = 'android';
    }
    return yourOs;
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