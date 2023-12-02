
<template>
  <div v-title :data-title="title">
    <el-container class="me-area">
      <el-main class="me-main">
        <el-steps class="me-log-box" :space="100"  direction="vertical">
                 <el-step title="后台管理系统" status="process"  icon="el-icon-time" description="步骤 1步骤 1步骤 1步骤 1步骤 1"></el-step>
                 <el-step title="界面优化" status="finish" icon="el-icon-time"></el-step>
                 <el-step title="添加Live2D看板娘" status="finish" icon="el-icon-time" description="这是一段很长很长很长的描述性文字"></el-step>
                   <el-step title="实现留言板接口功能" status="finish" icon="el-icon-time" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="实现文章列表归档" status="finish" icon="el-icon-time" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="实现分类文章列表和标签文章列表" status="finish" icon="el-icon-time" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="成功文章功能" status="finish" icon="el-icon-time" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="实现评论功能" status="finish" icon="el-icon-time" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="实现文章详情页" icon="el-icon-time" status="finish" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="实现用户注册功能" icon="el-icon-time" status="finish" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="实现用户登录功能" icon="el-icon-time" status="finish" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="完成文章归档接口" icon="el-icon-time" status="finish" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="完成最新文章页接口" icon="el-icon-time" status="finish" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="完成最热文章页接口" icon="el-icon-time" status="finish" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="完成最热标签页接口" icon="el-icon-time" status="finish" description="这是一段很长很长很长的描述性文字"></el-step>
                 <el-step title="完成文章列表接口" icon="el-icon-time" status="finish" description="2023/10/11"></el-step>
                 <el-step title="完成后端项目基础搭建" icon="el-icon-time" status="finish" description="2023/10/11"></el-step>
                 <el-step title="完成web前端项目搭建" status="finish" icon="el-icon-time" description="2023/10/10"></el-step>

              </el-steps>
        <pre>

      ```
      # 无

      ```
			 </pre>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: 'Log',
    data() {
      return {}
    },
    computed: {
      title (){
        return '日志 - 小宇博客'
      }
    }
  }
</script>

<style scoped>
  .el-container {
    width: 700px;
  }

  .me-main {
    overflow: hidden;
  }

  .me-log-box {
    margin-left: 10%;
    margin-top: 20px;
  }

</style>
