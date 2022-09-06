; (() => {
  let source = null;

  // 用时间戳模拟登录用户
  const userId = new Date().getTime();

  if (window.EventSource) {

    // 建立连接
    source = new EventSource('http://localhost:9000/sse/sub/' + userId);
    console.log("连接用户=" + userId);
    /**
     * 连接一旦建立，就会触发open事件
     * 另一种写法：source.onopen = function (event) {}
     */
    source.addEventListener('open', function (e) {
      console.log("建立连接。。。");
    }, false);

    /**
     * 客户端收到服务器发来的数据
     * 另一种写法：source.onmessage = function (event) {}
     */
    source.addEventListener('message', function (e) {
      console.log(e.data);
    });


    /**
     * 如果发生通信错误（比如连接中断），就会触发error事件
     * 或者：
     * 另一种写法：source.onerror = function (event) {}
     */
    source.addEventListener('error', function (e) {
      if (e.readyState === EventSource.CLOSED) {
        console.log("连接关闭");
      } else {
        console.log(e);
      }
    }, false);

  } else {
    console.log("你的浏览器不支持SSE");
  }

  // 监听窗口关闭事件，主动去关闭sse连接，如果服务端设置永不过期，浏览器关闭后手动清理服务端数据
  window.onbeforeunload = function () {
    closeSse();
  };

  // 关闭Sse连接
  function closeSse() {
    source.close();
    const httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', 'http://localhost:9000/sse/disconnect/' + userId, true);
    httpRequest.send();
  }

})();
