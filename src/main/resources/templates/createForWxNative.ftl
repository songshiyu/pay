<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>支付</title>
    </head>
    <body>
        <div id="myQrcode"></div>
        <div id="orderId"></div>
        <div id="returnUrl"></div>

    <script src="https://cdn.bootcss.com/jquery/1.5.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
    <script>
        jQuery('#myQrcode').qrcode({
            text:"${codeUrl}"
        });

        $(function () {
            //定时器
            setInterval(function () {
              $.ajax({
                  url:'/pay/queryByOrderId',
                  data:{
                      'orderId':'${orderId}'
                  },
                  success:function (result) {
                      console.log(result)
                      if (result.platformStatus != null
                            && result.platformStatus === 'SUCCESS'){
                          location.href = $('#returnUrl').text
                      }
                  },
                  error:function (result) {
                      alert(result)
                  }
              })
            },2000)
        });
    </script>
    </body>
</html>