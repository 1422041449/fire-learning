import request from '@/utils/request'

let dataObj = {
  version: '1.0',
  nonceStr: 'jlwnoncestr',
  timestamp: '2021-10-24',
  signType: 'md5',
  content: { object: {} },
  sign: 'dafdsf'
}

export function upload(data) {
  return request({
    url: '/file/upload',
    method: 'post',
    data: data,
    contentType: false,
    // headers: {
    //   'Content-Type': 'multipart/form-data'
    // }
  })
}
