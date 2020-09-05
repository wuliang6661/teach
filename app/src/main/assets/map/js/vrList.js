var list1 = query('.list1');
var list2 = query('.list2');
var list3 = query('.list3');
var list4 = query('.list4');
var list5 = query('.list5');
var list6 = query('.list6');
var list7 = query('.list7');

//绑定点击事件
addEvent(list1);
addEvent(list2);
addEvent(list3);
addEvent(list4);
addEvent(list5);
addEvent(list6);
addEvent(list7);

registerhandler('goVR', function(res) {
    goVR(res)
});
// 按钮跳转
function addEvent(dom) {
    var name = dom.classList[1];
    var yourOs = queryDevice();
    dom.addEventListener('click', function() {
        // 1主题教室，2户外，3活动，4健康安全
        switch (name) {
            case 'list1':
                if (yourOs === 'iphone') {
                    callhandler('goVR', { val: '创新互动空间', title: '创新互动空间', url: 'https://www.kuleiman.com/122283/index.html' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goVR('创新互动空间', 'https://www.kuleiman.com/122283/index.html');
                }
                break;
            case 'list2':
                if (yourOs === 'iphone') {
                    callhandler('goVR', { val: '幼儿个别化学习空间', title: '幼儿个别化学习空间', url: 'https://www.kuleiman.com/126837/index.html' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goVR('幼儿个别化学习空间', 'https://www.kuleiman.com/126837/index.html');
                }
                break;
            case 'list3':
                if (yourOs === 'iphone') {
                    callhandler('goVR', { val: '幼儿多功能活动室', title: '幼儿多功能活动室', url: 'https://www.kuleiman.com/126838/index.html' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goVR('幼儿多功能活动室', 'https://www.kuleiman.com/126838/index.html');
                }
                break;
            case 'list4':
                if (yourOs === 'iphone') {
                    callhandler('goVR', { val: '幼儿图书阅览室', title: '幼儿图书阅览室', url: 'https://www.kuleiman.com/126839/index.html' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goVR('幼儿图书阅览室', 'https://www.kuleiman.com/126839/index.html');
                }
                break;
            case 'list5':
                if (yourOs === 'iphone') {
                    callhandler('goVR', { val: '幼儿智能建构室', title: '幼儿智能建构室', url: 'https://www.kuleiman.com/126840/index.html' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goVR('幼儿智能建构室', 'https://www.kuleiman.com/126840/index.html');
                }
                break;
            case 'list6':
                if (yourOs === 'iphone') {
                    callhandler('goVR', { val: '幼儿传统文化益智体验', title: '幼儿传统文化益智体验', url: 'https://www.kuleiman.com/126841/index.html' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goVR('幼儿传统文化益智体验', 'https://www.kuleiman.com/126841/index.html');
                }
                break;
            case 'list7':
                if (yourOs === 'iphone') {
                    callhandler('goVR', { val: '幼儿体适能运动空间', title: '幼儿体适能运动空间', url: 'https://www.kuleiman.com/126842/index.html' }, () => {
                        console.log('回调');
                    });
                }
                if (yourOs === 'android') {
                    window.app.goVR('幼儿体适能运动空间', 'https://www.kuleiman.com/126842/index.html');
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