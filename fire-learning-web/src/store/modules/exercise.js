import {
  addExercisesInfo,
  deleteExercisesInfo,
  editExercisesInfo,
  listExercisesInfo,
  listStageLearnExercises
} from '@/api/exercise'

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
  addExercisesInfo({}, data) {
    return new Promise((resolve, reject) => {
      addExercisesInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  listExercisesInfo({}, data) {
    return new Promise((resolve, reject) => {
      listExercisesInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  editExercisesInfo({}, data) {
    return new Promise((resolve, reject) => {
      editExercisesInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  deleteExercisesInfo({}, data) {
    return new Promise((resolve, reject) => {
      deleteExercisesInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  listStageLearnExercises({}, data) {
    return new Promise((resolve, reject) => {
      listStageLearnExercises(data).then(res => {
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

