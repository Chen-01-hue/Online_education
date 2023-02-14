import request from '@/utils/request'
export default {
  getPlayAuth(vid) {
    return request({
      url: `/edu_vod/video/getPlayAuth/${vid}`,
      method: 'get'
    })
  }

}
