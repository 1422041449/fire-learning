import request from '@/utils/request'

let dataObj = {
  version: '1.0',
  nonceStr: 'jlwnoncestr',
  timestamp: '2022-02-24',
  signType: 'md5',
  content: { object: {} },
  sign: 'dafdsf'
}

//创建阶段考试题
export function addStageTest(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/test/add',
    method: 'post',
    data: obj
  })
}

//查询阶段考试题
export function listStageTest(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/test/list',
    method: 'post',
    data: obj
  })
}

//修改阶段考试题
export function editStageTest(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/test/edit',
    method: 'post',
    data: obj
  })
}

//删除阶段考试题
export function deleteStageTest(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/test/delete',
    method: 'post',
    data: obj
  })
}

