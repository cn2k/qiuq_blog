<template>
  <div id="home">
    <el-container>
    	
    	<base-header :activeIndex="activeIndex"></base-header>
		  
		  <router-view class="me-container"/>
		  
			<base-footer ></base-footer>
		  
		</el-container>
		
  </div>
  
</template>
<style>

</style>

<script>
import BaseFooter from '@/components/BaseFooter'
import BaseHeader from '@/views/BaseHeader'

export default {
  name: 'Home',
  data (){
  	return {
  			activeIndex: '/',
  			footerShow:true
  	}
  },
  components:{
  	'base-header':BaseHeader,
  	'base-footer':BaseFooter
  },
  beforeRouteEnter (to, from, next){
  	 next(vm => {
    	vm.activeIndex = to.path
  	})
  },
  beforeRouteUpdate (to, from, next) {
	  if(to.path == '/'){
	  	this.footerShow = true
	  }else{
	  	this.footerShow = false
	  }
	  this.activeIndex = to.path
	  next()
	}
}
</script>

<style>

.me-container{
  margin: 100px auto 140px;
}
.background-me{
	background-image: url("/static/background/640.jpg");
    background-size: cover;
    background-repeat: no-repeat;
    background-attachment: fixed;
    width: 100%;
    height: 100%;
}	
</style>
