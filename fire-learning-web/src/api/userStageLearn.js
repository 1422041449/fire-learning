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
export function listStageLearnInfo(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/user/stage/learn/list/stage/info',
    method: 'post',
    data: obj
  })
}

//查询当前用户当前阶段学习题目
export function listLearnCurrentTest(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/user/stage/learn/list/learn/current/test',
    method: 'post',
    data: obj
  })
}

//提交阶段学习题答案
export function commitLearnTest(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/user/stage/learn/commit/learn/test',
    method: 'post',
    data: obj
  })
}

//校验阶段学习是否完成;
export function checkLearnProgress(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/user/stage/learn/check/learn/progress',
    method: 'post',
    data: obj
  })
}
