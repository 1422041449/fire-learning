import { addStageLearn, deleteStageLearn, editStageLearn, listStageLearn } from '@/api/stageLearn'

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
  addStageLearn({}, data) {
    return new Promise((resolve, reject) => {
      addStageLearn(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  listStageLearn({}, data) {
    return new Promise((resolve, reject) => {
      listStageLearn(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  editStageLearn({}, data) {
    return new Promise((resolve, reject) => {
      editStageLearn(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  deleteStageLearn({}, data) {
    return new Promise((resolve, reject) => {
      deleteStageLearn(data).then(res => {
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

