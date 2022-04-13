import {
  userCondition,
} from '@/api/userStageCondition'

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
  userCondition({}, data) {
    return new Promise((resolve, reject) => {
      userCondition(data).then(res => {
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

