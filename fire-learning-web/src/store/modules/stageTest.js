import {
  addStageTest,
  deleteStageTest,
  editStageTest,
  listStageTest,
} from '@/api/stageTest'

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
  addStageTest({}, data) {
    return new Promise((resolve, reject) => {
      addStageTest(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  listStageTest({}, data) {
    return new Promise((resolve, reject) => {
      listStageTest(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  editStageTest({}, data) {
    return new Promise((resolve, reject) => {
      editStageTest(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  deleteStageTest({}, data) {
    return new Promise((resolve, reject) => {
      deleteStageTest(data).then(res => {
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

