<template>
  <el-footer class="me-area">
    <div class="me-footer">
      <p>Designed by
        <strong>
          <router-link to="/" class="me-login-design-color">小宇博客</router-link>
          <br/>
          现在时间是:
          <span id="showTime"></span>
        </strong>
      </p>
    </div>
  </el-footer>

</template>

<script>

  export default {
    name: 'BaseFooter',
    data() {
      return {}
    },
    methods: {
    getTime() {
      // 获取日期类对象
      var date = new Date();
      // 利用get方法获取当前年、月、日、时、分、秒等信息，并拼接在一起。
      // 注意：这里的date.getDay()获取的是星期几，应该使用date.getDate()获取日期号数。
      var time = date.getFullYear() + "年" +
                (date.getMonth() + 1) + "月" +
                date.getDate() + "日" +
                date.getHours() + "时" +
                date.getMinutes() + "分" +
                date.getSeconds() + "秒";
      // 根据ID获取对应的span标签类对象
      var showTimeSpan = document.getElementById("showTime");
      // innerHTML
      // 语法：HTMLElementObject.innerHTML = text
      // 作用：改变同一行的一对HTML标签中的值
      showTimeSpan.innerHTML = time;
    }
  },
    created() {
      // setInterval，定时调用某个方法，第一个参数为方法名，第二个参数为毫秒数。
      setInterval(this.getTime, 1000);
    },
    mounted() {
    }
  }

  

</script>

<style>

  .el-footer {
    min-width: 100%;
    box-shadow: 0 -2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 1;
  }

  .me-footer {
    text-align: center;
    line-height: 50px;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;
    background:white;
  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

</style>
