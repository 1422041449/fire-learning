import request from '@/utils/request'

let dataObj = {
  version: '1.0',
  nonceStr: 'jlwnoncestr',
  timestamp: '2022-02-24',
  signType: 'md5',
  content: { object: {} },
  sign: 'dafdsf'
}

//创建题目
export function addStageLearn(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/learn/add/stageLearn',
    method: 'post',
    data: obj
  })
}

//查询题目
export function listStageLearn(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/learn/list/stageLearn',
    method: 'post',
    data: obj
  })
}

//修改题目
export function editStageLearn(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/learn/edit/stageLearn',
    method: 'post',
    data: obj
  })
}

//删除题目
export function deleteStageLearn(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/learn/delete/stageLearn',
    method: 'post',
    data: obj
  })
}

//获取阶段学习题中阶段考试题未选择的题目
export function listStageTestExercises(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/learn/list/stageTest/exercises',
    method: 'post',
    data: obj
  })
}
