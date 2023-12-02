<template>
  <el-card>
    <h1 class="me-web-title">本网站已运行</h1>
    <div class="me-web-time">
      <span id="timescope"></span>
    </div>
  </el-card>

</template>

<script>
  export default {
    name: 'RunTime',
    methods: {
      showTool(tool) {
        this.$message({
          duration: 0,
          showClose: true,
          dangerouslyUseHTMLString: true,
          message: '<strong>' + tool.message + '</strong>'
        });
      },
      inittime() {
        var tm=document.querySelector("#timescope");
        var offset = Math.floor((Date.now() - Date.parse(new Date(this.overtime))) / 1000);
        var secLeft = offset % 60;
        var minLeft = Math.floor(offset / 60) % 60;
        var hourLeft = Math.floor(offset / 60 / 60) % 24;
        var day = Math.floor(offset / 60 / 60 / 24);

        // 补0操作
        secLeft = (secLeft < 10 ? '0' : '') + secLeft;
        minLeft = (minLeft < 10 ? '0' : '') + minLeft;
        hourLeft = (hourLeft < 10 ? '0' : '') + hourLeft;

        // 拼接时间格式，写入timehtml
        tm.innerHTML = day + '天' + hourLeft + '时' + minLeft + '分' + secLeft + '秒';
      }

  },
   created () {
        this.overtime = '2023-11-20 14:00:00'
        setInterval(() => {
          this.inittime();; // 确保这里能够实时输出时间
        }, 1000);
    },
    }
</script>

<style scoped>
  .me-web-title {
    text-align: center;
    font-size: 30px;
    border-bottom: 1px solid #5FB878;
  }

  .me-web-time {
    padding: 8px 0;
  }

  .me-icon-job {
    padding-left: 16px;
  }

  .me-author-tool {
    text-align: center;
    padding-top: 10px;
  }

  .me-author-tool i {
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
  }

#timescope{
	background-image: -webkit-linear-gradient(left, #147B96, #E6D205 25%, #147B96 50%, #E6D205 75%, #147B96);
    -webkit-text-fill-color: transparent;
    -webkit-background-clip: text;
    -webkit-background-size: 200% 100%;
    -webkit-animation:  maskedAnimation 4s infinite linear;
    font-size: 20px;
    display: block;
    text-align: center;
}
 
@keyframes maskedAnimation {
	0% {
    background-position: 0 0;
	}
	100% {
	    background-position: -100% 0;
	}
}
</style>
 
