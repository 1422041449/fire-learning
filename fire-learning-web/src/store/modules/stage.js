import { addStageInfo, deleteStageInfo, editStageInfo, listStageInfo } from '@/api/stage'

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
  addStageInfo({}, data) {
    return new Promise((resolve, reject) => {
      addStageInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  listStageInfo({}, data) {
    return new Promise((resolve, reject) => {
      listStageInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  editStageInfo({}, data) {
    return new Promise((resolve, reject) => {
      editStageInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  deleteStageInfo({}, data) {
    return new Promise((resolve, reject) => {
      deleteStageInfo(data).then(res => {
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

