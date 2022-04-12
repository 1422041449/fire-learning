import request from '@/utils/request'

let dataObj = {
  version: '1.0',
  nonceStr: 'jlwnoncestr',
  timestamp: '2022-02-24',
  signType: 'md5',
  content: { object: {} },
  sign: 'dafdsf'
}

//查询当前用户阶段情况
export function listStageTestInfo(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/user/stage/test/list/stage/info',
    method: 'post',
    data: obj
  })
}

//查询当前用户当前阶段考试题目
export function listTestCurrentTest(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/user/stage/test/list/test/current/test',
    method: 'post',
    data: obj
  })
}
//交卷
export function commitTestAnswer(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/user/stage/test/commit/test/answer',
    method: 'post',
    data: obj
  })
}
//查询当前用户当前阶段考试详情
export function listTestCurrentTestDetail(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/user/stage/test/list/test/current/test/detail',
    method: 'post',
    data: obj
  })
}
