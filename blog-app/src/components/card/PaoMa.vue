<template>
    <div>
      <el-carousel indicator-position="outside" height="400px" >
        <el-carousel-item v-for="item in imges" :key="item">
          <img :src="item.url" width="100%">
        </el-carousel-item>
      </el-carousel>
    </div>
</template>
 
<script>
    export default {
        name: "zouma",
      data () {
        return {
          imges: [
            {url: "/static/zoumaimges/1.jpg"},
            {url: "/static/zoumaimges/2.jpg"},
            {url: "/static/zoumaimges/3.jpg"},
            {url: "/static/zoumaimges/4.jpg"},
            {url: "/static/zoumaimges/5.jpg"},
          ]
        }
      }
    }
</script>
 
<style scoped>
 
</style>