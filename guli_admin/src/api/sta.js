import request from '@/utils/request' //引入已经封装好的axios 和 拦截器

export default{
  //生成统计数据
  createStaByDay(day){
    return request({
      url:'/staservice/sta/registerCount/'+day,
      method: 'post'
    })
  },
  //图表显示
  getShowData(searchObj) {
    return request({
      url: `/staservice/sta/getShowData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
      method: 'get'
    })
  }

}
