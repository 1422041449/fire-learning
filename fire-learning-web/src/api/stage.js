import request from '@/utils/request'

let dataObj = {
  version: '1.0',
  nonceStr: 'jlwnoncestr',
  timestamp: '2022-02-24',
  signType: 'md5',
  content: { object: {} },
  sign: 'dafdsf'
}

//创建阶段
export function addStageInfo(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/info/add/stage/info',
    method: 'post',
    data: obj
  })
}

//查询阶段
export function listStageInfo(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/info/list/stage/info',
    method: 'post',
    data: obj
  })
}

//修改阶段
export function editStageInfo(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/info/edit/stage/info',
    method: 'post',
    data: obj
  })
}

//删除阶段
export function deleteStageInfo(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/info/delete/stage/info',
    method: 'post',
    data: obj
  })
}
