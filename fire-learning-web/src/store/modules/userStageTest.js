import {listStageTestInfo,listTestCurrentTest,commitTestAnswer } from '@/api/userStageTest'

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
  listStageTestInfo({}, data) {
    return new Promise((resolve, reject) => {
      listStageTestInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  listTestCurrentTest({}, data) {
    return new Promise((resolve, reject) => {
      listTestCurrentTest(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },

  commitTestAnswer({}, data) {
    return new Promise((resolve, reject) => {
      commitTestAnswer(data).then(res => {
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

