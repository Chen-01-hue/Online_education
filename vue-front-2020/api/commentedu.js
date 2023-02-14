import request from '@/utils/request'
export default {
  getPageList(page, limit, courseId) {
    return request({
      url: `/eduservice/edu-comment/getCommentsByCourseId/${page}/${limit}/${courseId}`,
      method: 'get',
    })
  },
  postComment(comment) {
    return request({
      url: `/eduservice/edu-comment/postComments`,
      method: 'post',
      data: comment
    })
  }
}
