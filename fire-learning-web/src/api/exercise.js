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
export function addExercisesInfo(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/exercises/info/add/exercises/info',
    method: 'post',
    data: obj
  })
}

//查询题目
export function listExercisesInfo(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/exercises/info/list/exercises/info',
    method: 'post',
    data: obj
  })
}

//修改题目
export function editExercisesInfo(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/exercises/info/edit/exercises/info',
    method: 'post',
    data: obj
  })
}

//删除题目
export function deleteExercisesInfo(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/exercises/info/delete/exercises/info',
    method: 'post',
    data: obj
  })
}

//删除题目
export function listStageLearnExercises(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/exercises/info/list/stageLearn/exercises',
    method: 'post',
    data: obj
  })
}
