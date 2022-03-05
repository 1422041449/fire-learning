import { getInfo, login, logout, register, listUserInfo, deleteUserInfo ,updateUserInfo} from '@/api/userinfo'
import { getToken, removeToken, setToken } from '@/utils/auth'
// import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    role: '',
    username: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLE: (state, role) => {
    state.role = role
  },
  SET_USERNAME: (state, username) => {
    state.username = username
  }
}

const actions = {
  login({ commit }, data) {
    return new Promise((resolve, reject) => {
      login(data).then(res => {
        const { data } = res
        commit('SET_TOKEN', data.token)
        setToken(data.token)
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo().then(res => {
        const { data } = res
        if (!data) {
          return reject('Verification failed, please Login again.')
        }
        console.log(res)
        const { name, avatar, role, username } = data
        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        commit('SET_ROLE', role)
        commit('SET_USERNAME', username)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        // resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  },

  register({}, data) {
    return new Promise((resolve, reject) => {
      register(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },

  listUserInfo({}, data) {
    return new Promise((resolve, reject) => {
      listUserInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },

  deleteUserInfo({}, data) {
    return new Promise((resolve, reject) => {
      deleteUserInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },

  updateUserInfo({}, data) {
    return new Promise((resolve, reject) => {
      updateUserInfo(data).then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  }

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

