import request from '@/utils/request'

let dataObj = {
  version: '1.0',
  nonceStr: 'jlwnoncestr',
  timestamp: '2022-02-24',
  signType: 'md5',
  content: { object: {} },
  sign: 'dafdsf'
}

//查询用户阶段学习考试情况
export function userCondition(data) {
  let obj = JSON.parse(JSON.stringify(dataObj))
  if (data) {
    obj.content = data
  }
  return request({
    url: '/stage/info/user/condition',
    method: 'post',
    data: obj
  })
}

