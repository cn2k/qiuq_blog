<template>
  <div>
    <el-form
  :model="messageForm"
  :rules="messageRules"
  ref="messageForm"
  label-width="100px"
  class="message-form"
  label-position="top"
>
  <el-row>
    <el-col :span="24">
      <el-form-item label="昵称" prop="nickname" style="width: 660px;">
        <el-input v-model="messageForm.nickname" class="custom-input" clearable placeholder="请输入昵称"></el-input>
      </el-form-item>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="24">
      <el-form-item label="邮箱" prop="email" style="width: 660px;">
        <el-input v-model="messageForm.email" class="custom-input" clearable placeholder="请输入邮箱"></el-input>
      </el-form-item>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="24">
      <el-form-item label="留言内容" prop="content">
        <el-input
          type="textarea"
          v-model="messageForm.content"
          clearable
          placeholder="请输入留言内容"
          style="width: 660px;"
        ></el-input>
      </el-form-item>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="24" :offset="15" style="text-align: end;">
      <el-button type="primary" @click="submitForm('messageForm')">提交留言</el-button>
    </el-col>
  </el-row>
</el-form>
    <el-card class="box-card" style="width: 1000px;">
      <div slot="header" class="clearfix">
        <span>留言汇总</span>
      </div>
      <el-table :data="messages" border style="width: 100%;">
        <el-table-column  prop="nickname" label="昵称"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="content" label="内容"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      messageForm: {
        nickname: '',
        email: '',
        content: ''
      },
      messageRules: {
        // 省略部分代码
      },
      messages: [] // 存储留言数据
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.addMessage();
        } else {
          return false;
        }
      });
    },
    addMessage() {
      axios.post("https://9420666.xyz"+'/api/visiters/plusadd', this.messageForm)
        .then(response => {
          if (response.status === 200) {
            this.$message({
              showClose: true,
              message: '留言添加成功',
              type: 'success'
            });
            window.location.reload(); // 刷新浏览器界面
          } else {
            this.$message.error('留言添加失败');
            window.location.reload(); // 刷新浏览器界面
          }
        })
        .catch(error => {
          console.error('留言添加失败', error);
        });
    },
    clearForm() {
      // 清空表单数据
      this.messageForm.nickname = '';
      this.messageForm.email = '';
      this.messageForm.content = '';
    },
    fetchData() {
      // 获取留言数据
      axios.get("https://9420666.xyz"+'/api/visiters')
        .then(response => {
          this.messages = response.data.data; // 将返回的留言数据赋值给messages
        })
        .catch(error => {
          console.error('获取留言数据失败', error);
        });
    }
  },
  mounted() {
    this.fetchData(); // 组件挂载时获取留言数据
  }
};
</script>

<style>
.message-form {
  max-width: 400px;
  margin: 0 200px;
}
.el-form-item {
    margin-bottom: 5px;
}
.el-input__inner {
    height: 50px;
}

element.style{
  height: 96px;
}
.text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }
</style>


