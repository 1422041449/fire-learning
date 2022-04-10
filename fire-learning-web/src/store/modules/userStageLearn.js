import {listStageLearnInfo,listLearnCurrentTest,commitLearnTest } from '@/api/userStageLearn'

// const getDefaultState = () => {
//   return {
//     token: getToken(),
//     name: '',
//     avatar: '',
//     role: '',
//     username: ''
//   }
// }
//
// const state = getDefaultState()

const actions = {
  listStageLearnInfo({}, data) {
    return new Promise((resolve, reject) => {
      listStageLearnInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  listLearnCurrentTest({}, data) {
    return new Promise((resolve, reject) => {
      listLearnCurrentTest(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  commitLearnTest({}, data) {
    return new Promise((resolve, reject) => {
      commitLearnTest(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },



}

export default {
  namespaced: true,
  actions
}

