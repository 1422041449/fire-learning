import { upload } from '@/api/upload'

const actions = {
  upload({}, data) {
    return new Promise((resolve, reject) => {
      upload(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  }

}

export default {
  namespaced: true,
  actions
}

